package glsx.com.cn.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.insurance.core.utils.JacksonUtils;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;

import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import glsx.com.cn.task.common.Constants;
import glsx.com.cn.task.manager.FlowCardServiceManager;
import glsx.com.cn.task.mapper.AttribManaMapper;
import glsx.com.cn.task.mapper.BsMerchantOrderVehicleMapper;
import glsx.com.cn.task.mapper.DeviceCodeMapper;
import glsx.com.cn.task.mapper.DeviceInfoGpsPreimportMapper;
import glsx.com.cn.task.mapper.DeviceInfoMapper;
import glsx.com.cn.task.mapper.DeviceTypeDispatchSurpportMapper;
import glsx.com.cn.task.mapper.ExsysDispatchRuleMapper;
import glsx.com.cn.task.mapper.MerchantOrderDetailMapper;
import glsx.com.cn.task.mapper.MerchantOrderMapper;
import glsx.com.cn.task.mapper.OrderInfoMapper;
import glsx.com.cn.task.mapper.ProductMapper;
import glsx.com.cn.task.mapper.SyncLastidRecordMapper;
import glsx.com.cn.task.mapper.SyncResultLogMapper;
import glsx.com.cn.task.model.BsMerchantOrderVehicle;
import glsx.com.cn.task.model.DeviceCode;
import glsx.com.cn.task.model.DeviceInfo;
import glsx.com.cn.task.model.DeviceInfoGpsPreimport;
import glsx.com.cn.task.model.DeviceTypeDispatchSurpport;
import glsx.com.cn.task.model.ExsysDispatchLog;
import glsx.com.cn.task.model.ExsysDispatchRule;
import glsx.com.cn.task.model.MerchantOrder;
import glsx.com.cn.task.model.MerchantOrderDetail;
import glsx.com.cn.task.model.OrderInfo;
import glsx.com.cn.task.model.Product;
import glsx.com.cn.task.model.SyncLastidRecord;
import glsx.com.cn.task.model.SyncResultLog;
import glsx.com.cn.task.service.SyncDeviceToExsysServer;

@Service
public class SyncDeviceToExsysServerImpl implements SyncDeviceToExsysServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SyncLastidRecordMapper syncLastidRecordMapper;
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	@Autowired
	private DeviceInfoGpsPreimportMapper deviceInfoGpsMapper;
	@Autowired
	private FlowCardServiceManager flowCardServiceManager;
	@Autowired
	private SyncResultLogMapper syncResultLogMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private BsMerchantOrderVehicleMapper merchantOrderVehicleMapper;
	@Autowired
	private MerchantOrderDetailMapper merchantOrderDetailMapper;
	@Autowired
	private MerchantOrderMapper merchantOrderMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private AttribManaMapper attribManaMapper;
	@Autowired
	private DeviceCodeMapper deviceCodeMapper;
	@Autowired
	private ExsysDispatchRuleMapper dispatchRuleMapper;
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private DeviceTypeDispatchSurpportMapper deviceTypeDispatchSupportMapper;

	@Override
	public void dispatchDeviceToExsysServer() {
		logger.info("执行dispatchLogDeviceToExsysServer方法");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = df.format(new Date());
		Date nowTime = new Date();
		SyncResultLog syncResultLog = new SyncResultLog();

		try {
			SyncLastidRecord syncLastidRecord = syncLastidRecordMapper
					.getSyncLastidRecord();
			logger.info("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer 获取最后记录同步结果：{}"
					+ syncLastidRecord);
			Integer lastTimeStamp = syncLastidRecord.getLastSyncExterSystemId();
			String strLastDate = TimeStamp2Date(lastTimeStamp.toString(),
					"yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date lastDate = sdf.parse(strLastDate);

			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setStatus("OUT"); // 并且为已经出库的

			String strIccid = null;

			deviceInfo.setUpdatedDate(lastDate);
			List<DeviceInfo> listDeviceInfo = deviceInfoMapper
					.getDeviceInfoUserInSyncPhicalDevice(deviceInfo);

			if (listDeviceInfo == null) {
				logger.info("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer null listDeviceInfo");
				return;
			}

			Map<String, AttribMana> mapAttribMana = new HashMap<String, AttribMana>();
			Map<Integer, WareHouseInfo> mapWareHouseInfo = new HashMap<Integer, WareHouseInfo>();
			Map<String, DeviceInfoGpsPreimport> mapGpsPreImport = new HashMap<String, DeviceInfoGpsPreimport>();
			Map<String, OrderInfo> mapOrderInfo = new HashMap<String, OrderInfo>();
			Map<Integer, DeviceCode> mapDeviceCode = new HashMap<Integer, DeviceCode>();
			Map<String, MerchantOrderDetail> mapMerchantOrderDetail = new HashMap<String, MerchantOrderDetail>();
			Map<String, MerchantOrder> mapMerchantOrder = new HashMap<String, MerchantOrder>();
			Map<String, Product> mapProduct = new HashMap<String, Product>();
			List<ExsysDispatchLog> listDispatchLog = new ArrayList<ExsysDispatchLog>();
			Map<Date,List<String>> mapGpsPreImportCondition = new HashMap<Date,List<String>>();
			Map<Integer,DeviceTypeDispatchSurpport> mapCacheDeviceTypeSurpport =  getMapDeviceDispatchSurpport();
			logger.info("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer  mapCacheDeviceTypeSurpport:{}",mapCacheDeviceTypeSurpport);
			for(DeviceInfo oInfo : listDeviceInfo)
			{
				logger.info("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer handle oInfo="
						+ oInfo.toString());
				if (lastDate.compareTo(oInfo.getUpdatedDate()) < 0) {
					lastDate = oInfo.getUpdatedDate();
				}
				try
				{
					ExsysDispatchLog dispatchLog = new ExsysDispatchLog();
					dispatchLog.setSn(oInfo.getSn());
					dispatchLog.setImei(oInfo.getImei());
					dispatchLog.setIccid(oInfo.getIccid());
					dispatchLog.setDispatchNo(oInfo.getOrderCode());
					if (oInfo.getWareHouseIdUp() != null) {
						dispatchLog.setFactoryNo(String.valueOf(oInfo
								.getWareHouseIdUp()));
					}
					dispatchLog.setVerifCode(oInfo.getVcode());
					dispatchLog
							.setInTimestamp(oInfo.getCreatedDate().getTime());
					dispatchLog.setOutTimestamp(oInfo.getUpdatedDate()
							.getTime());
					dispatchLog.setCreatedBy(oInfo.getCreatedBy());
					dispatchLog.setCreatedDate(oInfo.getCreatedDate());
					dispatchLog.setUpdatedBy(oInfo.getUpdatedBy());
					dispatchLog.setUpdatedDate(oInfo.getUpdatedDate());
					dispatchLog.setResentCount(0);
					dispatchLog.setResentMax(6);
					dispatchLog.setImsi(oInfo.getImsi());
					dispatchLog.setCardNo(oInfo.getSimCardNo());
					dispatchLog.setSimPhone(oInfo.getSimCardNo());
					setDeviceAttrib(oInfo.getAttribCode(), mapAttribMana,dispatchLog);
					setWareHouseInfo(Integer.valueOf(dispatchLog.getFactoryNo()), mapWareHouseInfo,dispatchLog);
					setOrderInfo(dispatchLog.getDispatchNo(), mapOrderInfo,dispatchLog);
					setDeviceCode(dispatchLog.getDeviceCode(), mapDeviceCode,dispatchLog);					
					setMerchantOrderDetail(dispatchLog.getDispatchNo(), mapMerchantOrderDetail,mapMerchantOrder, mapProduct, dispatchLog);
					logger.info("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer  dispatchLog="
							+ dispatchLog.toString());
					//非gps不参与分发
					if(!isDispatchDeviceType(dispatchLog.getDeviceType(),mapCacheDeviceTypeSurpport))
					{
						continue;
					}
					
					List<String> listSns = mapGpsPreImportCondition.get(oInfo.getUpdatedDate());
					if(StringUtils.isEmpty(listSns))
					{
						listSns = new ArrayList<String>();
						mapGpsPreImportCondition.put(oInfo.getUpdatedDate(), listSns);
					}
					listSns.add(oInfo.getSn());
					listDispatchLog.add(dispatchLog);					
				}
				catch (Exception e)
				{
					syncResultLog.setId(null);
					syncResultLog.setErrorCode("9000");
					syncResultLog.setErrorMsg("9000错误 查看日志");
					syncResultLog.setFlag("TE");
					syncResultLog.setResult("FA");
					syncResultLog.setIccid(oInfo.getIccid());
					syncResultLog.setImei(oInfo.getImei());
					syncResultLog.setSn(oInfo.getSn());
					syncResultLog.setDeletedFlag("N");
					syncResultLog.setCreatedBy("admin");
					syncResultLog.setUpdatedBy("admin");
					syncResultLog.setCreatedDate(new Date());
					syncResultLog.setUpdatedDate(new Date());
					syncResultLogMapper.insert(syncResultLog);
					logger.error("dispatchDeviceToExsysServerImpl::dispatchLogDeviceToExsysServer error describle:"
							+ e.getMessage());
				}	
			}
			
			setDeviceInfoGpsPreimport(mapGpsPreImportCondition, mapGpsPreImport);
			handleDispatch2Exsystem(listDispatchLog,mapGpsPreImport,mapCacheDeviceTypeSurpport);
			
			lastTimeStamp = (int) (lastDate.getTime() / 1000);
			if (lastTimeStamp > syncLastidRecord.getLastSyncExterSystemId()) {
				syncLastidRecord.setLastSyncExterSystemId(lastTimeStamp);
				syncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
			}
		}catch (Exception e) {
			logger.error("同步失败" + e.getMessage(), e);
		}
		logger.info("执行dispatchLogDeviceToExsysServer方法  完毕");
	}
	
	private boolean isDispatchDeviceType(Integer deviceType,Map<Integer,DeviceTypeDispatchSurpport> mapCacheDeviceTypeSurpport){
		
		DeviceTypeDispatchSurpport support = mapCacheDeviceTypeSurpport.get(deviceType);
		if(null == support){
			return false;
		}
		return true;
	}
	
	private void FillCardInfo(ExsysDispatchLog dispatchLog,Map<Integer,DeviceTypeDispatchSurpport> mapCacheDeviceTypeSurpport) throws Exception
	{
		//非GPS不参与分发
		if(!this.isDispatchDeviceType(dispatchLog.getDeviceType(),mapCacheDeviceTypeSurpport))
		{
			return;
		}
		FlowCardRequest flowCardRequest = new FlowCardRequest();
		String strIccid = dispatchLog.getIccid();
		if(StringUtils.isEmpty(strIccid))
		{
			return;
		}

		flowCardRequest.setKeyWord(strIccid.toUpperCase());
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		flowCardRequest.setTime(time);  //时间
		flowCardRequest.setInvoker("glsx");
		flowCardRequest.setVersion("1.0.0"); //版本号
		flowCardRequest.setConsumer("task-supplychain"); //项目名称
		FlowResponse<Flowcard> response = flowCardServiceManager.getFlowCardByIccid(flowCardRequest);
		logger.info("SyncLogDeviceToExsysServerImpl::FillCardInfo 同步设备ICCID:" + strIccid.toUpperCase() + ",流量卡服务返回的code：" + response.getCode() + "返回错误信息：" + response.getMessage());
		if (response == null || !response.getCode().equals("1000") || response.getEntiy() == null)
		{
			logger.info("SyncLogDeviceToExsysServerImpl::FillCardInfo 【flowCardServiceManager---->getFlowCardByIccid】通过iccid查询卡信息失败,参数:" + flowCardRequest.toString());
			return;
		}
		dispatchLog.setImsi(response.getEntiy().getImsi());
		dispatchLog.setSimPhone(response.getEntiy().getCardNo());
		dispatchLog.setCardNo(response.getEntiy().getCardNo());
	}
	
	private void handleDispatch2Exsystem(
			List<ExsysDispatchLog> listDispatchLog,
			Map<String, DeviceInfoGpsPreimport> mapGpsPreImport,
			Map<Integer,DeviceTypeDispatchSurpport> mapCacheDeviceTypeSurpport) {
		List<ExsysDispatchLog> listKafkaDispatchLog = new ArrayList<ExsysDispatchLog>();
		Map<String,List<ExsysDispatchRule>> mapDispatchRule = new HashMap<String,List<ExsysDispatchRule>>();
		
		for (ExsysDispatchLog dispatchLog : listDispatchLog) {
			DeviceInfoGpsPreimport preImport = mapGpsPreImport.get(dispatchLog
					.getSn());
			try {
				if (StringUtils.isEmpty(preImport)) {
					//扫码工具入库
					if(StringUtils.isEmpty(dispatchLog.getImsi()) || StringUtils.isEmpty(dispatchLog.getSimPhone()) || StringUtils.isEmpty(dispatchLog.getCardNo())){
						FillCardInfo(dispatchLog,mapCacheDeviceTypeSurpport);
					}	
				}
				else
				{
					dispatchLog.setImsi(preImport.getImsi());
					dispatchLog.setSimPhone(preImport.getSimCardNo());
					dispatchLog.setCardNo(preImport.getSimCardNo());
				}
				List<ExsysDispatchRule> dispatchRuleList = this
						.GetSysDispatchRule(dispatchLog,mapDispatchRule);
				if (!StringUtils.isEmpty(dispatchRuleList)) {
					for (ExsysDispatchRule rule : dispatchRuleList) {
						ExsysDispatchLog kafkaDispatchLog = new ExsysDispatchLog();
						dispatchLog.setSystemFlag(rule.getSystemFlag());
						dispatchLog.setModuleFlag(rule.getModuleFlag());
						dispatchLog.setSoftversion("V1.0");
						BeanUtils.copyProperties(kafkaDispatchLog, dispatchLog);
						listKafkaDispatchLog.add(kafkaDispatchLog);
					}
				} else {
					if(!StringUtils.isEmpty(preImport))
					{
						preImport.setResult("FA");
						preImport.setResultDesc("未找到分发规则 分发设备信息:" + "deviceType:"
								+ dispatchLog.getDeviceType() + " mnumName:"
								+ dispatchLog.getMnumName() + " snHead:"
								+ dispatchLog.getSn().substring(0, 2) + " subject:"
								+ dispatchLog.getSubjectId() + " issure:"
								+ dispatchLog.getIsSure());
						deviceInfoGpsMapper.updateByPrimaryKeySelective(preImport);
					}	
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		batchSendKafka(listKafkaDispatchLog);
	}
	
	private void batchSendKafka(List<ExsysDispatchLog> listKafkaDispatchLog)
	{
		List<ExsysDispatchLog> listLog = new ArrayList<ExsysDispatchLog>();
		int i=0;
		for(ExsysDispatchLog dispatchLog:listKafkaDispatchLog)
		{
			listLog.add(dispatchLog);
			i++;
			if(i%Constants.batch_dispatch_log_step == 0)
			{
				String strJson = JacksonUtils.beanToJson(listLog);
				logger.info("发送kafka数据 strJson" + strJson);
				kafkaProducer.send("dispatch_deviceinfo_to_exsystem",
						strJson.getBytes());
				listLog.clear();
			}
		}
		String strJson = JacksonUtils.beanToJson(listLog);
		logger.info("发送kafka数据 strJson" + strJson);
		kafkaProducer.send("dispatch_deviceinfo_to_exsystem",
				strJson.getBytes());
	}
	
	private Map<String, DeviceInfoGpsPreimport> setDeviceInfoGpsPreimport(Map<Date,List<String>> mapGpsPreImportCondition,Map<String, DeviceInfoGpsPreimport> mapGpsPreImport)
	{
		for(Map.Entry<Date,List<String>> entry:mapGpsPreImportCondition.entrySet())
		{
			List<String> listSns = entry.getValue();
			if(StringUtils.isEmpty(listSns) || listSns.size() == 0)
			{
				continue;
			}
			List<DeviceInfoGpsPreimport> listResult = deviceInfoGpsMapper.listDeviceInfoGpsPreimport(entry.getKey(),listSns);
			if(StringUtils.isEmpty(listResult))
			{
				continue;
			}
			for(DeviceInfoGpsPreimport gpsPre:listResult)
			{
				mapGpsPreImport.put(gpsPre.getSn(), gpsPre);
			}
		}
		return mapGpsPreImport;
	}
	
	private void setDeviceAttrib(String attribCode,
			Map<String, AttribMana> mapAttribMana, ExsysDispatchLog dispatchLog) {
		AttribMana retObject = null;
		if (StringUtils.isEmpty(attribCode)) {
			return;
		}
		retObject = mapAttribMana.get(attribCode);
		if (StringUtils.isEmpty(retObject)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName("admin");
			userInfo.setConsumer("web-supplychain");
			userInfo.setVersion("v1.0");
			userInfo.setTime(getCurrentDate());

			AttribMana condition = new AttribMana();
			condition.setAttribCode(attribCode);
			RpcResponse<AttribMana> rsp = supplyChainRemote
					.getAttribManaByManaCode(userInfo, condition);

			ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				return;
			}
			retObject = rsp.getResult();
			if (!StringUtils.isEmpty(retObject)) {
				mapAttribMana.put(attribCode, retObject);
			}	
		}
		if (!StringUtils.isEmpty(retObject)) {
			dispatchLog.setMnumName(retObject.getDevMnumName());
			dispatchLog.setDeviceType(retObject.getDevTypeId());
			dispatchLog.setDeviceTypeName(retObject.getDevTypeName());
		}
	}

	private void setWareHouseInfo(Integer wareHouseInfoId,
			Map<Integer, WareHouseInfo> mapWareHouseInfo,ExsysDispatchLog dispatchLog) {
		WareHouseInfo retObject = null;
		if (StringUtils.isEmpty(wareHouseInfoId)) {
			return;
		}
		retObject = mapWareHouseInfo.get(wareHouseInfoId);
		if (StringUtils.isEmpty(retObject)) {
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName("admin");
			userInfo.setConsumer("web-supplychain");
			userInfo.setVersion("v1.0");
			userInfo.setTime(getCurrentDate());
			
			WareHouseInfo condition = new WareHouseInfo();
			condition.setId(wareHouseInfoId);
			RpcResponse<WareHouseInfo> rsp = supplyChainRemote.getWareHouseInfo(
					userInfo, condition);
			
			ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				return ;
			}
			retObject = rsp.getResult();
			if(!StringUtils.isEmpty(retObject))
			{
				mapWareHouseInfo.put(wareHouseInfoId, retObject);
			}
		}
		if(!StringUtils.isEmpty(retObject))
		{
			dispatchLog.setFactoryName(retObject.getName());
		}
	}

	private void setOrderInfo(String orderCode,
			Map<String, OrderInfo> mapOrderInfo,ExsysDispatchLog dispatchLog) {
		OrderInfo retObject = null;
		if (StringUtils.isEmpty(orderCode)) {
			return ;
		}
		retObject = mapOrderInfo.get(orderCode);
		if (StringUtils.isEmpty(retObject)) {
			retObject = orderInfoMapper
					.getOrderInfoByOrderCode(orderCode);
			if (!StringUtils.isEmpty(retObject)) {
				mapOrderInfo.put(orderCode, retObject);
			}
		}
		if(!StringUtils.isEmpty(retObject))
		{
			dispatchLog.setPackageNo(retObject.getPackageOne());
			dispatchLog.setToMerchantNo(retObject.getSendMerchantNo());
			dispatchLog.setDeviceCode(retObject.getDeviceId());
		}
	}

	private void setDeviceCode(Integer deviceCode,
			Map<Integer, DeviceCode> mapDeviceCode,ExsysDispatchLog dispatchLog) {
		DeviceCode retObject = null;
		if (StringUtils.isEmpty(deviceCode)) {
			return;
		}
		retObject = mapDeviceCode.get(deviceCode);
		if (StringUtils.isEmpty(retObject)) {
			DeviceCode dcCondition = new DeviceCode();
			dcCondition.setDeviceCode(deviceCode);
			retObject = deviceCodeMapper.selectByUniqueKey(dcCondition);
			if(!StringUtils.isEmpty(retObject))
			{
				mapDeviceCode.put(deviceCode, retObject);
			}
		}
		if(!StringUtils.isEmpty(retObject))
		{
			dispatchLog.setDeviceCodeName(retObject.getDeviceName());
		}
	}

	private void setMerchantOrderDetail(String dispatchOrderNum,
			Map<String, MerchantOrderDetail> mapMerchantOrderDetail,
			Map<String, MerchantOrder> mapMerchantOrder,
			Map<String, Product> mapProduct,
			ExsysDispatchLog dispatchLog) {
		MerchantOrderDetail retObject = null;
		if (StringUtils.isEmpty(dispatchOrderNum)) {
			return ;
		}
		retObject = mapMerchantOrderDetail.get(dispatchOrderNum);
		if (StringUtils.isEmpty(retObject)) {
			BsMerchantOrderVehicle vehCondition = new BsMerchantOrderVehicle();
			vehCondition.setDispatchOrderCode(dispatchOrderNum);
			vehCondition.setDeletedFlag("N");
			vehCondition = merchantOrderVehicleMapper.selectOne(vehCondition);
			if(null != vehCondition){
				MerchantOrderDetail modCodition = new MerchantOrderDetail();
				modCodition.setMerchantOrderNumber(vehCondition.getMerchantOrder());
				modCodition.setDeletedFlag("N");
				retObject = merchantOrderDetailMapper.selectOne(modCodition);
				if(!StringUtils.isEmpty(retObject))
				{
					mapMerchantOrderDetail.put(dispatchOrderNum, retObject);
				}
			}	
		}
		if(StringUtils.isEmpty(retObject))
		{
			return;
		}
		dispatchLog.setSubjectId(retObject.getSubjectId());
		dispatchLog.setIsSure(retObject.getInsure());
		setMerchantOrder(retObject.getMerchantOrderNumber(), mapMerchantOrder,dispatchLog);
		setProduct(retObject.getProductCode(),mapProduct,dispatchLog);
	}

	private void setMerchantOrder(String merchantOrderCode,
			Map<String, MerchantOrder> mapMerchantOrder,
			ExsysDispatchLog dispatchLog) {
		MerchantOrder retObject = null;
		if (StringUtils.isEmpty(merchantOrderCode)) {
			return;
		}
		retObject = mapMerchantOrder.get(merchantOrderCode);
		if (StringUtils.isEmpty(retObject)) {
			MerchantOrder condition = new MerchantOrder();
			condition.setOrderNumber(merchantOrderCode);
			retObject = merchantOrderMapper.selectOne(condition);
			if (!StringUtils.isEmpty(retObject))
			{
				mapMerchantOrder.put(merchantOrderCode, retObject);
			}
		}
		if(!StringUtils.isEmpty(retObject))
		{
			dispatchLog.setOrderNo(retObject.getOrderNumber());
			dispatchLog.setThirdOrderNo(retObject.getRemarks());
		}
	}

	private void setProduct(String productCode,
			Map<String, Product> mapProduct,
			ExsysDispatchLog dispatchLog) {
		Product retObject = null;
		if (StringUtils.isEmpty(productCode)) {
			return;
		}
		retObject = mapProduct.get(productCode);
		if (StringUtils.isEmpty(retObject)) {
			Product condition = new Product();
			condition.setCode(productCode);
			retObject = productMapper.selectOne(condition);
			if (!StringUtils.isEmpty(retObject))
			{
				mapProduct.put(productCode, retObject);
			}
		}
		if (!StringUtils.isEmpty(retObject)) {
			dispatchLog.setProductNo(retObject.getCode());
			dispatchLog.setProductName(retObject.getName());
		}
	}

	private List<ExsysDispatchRule> GetSysDispatchRule(
			ExsysDispatchLog dispatchLog,Map<String,List<ExsysDispatchRule>> mapDispatchRule) throws Exception {
		List<ExsysDispatchRule> ruleList = null;
		// 非GPS或sn为空不参与分发
		if (!dispatchLog.getDeviceType().equals(8)
				|| StringUtils.isEmpty(dispatchLog.getSn())) {
			return ruleList;
		}
		if (StringUtils.isEmpty(dispatchLog.getMnumName())) {
			return ruleList;
		}
		if (dispatchLog.getSubjectId() == null) {
			return ruleList;
		}
		if (StringUtils.isEmpty(dispatchLog.getIsSure())) {
			return ruleList;
		}
		String strKey = "key" + dispatchLog.getDeviceType() + dispatchLog.getMnumName() + dispatchLog.getSn().substring(0, 2) + dispatchLog.getSubjectId() + dispatchLog.getIsSure();
		ruleList = mapDispatchRule.get(strKey);
		if(!StringUtils.isEmpty(ruleList))
		{
			return ruleList;
		}
		Example example = new Example(ExsysDispatchRule.class);
		example.createCriteria()
				.andEqualTo("deviceType", dispatchLog.getDeviceType())
				.andEqualTo("mnumName", dispatchLog.getMnumName())
				.andEqualTo("snHead", dispatchLog.getSn().substring(0, 2))
				.andEqualTo("subject", dispatchLog.getSubjectId())
				.andEqualTo("issure", dispatchLog.getIsSure());
		ruleList = dispatchRuleMapper.selectByExample(example);
		if(!StringUtils.isEmpty(ruleList))
		{
			mapDispatchRule.put(strKey, ruleList);
		}
		return ruleList;
	}
	
	private Map<Integer,DeviceTypeDispatchSurpport> getMapDeviceDispatchSurpport(){
		Map<Integer,DeviceTypeDispatchSurpport> mapResult = new HashMap<>();
		List<DeviceTypeDispatchSurpport> listSurpports = deviceTypeDispatchSupportMapper.selectAll();
		if(null == listSurpports){
			return mapResult;
		}
		for(DeviceTypeDispatchSurpport suppport:listSurpports){
			mapResult.put(suppport.getDeviceTypeId(), suppport);
		}
		return mapResult;
	}

	private String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	static public String TimeStamp2Date(String timestampString, String formats) {

		if (StringUtils.isEmpty(formats)) {
			formats = "yyyy-MM-dd HH:mm:ss";
		}

		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats, Locale.CHINA)
				.format(new Date(timestamp));
		return date;
	}

}
