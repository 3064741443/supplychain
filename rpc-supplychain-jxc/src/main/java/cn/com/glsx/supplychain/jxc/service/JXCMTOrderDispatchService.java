package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.converter.JXCMTBsLogisticsRpcConvert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.DispatchOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderSignStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.kafka.*;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTOrderInfoDetailMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTOrderInfoMapper;
import cn.com.glsx.supplychain.jxc.model.*;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JXCMTOrderDispatchService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTOrderDispatchService.class);
	@Autowired
	private JXCMTOrderInfoDetailMapper jxcmtOrderInfoDetailMapper;
	@Autowired
	private JXCMTOrderInfoMapper jxcmtOrderInfoMapper;
	@Autowired
	private JXCMTWarehouseService jxcmtWarehouseService;
	@Autowired
	private JXCMTBsLogisticsService jxcmtBsLogisticsService;
	@Autowired
	private JXCMTOrderService jxcmtOrderService;
	@Autowired
	private JXCMTGhMerchantOrderService jxcmtGhMerchantOrderService;
	@Autowired
	private JXCMTBsAddressService jxcmtBsAddressService;
	@Autowired
	private JXCMTBsDealerUserInfoService jxcmtBsUserInfoService;
	@Autowired
	private JXCMTProductService jxcmtProductService;
	@Autowired
	private JXCMTAttribManaService jxcmtAttribManaService;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
	private JXCMTMaterialInfoService jxcmtMaterialInfoService;
	@Autowired
    private SendKafkaService sendKafkaService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	
	
	public List<JXCMTDispatchOrderNumCount> selectOrderDetailCount(List<String> listDispatchOrderCodes){
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return null;
		}
		return jxcmtOrderInfoDetailMapper.selectOrderDetailCount(listDispatchOrderCodes);
	}
	
	public List<JXCMTOrderInfo> listOrderInfo(List<String> listDispatchOrderCodes){
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andIn("orderCode", listDispatchOrderCodes);
		return jxcmtOrderInfoMapper.selectByExample(example);
	}
	
	public Map<String,JXCMTOrderInfo> mapOrderInfo(List<String> listDispatchOrderCodes){
		Map<String,JXCMTOrderInfo> mapResult = null;
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andIn("orderCode", listDispatchOrderCodes);
		List<JXCMTOrderInfo> listOrderInfo = jxcmtOrderInfoMapper.selectByExample(example);
		if(null != listOrderInfo && !listOrderInfo.isEmpty()){
			mapResult = listOrderInfo.stream().collect(Collectors.toMap(JXCMTOrderInfo::getOrderCode, a->a));
		}
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
	
	public Integer countOrderDetailByLogisticsId(Integer logisticsId,String dispatchOrderCode){
		JXCMTOrderInfoDetail condition = new JXCMTOrderInfoDetail();
		condition.setLogisticsId(logisticsId);
		condition.setOrderCode(dispatchOrderCode);
		condition.setDeletedFlag("N");
		return jxcmtOrderInfoDetailMapper.selectCount(condition);
	}
	
	public Integer deleteDispatchOrderByOrderCode(String orderCode){
		JXCMTOrderInfo record = new JXCMTOrderInfo();
		record.setStatus(DispatchOrderStatusEnum.STATUS_CL.getValue());
		record.setDeletedFlag("Y");
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andEqualTo("orderCode", orderCode);
		return jxcmtOrderInfoMapper.updateByExampleSelective(record, example);
	}
	
	public Integer delDispatchOrderDetail(JXCMTOrderInfoDetail detail){
		if(null == detail){
			return 0;
		}
		if(null == detail.getId()){
			return 0;
		}
		return jxcmtOrderInfoDetailMapper.deleteByPrimaryKey(detail.getId());
	}
	
	public Integer addDispatchOrderDetail(JXCMTOrderInfoDetail detail){
		return jxcmtOrderInfoDetailMapper.insertSelective(detail);
	}
	
	public Integer batchAddDispatchOrderDetail(List<JXCMTOrderInfoDetail> listDetails){
		if(null == listDetails || listDetails.isEmpty()){
			return 0;
		}
		return jxcmtOrderInfoDetailMapper.insertList(listDetails);
	}
	
	public JXCMTOrderInfoDetail getDispatchOrderDetailBySn(String sn,String dispatchOrderCode){
		JXCMTOrderInfoDetail condition = new JXCMTOrderInfoDetail();
		condition.setOrderCode(dispatchOrderCode);
		condition.setSn(sn);
		return jxcmtOrderInfoDetailMapper.selectOne(condition);
	}
	
	public Map<String,JXCMTWarehouseInfo> listOrderWarehouseInfos(List<String> listDispatchOrderCodes){
		Map<String,JXCMTWarehouseInfo> mapResult = new HashMap<>();
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return mapResult;
		}
		List<JXCMTOrderInfo> listOrderInfo = listOrderInfo(listDispatchOrderCodes);
		if(null == listOrderInfo || listOrderInfo.isEmpty()){
			return mapResult;
		}
		List<Integer> listWarehouseIds = listOrderInfo.stream().map(JXCMTOrderInfo::getWarehouseId).collect(Collectors.toList());
		List<JXCMTWarehouseInfo> listWarehouseInfo = jxcmtWarehouseService.listWarehouseInfoByIds(listWarehouseIds);
		Map<Integer,JXCMTWarehouseInfo> mapWarehouseInfo = listWarehouseInfo.stream().collect(Collectors.toMap(JXCMTWarehouseInfo::getId, a->a));
		if(null == mapWarehouseInfo){
			mapWarehouseInfo = new HashMap<>();
		}
		for(JXCMTOrderInfo orderInfo:listOrderInfo){
			if(null == orderInfo.getWarehouseId()){
				continue;
			}
			mapResult.put(orderInfo.getOrderCode(), mapWarehouseInfo.get(orderInfo.getWarehouseId()));
		}
		return mapResult;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchSpOrderScanYBatch(JXCMTSpOrderDispatchScanYDTO record) throws RpcServiceException{
		Integer result = 0;
		List<JXCMTOrderInfo> listOrderInfo = new ArrayList<>();
		List<String> listMerchantOrders = record.getListMerchantOrderWarehouseDto().stream().map(t->t.getMerchantOrder()).distinct().collect(Collectors.toList());
		Map<String,Integer> mapMerchantOrderWareHouse = record.getListMerchantOrderWarehouseDto().stream().collect(Collectors.toMap(JXCMTMerchantOrderWarehouseDTO::getMerchantOrder, JXCMTMerchantOrderWarehouseDTO::getWarehouseId));
		List<JXCMTBsMerchantOrder> listBsMerchantOrder = jxcmtOrderService.listBsMerchantOrderByBsMerchantOrder(listMerchantOrders);
		List<JXCMTBsMerchantOrderDetail> listBsMerchantOrderDetail = jxcmtOrderService.listBsMerchantOrderDetailsByMerchantOrders(listMerchantOrders);
		List<JXCMTEcMerchantOrder> listEcMerchantOrders = jxcmtOrderService.listEcMerchantOrderByBsMerchantOrders(listMerchantOrders);
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtOrderService.listBsMerchantOrderVehicleByBsMerchantOrderCodes(listMerchantOrders, false);
		List<JXCMTBsLogistics> listBsLogistics = jxcmtBsLogisticsService.listBsLogisticsByBsMerchantOrderCodes(listMerchantOrders);
		List<Long> listAddressIds = listBsLogistics.stream().map(t->t.getReceiveId()).distinct().collect(Collectors.toList());
		List<JXCMTBsAddressDTO> listBsAddress = jxcmtBsAddressService.listAddressByIds(listAddressIds);
		List<String> listMerchantCodes = listBsMerchantOrder.stream().map(t->t.getMerchantCode()).distinct().collect(Collectors.toList());
		Map<String,String> mapMerchantInfo = jxcmtBsUserInfoService.mapMerchantName(listMerchantCodes);
		Map<Integer,JXCMTBsAddressDTO> mapAddress = listBsAddress.stream().collect(Collectors.toMap(JXCMTBsAddressDTO::getId, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsLogistics> mapLogistics = listBsLogistics.stream().collect(Collectors.toMap(JXCMTBsLogistics::getServiceCode, a -> a,(k1,k2)->k1));
		Map<String,JXCMTEcMerchantOrder> mapEcMerchantOrder = listEcMerchantOrders.stream().collect(Collectors.toMap(JXCMTEcMerchantOrder::getOrderNumber, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsMerchantOrderDetail> mapBsMerchantOrderDetail = listBsMerchantOrderDetail.stream().collect(Collectors.toMap(JXCMTBsMerchantOrderDetail::getMerchantOrderNumber, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsMerchantOrder> mapBsMerchantOrder = listBsMerchantOrder.stream().collect(Collectors.toMap(JXCMTBsMerchantOrder::getOrderNumber, a -> a,(k1,k2)->k1));
		try{
			JXCMTBsMerchantOrder bsMerchantOrder = null;
			JXCMTEcMerchantOrder ecMerchantOrder = null;
			JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = null;
			JXCMTBsAddressDTO bsAddress = null;
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
				String orderCode = JxcUtils.generatorOrderCode(snowflakeWorker);
				bsMerchantOrder = mapBsMerchantOrder.get(vehicle.getMerchantOrder());
				bsMerchantOrderDetail = mapBsMerchantOrderDetail.get(vehicle.getMerchantOrder());
				bsAddress = mapAddress.get(mapLogistics.get(vehicle.getMerchantOrder()).getReceiveId().intValue());
				String contactAddress = (StringUtils.isEmpty(bsAddress.getProvinceName())?"":bsAddress.getProvinceName()) + (StringUtils.isEmpty(bsAddress.getCityName())?"":bsAddress.getCityName()) + (StringUtils.isEmpty(bsAddress.getAreaName())?"":bsAddress.getAreaName()) + bsAddress.getAddress();
				listOrderInfo.add(generatroOrderInfoY(orderCode,vehicle.getBsCheckQuantity(),
						bsMerchantOrder.getMerchantCode(),mapMerchantInfo.get(bsMerchantOrder.getMerchantCode()),
						contactAddress,bsAddress.getMobile(),bsAddress.getName(),
						"Y",mapMerchantOrderWareHouse.get(vehicle.getMerchantOrder()),record));
				vehicle.setDispatchOrderCode(orderCode);
				vehicle.setUpdatedBy(record.getConsumer());
				vehicle.setUpdatedDate(JxcUtils.getNowDate());
				jxcmtOrderService.updateBsMerchantOrderVehicleSelective(vehicle);
			}
			jxcmtOrderService.updateBsMerchantOrderStatu(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode(), listMerchantOrders);
			jxcmtOrderService.updateEcMerchantOrderStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName(), listMerchantOrders);
			insertOrderInfos(listOrderInfo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchSpOrderScanYSingle(JXCMTSpOrderDispatchScanYDTO record) throws RpcServiceException{
		Integer result = 0;
		List<JXCMTOrderInfo> listOrderInfo = new ArrayList<>();
		String merchantOrder = record.getListMerchantOrderWarehouseDto().get(0).getMerchantOrder();
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtOrderService.listBsMerchantOrderVehicleByBsMerchantOrderCode(merchantOrder);
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrder);
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = jxcmtOrderService.getBsMerchantOrderDetailByMerchantOrder(merchantOrder);
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrder);	
		JXCMTOrderInfo orderInfo = null;
		try{
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
				String orderCode = JxcUtils.generatorOrderCode(snowflakeWorker);
				listOrderInfo.add(generatroOrderInfoY(orderCode,vehicle.getBsCheckQuantity(),record.getSendMerchantNo(),record.getSendMerchantName(),
						record.getAddress(),record.getMobile(),record.getContacts(),"Y",record.getListMerchantOrderWarehouseDto().get(0).getWarehouseId(),
						record));
				vehicle.setDispatchOrderCode(orderCode);
				vehicle.setUpdatedBy(record.getConsumer());
				vehicle.setUpdatedDate(JxcUtils.getNowDate());
				jxcmtOrderService.updateBsMerchantOrderVehicleSelective(vehicle);
			}
			bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName());
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			insertOrderInfos(listOrderInfo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchSpOrderScanNBatch(JXCMTSpOrderDispatchScanNDTO record) throws RpcServiceException{
		Integer result = 0;
		List<JXCMTOrderInfo> listOrderInfo = new ArrayList<>();
		List<String> listMerchantOrders = record.getListMerchantOrderWarehouseDto().stream().map(t->t.getMerchantOrder()).distinct().collect(Collectors.toList());
		Map<String,Integer> mapMerchantOrderWareHouse = record.getListMerchantOrderWarehouseDto().stream().collect(Collectors.toMap(JXCMTMerchantOrderWarehouseDTO::getMerchantOrder, JXCMTMerchantOrderWarehouseDTO::getWarehouseId));
		List<JXCMTBsMerchantOrder> listBsMerchantOrder = jxcmtOrderService.listBsMerchantOrderByBsMerchantOrder(listMerchantOrders);
		List<JXCMTBsMerchantOrderDetail> listBsMerchantOrderDetail = jxcmtOrderService.listBsMerchantOrderDetailsByMerchantOrders(listMerchantOrders);
		List<JXCMTEcMerchantOrder> listEcMerchantOrders = jxcmtOrderService.listEcMerchantOrderByBsMerchantOrders(listMerchantOrders);
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtOrderService.listBsMerchantOrderVehicleByBsMerchantOrderCodes(listMerchantOrders, false);
		List<JXCMTBsLogistics> listBsLogistics = jxcmtBsLogisticsService.listBsLogisticsByBsMerchantOrderCodes(listMerchantOrders);
		List<Long> listAddressIds = listBsLogistics.stream().map(t->t.getReceiveId()).distinct().collect(Collectors.toList());
		List<JXCMTBsAddressDTO> listBsAddress = jxcmtBsAddressService.listAddressByIds(listAddressIds);
		List<String> listMerchantCodes = listBsMerchantOrder.stream().map(t->t.getMerchantCode()).distinct().collect(Collectors.toList());
		Map<String,String> mapMerchantInfo = jxcmtBsUserInfoService.mapMerchantName(listMerchantCodes);
		Map<Integer,JXCMTBsAddressDTO> mapAddress = listBsAddress.stream().collect(Collectors.toMap(JXCMTBsAddressDTO::getId, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsLogistics> mapLogistics = listBsLogistics.stream().collect(Collectors.toMap(JXCMTBsLogistics::getServiceCode, a -> a,(k1,k2)->k1));
		Map<String,JXCMTEcMerchantOrder> mapEcMerchantOrder = listEcMerchantOrders.stream().collect(Collectors.toMap(JXCMTEcMerchantOrder::getOrderNumber, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsMerchantOrderDetail> mapBsMerchantOrderDetail = listBsMerchantOrderDetail.stream().collect(Collectors.toMap(JXCMTBsMerchantOrderDetail::getMerchantOrderNumber, a -> a,(k1,k2)->k1));
		Map<String,JXCMTBsMerchantOrder> mapBsMerchantOrder = listBsMerchantOrder.stream().collect(Collectors.toMap(JXCMTBsMerchantOrder::getOrderNumber, a -> a,(k1,k2)->k1));
		try{
			JXCMTBsMerchantOrder bsMerchantOrder = null;
			JXCMTEcMerchantOrder ecMerchantOrder = null;
			JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = null;
			JXCMTBsAddressDTO bsAddress = null;
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
				String orderCode = JxcUtils.generatorOrderCode(snowflakeWorker);
				bsMerchantOrder = mapBsMerchantOrder.get(vehicle.getMerchantOrder());
				bsMerchantOrderDetail = mapBsMerchantOrderDetail.get(vehicle.getMerchantOrder());
				bsAddress = mapAddress.get(mapLogistics.get(vehicle.getMerchantOrder()).getReceiveId().intValue());
				String contactAddress = (StringUtils.isEmpty(bsAddress.getProvinceName())?"":bsAddress.getProvinceName()) + (StringUtils.isEmpty(bsAddress.getCityName())?"":bsAddress.getCityName()) + (StringUtils.isEmpty(bsAddress.getAreaName())?"":bsAddress.getAreaName()) + bsAddress.getAddress();
				listOrderInfo.add(generatroOrderInfoN(orderCode,vehicle.getBsCheckQuantity(),
						bsMerchantOrder.getMerchantCode(),mapMerchantInfo.get(bsMerchantOrder.getMerchantCode()),
						contactAddress,bsAddress.getMobile(),bsAddress.getName(),
						"N",mapMerchantOrderWareHouse.get(vehicle.getMerchantOrder()),record));
				vehicle.setDispatchOrderCode(orderCode);
				vehicle.setUpdatedBy(record.getConsumer());
				vehicle.setUpdatedDate(JxcUtils.getNowDate());
				jxcmtOrderService.updateBsMerchantOrderVehicleSelective(vehicle);
			}
			jxcmtOrderService.updateBsMerchantOrderStatu(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode(), listMerchantOrders);
			jxcmtOrderService.updateEcMerchantOrderStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName(), listMerchantOrders);
			insertOrderInfos(listOrderInfo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchSpOrderScanNSingle(JXCMTSpOrderDispatchScanNDTO record) throws RpcServiceException{
		Integer result = 0;
		List<JXCMTOrderInfo> listOrderInfo = new ArrayList<>();
		String merchantOrder = record.getListMerchantOrderWarehouseDto().get(0).getMerchantOrder();
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtOrderService.listBsMerchantOrderVehicleByBsMerchantOrderCode(merchantOrder);
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrder);
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = jxcmtOrderService.getBsMerchantOrderDetailByMerchantOrder(merchantOrder);
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrder);
		JXCMTOrderInfo orderInfo = null;
		try{
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
				String orderCode = JxcUtils.generatorOrderCode(snowflakeWorker);
				listOrderInfo.add(generatroOrderInfoN(orderCode,vehicle.getBsCheckQuantity(),record.getSendMerchantNo(),record.getSendMerchantName(),
						record.getAddress(),record.getMobile(),record.getContacts(),"N",record.getListMerchantOrderWarehouseDto().get(0).getWarehouseId(),
						record));
				vehicle.setDispatchOrderCode(orderCode);
				vehicle.setUpdatedBy(record.getConsumer());
				vehicle.setUpdatedDate(JxcUtils.getNowDate());
				jxcmtOrderService.updateBsMerchantOrderVehicleSelective(vehicle);
			}
			bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName());
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			insertOrderInfos(listOrderInfo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchSpOrderDirect(JXCMTSpOrderDispatchDirectDTO record) throws RpcServiceException{
		Integer result = 0;
		List<JXCMTBsLogistics> listBsLogistics = new ArrayList<>();
		List<JXCMTOrderInfo> listOrderInfo = new ArrayList<>();
		String merchantOrder = record.getMerchantOrder();
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrder);		
		JXCMTBsDealerUserInfo bsMerchantUserInfo = jxcmtBsUserInfoService.getBsDealerUserInfo(bsMerchantOrder.getMerchantCode());
		bsMerchantOrder.setMerchantName(bsMerchantUserInfo.getMerchantName());
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtOrderService.listBsMerchantOrderVehicleByBsMerchantOrderCode(merchantOrder);
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = jxcmtOrderService.getBsMerchantOrderDetailByMerchantOrder(merchantOrder);
		JXCMTProductDetail productDetail =  jxcmtProductService.getBsProductDetailByProductCode(bsMerchantOrderDetail.getProductCode());
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrder);
		JXCMTBsLogistics bsLogistics = jxcmtBsLogisticsService.getBsLogisticsByBsMerchantOrderCode(merchantOrder);
		JXCMTBsAddress bsAddress = jxcmtBsAddressService.getAddressById(bsLogistics.getReceiveId().intValue());
		int total = bsMerchantOrderDetail.getCheckQuantity().intValue();
		int alreadSendNums = 0;
		if(null != ecMerchantOrder.getShipmentQuantity()){
			alreadSendNums = ecMerchantOrder.getAlreadyShipmentQuantity().intValue();
		}
		int nowSendNums = record.getSendNums().intValue();
		int checkQuantity = ecMerchantOrder.getCheckQuantity();
		if(nowSendNums > (checkQuantity - alreadSendNums)){
			logger.info("JXCMTOrderDispatchService::dispatchSpOrderDirect send bigger than owe quanlity");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_DISPATCH_NUMBER_BIGGER);
		}

		try{	
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
				String orderCode = vehicle.getDispatchOrderCode();
				if(StringUtils.isEmpty(orderCode)){
					orderCode = JxcUtils.generatorOrderCode(snowflakeWorker);
					String status = "UF";
					int bsCheckQuantity = vehicle.getBsCheckQuantity().intValue();
					int sendQulities = 0;
					if(nowSendNums < bsCheckQuantity){
						sendQulities = nowSendNums;
						nowSendNums = 0;
					}else{
						sendQulities = bsCheckQuantity;
						nowSendNums = nowSendNums - sendQulities;
						status = "OV";
					}
					listOrderInfo.add(this.generatroOrderInfo(orderCode, vehicle.getBsCheckQuantity(),
							bsMerchantOrder.getMerchantCode(), bsMerchantOrder.getMerchantName(), bsAddress.getAddress(), bsAddress.getMobile(), bsAddress.getName(), 
							"D", record.getWarehouseId(),status,0,"",
							"",productDetail.getMaterialCode(),productDetail.getMaterialName(),"",record.getConsumer(),sendQulities));
					listBsLogistics.add(this.generatorBsLogistics(record.getLogisticsNum(), record.getLogisticsCompany(), (byte)5, orderCode, bsAddress.getId(), sendQulities,
							record.getConsumer(), record.getSendTime()));
					vehicle.setDispatchOrderCode(orderCode);
					vehicle.setUpdatedBy(record.getConsumer());
					vehicle.setUpdatedDate(JxcUtils.getNowDate());
					jxcmtOrderService.updateBsMerchantOrderVehicleSelective(vehicle);
				}else{
					JXCMTOrderInfo orderInfo = getOrderInfoByDispatchOrderCode(orderCode);
					if(orderInfo.getStatus().equals("OV")){
						continue;
					}
					int sendQulities = orderInfo.getSendQuanlity();
					int logisticsSend = 0;
					if(nowSendNums < (orderInfo.getTotal()-sendQulities)){
						sendQulities += nowSendNums;
						orderInfo.setSendQuanlity(sendQulities);
						logisticsSend = nowSendNums;
						nowSendNums = 0;
					}else{
						logisticsSend = orderInfo.getTotal()-sendQulities;
						nowSendNums = nowSendNums - (orderInfo.getTotal()-sendQulities);
						sendQulities = orderInfo.getTotal();
						orderInfo.setSendQuanlity(sendQulities);
						orderInfo.setStatus("OV");

					}
					listBsLogistics.add(this.generatorBsLogistics(record.getLogisticsNum(), record.getLogisticsCompany(), (byte)5, orderCode, bsAddress.getId(), logisticsSend,
							record.getConsumer(), record.getSendTime()));
					this.updateOrderInfo(orderInfo);
				}
			}
			if(record.getSendNums().intValue() + alreadSendNums >= checkQuantity){
				bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
				ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName());
			}else{
				bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
				ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName());
			}
			if(bsMerchantOrder.getSignStatus().equals(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getCode())){
				bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getCode());
				ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
			}
			ecMerchantOrder.setAlreadyShipmentQuantity(ecMerchantOrder.getAlreadyShipmentQuantity()+record.getSendNums().intValue());
			ecMerchantOrder.setShipmentQuantity(ecMerchantOrder.getShipmentQuantity() + " " + record.getSendNums().intValue());
			ecMerchantOrder.setShipmentTime(ecMerchantOrder.getShipmentTime() + " " + record.getSendTime());
			ecMerchantOrder.setOweQuantity(ecMerchantOrder.getCheckQuantity() - ecMerchantOrder.getAlreadyShipmentQuantity());
			ecMerchantOrder.setLogisticsDesc(ecMerchantOrder.getLogisticsDesc() + "\n" + record.getLogisticsNum() + ":" + record.getSendNums().intValue() + "\n" + record.getLogisticsCompany());
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			if(!StringUtils.isEmpty(listOrderInfo)&&listOrderInfo.size()>0) {
				insertOrderInfos(listOrderInfo);
			}
			jxcmtBsLogisticsService.insertListJxcmtBsLogistics(listBsLogistics);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	public Page<JXCMTOrderInfoSignDTO> pageSignOrders(RpcPagination<JXCMTOrderInfoSignDTO> pagination){
		JXCMTOrderInfoSignDTO condition = pagination.getCondition();
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<JXCMTOrderInfoSignDTO> pageOrderInfo =  jxcmtOrderInfoMapper.pageSignOrders(condition);
		logger.info("pageOrderInfo:{}",pageOrderInfo);
		if(null == pageOrderInfo || null == pageOrderInfo.getResult() || pageOrderInfo.getResult().isEmpty()){
			return pageOrderInfo;
		}
		for(JXCMTOrderInfoSignDTO dto:pageOrderInfo){
			if(StringUtils.isEmpty(dto.getBillSignNumber())){
				dto.setBillStatus("U");
			}else{
				dto.setBillStatus("A");
			}
		}
		return pageOrderInfo;
	}
	
	public Integer genBillNumber(JXCMTGenBillNumberDTO record){
		GenBills genBills = new GenBills();
		List<Bill> listBills = new ArrayList<>();
		List<BillDetail> listBillDetail = null;
		Map<String,Bill> mapBill = new HashMap<>();
		Bill bill = null;
		BillDetail billDetail = null;
		String strKey = "";
		for(JXCMTOrderInfoSignDTO signDto:record.getListOrderInfoSigns()){
			strKey = "mt:" + signDto.getSendMerchantNo() + "c:"+signDto.getContacts() + "m:" + signDto.getMobile() + "a:" + signDto.getAddress();
			bill = mapBill.get(strKey);
			if(null == bill){
				bill = new Bill();
				mapBill.put(strKey, bill);
				listBills.add(bill);
				
				bill.setBillSignNumber(JxcUtils.generatorBillNumber(snowflakeWorker));
				bill.setSendMerchantNo(signDto.getSendMerchantNo());
				bill.setSendMerchantName(signDto.getSendMerchantName());
				bill.setAddress(signDto.getAddress());
				bill.setContacts(signDto.getContacts());
				bill.setMobile(signDto.getMobile());
				listBillDetail = new ArrayList<BillDetail>();
				bill.setListBillDetail(listBillDetail);
			}
			billDetail = new BillDetail();
			billDetail.setMerchantOrder(signDto.getMerchantOrder());
			billDetail.setDispatchOrderCode(signDto.getDispatchOrderCode());
			billDetail.setMaterialCode(signDto.getMaterialCode());
			billDetail.setMaterialName(signDto.getMaterialName());
			billDetail.setLogisticsCpy(signDto.getLogisticsCpy());
			billDetail.setLogisticsNo(signDto.getLogisticsNo());
			billDetail.setLogisticsSendTime(signDto.getLogisticsSendTime());
			billDetail.setLogisticsShipmentsQuantity(signDto.getLogisticsShipmentsQuantity());
			bill.getListBillDetail().add(billDetail);	
		}
		genBills.setListBills(listBills);
		genBills.setUserName(record.getUserName());
		genBills.setBillType(record.getBillType());
		sendKafkaService.notifyGenSignDispatchOrderInfo(genBills);
		return 0;
	}
	
	public Page<JXCMTOrderInfoDTO> pageDispatchOrder(RpcPagination<JXCMTOrderInfoDTO> pagination){
		JXCMTOrderInfo condition = new JXCMTOrderInfo();
		condition.setOrderCode(pagination.getCondition().getDispatchOrderCode());
		condition.setDeviceTypeId(pagination.getCondition().getDeviceTypeId());
		condition.setStatus(pagination.getCondition().getStatus());
		condition.setMerchantOrder(pagination.getCondition().getMerchantOrder());
		condition.setSendMerchantNo(pagination.getCondition().getSendMerchantNo());
		condition.setWarehouseId(pagination.getCondition().getWarehouseId());
		condition.setQueryType(pagination.getCondition().getBsQueryType());
		condition.setOrderTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeEnd()));
		condition.setOrderDistribTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getOrderDistribTimeStart()));
		condition.setOrderDistribTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getOrderDistribTimeEnd()));
		condition.setFactMaterialCode(pagination.getCondition().getFactMaterialCode());
		condition.setFactMaterialName(pagination.getCondition().getFactMaterialName());
		condition.setStartSendTime(pagination.getCondition().getStartSendTime());
		condition.setEndSendTime(pagination.getCondition().getEndSendTime());
		condition.setDeletedFlag("N");
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<JXCMTOrderInfoDTO> pageOrderInfo = jxcmtOrderInfoMapper.pageOrderInfo(condition);
		if(null == pageOrderInfo || null == pageOrderInfo.getResult() || pageOrderInfo.getResult().isEmpty()){
			return pageOrderInfo;
		}		
		List<Integer> listChannelIds = pageOrderInfo.getResult().stream().map(JXCMTOrderInfoDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = jxcmtOrderService.listBsMerchantChannelName(listChannelIds);
		logger.info("mapMerchantChannels:{}",mapMerchantChannels);
		List<String> listMerchantOrder = pageOrderInfo.getResult().stream().map(JXCMTOrderInfoDTO::getMerchantOrder).collect(Collectors.toList());
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
		Map<String,JXCMTDeviceType> mapCache = new HashMap<>();
		JXCMTDeviceType jxcmtDeviceType = null;
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		for(JXCMTOrderInfoDTO dto:pageOrderInfo){
			if(null != dto.getChannelId()){
				dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			}
			if(dto.getDeviceTypeId() == null){
				jxcmtDeviceType = jxcmtAttribManaService.getDeviceTypeByAttribCode(dto.getAttribCode(), mapCache);
				if(null != jxcmtDeviceType){
					dto.setDeviceTypeId(jxcmtDeviceType.getId());
					dto.setDeviceTypeName(jxcmtDeviceType.getName());
				}
			}
			if(StringUtils.isEmpty(dto.getNdScan())){
				dto.setNdScan("Y");
			}
			listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
			if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
				continue;
			}
			String optionConfigDesc = "";
			String fastenConfigDesc = "";
			for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
				oConfig.setOption(oConfig.getStrOption());
				attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
				if(null == attribInfo){
					continue;
				}
				if(oConfig.getOption().equals("O")){
					optionConfigDesc += attribInfo.getComment();
					optionConfigDesc += ":";
					optionConfigDesc += attribInfo.getName();
					optionConfigDesc += "/";
				}else if(oConfig.getOption().equals("F")){
					fastenConfigDesc += attribInfo.getComment();
					fastenConfigDesc += ":";
					fastenConfigDesc += attribInfo.getName();
					fastenConfigDesc += "/";
				}
			}
			dto.setFastenConfigDesc(fastenConfigDesc);
			dto.setOptionConfigDesc(optionConfigDesc);		
		}
		return pageOrderInfo;
	}

	public List<JXCMTOrderInfoDTO> exportDispatchOrder(JXCMTOrderInfoDTO orderInfoDTO){
		logger.info("JXCMTOrderDispatchService::exportDispatchOrder orderInfoDTO::{}",orderInfoDTO);
		//发送kafka异步执行
		ExportJXCMTOrderInfo exportJXCMTOrderInfo = generatorExportJXCMTOrderInfo(orderInfoDTO);
		sendKafkaService.notifyJXCMTOrderInfo(orderInfoDTO.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_INVOICE, exportJXCMTOrderInfo);
		JXCMTOrderInfo condition = new JXCMTOrderInfo();
		condition.setOrderCode(orderInfoDTO.getDispatchOrderCode());
		condition.setDeviceTypeId(orderInfoDTO.getDeviceTypeId());
		condition.setStatus(orderInfoDTO.getStatus());
		condition.setMerchantOrder(orderInfoDTO.getMerchantOrder());
		condition.setSendMerchantNo(orderInfoDTO.getSendMerchantNo());
		condition.setWarehouseId(orderInfoDTO.getWarehouseId());
		condition.setQueryType(orderInfoDTO.getBsQueryType());
		condition.setOrderTimeStart(JxcUtils.getDateFromString(orderInfoDTO.getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(orderInfoDTO.getOrderTimeEnd()));
		condition.setOrderDistribTimeStart(JxcUtils.getDateFromString(orderInfoDTO.getOrderDistribTimeStart()));
		condition.setOrderDistribTimeEnd(JxcUtils.getDateFromString(orderInfoDTO.getOrderDistribTimeEnd()));
		condition.setFactMaterialCode(orderInfoDTO.getFactMaterialCode());
		condition.setFactMaterialName(orderInfoDTO.getFactMaterialName());
		condition.setStartSendTime(orderInfoDTO.getStartSendTime());
		condition.setEndSendTime(orderInfoDTO.getEndSendTime());
		condition.setDeletedFlag("N");
		List<JXCMTOrderInfoDTO> orderInfoDTOList = jxcmtOrderInfoMapper.exportOrderInfo(condition);
		if(null == orderInfoDTOList  || orderInfoDTOList.isEmpty()){
			return orderInfoDTOList;
		}
		List<Integer> listChannelIds = orderInfoDTOList.stream().map(JXCMTOrderInfoDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = jxcmtOrderService.listBsMerchantChannelName(listChannelIds);
		logger.info("mapMerchantChannels:{}",mapMerchantChannels);
		List<String> listMerchantOrder = orderInfoDTOList.stream().map(JXCMTOrderInfoDTO::getMerchantOrder).collect(Collectors.toList());
		List<String> listOrderCode= orderInfoDTOList.stream().map(JXCMTOrderInfoDTO::getDispatchOrderCode).collect(Collectors.toList());
		List<JXCMTBsLogistics> listBsLogistics=jxcmtBsLogisticsService.listBsLogisticsByOrderCodes(listOrderCode);
		Map<String,List<JXCMTBsLogistics>> mapBsLogistics=null;
		if(!StringUtils.isEmpty(listBsLogistics)&&listBsLogistics.size()>0){
			mapBsLogistics=listBsLogistics.stream().collect(Collectors.groupingBy(JXCMTBsLogistics::getServiceCode));
		}
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
		Map<String,JXCMTDeviceType> mapCache = new HashMap<>();
		JXCMTDeviceType jxcmtDeviceType = null;
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		for(JXCMTOrderInfoDTO dto:orderInfoDTOList){
			if(null != dto.getChannelId()){
				dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			}
			if(dto.getDeviceTypeId() == null){
				jxcmtDeviceType = jxcmtAttribManaService.getDeviceTypeByAttribCode(dto.getAttribCode(), mapCache);
				if(null != jxcmtDeviceType){
					dto.setDeviceTypeId(jxcmtDeviceType.getId());
					dto.setDeviceTypeName(jxcmtDeviceType.getName());
				}
			}
			if(StringUtils.isEmpty(dto.getNdScan())){
				dto.setNdScan("Y");
			}
			dto.setLogisticsDto(JXCMTBsLogisticsRpcConvert.convertListBean(mapBsLogistics.get(dto.getDispatchOrderCode())));
			listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
			if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
				continue;
			}
			String optionConfigDesc = "";
			String fastenConfigDesc = "";
			for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
				oConfig.setOption(oConfig.getStrOption());
				attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
				if(null == attribInfo){
					continue;
				}
				if(oConfig.getOption().equals("O")){
					optionConfigDesc += attribInfo.getComment();
					optionConfigDesc += ":";
					optionConfigDesc += attribInfo.getName();
					optionConfigDesc += "/";
				}else if(oConfig.getOption().equals("F")){
					fastenConfigDesc += attribInfo.getComment();
					fastenConfigDesc += ":";
					fastenConfigDesc += attribInfo.getName();
					fastenConfigDesc += "/";
				}
			}
			dto.setFastenConfigDesc(fastenConfigDesc);
			dto.setOptionConfigDesc(optionConfigDesc);
		}
		return orderInfoDTOList;
	}

	private ExportJXCMTOrderInfo generatorExportJXCMTOrderInfo(JXCMTOrderInfoDTO orderInfoDTO){
		ExportJXCMTOrderInfo exportJXCMTOrderInfo=new ExportJXCMTOrderInfo();
		exportJXCMTOrderInfo.setDeviceTypeId(orderInfoDTO.getDeviceTypeId());
		exportJXCMTOrderInfo.setDispatchOrderCode(orderInfoDTO.getDispatchOrderCode());
		exportJXCMTOrderInfo.setStatus(orderInfoDTO.getStatus());
		exportJXCMTOrderInfo.setMerchantOrder(orderInfoDTO.getMerchantOrder());
		exportJXCMTOrderInfo.setSendMerchantNo(orderInfoDTO.getSendMerchantNo());
		exportJXCMTOrderInfo.setWarehouseId(orderInfoDTO.getWarehouseId());
		return exportJXCMTOrderInfo;
	}
	
	public Page<JXCMTOrderInfoDetailDTO> pageDispatchOrderDetail(JXCMTOrderInfoDetailDTO record){
		JXCMTOrderInfo orderInfo = this.getOrderInfoByDispatchOrderCode(record.getDispatchOrderCode());
		if(!StringUtils.isEmpty(orderInfo.getNdScan()) && orderInfo.getNdScan().equals("Y")){
			JXCMTOrderInfoDetail condition = new JXCMTOrderInfoDetail();
			condition.setOrderCode(record.getDispatchOrderCode());
			condition.setIccid(record.getIccid());
			condition.setImei(record.getImei());
			condition.setSn(record.getSn());
			condition.setAttribCode(record.getAttribCode());
			PageHelper.startPage(record.getPageNo(),record.getPageSize());
			return jxcmtOrderInfoDetailMapper.pageDispatchOrderDetail(condition);
		}else{
			return jxcmtBsLogisticsService.pageOrderInfoDetail(record.getDispatchOrderCode(), record.getPageNo(), record.getPageSize());
		}
		
	}
	
	public Page<JXCMTOrderInfoMerchantDTO> pageDispatchOrderMerchant(RpcPagination<JXCMTOrderInfoMerchantDTO> pagination){
		JXCMTOrderInfo condition = new JXCMTOrderInfo();
		condition.setSendMerchantName(pagination.getCondition().getSendMerchantName());
		PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
		return jxcmtOrderInfoMapper.pageOrderInfoMerchant(condition);
	}
	
	
	public JXCMTGhMerchantOrderConfigDTO getGhMerchantOrderConfig(JXCMTGhMerchantOrderConfigDTO record){
		record.setOptionConfigDesc("--");
		record.setFastenConfigDesc("--");
		String merchantOrder = jxcmtOrderService.getBsMerchantOrderCodeByDispatchOrder(record.getDispatchOrderCode());
		if(StringUtils.isEmpty(merchantOrder)){
			return record;
		}
		String ghMerchantOrder = jxcmtGhMerchantOrderService.getGhMerchantOrderByMerchantOrder(merchantOrder);
		if(StringUtils.isEmpty(ghMerchantOrder)){
			return record;
		}
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = jxcmtGhMerchantOrderService.listGhMerchantOrderConfigByGhMerchantOrder(ghMerchantOrder);
		if(null == listGhMerchantOrderConfig){
			return record;
		}
		String optionConfigDesc = "";
		String fastenConfigDesc = "";
		JXCMTAttribInfo attribInfo = null;
		for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
			attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
			if(null == attribInfo){
				continue;
			}
			if(oConfig.getOption().equals("O")){
				optionConfigDesc += attribInfo.getComment();
				optionConfigDesc += ":";
				optionConfigDesc += attribInfo.getName();
				optionConfigDesc += "/";
			}else if(oConfig.getOption().equals("F")){
				fastenConfigDesc += attribInfo.getComment();
				fastenConfigDesc += ":";
				fastenConfigDesc += attribInfo.getName();
				fastenConfigDesc += "/";
			}
		}
		record.setOptionConfigDesc(optionConfigDesc);
		record.setFastenConfigDesc(fastenConfigDesc);
		return record;
	}
	
	
	public JXCMTOrderInfo getOrderInfoByDispatchOrderCode(String dispatchOrderCode){
		if(StringUtils.isEmpty(dispatchOrderCode)){
			return null;
		}
		JXCMTOrderInfo condition = new JXCMTOrderInfo();
		condition.setOrderCode(dispatchOrderCode);
		condition.setDeletedFlag("N");
		return jxcmtOrderInfoMapper.selectOne(condition);
	}
	
	public Integer updateOrderInfo(JXCMTOrderInfo record){
		if(null == record){
			return 0;
		}
		return jxcmtOrderInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	public Integer updateOrderInfoStatusBatch(String status,List<String> listOrderCodes){
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andIn("orderCode", listOrderCodes);
		JXCMTOrderInfo record = new JXCMTOrderInfo();
		record.setStatus(status);
		return jxcmtOrderInfoMapper.updateByExample(record, example);
	}
	
	public Integer updateOrderInfoStatu(String status,String dispatchOrderCode){
		logger.info("updateOrderInfoStatu status:{},dispatchOrderCode:{}",status,dispatchOrderCode);
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andEqualTo("orderCode", dispatchOrderCode);
		JXCMTOrderInfo record = new JXCMTOrderInfo();
		record.setStatus(status);
		return jxcmtOrderInfoMapper.updateByExampleSelective(record, example);
	}
	
	public String convertStatus(String status){
		if(StringUtils.isEmpty(status)){
			return "";
		}
		if(status.equals(DispatchOrderStatusEnum.STATUS_UF.getValue())){
			return "未完成";
		}
		if(status.equals(DispatchOrderStatusEnum.STATUS_OV.getValue())){
			return "已完成";
		}
		if(status.equals(DispatchOrderStatusEnum.STATUS_CL.getValue())){
			return "已取消";
		}
		return "";
	}
	
	private Integer insertOrderInfos(List<JXCMTOrderInfo> listOrderInfos){
		return jxcmtOrderInfoMapper.insertList(listOrderInfos);
	}
	
	private JXCMTOrderInfo generatroOrderInfoY(String orderCode,Integer total,
			String sendMerchantNo,String sendMerchantName,String address,String mobile,String contact,
			String ndScan,Integer warehouseId,
			JXCMTSpOrderDispatchScanYDTO record){
		JXCMTOrderInfo orderInfo = new JXCMTOrderInfo();
		orderInfo.setOrderCode(orderCode);
		orderInfo.setTotal(total);
		orderInfo.setStatus("UF");
		orderInfo.setDeviceId(record.getDeviceId());
		orderInfo.setDeviceName(record.getDeviceName());
		orderInfo.setPackageOne(record.getPackageOne());
		orderInfo.setPackageTwo(record.getPackageOne());
		orderInfo.setAttribCode(record.getMaterialCode());
		orderInfo.setBatch(orderCode);
		orderInfo.setWarehouseId(warehouseId);
		orderInfo.setSendMerchantNo(sendMerchantNo);
		orderInfo.setSendMerchantName(sendMerchantName);
		orderInfo.setAddress(address);
		orderInfo.setContacts(contact);
		orderInfo.setMobile(mobile);
		orderInfo.setRemark(record.getRemark());
		orderInfo.setCreatedBy(record.getConsumer());
		orderInfo.setCreatedDate(JxcUtils.getNowDate());
		orderInfo.setUpdatedBy(record.getConsumer());
		orderInfo.setUpdatedDate(JxcUtils.getNowDate());
		orderInfo.setMaterialCode(record.getMaterialCode());
		orderInfo.setMaterialName(record.getMaterialName());
		orderInfo.setNdScan(ndScan);
		orderInfo.setDeletedFlag("N");
		return orderInfo;
	}
	
	private JXCMTOrderInfo generatroOrderInfoN(String orderCode,Integer total,
			String sendMerchantNo,String sendMerchantName,String address,String mobile,String contact,
			String ndScan,Integer warehouseId,
			JXCMTSpOrderDispatchScanNDTO record){
		
		JXCMTOrderInfo orderInfo = new JXCMTOrderInfo();
		orderInfo.setOrderCode(orderCode);
		orderInfo.setTotal(total);
		orderInfo.setStatus("UF");
		orderInfo.setAttribCode(record.getMaterialCode());
		orderInfo.setBatch(orderCode);
		orderInfo.setWarehouseId(warehouseId);
		orderInfo.setSendMerchantNo(sendMerchantNo);
		orderInfo.setSendMerchantName(sendMerchantName);
		orderInfo.setAddress(address);
		orderInfo.setContacts(contact);
		orderInfo.setMobile(mobile);
		orderInfo.setRemark(record.getRemark());
		orderInfo.setCreatedBy(record.getConsumer());
		orderInfo.setCreatedDate(JxcUtils.getNowDate());
		orderInfo.setUpdatedBy(record.getConsumer());
		orderInfo.setUpdatedDate(JxcUtils.getNowDate());
		orderInfo.setMaterialCode(record.getMaterialCode());
		orderInfo.setMaterialName(record.getMaterialName());
		orderInfo.setNdScan(ndScan);
		orderInfo.setDeletedFlag("N");
		JXCMTMaterialInfo materialInfo = jxcmtMaterialInfoService.getMaterialInfoByMaterialCode(record.getMaterialCode());
		//如果没有类型或者类型为配件的 给个默认小类
		if(materialInfo.getDeviceTypeId() == null || materialInfo.getDeviceTypeId().equals(10)){
			orderInfo.setDeviceId(100120);
		}
		return orderInfo;
	}
	
	private JXCMTOrderInfo generatroOrderInfo(String orderCode,Integer total,
			String sendMerchantNo,String sendMerchantName,String address,String mobile,String contact,
			String ndScan,Integer warehouseId,
			String status,Integer deviceId,String deviceName,
			String packageOne,String materialCode,String materialName,String remark,String consum,Integer sendQulities){
		JXCMTOrderInfo orderInfo = new JXCMTOrderInfo();
		orderInfo.setOrderCode(orderCode);
		orderInfo.setTotal(total);
		orderInfo.setStatus(status);
		orderInfo.setDeviceId(deviceId);
		orderInfo.setDeviceName(deviceName);
		orderInfo.setPackageOne(packageOne);
		orderInfo.setPackageTwo(packageOne);
		orderInfo.setAttribCode(materialCode);
		orderInfo.setBatch(orderCode);
		orderInfo.setWarehouseId(warehouseId);
		orderInfo.setSendMerchantNo(sendMerchantNo);
		orderInfo.setSendMerchantName(sendMerchantName);
		orderInfo.setAddress(address);
		orderInfo.setContacts(contact);
		orderInfo.setMobile(mobile);
		orderInfo.setRemark(remark);
		orderInfo.setCreatedBy(consum);
		orderInfo.setCreatedDate(JxcUtils.getNowDate());
		orderInfo.setUpdatedBy(consum);
		orderInfo.setUpdatedDate(JxcUtils.getNowDate());
		orderInfo.setMaterialCode(materialCode);
		orderInfo.setMaterialName(materialName);
		orderInfo.setNdScan(ndScan);
		orderInfo.setDeletedFlag("N");
		orderInfo.setSendQuanlity(sendQulities);
		return orderInfo;
	}
	
	private JXCMTBsLogistics generatorBsLogistics(String orderNumber,String company,Byte type,String serviceCode,Long receiveId,
			Integer shipmentsQuantity,String consumer,String sendTime){
		JXCMTBsLogistics logistics = new JXCMTBsLogistics();
		logistics.setAccept("N");
		logistics.setCode(JxcUtils.generatorOrderCode(snowflakeWorker));
		logistics.setOrderNumber(orderNumber);
		logistics.setCompany(company);
		logistics.setType(type);
		logistics.setServiceCode(serviceCode);
		logistics.setSendId(null);
		logistics.setReceiveId(receiveId);
		logistics.setCreatedBy(consumer);
		logistics.setCreatedDate(JxcUtils.getNowDate());
		logistics.setUpdatedBy(consumer);
		logistics.setUpdatedDate(JxcUtils.getNowDate());
		logistics.setDeletedFlag("N");
		logistics.setShipmentsQuantity(shipmentsQuantity);
		logistics.setSendTime(sendTime);
		return logistics;
	}

	private Map<String,JXCMTOrderInfo> mapOrderInfoByDispatchOrderCodes(List<String> listDispatchOrderCodes){
		Map<String,JXCMTOrderInfo> mapResult = new HashMap<>();
		Example example = new Example(JXCMTOrderInfo.class);
		example.createCriteria().andIn("orderCode", listDispatchOrderCodes)
								.andEqualTo("deletedFlag", "N");
		List<JXCMTOrderInfo> listOrderInfo = jxcmtOrderInfoMapper.selectByExample(example);
		if(null == listOrderInfo || listOrderInfo.isEmpty()){
			return mapResult;
		}
		for(JXCMTOrderInfo order:listOrderInfo){
			mapResult.put(order.getOrderCode(), order);
		}
		return mapResult;
	}
}
