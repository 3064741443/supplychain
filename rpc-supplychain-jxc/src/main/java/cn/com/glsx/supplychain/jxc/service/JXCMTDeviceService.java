package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.JXCMTCheckImportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOderDeviceDTO;
import cn.com.glsx.supplychain.jxc.enums.*;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceInfoGpsPreimportMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceInfoMapper;
import cn.com.glsx.supplychain.jxc.model.*;
import cn.com.glsx.supplychain.jxc.util.JxcStringUtil;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import cn.com.glsx.supplychain.jxc.vo.CheckImportDataVo;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JXCMTDeviceService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTDeviceService.class);
	@Autowired
	private JXCMTDeviceInfoMapper jxcmtDeviceInfoMapper;
	@Autowired
	private JXCMTDeviceInfoGpsPreimportMapper jxcmtDeviceInfoGpsPreimportMapper;
	@Autowired
	private JXCMTUserInfoService jxcmtUserInfoService;
	@Autowired
	private JXCMTOrderDispatchService jxcmtOrderDispatchService;
	@Autowired
	private JXCMTBsAddressService jxcmtBsAddressService;
	@Autowired
	private JXCMTBsLogisticsService jxcmtBsLogisticsService;
	@Autowired
	private JXCMTOrderService jxcmtOrderService;
	@Autowired
	private JXCMTDeviceFileService jxcmtDeviceFileService;
	@Autowired
	private JXCMTDeviceTypeService jxcmtDeviceTypeService;
	@Autowired
	private JXCMTAttribManaService jxcmtAttribManaService;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
	private JXCMTWarehouseService jxmtWarehouseService;
	@Autowired
	private JXCMTDeviceInfoService jxcmtDeviceInfoService;
	@Autowired
	private OpsMgrManager opsMgrManager;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	
	private Integer handleDevice2ExternSystem(JXCMTOrderInfo orderInfo,JXCMTBsLogistics logistics,List<JXCMTDeviceInfo> listDeviceInfos){
		if(null == orderInfo || null == logistics){
			return 0;
		}
		if(null == listDeviceInfos || listDeviceInfos.isEmpty()){
			return 0;
		}
		if(!jxcmtDeviceTypeService.supportDispatchDeviceByDeviceCode(orderInfo.getDeviceId())){
			return 0;
		}
		JXCMTAttribMana jxcmtAttribMana = jxcmtAttribManaService.getAttribManaByAttribCode(orderInfo.getAttribCode());
		if(null == jxcmtAttribMana){
			return 0;
		}
		if(null == jxcmtAttribMana.getDevMnumId()){
			return 0;
		}
		
		String devMnumName = jxcmtAttribInfoService.getAttribInfoNameById(jxcmtAttribMana.getDevMnumId(), null);
		List<JXCMTDeviceInfoGpsPreimport> listGpsDeviceInfo = new ArrayList<>();
		JXCMTDeviceInfoGpsPreimport gpsDeviceInfo = null;
		String seedTag = "at" + JxcUtils.generatorOrderCode(snowflakeWorker);
		for(JXCMTDeviceInfo deviceInfo:listDeviceInfos){
			gpsDeviceInfo = new JXCMTDeviceInfoGpsPreimport();
			gpsDeviceInfo.setOrderCode(orderInfo.getOrderCode());
			gpsDeviceInfo.setSimCardNo(deviceInfo.getSimCardNo());
			gpsDeviceInfo.setIccid(deviceInfo.getIccid());
			gpsDeviceInfo.setImsi(deviceInfo.getImsi());
			gpsDeviceInfo.setImei(deviceInfo.getImei());
			gpsDeviceInfo.setSn(deviceInfo.getSn());
			gpsDeviceInfo.setModel(devMnumName);
			gpsDeviceInfo.setBatch(deviceInfo.getBatch());
			gpsDeviceInfo.setVcode(deviceInfo.getVcode());
			gpsDeviceInfo.setLogisticsNo(logistics.getOrderNumber());
			gpsDeviceInfo.setLogisticsCpy(logistics.getCompany());
			gpsDeviceInfo.setFactoryName(jxmtWarehouseService.getWarehouseNameById(orderInfo.getWarehouseId()));
			gpsDeviceInfo.setResult("UN");
			gpsDeviceInfo.setResultDesc("");
			gpsDeviceInfo.setSeedTag(seedTag);
			gpsDeviceInfo.setCreatedBy(deviceInfo.getCreatedBy());
			gpsDeviceInfo.setUpdatedBy(deviceInfo.getUpdatedBy());
			gpsDeviceInfo.setCreatedDate(deviceInfo.getCreatedDate());
			gpsDeviceInfo.setUpdatedDate(deviceInfo.getUpdatedDate());
			gpsDeviceInfo.setDeletedFlag("N");
			listGpsDeviceInfo.add(gpsDeviceInfo);
		}
		jxcmtDeviceInfoGpsPreimportMapper.insertList(listGpsDeviceInfo);
		return 0;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer deliveryDirect(JXCMTOderDeviceDTO record) throws RpcServiceException{
		int result = 0;
		JXCMTUseInfo userInfo = jxcmtUserInfoService.getUserInfoByName(record.getUserName());
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("JXCMTDeviceService::deliveryDirect userName is not exist!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("JXCMTDeviceService::deliveryDirect invalid username!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		JXCMTOrderInfo orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(record.getDispatchOrderCode());
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::deliveryDirect " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::deliveryDirect " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "已完成!";
			logger.error("JXCMTDeviceService::deliveryDirect " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 已取消!";
			logger.error("JXCMTDeviceService::deliveryDirect " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//如果是超级用户 放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + record.getDispatchOrderCode() + "用户无权限!";
				logger.error("JXCMTDeviceService::deliveryDirect " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		JXCMTBsAddress address = this.generatorAddress(orderInfo, userInfo);
		JXCMTBsLogistics logistics = this.generatorLogistics(record.getLogisticsNo(), record.getLogisticsCpy(), (byte)5,orderInfo, address, userInfo);
		int logisticsShipments = 0;
		if(null != logistics.getShipmentsQuantity()){
			logisticsShipments = logistics.getShipmentsQuantity();
		}
		logisticsShipments = logisticsShipments + record.getSendQulities();
		logistics.setSendTime(record.getSendTime());
		logistics.setShipmentsQuantity(logisticsShipments);
		
		int sendQulities = (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity());
		sendQulities = sendQulities + record.getSendQulities();
		orderInfo.setSendQuanlity(sendQulities);
		if(sendQulities == orderInfo.getTotal().intValue()){
			orderInfo.setStatus(DispatchOrderStatusEnum.STATUS_OV.getValue());
		}
		result = orderInfo.getTotal().intValue() - sendQulities;
		if(result < 0){
			String message = "订单:" + record.getDispatchOrderCode() + " 超出订单需求总数!";
			logger.error("JXCMTDeviceService::deliveryDirect " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		String merchantOrderCode = jxcmtOrderService.getBsMerchantOrderCodeByDispatchOrder(record.getDispatchOrderCode());
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrderCode);
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrderCode);
		int bsSendQulities = (ecMerchantOrder.getAlreadyShipmentQuantity()==null?0:ecMerchantOrder.getAlreadyShipmentQuantity());
		bsSendQulities += record.getSendQulities();
		int bsCheckQulities = (ecMerchantOrder.getCheckQuantity()==null?0:ecMerchantOrder.getCheckQuantity());
		int bsOwerQulities = bsCheckQulities - bsSendQulities;
		ecMerchantOrder.setOweQuantity(bsOwerQulities);
		ecMerchantOrder.setAlreadyShipmentQuantity(bsSendQulities);
		ecMerchantOrder.setStatus("已发货");
		ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
		bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getCode());
		if(bsOwerQulities == 0){
			ecMerchantOrder.setStatus("完成发货");
			bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
		}
		try{
			jxcmtOrderDispatchService.updateOrderInfo(orderInfo);
			jxcmtBsLogisticsService.updateBsLogisticsSeletive(logistics);
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	} 
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public JXCMTCheckImportDTO importBatchDispatchDevice(JXCMTOderDeviceDTO record) throws RpcServiceException{
		JXCMTCheckImportDTO dtoResult = new JXCMTCheckImportDTO();
		List<JXCMTDeviceInfoDTO> listDeviceInfoSuccess = new ArrayList<>();
		List<JXCMTDeviceInfoDTO> listDeviceInfoFailed = new ArrayList<>();
		JXCMTUseInfo userInfo = jxcmtUserInfoService.getUserInfoByName(record.getUserName());
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice userName is not exist!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice invalid username!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		JXCMTOrderInfo orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(record.getDispatchOrderCode());
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "已完成!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 已取消!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//如果是超级用户 放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + record.getDispatchOrderCode() + "用户无权限!";
				logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		boolean beSupportDispatch = jxcmtDeviceTypeService.supportDispatchDeviceByDeviceCode(orderInfo.getDeviceId());
		int sendQulities = (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity());
		sendQulities = sendQulities + record.getListDeviceInfos().size();
		if(sendQulities > orderInfo.getTotal().intValue()){
			String message = "订单:" + record.getDispatchOrderCode() + " 超出订单需求总数!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		List<String> listSn = record.getListDeviceInfos().stream().map(t->t.getSn()).distinct().collect(Collectors.toList());
		List<JXCMTDeviceInfo> listDeviceInfos = this.listDeviceInfoByListSn(listSn);
		if(null == listDeviceInfos || listDeviceInfos.isEmpty()){
			String message = "所有设备均未入库";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		
		JXCMTBsAddress address = this.generatorAddress(orderInfo, userInfo);
		JXCMTBsLogistics logistics = this.generatorLogistics(record.getLogisticsNo(), record.getLogisticsCpy(),(byte)4, orderInfo, address, userInfo);
		
		Map<String,JXCMTDeviceInfo> mapDeviceInfos = listDeviceInfos.stream().collect(Collectors.toMap(JXCMTDeviceInfo::getSn, a -> a,(k1,k2)->k1));
		Map<String,Integer> mapRepeatRecord = new HashMap<>();
		JXCMTDeviceInfo deviceInfo = null;
		Integer repeatFlag = null;
		String message = "";
		List<JXCMTOrderInfoDetail> listOrderInfoDetail = new ArrayList<>();
		List<String> listUpdateDeviceInfoSn = new ArrayList<>();
		
		List<JXCMTDeviceInfo> listToExternSystemDevice = new ArrayList<>();
		for(JXCMTDeviceInfoDTO dto:record.getListDeviceInfos()){
			repeatFlag = mapRepeatRecord.get(dto.getSn());
			if(null != repeatFlag){
				message = "sn重复";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			deviceInfo = mapDeviceInfos.get(dto.getSn());
			if(null == deviceInfo){
				message = "sn未入库存";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			if(!deviceInfo.getStatus().equals(DeviceEnum.STATUS_IN.getValue())){
				message = "sn已被出库或者未入库存";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			//如果是需要分发到业务系统的数据 imsi simcard vcode 必须要填写
			if(beSupportDispatch){
				if(StringUtils.isEmpty(deviceInfo.getIccid())){
					message = "支持分发业务系统的设备, 入库数据iccid为空, 请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
				if(StringUtils.isEmpty(deviceInfo.getImsi())){
					message = "支持分发业务系统的设备, 入库数据imsi为空 ,请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
				if(StringUtils.isEmpty(deviceInfo.getSimCardNo())){
					message = "支持分发业务系统的设备, 入库数据sim card no为空, 请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
			}
			//老工具扫描的数据不做仓库检测
			if(!deviceInfo.getWarehouseIdUp().equals(5)){
				//超级用户不做仓库验证
				if(userInfo.getIsSup() == 0){
					if(!deviceInfo.getWarehouseIdUp().equals(orderInfo.getWarehouseId())){
						message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + deviceInfo.getSn() + "订单仓库与设备现存仓库不匹配";
						logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
						dto.setFailedReason(message);
						listDeviceInfoFailed.add(dto);
						continue;
					}
				}
				//扫码出库无需校验SN入库时的物料编码与出库的物料编码一致性	
//				if(!deviceInfo.getAttribCode().equals(orderInfo.getAttribCode())){
//					message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + deviceInfo.getSn() + "订单配置编码:" + orderInfo.getAttribCode() + " 设备编码:" + deviceInfo.getAttribCode() + " 订单配置码与设备配置码不匹配";
//					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
//					dto.setFailedReason(message);
//					listDeviceInfoFailed.add(dto);
//				}
			}
			
			if(!StringUtils.isEmpty(dto.getIccid())){
				if(!dto.getIccid().equals(deviceInfo.getIccid())){		
					message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "入库iccid:" + dto.getIccid() + " 库存iccid:" + deviceInfo.getIccid() + " 不一致";			
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
				}
			}
			mapRepeatRecord.put(dto.getSn(), 1);
			listDeviceInfoSuccess.add(dto);
			listUpdateDeviceInfoSn.add(dto.getSn());
			listOrderInfoDetail.add(this.generatorOrderInfoDetail(deviceInfo, orderInfo, logistics, userInfo));
			listToExternSystemDevice.add(deviceInfo);
		}
		dtoResult.setListDeviceInfoFailed(listDeviceInfoFailed);
		dtoResult.setListDeviceInfoSuccess(listDeviceInfoSuccess);
		if(listDeviceInfoSuccess.isEmpty()){
			dtoResult.setParatope(orderInfo.getTotal() - (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity()));
			return dtoResult;
		}
		
		int logisticsShipments = 0;
		if(null != logistics.getShipmentsQuantity()){
			logisticsShipments = logistics.getShipmentsQuantity();
		}
		logisticsShipments = logisticsShipments + listDeviceInfoSuccess.size();
		logistics.setSendTime(JxcUtils.getStringFromDate(JxcUtils.getNowDate()));
		logistics.setShipmentsQuantity(logisticsShipments);
		
		sendQulities = (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity());
		sendQulities = sendQulities + listDeviceInfoSuccess.size();
		orderInfo.setSendQuanlity(sendQulities);
		if(sendQulities == orderInfo.getTotal().intValue()){
			orderInfo.setStatus(DispatchOrderStatusEnum.STATUS_OV.getValue());
		}
		dtoResult.setParatope(orderInfo.getTotal().intValue() - sendQulities);
		String merchantOrderCode = jxcmtOrderService.getBsMerchantOrderCodeByDispatchOrder(record.getDispatchOrderCode());
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrderCode);
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrderCode);
		int bsSendQulities = (ecMerchantOrder.getAlreadyShipmentQuantity()==null?0:ecMerchantOrder.getAlreadyShipmentQuantity());
		bsSendQulities += listDeviceInfoSuccess.size();
		int bsCheckQulities = (ecMerchantOrder.getCheckQuantity()==null?0:ecMerchantOrder.getCheckQuantity());
		int bsOwerQulities = bsCheckQulities - bsSendQulities;
		ecMerchantOrder.setOweQuantity(bsOwerQulities);
		ecMerchantOrder.setAlreadyShipmentQuantity(bsSendQulities);
		ecMerchantOrder.setStatus("已发货");
		ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
		bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getCode());
		if(bsOwerQulities == 0){
			ecMerchantOrder.setStatus("完成发货");
			bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
		}
		try{
			this.batchUpdateDeviceInfoStatus(DeviceEnum.STATUS_OUT.getValue(), userInfo.getUserName(), record.getDispatchOrderCode(), orderInfo.getWarehouseId(), listUpdateDeviceInfoSn);
			jxcmtOrderDispatchService.batchAddDispatchOrderDetail(listOrderInfoDetail);
			jxcmtOrderDispatchService.updateOrderInfo(orderInfo);
			jxcmtBsLogisticsService.updateBsLogisticsSeletive(logistics);
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			this.handleDevice2ExternSystem(orderInfo,logistics,listToExternSystemDevice);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}		
		return dtoResult;
		
	}
	
	public JXCMTCheckImportDTO checkBatchDispatchDevice(JXCMTOderDeviceDTO record) throws RpcServiceException{
		JXCMTCheckImportDTO dtoResult = new JXCMTCheckImportDTO();
		List<JXCMTDeviceInfoDTO> listDeviceInfoSuccess = new ArrayList<>();
		List<JXCMTDeviceInfoDTO> listDeviceInfoFailed = new ArrayList<>();
		JXCMTUseInfo userInfo = jxcmtUserInfoService.getUserInfoByName(record.getUserName());
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice userName is not exist!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice invalid username!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		JXCMTOrderInfo orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(record.getDispatchOrderCode());
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "已完成!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 已取消!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//如果是超级用户 放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + record.getDispatchOrderCode() + "用户无权限!";
				logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		boolean beSupportDispatch = jxcmtDeviceTypeService.supportDispatchDeviceByDeviceCode(orderInfo.getDeviceId());
		int sendQulities = (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity());
		sendQulities = sendQulities + record.getListDeviceInfos().size();
		if(sendQulities > orderInfo.getTotal().intValue()){
			String message = "订单:" + record.getDispatchOrderCode() + " 超出订单需求总数!";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		List<String> listSn = record.getListDeviceInfos().stream().map(t->t.getSn()).distinct().collect(Collectors.toList());
		List<JXCMTDeviceInfo> listDeviceInfos = this.listDeviceInfoByListSn(listSn);
		if(null == listDeviceInfos || listDeviceInfos.isEmpty()){
			String message = "所有设备均未入库";
			logger.error("JXCMTDeviceService::checkBatchDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		Map<String,JXCMTDeviceInfo> mapDeviceInfos = listDeviceInfos.stream().collect(Collectors.toMap(JXCMTDeviceInfo::getSn, a -> a,(k1,k2)->k1));
		Map<String,Integer> mapRepeatRecord = new HashMap<>();
		JXCMTDeviceInfo deviceInfo = null;
		Integer repeatFlag = null;
		String message = "";
		for(JXCMTDeviceInfoDTO dto:record.getListDeviceInfos()){
			repeatFlag = mapRepeatRecord.get(dto.getSn());
			if(null != repeatFlag){
				message = "sn重复";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			deviceInfo = mapDeviceInfos.get(dto.getSn());
			if(null == deviceInfo){
				message = "sn未入库存";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			if(!deviceInfo.getStatus().equals(DeviceEnum.STATUS_IN.getValue())){
				message = "sn已被出库或者未入库存";
				logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
				dto.setFailedReason(message);
				listDeviceInfoFailed.add(dto);
				continue;
			}
			//老工具扫描的数据不做仓库检测
			if(!deviceInfo.getWarehouseIdUp().equals(5)){
				//超级用户不做仓库验证
				if(userInfo.getIsSup() == 0){
					if(!deviceInfo.getWarehouseIdUp().equals(orderInfo.getWarehouseId())){
						message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + deviceInfo.getSn() + "订单仓库与设备现存仓库不匹配";
						logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
						dto.setFailedReason(message);
						listDeviceInfoFailed.add(dto);
						continue;
					}
				}
	//扫码出库无需校验SN入库时的物料编码与出库的物料编码一致性			
//				if(!deviceInfo.getAttribCode().equals(orderInfo.getAttribCode())){
//					message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + deviceInfo.getSn() + "订单配置编码:" + orderInfo.getAttribCode() + " 设备编码:" + deviceInfo.getAttribCode() + " 订单配置码与设备配置码不匹配";
//					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
//					dto.setFailedReason(message);
//					listDeviceInfoFailed.add(dto);
//				}
			}
			
			if(!StringUtils.isEmpty(dto.getIccid())){
				if(!dto.getIccid().equals(deviceInfo.getIccid())){		
					message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "入库iccid:" + dto.getIccid() + " 库存iccid:" + deviceInfo.getIccid() + " 不一致";			
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
				}
			}
			
			//如果是需要分发到业务系统的数据 imsi simcard vcode 必须要填写
			if(beSupportDispatch){
				if(StringUtils.isEmpty(deviceInfo.getIccid())){
					message = "支持分发业务系统的设备, 入库数据iccid为空, 请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
				if(StringUtils.isEmpty(deviceInfo.getImsi())){
					message = "支持分发业务系统的设备, 入库数据imsi为空 ,请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
				if(StringUtils.isEmpty(deviceInfo.getSimCardNo())){
					message = "支持分发业务系统的设备, 入库数据sim card no为空, 请从设备库存管理导入入库";
					logger.info("JXCMTDeviceService::checkBatchDispatchDevice " + message);
					dto.setFailedReason(message);
					listDeviceInfoFailed.add(dto);
					continue;
				}
			}
			listDeviceInfoSuccess.add(dto);
		}
		dtoResult.setListDeviceInfoFailed(listDeviceInfoFailed);
		dtoResult.setListDeviceInfoSuccess(listDeviceInfoSuccess);
		return dtoResult;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer cancelDispatchDevice(JXCMTOderDeviceDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTUseInfo userInfo = jxcmtUserInfoService.getUserInfoByName(record.getUserName());
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("JXCMTDeviceService::cancelDispatchDevice userName is not exist!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("JXCMTDeviceService::cancelDispatchDevice invalid username!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		//获取设备入库信息
		JXCMTDeviceInfo tempDeviceInfo = getDeviceInfo(record.getListDeviceInfos().get(0).getSn());
		if(null == tempDeviceInfo){
			String message = "设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "未入库";
			logger.error("JXCMTDeviceService::cancelDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//超级用户放开权限
		if(userInfo.getIsSup() == 0){
			if(!tempDeviceInfo.getWarehouseIdUp().equals(userInfo.getWarehouseId())){
				logger.error("JXCMTDeviceService::cancelDispatchDevice invalid username!");
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
		}
		if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_IN.getValue())){
			String message = "设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "未出库";
			logger.error("JXCMTDeviceService::cancelDispatchDevice " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		
		String dispatchOrderCode = tempDeviceInfo.getOrderCode();
		
		JXCMTDeviceFileSnapshot deviceFileSnapshot = jxcmtDeviceFileService.getDeviceFileSnapshotBySn(record.getListDeviceInfos().get(0).getSn());
		if(null != deviceFileSnapshot){
			if(deviceFileSnapshot.getPackageStatu().equals(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue()) ||
					!StringUtils.isEmpty(deviceFileSnapshot.getUserId()) ||
					!StringUtils.isEmpty(deviceFileSnapshot.getPackageUserId())){
				String message = "设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "已经被激活或者被用户绑定";
				logger.error("JXCMTDeviceService::cancelDispatchDevice " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			deviceFileSnapshot.setDeletedFlag("Y");
			deviceFileSnapshot.setUpdatedBy(userInfo.getUserName());
			deviceFileSnapshot.setUpdatedDate(JxcUtils.getNowDate());
		}
		JXCMTDeviceFile deviceFile = jxcmtDeviceFileService.getDeviceFileBySn(record.getListDeviceInfos().get(0).getSn());
		if(null != deviceFile){
			deviceFile.setDeletedFlag("Y");
			deviceFile.setUpdatedBy(userInfo.getUserName());
			deviceFile.setUpdatedDate(JxcUtils.getNowDate());
		}
		tempDeviceInfo.setOrderCode("");
		tempDeviceInfo.setStatus(DeviceEnum.STATUS_IN.getValue());
		tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
		tempDeviceInfo.setUpdatedDate(JxcUtils.getNowDate());
		
		JXCMTOrderInfo orderInfo = null;
		JXCMTBsLogistics bsLogistics = null;
		JXCMTBsMerchantOrder bsMerchantOrder = null;
		JXCMTEcMerchantOrder ecMerchantOrder = null;
		JXCMTOrderInfoDetail orderInfoDetail = jxcmtOrderDispatchService.getDispatchOrderDetailBySn(record.getListDeviceInfos().get(0).getSn(),dispatchOrderCode);
		if(null != orderInfoDetail){
			if(null != orderInfoDetail.getLogisticsId()){
				bsLogistics = jxcmtBsLogisticsService.getBsLogisticsById(orderInfoDetail.getLogisticsId().longValue());
				if(null != bsLogistics){
					bsLogistics.setShipmentsQuantity(bsLogistics.getShipmentsQuantity() - record.getListDeviceInfos().size());
					//如果被签收 拒绝删除出库
					if(null != bsLogistics.getAccept()){
						if(bsLogistics.getAccept().equals("Y")){
							String message = "设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "已被签收";
							logger.error("JXCMTDeviceService::cancelDispatchDevice " + message);
							JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
							throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
						}
					}
				}
			}
			if(!StringUtils.isEmpty(orderInfoDetail.getOrderCode())){
				orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(orderInfoDetail.getOrderCode());
				if(null != orderInfo){
					String merchantOrderCode = jxcmtOrderService.getBsMerchantOrderCodeByDispatchOrder(orderInfo.getOrderCode());
					bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrderCode);
					ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrderCode);
					orderInfo.setStatus(DispatchOrderStatusEnum.STATUS_UF.getValue());
					orderInfo.setSendQuanlity(orderInfo.getSendQuanlity() - record.getListDeviceInfos().size());
					ecMerchantOrder.setAlreadyShipmentQuantity(ecMerchantOrder.getAlreadyShipmentQuantity()-record.getListDeviceInfos().size());
					if(ecMerchantOrder.getAlreadyShipmentQuantity() <= 0){
						ecMerchantOrder.setStatus("待发货");
						bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
						bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getCode());
						ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getName());
					}else{
						ecMerchantOrder.setStatus("部分发货");
						bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
					}
					result = orderInfo.getTotal() - orderInfo.getSendQuanlity();
				}
			}
		}
		try{		
			this.updateDeviceInfoAll(tempDeviceInfo);
			jxcmtDeviceFileService.updateDeviceFile(deviceFile);
			jxcmtDeviceFileService.updateDeviceFileSnapshot(deviceFileSnapshot);
			jxcmtOrderDispatchService.delDispatchOrderDetail(orderInfoDetail);
			jxcmtOrderDispatchService.updateOrderInfo(orderInfo);
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			if(bsLogistics != null)
			{
				//如果已发被删成0 直接删除这个物流单
				int shipmentQuantity = ((bsLogistics.getShipmentsQuantity()==null)?0:bsLogistics.getShipmentsQuantity());
				if(shipmentQuantity <= 0){
					jxcmtBsLogisticsService.delBsLogisticsById(bsLogistics.getId());
				}else{
					jxcmtBsLogisticsService.updateBsLogisticsSeletive(bsLogistics);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer dispatchDeviceDispatch(JXCMTOderDeviceDTO record) throws RpcServiceException{
		Integer result = 0;
		//获取操作员信息
		JXCMTUseInfo userInfo = jxcmtUserInfoService.getUserInfoByName(record.getUserName());
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch userName is not exist!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch invalid username!");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		JXCMTOrderInfo orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(record.getDispatchOrderCode());
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "不存在或者无效!";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + "已完成!";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(DispatchOrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 已取消!";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//如果是超级用户 放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + record.getDispatchOrderCode() + "用户无权限!";
				logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		
		boolean beSupportDispatch = jxcmtDeviceTypeService.supportDispatchDeviceByDeviceCode(orderInfo.getDeviceId());
		
		JXCMTDeviceInfo tempDeviceInfo = getDeviceInfo(record.getListDeviceInfos().get(0).getSn());
		if(StringUtils.isEmpty(tempDeviceInfo))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "未入库";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);	
		}
		if(tempDeviceInfo.getDeletedFlag().equals("Y"))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "已被初始化,但未被入库";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
		{
			String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "已出库";
			logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
			JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		
		if(!StringUtils.isEmpty(record.getListDeviceInfos().get(0).getIccid())){
			if(!record.getListDeviceInfos().get(0).getIccid().equals(tempDeviceInfo.getIccid())){
				String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "入库iccid:" + record.getListDeviceInfos().get(0).getIccid() + " 库存iccid:" + tempDeviceInfo.getIccid() + " 不一致";
				logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		//老工具扫描的数据不做仓库检测
		if(!tempDeviceInfo.getWarehouseIdUp().equals(5))
		{
			//超级用户不做仓库验证
			if(userInfo.getIsSup() == 0)
			{
				if(!tempDeviceInfo.getWarehouseIdUp().equals(orderInfo.getWarehouseId()))
				{
					String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "订单仓库与设备现存仓库不匹配";
					logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
					JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			//扫码出库无需校验SN入库时的物料编码与出库的物料编码一致性
			/*if(!tempDeviceInfo.getAttribCode().equals(orderInfo.getAttribCode()))
			{
				String message = "订单:" + record.getDispatchOrderCode() + " 设备sn/imei:" + record.getListDeviceInfos().get(0).getSn() + "订单配置编码:" + orderInfo.getAttribCode() + " 设备编码:" + tempDeviceInfo.getAttribCode() + " 订单配置码与设备配置码不匹配";
				logger.error("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}*/
		}
		
		//如果是需要分发到业务系统的数据 imsi simcard vcode 必须要填写
		if(beSupportDispatch){
			if(StringUtils.isEmpty(tempDeviceInfo.getIccid())){
				String message = "支持分发业务系统的设备, 入库数据iccid为空, 请从设备库存管理导入入库";
				logger.info("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(StringUtils.isEmpty(tempDeviceInfo.getImsi())){
				String message = "支持分发业务系统的设备, 入库数据imsi为空 ,请从设备库存管理导入入库";
				logger.info("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(StringUtils.isEmpty(tempDeviceInfo.getSimCardNo())){
				String message = "支持分发业务系统的设备, 入库数据sim card no为空, 请从设备库存管理导入入库";
				logger.info("JXCMTDeviceService::dispatchDeviceDispatch " + message);
				JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		
		JXCMTBsAddress address = this.generatorAddress(orderInfo, userInfo);
		JXCMTBsLogistics logistics = this.generatorLogistics(record.getLogisticsNo(), record.getLogisticsCpy(),(byte)4, orderInfo, address, userInfo);
		JXCMTOrderInfoDetail orderInfoDetail = this.generatorOrderInfoDetail(tempDeviceInfo,orderInfo,logistics,userInfo);
		
		tempDeviceInfo.setStatus(DeviceEnum.STATUS_OUT.getValue());
		tempDeviceInfo.setWarehouseIdUp(tempDeviceInfo.getWarehouseId());
		tempDeviceInfo.setOrderCode(orderInfo.getOrderCode());
		tempDeviceInfo.setDeletedFlag("N");
		tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
		tempDeviceInfo.setUpdatedDate(JxcUtils.getNowDate());
		
		int logisticsShipments = 0;
		if(null != logistics.getShipmentsQuantity()){
			logisticsShipments = logistics.getShipmentsQuantity();
		}
		logisticsShipments = logisticsShipments + record.getListDeviceInfos().size();
		logistics.setSendTime(JxcUtils.getStringFromDate(JxcUtils.getNowDate()));
		logistics.setShipmentsQuantity(logisticsShipments);
		
		int sendQulities = (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity());
		sendQulities = sendQulities + record.getListDeviceInfos().size();
		orderInfo.setSendQuanlity(sendQulities);
		if(sendQulities == orderInfo.getTotal().intValue()){
			orderInfo.setStatus(DispatchOrderStatusEnum.STATUS_OV.getValue());
		}
		result = orderInfo.getTotal().intValue() - sendQulities;
		
		String merchantOrderCode = jxcmtOrderService.getBsMerchantOrderCodeByDispatchOrder(record.getDispatchOrderCode());
		JXCMTBsMerchantOrder bsMerchantOrder = jxcmtOrderService.getBsMerchantOrderByBsMerchantOrder(merchantOrderCode);
		JXCMTEcMerchantOrder ecMerchantOrder = jxcmtOrderService.getEcMerchantOrderByBsMerchantOrder(merchantOrderCode);
		
		int bsSendQulities = (ecMerchantOrder.getAlreadyShipmentQuantity()==null?0:ecMerchantOrder.getAlreadyShipmentQuantity());
		bsSendQulities += record.getListDeviceInfos().size();
		int bsCheckQulities = (ecMerchantOrder.getCheckQuantity()==null?0:ecMerchantOrder.getCheckQuantity());
		int bsOwerQulities = bsCheckQulities - bsSendQulities;
		ecMerchantOrder.setOweQuantity(bsOwerQulities);
		ecMerchantOrder.setAlreadyShipmentQuantity(bsSendQulities);
		ecMerchantOrder.setStatus("已发货");
		ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
		bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getCode());
		if(bsOwerQulities == 0){
			ecMerchantOrder.setStatus("完成发货");
			bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
		}
		try{
			List<JXCMTDeviceInfo> listToExternSystemDevice = new ArrayList<>();
			listToExternSystemDevice.add(tempDeviceInfo);
			this.updateDeviceInfo(tempDeviceInfo);
			jxcmtOrderDispatchService.addDispatchOrderDetail(orderInfoDetail);
			jxcmtOrderDispatchService.updateOrderInfo(orderInfo);
			jxcmtBsLogisticsService.updateBsLogisticsSeletive(logistics);
			jxcmtOrderService.updateBsMerchantOrder(bsMerchantOrder);
			jxcmtOrderService.updateEcMerchantOrder(ecMerchantOrder);
			this.handleDevice2ExternSystem(orderInfo,logistics,listToExternSystemDevice);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	
	public Integer updateDeviceInfoAll(JXCMTDeviceInfo deviceInfo){
		return jxcmtDeviceInfoMapper.updateByPrimaryKey(deviceInfo);
	}
	
	public Integer updateDeviceInfo(JXCMTDeviceInfo deviceInfo){
		return jxcmtDeviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);
	}
	
	public JXCMTDeviceInfo getDeviceInfo(String sn){
		JXCMTDeviceInfo condition = new JXCMTDeviceInfo();
		condition.setSn(sn);
		condition.setDeletedFlag("N");
		return jxcmtDeviceInfoMapper.selectOne(condition);
	}
	
	private List<JXCMTDeviceInfo> listDeviceInfoByListSn(List<String> listSn){
		Example example = new Example(JXCMTDeviceInfo.class);
		example.createCriteria().andIn("sn", listSn)
								.andEqualTo("deletedFlag", "N");
		return jxcmtDeviceInfoMapper.selectByExample(example);
	}
	
	private Integer batchUpdateDeviceInfoStatus(String status,String userName,String orderCode,Integer warehouseId,List<String> listSn){
		JXCMTDeviceInfo deviceInfo = new JXCMTDeviceInfo();
		deviceInfo.setStatus(status);
		deviceInfo.setUpdatedBy(userName);
		deviceInfo.setUpdatedDate(JxcUtils.getNowDate());
		deviceInfo.setOrderCode(orderCode);
		deviceInfo.setWarehouseIdUp(warehouseId);
		Example example = new Example(JXCMTDeviceInfo.class);
		example.createCriteria().andIn("sn", listSn);
		return jxcmtDeviceInfoMapper.updateByExampleSelective(deviceInfo, example);
	}
	
	
	private JXCMTBsAddress generatorAddress(JXCMTOrderInfo orderInfo,JXCMTUseInfo userInfo){
		JXCMTBsAddress address = new JXCMTBsAddress();
		address.setName(orderInfo.getContacts());
		address.setMobile(orderInfo.getMobile());
		address.setAddress(orderInfo.getAddress());
		address.setMerchantCode(orderInfo.getSendMerchantNo());
		address.setCreatedBy(userInfo.getUserName());
		address.setUpdatedBy(userInfo.getUserName());
		address.setCreatedDate(JxcUtils.getNowDate());
		address.setUpdatedDate(JxcUtils.getNowDate());
		address.setDeletedFlag("N");
		return jxcmtBsAddressService.addIfNotExist(address);
	}
	
	private JXCMTBsLogistics generatorLogistics(String logisticsNum,String logisticsCpy,byte logisticsType,
			JXCMTOrderInfo orderInfo,JXCMTBsAddress address,JXCMTUseInfo userInfo){
		JXCMTBsLogistics logistics = new JXCMTBsLogistics();
		logistics.setCode(Constants.LOGI_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker));
		logistics.setServiceCode(orderInfo.getOrderCode());
		logistics.setReceiveId(address.getId());
		logistics.setType(logisticsType);
		logistics.setOrderNumber(logisticsNum);
		logistics.setCompany(logisticsCpy);
		logistics.setAccept("N");
		logistics.setCreatedBy(userInfo.getUserName());
		logistics.setUpdatedBy(userInfo.getUserName());
		logistics.setCreatedDate(JxcUtils.getNowDate());
		logistics.setUpdatedDate(JxcUtils.getNowDate());
		logistics.setDeletedFlag("N");
		return jxcmtBsLogisticsService.addIfNotExist(logistics);
	}
	
	private JXCMTOrderInfoDetail generatorOrderInfoDetail(JXCMTDeviceInfo deviceInfo,JXCMTOrderInfo orderInfo,
			JXCMTBsLogistics logistics,JXCMTUseInfo userInfo){
		JXCMTOrderInfoDetail orderInfoDetail = new JXCMTOrderInfoDetail();
		orderInfoDetail.setOrderCode(orderInfo.getOrderCode());
		orderInfoDetail.setIccid(deviceInfo.getIccid());
		orderInfoDetail.setImei(deviceInfo.getImei());
		orderInfoDetail.setSn(deviceInfo.getSn());
		orderInfoDetail.setAttribCode(deviceInfo.getAttribCode());
		orderInfoDetail.setBatch(deviceInfo.getBatch());
		orderInfoDetail.setWarehouseId(deviceInfo.getWarehouseId());
		orderInfoDetail.setWarehouseIdUp(deviceInfo.getWarehouseId());
		orderInfoDetail.setCreatedBy(userInfo.getUserName());
		orderInfoDetail.setUpdatedBy(userInfo.getUserName());
		orderInfoDetail.setCreatedDate(JxcUtils.getNowDate());
		orderInfoDetail.setUpdatedDate(JxcUtils.getNowDate());
		orderInfoDetail.setLogisticsId(logistics.getId().intValue());
		orderInfoDetail.setDeletedFlag("N");
		return orderInfoDetail;
	}

	/**
	 * @param factoryName
	 * @author: luoqiang
	 * @description: 判断仓库名是否存在
	 * @date: 2020/9/10 14:46
	 * @return: boolean
	 */
	private boolean isFactoryNameExists(String factoryName) {
		if (StringUtils.isEmpty(factoryName)) {
			return false;
		}
		JXCMTWarehouseInfo wareHouseInfo = jxmtWarehouseService.getWarehouseByName(factoryName);
		if (StringUtils.isEmpty(wareHouseInfo)) {
			return false;
		}
		return true;
	}

	/**
	 * @author: luoqiang
	 * @description: 判断属性配置物料是否存在
	 * @date: 2020/9/10 16:35
	 * @param attribCode
	 * @return: boolean
	 */
	private boolean isAttribManaExists(String attribCode) {
		if (StringUtils.isEmpty(attribCode)) {
			return false;
		}
		JXCMTAttribMana attribMana = jxcmtAttribManaService.getAttribManaByAttribCode(attribCode);
		if (StringUtils.isEmpty(attribMana)) {
			return false;
		}
		return true;
	}

	public CheckImportDataVo checkImportDeviceList(List<JXCMTDeviceInfoImport> importList) {
		CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
		List<JXCMTDeviceInfoImport> deviceInfoImportSuccessList = new ArrayList<>();
		List<JXCMTDeviceInfoImport> deviceInfoImportFailList = new ArrayList<>();
		List<JXCMTAttribMana> jxcmtAttribManaList = new ArrayList<>();
		List<String> attribCodes = new ArrayList<>();
		Map<String, Integer> mapSn = new HashMap<String, Integer>();
		Map<String, Integer> mapImei = new HashMap<String, Integer>();
		Map<String, Integer> mapIccid = new HashMap<String, Integer>();
		Map<String, Integer> mapImsi=new HashMap<>();
		Map<String, JXCMTDeviceInfo>  snDeviceInfoMap=null;
		Map<String, JXCMTDeviceInfo>  iccidDeviceInfoMap=null;
		Map<String, JXCMTDeviceInfo>  imeiDeviceInfoMap=null;
       // Map<String, Flowcard> iccidFlowCardMap=null;
		Map<String, JXCMTWarehouseInfo> warehouseInfoMap = new HashMap<>();
		Map<String, JXCMTWarehouseInfo> warehouseInfoUpMap = new HashMap<>();
		List<String> warehouseInfoNames = new ArrayList<>();
		List<String> warehouseInfoUpNames = new ArrayList<>();
		List<String> listSn = new ArrayList<String>();
		List<String> listIccid = new ArrayList<String>();
		List<String> listImei=new ArrayList<>();
		List<JXCMTDeviceInfo> deviceInfoList = null;
		JXCMTAttribMana attribMana=null;
		for (JXCMTDeviceInfoImport deviceInfoImport : importList) {
			warehouseInfoNames.add(deviceInfoImport.getWareHouseName());
			warehouseInfoUpNames.add(deviceInfoImport.getWareHouseUpName());
		}
		warehouseInfoMap = jxmtWarehouseService.listMapWareHouseInfo(warehouseInfoNames);
		warehouseInfoUpMap = jxmtWarehouseService.listMapWareHouseInfo(warehouseInfoUpNames);
		for (JXCMTDeviceInfoImport deviceInfoImport : importList) {
			if (!StringUtils.isEmpty(deviceInfoImport.getSn())) {
				Integer count = mapSn.get(deviceInfoImport.getSn());
				if (count == null || count == 0) {
					mapSn.put(deviceInfoImport.getSn(), 1);
				} else {
					count++;
					mapSn.put(deviceInfoImport.getSn(), count);
				}
			}

			if (!StringUtils.isEmpty(deviceInfoImport.getImei())) {
				Integer count = mapImei.get(deviceInfoImport.getImei());
				if (count == null || count == 0) {
					mapImei.put(deviceInfoImport.getImei(), 1);
				} else {
					count++;
					mapImei.put(deviceInfoImport.getImei(), count);
				}
			}

			if (!StringUtils.isEmpty(deviceInfoImport.getIccid())) {
				Integer count = mapIccid.get(deviceInfoImport.getIccid());
				if (count == null || count == 0) {
					mapIccid.put(deviceInfoImport.getIccid(), 1);
				} else {
					count++;
					mapIccid.put(deviceInfoImport.getIccid(), count);
				}
			}

			if (!StringUtils.isEmpty(deviceInfoImport.getImsi())) {
				Integer count = mapImsi.get(deviceInfoImport.getImsi());
				if (count == null || count == 0) {
					mapImsi.put(deviceInfoImport.getImsi(), 1);
				} else {
					count++;
					mapImsi.put(deviceInfoImport.getImsi(), count);
				}
			}
		}

		// 计算sn/iccid/imei在系统中是否入库
		listSn.addAll(mapSn.keySet());
		listIccid.addAll(mapIccid.keySet());
		listImei.addAll(mapImei.keySet());
		deviceInfoList =jxcmtDeviceInfoService.listDeviceInfoBySn(listSn);
		if (!StringUtils.isEmpty(deviceInfoList)) {
			for (JXCMTDeviceInfo deviceInfo : deviceInfoList) {
				Integer count = mapSn.get(deviceInfo.getSn());
				count++;
				mapSn.put(deviceInfo.getSn(), count);
			}
			deviceInfoList.clear();
		}
		deviceInfoList =jxcmtDeviceInfoService.listDeviceInfoByIccids(listIccid);
		if (!StringUtils.isEmpty(deviceInfoList)) {
			for (JXCMTDeviceInfo deviceInfo : deviceInfoList) {
				Integer count = mapIccid.get(deviceInfo.getIccid());
				count++;
				mapIccid.put(deviceInfo.getIccid(), count);
			}
		}

		deviceInfoList =jxcmtDeviceInfoService.listDeviceInfoByImei(listImei);
		if (!StringUtils.isEmpty(deviceInfoList)) {
			for (JXCMTDeviceInfo deviceInfo : deviceInfoList) {
				Integer count = mapImei.get(deviceInfo.getImei());
				count++;
				mapImei.put(deviceInfo.getImei(), count);
			}
		}
		/*FlowCardRequest flowCardRequest=new FlowCardRequest();
		String []strings=listIccid.stream().toArray(String[]::new);
		//String []strings=(String[]) listIccid.toArray();
		flowCardRequest.setIccids(strings);
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		flowCardRequest.setTime(time);  //时间
		flowCardRequest.setInvoker("glsx");
		flowCardRequest.setVersion("2.2.0"); //版本号
		flowCardRequest.setConsumer("task-supplychain"); //项目名称
		try {
			FlowResponse<List<Flowcard>> response=opsMgrManager.getFlowCardBatch(flowCardRequest);
			iccidFlowCardMap=response.getEntiy().stream().collect(Collectors.toMap(Flowcard::getIccid, Function.identity(), (key1, key2) -> key2));

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		snDeviceInfoMap=jxcmtDeviceInfoService.listMapDeviceInfoBySn(listSn);
		//iccidDeviceInfoMap=jxcmtDeviceInfoService.listMapDeviceInfoByIccids(listIccid);
		imeiDeviceInfoMap=jxcmtDeviceInfoService.listMapDeviceInfoByImei(listImei);
		for (JXCMTDeviceInfoImport deviceInfoImport : importList) {
			if (StringUtils.isEmpty(deviceInfoImport.getAttribCode())) {
				deviceInfoImport.setResultDesc("物料编码不能为空");
				deviceInfoImportFailList.add(deviceInfoImport);
				continue;
			}

			if (StringUtils.isEmpty(deviceInfoImport.getVcode())) {
				deviceInfoImport.setResultDesc("验证码不能为空");
				deviceInfoImportFailList.add(deviceInfoImport);
				continue;
			}


			if (StringUtils.isEmpty(deviceInfoImport.getWareHouseName())) {
				deviceInfoImport.setResultDesc("生产工厂不能为空");
				deviceInfoImportFailList.add(deviceInfoImport);
				continue;
			}

			if (StringUtils.isEmpty(deviceInfoImport.getWareHouseUpName())) {
				deviceInfoImport.setResultDesc("当前所在工厂/仓库");
				deviceInfoImportFailList.add(deviceInfoImport);
				continue;
			}

			// 验证属性配置物料是否存在
			if (!isAttribManaExists(deviceInfoImport.getAttribCode())) {
				deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_ATTRIB_MANA.getValue());
				deviceInfoImportFailList.add(deviceInfoImport);
				continue;
			}else{
				attribMana = jxcmtAttribManaService.getAttribManaByAttribCode(deviceInfoImport.getAttribCode());
				if(attribMana.getDevTypeId()==2||attribMana.getDevTypeId()==12134){
					if(attribMana.getOrNetId()==57){
						if(StringUtils.isEmpty(deviceInfoImport.getSn())) {
							deviceInfoImport.setResultDesc("sn不能为空");
							deviceInfoImportFailList.add(deviceInfoImport);
							continue;
						}else{
							if(!StringUtils.isEmpty(snDeviceInfoMap.get(deviceInfoImport.getSn()))){
								deviceInfoImport.setResultDesc("sn在系统中已存在,不能重复导入");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}
						}
					}else {
						logger.info("自有卡开始处理");
						if(attribMana.getCardSelfId()==63){
							if(StringUtils.isEmpty(deviceInfoImport.getImei())||StringUtils.isEmpty(deviceInfoImport.getIccid())){
								deviceInfoImport.setResultDesc("iccid和imei都不能为空，请检查excel信息");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}else{
								if(!StringUtils.isEmpty(imeiDeviceInfoMap.get(deviceInfoImport.getImei()))){
									deviceInfoImport.setResultDesc("imei在系统中已存在,不能重复导入");
									deviceInfoImportFailList.add(deviceInfoImport);
									continue;
								}
								/*if(StringUtils.isEmpty(iccidFlowCardMap.get(deviceInfoImport.getIccid()))){
									deviceInfoImport.setResultDesc("iccid在流量卡平台不存在,不能导入");
									deviceInfoImportFailList.add(deviceInfoImport);
									continue;
								}*/
							}
							//
							logger.info("外部卡开始处理");
						}else if(attribMana.getCardSelfId()==64){
							if(StringUtils.isEmpty(deviceInfoImport.getImei())||StringUtils.isEmpty(deviceInfoImport.getIccid())){
								deviceInfoImport.setResultDesc("iccid和imei都不能为空，请检查excel信息");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}else{
								if(!StringUtils.isEmpty(imeiDeviceInfoMap.get(deviceInfoImport.getImei()))){
									deviceInfoImport.setResultDesc("imei在系统中已存在,不能重复导入");
									deviceInfoImportFailList.add(deviceInfoImport);
									continue;
								}
							}
						}

					}

				}

				if(attribMana.getDevTypeId()==6||attribMana.getDevTypeId()==1 || attribMana.getDevTypeId()==8 ||attribMana.getDevTypeId()==12501){
					if(attribMana.getOrNetId()==57){
						if(StringUtils.isEmpty(deviceInfoImport.getSn())) {
							deviceInfoImport.setResultDesc("sn不能为空");
							deviceInfoImportFailList.add(deviceInfoImport);
							continue;
						}else{
							if(!StringUtils.isEmpty(snDeviceInfoMap.get(deviceInfoImport.getSn()))){
								deviceInfoImport.setResultDesc("sn在系统中已存在,不能重复导入");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}
						}
					}else {
						if(attribMana.getCardSelfId()==63){
							if(StringUtils.isEmpty(deviceInfoImport.getSn())||StringUtils.isEmpty(deviceInfoImport.getIccid())){
								deviceInfoImport.setResultDesc("sn和iccid都不能为空，请检查excel信息");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}else{
								if(!StringUtils.isEmpty(imeiDeviceInfoMap.get(deviceInfoImport.getSn()))){
									deviceInfoImport.setResultDesc("sn在系统中已存在,不能重复导入");
									deviceInfoImportFailList.add(deviceInfoImport);
									continue;
								}
//								if(StringUtils.isEmpty(iccidFlowCardMap.get(deviceInfoImport.getIccid()))){
//									deviceInfoImport.setResultDesc("iccid在流量卡平台不存在,不能导入");
//									deviceInfoImportFailList.add(deviceInfoImport);
//									continue;
//								}
							}
						}else if(attribMana.getCardSelfId()==64){
							if(StringUtils.isEmpty(deviceInfoImport.getSn())||StringUtils.isEmpty(deviceInfoImport.getIccid())){
								deviceInfoImport.setResultDesc("sn和iccid都不能为空，请检查excel信息");
								deviceInfoImportFailList.add(deviceInfoImport);
								continue;
							}else{
								if(!StringUtils.isEmpty(imeiDeviceInfoMap.get(deviceInfoImport.getSn()))){
									deviceInfoImport.setResultDesc("sn在系统中已存在,不能重复导入");
									deviceInfoImportFailList.add(deviceInfoImport);
									continue;
								}
							}
						}
					}

				}


			}

			// 验证工厂仓库是否存在
			if(!StringUtils.isEmpty(deviceInfoImport.getWareHouseName())) {
				if (!isFactoryNameExists(deviceInfoImport.getWareHouseName())) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_WHOUSE_NAME_EMPTY.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				} else {
					deviceInfoImport.setWareHouseId(warehouseInfoMap.get(deviceInfoImport.getWareHouseName()).getId());
				}
			}

			// 验证当前所在工厂/仓库是否存在
			if(!StringUtils.isEmpty(deviceInfoImport.getWareHouseUpName())) {
				if (!isFactoryNameExists(deviceInfoImport.getWareHouseUpName())) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_WHOUSE_NAME_EMPTY.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				} else {
					deviceInfoImport.setWareHouseIdUp(warehouseInfoUpMap.get(deviceInfoImport.getWareHouseUpName()).getId());
				}
			}
			// 验证sn格式是否正确
			if(!StringUtils.isEmpty(deviceInfoImport.getSn())) {
				if (!JxcStringUtil.isRightFormatSn(deviceInfoImport.getSn())) {
					// sn为空
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_SN.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}

			// 验证iccid格式是否正确
			if(!StringUtils.isEmpty(deviceInfoImport.getIccid())) {
				if (!JxcStringUtil.isRightFormatIccid(deviceInfoImport.getIccid())) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_ICCID.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}
			// 验证imei格式是否正确
			if(!StringUtils.isEmpty(deviceInfoImport.getImei())) {
				if (!JxcStringUtil.isRightFormatSn(deviceInfoImport.getImei())) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_IMSI
							.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}
			Integer count=0;
			// 验证是sn、iccid、imei是否重复
			if(!StringUtils.isEmpty(deviceInfoImport.getSn())) {
				count = mapSn.get(deviceInfoImport.getSn());
				if (count >= 2) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_DEVICE_SN_REPEAT.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}
			if(!StringUtils.isEmpty(deviceInfoImport.getIccid())) {
				count = mapIccid.get(deviceInfoImport.getIccid());
				if (count >= 2) {
					deviceInfoImport
							.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_DEVICE_ICCID_REPEAT
									.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}
			if (!StringUtils.isEmpty(deviceInfoImport.getImei())) {
				count = mapImei.get(deviceInfoImport.getImei());
				if (count >= 2) {
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_DEVICE_IMEI_REPEAT
							.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}

			if(!StringUtils.isEmpty(deviceInfoImport.getImsi())){
				count=mapImsi.get(deviceInfoImport.getImsi());
				if(count>=2){
					deviceInfoImport.setResultDesc(JXCReasonInvalidDeviceNum.INVALID_DEVICE_IMSI_REPEAT.getValue());
					deviceInfoImportFailList.add(deviceInfoImport);
					continue;
				}
			}
			deviceInfoImportSuccessList.add(deviceInfoImport);
		}
		logger.info("JXCMTDeviceService::checkImportDeviceList: check ok !");
		checkImportDataVo.setDeviceInfoImportSuccessList(deviceInfoImportSuccessList);
		checkImportDataVo.setDeviceInfoImportFailList(deviceInfoImportFailList);
		return checkImportDataVo;
	}

	public Integer importDeviceInfoList(String userName,List<JXCMTDeviceInfoImport> importList) {
		Integer result = 0;
		logger.info("JXCMTDeviceService::importDeviceInfoList: importList.size=" + importList.size());
		if (importList.size() == 0) {
			return result;
		}
		List<JXCMTDeviceInfo> deviceInfoList=new ArrayList<>();
		List<String> snList=new ArrayList<>();
		JXCMTDeviceInfo jxcmtDeviceInfo=null;
		for(JXCMTDeviceInfoImport deviceInfoImport:importList){
			snList.add(deviceInfoImport.getSn());
			jxcmtDeviceInfo=new JXCMTDeviceInfo();
			jxcmtDeviceInfo.setIccid(deviceInfoImport.getIccid());
			jxcmtDeviceInfo.setImei(deviceInfoImport.getImei());
			jxcmtDeviceInfo.setAttribCode(deviceInfoImport.getAttribCode());
			jxcmtDeviceInfo.setBatch(deviceInfoImport.getBatch());
			jxcmtDeviceInfo.setStatus("IN");
			jxcmtDeviceInfo.setWarehouseId(deviceInfoImport.getWareHouseId());
			jxcmtDeviceInfo.setWarehouseIdUp(deviceInfoImport.getWareHouseIdUp());
			jxcmtDeviceInfo.setCreatedBy(userName);
			jxcmtDeviceInfo.setCreatedDate(new Date());
			jxcmtDeviceInfo.setUpdatedBy(userName);
			jxcmtDeviceInfo.setUpdatedDate(new Date());
			jxcmtDeviceInfo.setDeletedFlag("N");
			jxcmtDeviceInfo.setVcode(deviceInfoImport.getVcode());
			jxcmtDeviceInfo.setImsi(deviceInfoImport.getImsi());
			jxcmtDeviceInfo.setSimCardNo(deviceInfoImport.getSimCardNo());
			if(StringUtils.isEmpty(deviceInfoImport.getSn())&&!StringUtils.isEmpty(deviceInfoImport.getImei())){
				jxcmtDeviceInfo.setSn(deviceInfoImport.getImei());
			}else{
				jxcmtDeviceInfo.setSn(deviceInfoImport.getSn());
			}
			jxcmtDeviceInfo.setVcode(deviceInfoImport.getVcode());
			jxcmtDeviceInfo.setImsi(deviceInfoImport.getImsi());
			jxcmtDeviceInfo.setSimCardNo(deviceInfoImport.getSimCardNo());
			deviceInfoList.add(jxcmtDeviceInfo);
		}
		Example example = new Example(JXCMTDeviceInfo.class);
		example.createCriteria().andEqualTo("deletedFlag","Y")
								.andIn("sn",snList);
		List<JXCMTDeviceInfo> listDeletedDevices = jxcmtDeviceInfoMapper.selectByExample(example);
		if(null != listDeletedDevices && !listDeletedDevices.isEmpty()){
			List<String> listDeleteSns = listDeletedDevices.stream().map(JXCMTDeviceInfo::getSn).collect(Collectors.toList());
			Example example1= new Example(JXCMTDeviceInfo.class);
			example1.createCriteria().andIn("sn",listDeleteSns);
			jxcmtDeviceInfoMapper.deleteByExample(example1);
		}
		result= jxcmtDeviceInfoMapper.insertList(deviceInfoList);
		return result;
	}
	
	
}
