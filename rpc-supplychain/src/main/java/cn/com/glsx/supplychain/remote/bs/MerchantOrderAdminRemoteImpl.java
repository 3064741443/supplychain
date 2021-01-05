package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.mapper.bs.LogisticsMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderMapper;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.OrderInfoService;
import cn.com.glsx.supplychain.service.bs.LogisticsService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderDetailService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderSignService;
import cn.com.glsx.supplychain.service.bs.SubjectService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import cn.com.glsx.supplychain.vo.MerchantOrderSignVo;

import com.alibaba.dubbo.config.annotation.Service;
import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.platform.goods.common.entity.ServicePackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author xiexin
 * @version V1.0
 * @Title: MerchantOrderAdminRemote.java
 * @Description: 商户订单接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("MerchantOrderAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class MerchantOrderAdminRemoteImpl implements MerchantOrderAdminRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantOrderAdminRemoteImpl.class);

    @Autowired
    private MerchantOrderService merchantOrderService;
    
    @Autowired
    private MerchantOrderSignService merchantOrderSignService;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private MerchantOrderMapper merchantOrderMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private SubjectService subjectService;

    @Override
    public RpcResponse<RpcPagination<MerchantOrder>> listMerchantOrder(RpcPagination<MerchantOrder> pagination) {
        RpcResponse<RpcPagination<MerchantOrder>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(merchantOrderService.listMerchantOrder(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<MerchantOrder>> getMerchantOrderList(MerchantOrder merchantOrder) {
        RpcResponse<List<MerchantOrder>> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            result = RpcResponse.success(merchantOrderService.getMerchantOrderList(merchantOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<MerchantOrder> getMerchantOrderByMerchantOrderNumber(String merchantOrderNumber) {
        RpcResponse<MerchantOrder> result;
        try {
            RpcAssert.assertNotNull(merchantOrderNumber, DefaultErrorEnum.PARAMETER_NULL, "merchantOrderNumber must not be null");
            result = RpcResponse.success(merchantOrderService.getMerchantOrderByMerchantOrderNumber(merchantOrderNumber));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(MerchantOrder merchantOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            RpcAssert.assertNotNull(merchantOrder.getMerchantOrderDetailList(), DefaultErrorEnum.PARAMETER_NULL, "MerchantOrderDetailList must not be null");
            result = RpcResponse.success(merchantOrderService.add(merchantOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> addSplit(MerchantOrder merchantOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            RpcAssert.assertNotNull(merchantOrder.getMerchantOrderDetailList(), DefaultErrorEnum.PARAMETER_NULL, "MerchantOrderDetailList must not be null");
            result = RpcResponse.success(merchantOrderService.addSplit(merchantOrder));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateOrderStatus(MerchantOrder merchantOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder not be null");
            result = RpcResponse.success(merchantOrderService.updateOrderStatus(merchantOrder));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> acceptMerchantOrder(MerchantOrderDetail merchantOrderDetail) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrderDetail, DefaultErrorEnum.PARAMETER_NULL, "merchantOrderDetail must not be null");
            result = RpcResponse.success(merchantOrderService.acceptMerchantOrder(merchantOrderDetail));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> checkMerchantOrder(MerchantOrder merchantOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            result = RpcResponse.success(merchantOrderService.checkMerchantOrder(merchantOrder));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> addMerchantOrderDispatch(Long merchantOrderDetailId, String dispatchOrderNumber, String updatedBy) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(merchantOrderDetailId, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            RpcAssert.assertNotNull(dispatchOrderNumber, DefaultErrorEnum.PARAMETER_NULL, "dispatchOrderNumber must not be null");
            RpcAssert.assertNotNull(updatedBy, DefaultErrorEnum.PARAMETER_NULL, "updatedBy must not be null");
            result = RpcResponse.success(merchantOrderService.addMerchantOrderDispatch(merchantOrderDetailId,dispatchOrderNumber,updatedBy));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<OrderInfoDetail>> listOrderInfoDetail(List<OrderInfoDetail> orderInfoDetail) {
        RpcResponse<List<OrderInfoDetail>> result;
        try{
            RpcAssert.assertNotNull(orderInfoDetail, DefaultErrorEnum.PARAMETER_NULL, "orderInfoDetail must not be null");
            result = RpcResponse.success(merchantOrderService.listOrderInfoDetail(orderInfoDetail));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> sendGoodsUpdateMerchantOrderStatu(MerchantOrder merchantOrder) {
        RpcResponse<Integer> result;
        try{
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            RpcAssert.assertNotNull(merchantOrder.getLogistics(), DefaultErrorEnum.PARAMETER_NULL, "logistics must not be null");
            RpcAssert.assertNotNull(merchantOrder.getMerchantOrderDetailList(), DefaultErrorEnum.PARAMETER_NULL, "merchantOrderDetailList must not be null");
            result = RpcResponse.success(merchantOrderService.sendGoodsUpdateMerchantOrderStatu(merchantOrder));
        }catch (RpcServiceException e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<MerchantOrderExcelVo>> exportMerchantOrderExit(MerchantOrder merchantOrder) {
        RpcResponse<List<MerchantOrderExcelVo>> result;
        try {
            RpcAssert.assertNotNull(merchantOrder, DefaultErrorEnum.PARAMETER_NULL, "merchantOrder must not be null");
            result = RpcResponse.success(merchantOrderService.exportMerchantOrderExit(merchantOrder));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            result = RpcResponse.error(e);
        }
        return  result;
    }

    /*@Override
    public RpcResponse<CheckImportDataVo> checkImportMerchantOrderList(List<MerchantOrderImport> merchantOrderImportList) {
        LOGGER.info("MerchantOrderAdminRemoteImpl::checkImportMerchantOrderList: merchantOrderImportList.size=" + merchantOrderImportList.size());
        CheckImportDataVo result = new CheckImportDataVo();
        try {
            if (StringUtils.isEmpty(merchantOrderImportList) || merchantOrderImportList.size() == 0 ) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (merchantOrderImportList.size() > 2000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }

            //导入成功的数据返回
            List<MerchantOrderImport> successList = new ArrayList<MerchantOrderImport>();
            //导入失败的 数据
            List<MerchantOrderExport> failList = new ArrayList<>();

            for (MerchantOrderImport item : merchantOrderImportList) {

                LOGGER.info("MerchantOrderAdminRemoteImpl::checkImportMerchantOrderList handle data item=" + item.toString());
                MerchantOrderExport fail = new MerchantOrderExport();
                boolean add = true;

                //查询商户订单是否存在
                MerchantOrder merchantOrder = new MerchantOrder();
                merchantOrder.setOrderNumber(item.getOrderNumber());
                MerchantOrder merchantOrderInfo = merchantOrderMapper.selectOne(merchantOrder);
                if(StringUtils.isEmpty(merchantOrderInfo)){
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_NUMBER_NOT_EXIST.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                // 校验物流单字段长度是否合法
                if (item.getDispatchOrderNumber().length() > 18) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_DISPATCH_LENGTH.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //校验对应的物流单与物流公司是否为空
                if (StringUtils.isEmpty(item.getDispatchOrderNumber())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_IS_EMPTY.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //校验对应的物流公司是否为空
                if (StringUtils.isEmpty(item.getCompany())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_IS_EMPTY.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //发货数量不能大于已发货总数
                Integer shipmentQuantitySum = 0;
                MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
                merchantOrderDetail.setMerchantOrderNumber(item.getOrderNumber());
                merchantOrderDetail.setProductCode(item.getProductCode());
                MerchantOrderDetail merchantOrderDetail1Info = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
                if(!StringUtils.isEmpty(merchantOrderDetail1Info.getDispatchOrderNumber())){
                    Logistics logistics1 = new Logistics();
                    logistics1.setServiceCode(merchantOrderDetail1Info.getDispatchOrderNumber());
                    List<Logistics> logisticsList = logisticsMapper.select(logistics1);
                    for(int i =0;i<logisticsList.size();i++){
                        shipmentQuantitySum = shipmentQuantitySum + logisticsList.get(i).getShipmentsQuantity();
                    }
                    //去供应链订单查询发货数
                    if(shipmentQuantitySum == 0){
                        Integer Quantity = orderInfoMapper.getShipmentsQuantityByOrderCode(merchantOrderDetail1Info.getDispatchOrderNumber());
                        if(Quantity != 0){
                            shipmentQuantitySum = Quantity;
                        }
                    }
                }
                if(shipmentQuantitySum == merchantOrderDetail1Info.getCheckQuantity()){
                    add = true;
                }else{
                    if (item.getShipmentQuantity() + shipmentQuantitySum > merchantOrderDetail1Info.getCheckQuantity()) {
                        BeanUtils.copyProperties(fail, item);
                        fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_SHIPMENT_QUANTITY.getValue());
                        failList.add(fail);
                        add = false;
                        continue;
                    }
                }

                //发货数量不能大于审核数量
                if (item.getShipmentQuantity() > merchantOrderDetail1Info.getCheckQuantity()) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_QUANTITY.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //校验物流单号以及物流公司不能带逗号格式
                if (item.getOrderNumber().contains(",")) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_FORMAT_ERROR.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getCompany().contains(",")) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_MERCHANT_ORDER_FORMAT_ERROR.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }


                if (add) {
                    successList.add(item);
                }
            }
            LOGGER.info("MerchantOrderAdminRemoteImpl::checkImportMerchantOrderList: check ok !");

            result.setMerchantOrderImportList(successList);
            result.setMerchantOrderExportlist(failList);
            return RpcResponse.success(result);

        }catch (RpcServiceException e){
            e.printStackTrace();
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }*/

    /*@Override
    public RpcResponse<Integer> importMerchantOrderList(List<MerchantOrderImport> merchantOrderImportList) {
        RpcResponse<Integer> result;
        try{
            RpcAssert.assertNotNull(merchantOrderImportList, DefaultErrorEnum.PARAMETER_NULL, "merchantOrderImportList must not be null");
            result = RpcResponse.success(merchantOrderService.importMerchantOrderList(merchantOrderImportList));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }*/

    @Override
    public RpcResponse<List<Logistics>> getLogisticsInfoListByServiceCode(Logistics logistics) {
        RpcResponse<List<Logistics>> result;
        try{
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics must not be null");
            result = RpcResponse.success(logisticsService.getLogisticsInfoListByServiceCode(logistics));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateById(Logistics logistics) {
        RpcResponse<Integer> result;
        try{
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics must not be null");
            result = RpcResponse.success(logisticsService.updateById(logistics));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<OrderInfo> getOrderInfoByOrderCode(String orderCode) {
        RpcResponse<OrderInfo> result;
        try{
            RpcAssert.assertNotNull(orderCode, DefaultErrorEnum.PARAMETER_NULL, "orderCode must not be null");
            result = RpcResponse.success(orderInfoService.getOrderInfoByOrderCode(orderCode));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<Subject>> getSubjectlist(Subject subject) {
        RpcResponse<List<Subject>> result;
        try{
            RpcAssert.assertNotNull(subject, DefaultErrorEnum.PARAMETER_NULL, "subject must not be null");
            result = RpcResponse.success(subjectService.getlist(subject));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

	@Override
	public RpcResponse<List<MerchantOrderSignVo>> genSignOrderByMerchantOrderNum(String receiveOrderNo,
			List<MerchantOrder> listMerchantOrder) {
		
		RpcResponse<List<MerchantOrderSignVo>> result;
		try
		{
			RpcAssert.assertNotNull(receiveOrderNo, DefaultErrorEnum.PARAMETER_NULL, "receiveOrderNo must not be null");
			RpcAssert.assertNotNull(listMerchantOrder, DefaultErrorEnum.PARAMETER_NULL, "listMerchantOrder must not be null");
			result = RpcResponse.success(merchantOrderService.genSignOrderByMerchantOrderNum(receiveOrderNo,listMerchantOrder));
		}catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
		return result;
	}

	@Override
	public RpcResponse<Integer> batchAddMerchantOrderSign(
			List<BsMerchantOrderSign> listOrderSign) {
		
		RpcResponse<Integer> result;
		try
		{
			RpcAssert.assertNotNull(listOrderSign, DefaultErrorEnum.PARAMETER_NULL, "listOrderSign must not be null");
			RpcAssert.assertIsTrue(listOrderSign.size()>0, DefaultErrorEnum.DATA_NULL);
			result = RpcResponse.success(merchantOrderSignService.batchAddMerchantOrderSign(listOrderSign));
		}
		catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
		return result;
	}
	
	@Override
	public RpcResponse<Integer> updateMerchantOrderSignBySignCode(
			BsMerchantOrderSign merchantOrderSign) {
		
		RpcResponse<Integer> result;
		try
		{
			RpcAssert.assertNotNull(merchantOrderSign, DefaultErrorEnum.PARAMETER_NULL, "merchantOrderSign must not be null");
			RpcAssert.assertNotNull(merchantOrderSign.getMerchantSignNumber(), DefaultErrorEnum.PARAMETER_NULL, "merchantOrderSign.getMerchantSignNumber() must not be null");
			result = RpcResponse.success(merchantOrderSignService.updateMerchantOrderSignBySignCode(merchantOrderSign));
		}
		catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
		return result;
	}

	@Override
	public RpcResponse<List<BsMerchantOrderSign>> listMerchantOrderSignByMerchantOrder(
			List<String> listMerchantOrders) {
		
		RpcResponse<List<BsMerchantOrderSign>> result;
		try
		{
			RpcAssert.assertNotNull(listMerchantOrders, DefaultErrorEnum.PARAMETER_NULL, "listMerchantOrders must not be null");
			result = RpcResponse.success(merchantOrderSignService.listMerchantOrderSignByMerchantOrder(listMerchantOrders));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
		}
		return result;
	}

	

}
