package glsx.com.cn.task.service.impl;


import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import glsx.com.cn.task.enums.DispatchOrderStatusEnum;
import glsx.com.cn.task.enums.MerchantOrderKafkaMessageTypeEnum;
import glsx.com.cn.task.enums.MerchantOrderSignStatusEnum;
import glsx.com.cn.task.kafka.MerchantOrderKafkaMessage;
import glsx.com.cn.task.kafka.SendKafkaService;
import glsx.com.cn.task.kafka.SignMerchantOrder;
import glsx.com.cn.task.mapper.*;
import glsx.com.cn.task.model.BsMerchantOrderVehicle;
import glsx.com.cn.task.model.EcMerchantOrder;
import glsx.com.cn.task.model.Logistics;
import glsx.com.cn.task.model.MerchantOrder;
import glsx.com.cn.task.model.MerchantOrderDetail;
import glsx.com.cn.task.model.OrderInfo;
import glsx.com.cn.task.service.AcceptMerchantOrderService;
import glsx.com.cn.task.util.TaskUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AcceptMerchantOrderServiceImpl implements AcceptMerchantOrderService {
	@Autowired
	private SendKafkaService sendKafkaService;
	@Autowired
	private EcMerchantOrderMapper ecMerchantOrderMapper; 
	@Autowired
	private BsMerchantOrderVehicleMapper merchantOrderVehicleMapper; 
    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private LogisticsMapper logisticsMapper;
    @Autowired
    private MerchantOrderMapper merchantOrderMapper;
    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;
    @Autowired
    private SalesManagerMapper salesManagerMapper;
    
    private static final Integer signTimestampStep = 86400000*2;
   // private static final Integer signTimestampStep = 300000;

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptMerchantOrderServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public void acceptMerchantOrder() throws RpcServiceException{
    	try
    	{
    		LOGGER.info("AcceptMerchantOrderServiceImpl::acceptMerchantOrder start work!");
    		List<MerchantOrder> listMerchantOrder = null;
        	List<String> listMerchantOrderCodes = null;
        	List<String> listDispatchOrderCodes = new ArrayList<>();
        	Map<String,MerchantOrderDetail> mapMerchantOrderDetails = null;
        	Map<String,EcMerchantOrder> mapEcMerchantOrder = null;
        	Map<String,List<String>> mapMerchantOrder2DispatchOrder = null;
        	Map<String,OrderInfo> mapOrderInfos = null;
        	Map<String,List<Logistics>> mapDispatch2Logistics = null;	
        	//获取所有
        	Integer startPage = 0;
        	Integer pageSize = 1000;
        	while(true){
        		listDispatchOrderCodes.clear();
        		listMerchantOrder = listMerchantOrderForSign(startPage,pageSize);
        		
        		if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
        			break;
        		}
        		listMerchantOrderCodes = listMerchantOrder.stream().map(t->t.getOrderNumber()).distinct().collect(Collectors.toList());
        		mapMerchantOrderDetails = listMerchantOrderDetailByListMerchantOrders(listMerchantOrderCodes);
        		mapEcMerchantOrder = listEcMerchantOrderByListMerchantOrders(listMerchantOrderCodes);
        		mapMerchantOrder2DispatchOrder = listDispatchOrderCodesByListMerchantOrders(listMerchantOrderCodes,listDispatchOrderCodes);
        		if(null == mapMerchantOrder2DispatchOrder || mapMerchantOrder2DispatchOrder.isEmpty()){
        			if(listMerchantOrder.size() < pageSize){
        				break;
        			}
        			startPage = (startPage+1)*pageSize;
        			continue;
        		}
        		mapOrderInfos = listOrderInfoByListDispatchOrderCodes(listDispatchOrderCodes);
        		if(null == mapOrderInfos || mapOrderInfos.isEmpty()){
        			if(listMerchantOrder.size() < pageSize){
        				break;
        			}
        			startPage = (startPage+1)*pageSize;
        			continue;
        		}
        		mapDispatch2Logistics = listLogisticsGroupByDispatchOrderCodes(listDispatchOrderCodes);
        		if(null == mapDispatch2Logistics || mapDispatch2Logistics.isEmpty()){
        			if(listMerchantOrder.size() < pageSize){
        				break;
        			}
        			startPage = (startPage+1)*pageSize;
        			continue;
        		}
        		setAcceptLogistics(mapDispatch2Logistics,mapOrderInfos);
        		setMerchantOrderSignStatus(listMerchantOrder,mapMerchantOrderDetails,mapEcMerchantOrder,mapMerchantOrder2DispatchOrder,mapOrderInfos,mapDispatch2Logistics);
        		if(listMerchantOrder.size() < pageSize){
    				break;
    			}
        		startPage = (startPage+1)*pageSize;	
        	}
        	LOGGER.info("AcceptMerchantOrderServiceImpl::acceptMerchantOrder finish work!");
    	}catch (Exception e) {
    		LOGGER.error(e.getMessage(), e);
			throw new RpcServiceException(e.getMessage());
		}
    	
    }
    
    public void setMerchantOrderSignStatus(List<MerchantOrder> listMerchantOrder,
    		Map<String,MerchantOrderDetail> mapMerchantOrderDetails,
    		Map<String,EcMerchantOrder> mapEcMerchantOrder,
    		Map<String,List<String>> mapMerchantOrder2DispatchOrder,
    		Map<String,OrderInfo> mapOrderInfos,
    		Map<String,List<Logistics>> mapDispatch2Logistics){
    	if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
    		return;
    	}
    	OrderInfo orderInfo = null;
    	List<String> listDispatchOrderCodes = null;
    	List<Logistics> listSubLogistics = null;
    	List<Logistics> listLogistics = new ArrayList<>();
    	MerchantOrderDetail merchantOrderDetail = null;
    	EcMerchantOrder ecMerchantOrder = null;
    	MerchantOrder updateMerchantOrder = new MerchantOrder();
    	MerchantOrderDetail updateMerchantOrderDetail = new MerchantOrderDetail();
    	EcMerchantOrder updateEcMerchantOrder = new EcMerchantOrder();
    	
    	for(MerchantOrder merchantOrder:listMerchantOrder){
    		int acceptQulities = 0;
    		MerchantOrderSignStatusEnum caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND;
    		listDispatchOrderCodes = mapMerchantOrder2DispatchOrder.get(merchantOrder.getOrderNumber());
    		if(null != listDispatchOrderCodes && !listDispatchOrderCodes.isEmpty()){
    			listLogistics.clear();
    			for(String dispatchOrderCode:listDispatchOrderCodes){
        			listSubLogistics = mapDispatch2Logistics.get(dispatchOrderCode);
        			if(listSubLogistics == null || listSubLogistics.isEmpty()){
        				continue;
        			}
        			listLogistics.addAll(listSubLogistics);
        		}
    			if(!listLogistics.isEmpty()){
    				boolean beFinish = true;
    				for(Logistics logistics:listLogistics){
    	    			if(logistics.getAccept().equals("Y")){
    	    				acceptQulities += (logistics.getShipmentsQuantity()==null?0:logistics.getShipmentsQuantity());
    	    			}else{
    	    				beFinish = false;
    	    			}
    	    		}
    				if(acceptQulities == 0){
    	    			caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN;
    	    		}else{
    	    			caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_PTSIGN;
    	    		}
    				
    				for(String dispatchOrderCode:listDispatchOrderCodes){
    	    			orderInfo = mapOrderInfos.get(dispatchOrderCode);
    	    			if(null == orderInfo){
    	    				continue;
    	    			}
    	    			if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_UF.getValue())){
    	    				beFinish = false;
    	    				break;
    	    			}
    	    		}
    				if(beFinish){
    					caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_ALSIGN;
    				}
        		}else{
        			caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND;
        		}	
    		}else{
    			caculateStatus = MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND;
    		}
    		
    		updateMerchantOrder.setId(merchantOrder.getId());
    		updateMerchantOrder.setUpdatedBy(merchantOrder.getUpdatedBy());
    		updateMerchantOrder.setUpdatedDate(TaskUtils.getNowDate());
    		updateMerchantOrder.setSignStatus(caculateStatus.getCode());
    		merchantOrderMapper.updateByPrimaryKeySelective(updateMerchantOrder);
    		merchantOrderDetail = mapMerchantOrderDetails.get(merchantOrder.getOrderNumber());
    		if(null != merchantOrderDetail){
    			updateMerchantOrderDetail.setId(merchantOrderDetail.getId());
    			updateMerchantOrderDetail.setAcceptQuantity(acceptQulities);
    			updateMerchantOrderDetail.setUpdatedBy(merchantOrder.getUpdatedBy());
    			updateMerchantOrderDetail.setUpdatedDate(TaskUtils.getNowDate());
    			merchantOrderDetailMapper.updateByPrimaryKeySelective(updateMerchantOrderDetail);
    		}
    		ecMerchantOrder = mapEcMerchantOrder.get(merchantOrder.getOrderNumber());
    		if(null != ecMerchantOrder){
    			updateEcMerchantOrder.setId(ecMerchantOrder.getId());
    			updateEcMerchantOrder.setSignStatus(caculateStatus.getName());
    			updateEcMerchantOrder.setSignQuantity(acceptQulities);
    			ecMerchantOrderMapper.updateByPrimaryKeySelective(updateEcMerchantOrder);
    		}	
    	}
    }
    
    public List<MerchantOrder> listMerchantOrderForSign(Integer startPage,Integer pageSize){
    	Example example = new Example(MerchantOrder.class);
    	example.createCriteria().andNotEqualTo("signStatus", MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_ALSIGN.getCode())
    							.andNotEqualTo("status", MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())
    							.andGreaterThan("id", 920);
    														
    	example.orderBy("id").asc();
    	RowBounds rowBounds = new RowBounds(startPage,pageSize);
    	return merchantOrderMapper.selectByExampleAndRowBounds(example, rowBounds);
    }
    
    public Map<String,MerchantOrderDetail> listMerchantOrderDetailByListMerchantOrders(List<String> listMerchantOrders){
    	Map<String,MerchantOrderDetail> mapResult = new HashMap<>();
    	Example example = new Example(MerchantOrderDetail.class);
    	example.createCriteria().andIn("merchantOrderNumber", listMerchantOrders);
    	List<MerchantOrderDetail> listDetail =  merchantOrderDetailMapper.selectByExample(example);
    	if(null == listDetail || listDetail.isEmpty()){
    		return mapResult;
    	}
    	for(MerchantOrderDetail detail:listDetail){
    		mapResult.put(detail.getMerchantOrderNumber(), detail);
    	}
    	return mapResult;
    }
    
    public Map<String,EcMerchantOrder> listEcMerchantOrderByListMerchantOrders(List<String> listMerchantOrders){
    	Map<String,EcMerchantOrder> mapResult = new HashMap<>();
    	Example example = new Example(EcMerchantOrder.class);
    	example.createCriteria().andIn("orderNumber", listMerchantOrders);
    	List<EcMerchantOrder> listEcMerchantOrder = ecMerchantOrderMapper.selectByExample(example);
    	if(null == listEcMerchantOrder || listEcMerchantOrder.isEmpty()){
    		return mapResult;
    	}
    	for(EcMerchantOrder ecMerchantOrder:listEcMerchantOrder){
    		mapResult.put(ecMerchantOrder.getOrderNumber(), ecMerchantOrder);
    	}
    	return mapResult;
    }
    
    public Map<String,List<String>> listDispatchOrderCodesByListMerchantOrders(List<String> listMerchantOrders,List<String> listDispatchOrderCodes){
    	Map<String,List<String>> mapResult = new HashMap<>();
    	Example example = new Example(BsMerchantOrderVehicle.class);
    	example.createCriteria().andIn("merchantOrder", listMerchantOrders)
    							.andEqualTo("deletedFlag","N");
    	List<BsMerchantOrderVehicle> listOrderVehicles = merchantOrderVehicleMapper.selectByExample(example);
    	if(null == listOrderVehicles || listOrderVehicles.isEmpty()){
    		return mapResult;
    	}
    	List<String> listDispatchOrders = null;
    	for(BsMerchantOrderVehicle vehicle:listOrderVehicles){
    		if(StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
    			continue;
    		}
    		listDispatchOrders = mapResult.get(vehicle.getMerchantOrder());
    		if(null == listDispatchOrders){
    			listDispatchOrders = new ArrayList<>();
    			mapResult.put(vehicle.getMerchantOrder(), listDispatchOrders);
    		}
    		listDispatchOrders.add(vehicle.getDispatchOrderCode());
    		listDispatchOrderCodes.add(vehicle.getDispatchOrderCode());
    	}
    	return mapResult;
    }
    
    public Map<String,OrderInfo> listOrderInfoByListDispatchOrderCodes(List<String> listDispatchOrderCodes){
    	Map<String,OrderInfo> mapResult = new HashMap<>();
    	Example example = new Example(OrderInfo.class);
    	example.createCriteria().andIn("orderCode", listDispatchOrderCodes)
    							.andNotEqualTo("status", DispatchOrderStatusEnum.STATUS_CL.getValue());
    	List<OrderInfo> listOrderInfo =  orderInfoMapper.selectByExample(example);
    	if(null == listOrderInfo || listOrderInfo.isEmpty()){
    		return mapResult;
    	}
    	for(OrderInfo orderInfo:listOrderInfo){
    		mapResult.put(orderInfo.getOrderCode(), orderInfo);
    	}
    	return mapResult;
    }
    
    public Map<String,List<Logistics>> listLogisticsGroupByDispatchOrderCodes(List<String> listDispatchOrderCodes){
    	Map<String,List<Logistics>> mapResult = new HashMap<>();
    	Example example = new Example(Logistics.class);
    	example.createCriteria().andIn("serviceCode", listDispatchOrderCodes)
    							.andBetween("type", 4, 5);
    	List<Logistics> listLogistics = logisticsMapper.selectByExample(example);
    	if(null == listLogistics || listLogistics.isEmpty()){
    		return mapResult;
    	}
    	List<Logistics> listSubject = null;
    	for(Logistics logistics:listLogistics){
    		listSubject = mapResult.get(logistics.getServiceCode());
    		if(null == listSubject){
    			listSubject = new ArrayList<>();
    			mapResult.put(logistics.getServiceCode(), listSubject);
    		}
    		listSubject.add(logistics);
    	}
    	return mapResult;
    }
    
    public void setAcceptLogistics(Map<String,List<Logistics>> mapDispatch2Logistics,Map<String,OrderInfo> mapOrderInfos){
    	String dispatchOrderCode = null;
    	List<Logistics> listLogistics = null;
    	OrderInfo orderInfo = null;
    	Date dateNow = TaskUtils.getNowDate();
    	Date sendTime = null;
    	for(Map.Entry<String,List<Logistics>> entry:mapDispatch2Logistics.entrySet()){
    		dispatchOrderCode = entry.getKey();
    		listLogistics = entry.getValue();
    		if(null == listLogistics || listLogistics.isEmpty()){
    			continue;
    		}
    		for(Logistics logistics:listLogistics){
    			if(null == logistics.getAccept()){
    				logistics.setAccept("N");
    			}
    			if(logistics.getAccept().equals("Y")){
    				continue;
    			}
    			orderInfo = mapOrderInfos.get(logistics.getServiceCode());
    			if(null == orderInfo){
    				continue;
    			}
    			if(!orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_OV.getValue())){
    				continue;
    			}
    			sendTime = getSendTimeDateFromLogistics(logistics);
    			if(dateNow.getTime() - sendTime.getTime() >= signTimestampStep){
    				logistics.setAccept("Y");
    				logistics.setUpdatedBy("admin");
    				logistics.setUpdatedDate(dateNow);
    				logisticsMapper.updateByPrimaryKeySelective(logistics);
    				
    				MerchantOrderKafkaMessage message = new MerchantOrderKafkaMessage();
    				SignMerchantOrder signMerchantOrder = new SignMerchantOrder();
    				signMerchantOrder.setOrderCode(logistics.getServiceCode());
    				signMerchantOrder.setLogiticsId(logistics.getId());
    				message.setMessageType(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_SIGN.getCode());
    				message.setSignMerchantOrder(signMerchantOrder);
    				sendKafkaService.sendSignMerchantOrderKafkaMessage(message);
    			}
    		}
    	}
    }
    
    public Date getSendTimeDateFromLogistics(Logistics logistics){
    	Date result = null;
    	if(StringUtils.isEmpty(logistics.getSendTime())){
    		result = logistics.getUpdatedDate();
    	}else{
    		result = TaskUtils.getDateFromString(logistics.getSendTime());
    	}
    	if(result == null){
    		result = TaskUtils.getDateFromStringYMD(logistics.getSendTime());
    	}
    	return result;
    }
   

//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
//    public void acceptMerchantOrder() {
//        List<Logistics> logisticsAcceptList = logisticsMapper.acceptMerchantOrder();
//        if(logisticsAcceptList != null && logisticsAcceptList.size() > 0) {
//            for (Logistics acceptLogisticsData : logisticsAcceptList) {
//                Date newDate = new Date();              
//                if((newDate.getTime() - acceptLogisticsData.getCreatedDate().getTime()) >=  86400000*2){
//                    MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
//                    merchantOrderDetail.setDispatchOrderNumber(acceptLogisticsData.getServiceCode());
//                    merchantOrderDetail.setDeletedFlag("N");
//                    merchantOrderDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
//                    if(merchantOrderDetail == null){
//                        continue;
//                    }
//                    acceptLogisticsData.setAccept("Y");
//                    if(acceptLogisticsData.getShipmentsQuantity() != null){
//                        if(merchantOrderDetail.getAcceptQuantity() != null){
//                            merchantOrderDetail.setAcceptQuantity(merchantOrderDetail.getAcceptQuantity() + acceptLogisticsData.getShipmentsQuantity());
//                        }else {
//                            merchantOrderDetail.setAcceptQuantity(acceptLogisticsData.getShipmentsQuantity());
//                        }
//                    }else{
//                        List<OrderInfoDetail> orderInfoDetailList = new ArrayList<>();
//                        OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
//                        orderInfoDetail.setLogisticsId(acceptLogisticsData.getId().intValue());
//                        orderInfoDetailList.add(orderInfoDetail);
//                        orderInfoDetailList = orderInfoDetailMapper.listOrderInfoDetail(orderInfoDetailList);
//                        if(merchantOrderDetail.getAcceptQuantity() != null){
//                            merchantOrderDetail.setAcceptQuantity(merchantOrderDetail.getAcceptQuantity() + orderInfoDetailList.size());
//                        }else {
//                            merchantOrderDetail.setAcceptQuantity(orderInfoDetailList.size());
//                        }
//                        acceptLogisticsData.setShipmentsQuantity(orderInfoDetailList.size());
//                    }
//                    merchantOrderDetail.setLogistics(acceptLogisticsData);
//                    //修改订单详情签收状态
//                    LOGGER.info("签收商户订单详情ID:{}", merchantOrderDetail.getId());
//                    merchantOrderDetail.setUpdatedDate(new Date());
//                    merchantOrderDetailMapper.updateById(merchantOrderDetail);
//                    //修改订单签收状态
//                    MerchantOrderDetail mod = new MerchantOrderDetail();
//                    mod.setMerchantOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
//                    List<MerchantOrderDetail> merchantOrderDetailList = merchantOrderDetailMapper.select(mod);
//                    merchantOrderDetail.getLogistics().setUpdatedDate(new Date());
//                    logisticsMapper.updateById(merchantOrderDetail.getLogistics());
//                    if (merchantOrderDetailList != null && merchantOrderDetailList.size() > 0) {
//                        Integer sum = 0;//当前订单已签收数量
//                        Integer checkSum = 0;//当前订单以是已审核数量
//                        Integer shipmentsSum = 0;//发货总数
//                        MerchantOrder merchantOrder = new MerchantOrder();
//                        for (MerchantOrderDetail model : merchantOrderDetailList) {
//                            if (model.getAcceptQuantity() != null) {
//                                sum += model.getAcceptQuantity();
//                            }
//                            checkSum += model.getCheckQuantity();
//                            //查询发货总数
//                            Integer shipmentsQuantity = orderInfoMapper.getShipmentsQuantityByOrderCode(model.getDispatchOrderNumber());
//                            if (shipmentsQuantity == 0) {
//                                Logistics logistics = new Logistics();
//                                logistics.setServiceCode(model.getDispatchOrderNumber());
//                                List<Logistics> logisticsList = logisticsMapper.select(logistics);
//                                if (logisticsList != null && logisticsList.size() > 0) {
//                                    for (Logistics list : logisticsList) {
//                                        shipmentsSum += list.getShipmentsQuantity();
//                                    }
//                                }
//                            } else {
//                                shipmentsSum += shipmentsQuantity;
//                            }
//                        }
//                        merchantOrder.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
//                        merchantOrder.setUpdatedDate(new Date());
//                        merchantOrder.setUpdatedBy(merchantOrderDetail.getUpdatedBy());
//                        if (shipmentsSum == checkSum && shipmentsSum == sum) {
//                            merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
//                        } else {
//                            merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode());
//                        }
//
//                        //将签收的信息添加到销售管理信息
//                        MerchantOrder merchantOrderNew = new MerchantOrder();
//                        merchantOrderNew.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
//                        merchantOrderNew = merchantOrderMapper.selectOne(merchantOrderNew);
//                        Sales sales = new Sales();
//                        List<Sales> salesList = new ArrayList<>();
//                        sales.setLogisticsId(merchantOrderDetail.getLogistics().getId());
//                        sales.setMerchantCode(merchantOrderNew.getMerchantCode());
//                        sales.setProductCode(merchantOrderDetail.getProductCode());
//                        sales.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
//                        sales.setQuantity(merchantOrderDetail.getLogistics().getShipmentsQuantity());
//                        //根据订单编号获取订单信息
//                        if(merchantOrderDetail.getDispatchOrderNumber() != null){
//                            OrderInfoDetail orderInfoDetail = orderInfoDetailMapper.getMaxUpdateOrderInfoDetailByOrderCode(merchantOrderDetail.getDispatchOrderNumber());
//                            if (orderInfoDetail != null) {
//                                sales.setDispatchTime(orderInfoDetail.getUpdatedDate());
//                            }
//                        }else{
//                            Logistics logistics = new Logistics();
//                            logistics.setServiceCode(merchantOrderDetailList.get(0).getDispatchOrderNumber());
//                            Logistics logisticsList = logisticsMapper.getMaxUpdateLogisticsByserviceCode(logistics);
//                            sales.setDispatchTime(logisticsList.getUpdatedDate());
//                        }
//                        sales.setStatus(SalesReconciliationStatus.SALES_STATUS_NOT_RECONCILATION.getCode());
//                        sales.setUpdatedDate(new Date());
//                        sales.setCreatedDate(new Date());
//
//                        sales.setCreatedBy(merchantOrderDetail.getUpdatedBy());
//                        sales.setUpdatedBy(merchantOrderDetail.getUpdatedBy());
//                        sales.setDeletedFlag("N");
//                        salesList.add(sales);
//                        salesManagerMapper.insertList(salesList);
//                        LOGGER.info("insert销售对账数据end");              	
//                        merchantOrderMapper.updateByOrderNumber(merchantOrder);
//                        
//                        MerchantOrderKafkaMessage message = new MerchantOrderKafkaMessage();
//                        SignMerchantOrder signMerchantOrder = new SignMerchantOrder();
//                        signMerchantOrder.setOrderCode(acceptLogisticsData.getServiceCode());
//                        signMerchantOrder.setLogiticsId(acceptLogisticsData.getId());
//                        message.setMessageType(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_SIGN.getCode());
//                        message.setSignMerchantOrder(signMerchantOrder);
//                        sendKafkaService.sendSignMerchantOrderKafkaMessage(message);
//                    }
//                }
//            }
//        }
//    }
}
