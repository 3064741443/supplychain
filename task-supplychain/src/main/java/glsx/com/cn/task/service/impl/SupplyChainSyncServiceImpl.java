package glsx.com.cn.task.service.impl;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;


//import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
//import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
//import cn.com.glsx.supplychain.remote.DeviceManagerService;
//import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import com.alibaba.fastjson.JSON;
import com.glsx.biz.common.user.entity.DeviceCategory;
import com.glsx.biz.user.common.entity.PhysicalDevice;
//import com.glsx.oms.flowservice.api.core.FlowResponse;
//import com.glsx.oms.flowservice.api.entity.FlowCard;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;
import com.glsx.oms.flowservice.api.request.DeviceImeiStockRequest;
//import com.glsx.oms.flowservice.api.request.FlowCardRequest;
import com.glsx.oms.flowservice.api.service.DeviceService;

import glsx.com.cn.task.enums.ProductHistoryPriceEnum;
import glsx.com.cn.task.manager.FlowCardServiceManager;
import glsx.com.cn.task.manager.PhysicalDeviceServiceManager;
import glsx.com.cn.task.mapper.*;
import glsx.com.cn.task.model.*;
import glsx.com.cn.task.service.SupplyChainSyncService;
import glsx.com.cn.task.util.DeviceImeiStockStatusEnum;
import glsx.com.cn.task.util.ErrorCodeEnum;
import glsx.com.cn.task.util.SyncResultLogFlagEnum;
import glsx.com.cn.task.util.SyncResultLogStatusEnum;

import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class SupplyChainSyncServiceImpl implements SupplyChainSyncService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    
    @Autowired
	private DeviceInfoGpsPreimportMapper deviceInfoGpsMapper;

    @Autowired
    private OrderInfoMapper OrderInfoMapper;

    @Autowired
    private SyncResultLogMapper syncResultLogMapper;

    @Autowired
    private SyncLastidRecordMapper syncLastidRecordMapper;

    @Autowired
    private FlowCardServiceManager flowCardServiceManager;

    @Autowired
    private PhysicalDeviceServiceManager physicalDeviceServiceManager;

    @Autowired
    private AttribManaMapper attribManaMapper;

    @Autowired
    private FirmwareInfoMapper firmwareInfoMapper;

    @Autowired
    private DeviceCardManagerMapper deviceCardManagerMapper;

    @Autowired
    private DeviceUpdateRecordMapper deviceUpdateRecordMapper;

    @Autowired
    private DeviceFileMapper deviceFileMapper;

    @Autowired
    private DeviceFileSnapshotMapper deviceFileSnapshotMapper;
/*
    @Autowired
    private DeviceManagerService deviceManagerService;

    @Autowired
    private SupplyChainAdminRemote supplyChainAdminRemote;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;
*/
    @Autowired
    private DeviceImeiStockMapper deviceImeiStockMapper;

    @Autowired
    private DeviceCodeMapper deviceCodeMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    @Autowired
    private SettlementMapper settlementMapper;

    @Autowired
    private SettlementInfoMapper settlementInfoMapper;

    @Resource
    private KafkaProducer kafkaProducer;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;
    
    @Autowired
    private MerchantOrderMapper merchantOrderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private SalesSummarizingMaterialDetailMapper salesSummarizingMaterialDetailMapper;

    @Autowired
    private DealerUserInfoMapper dealerUserInfoMapper;

    /**
     * 同步device_info信息到device_file表
     */
    @Override
    public void SyncDeviceInfoToDeviceFile() {
        logger.info("【device_info】数据同步到【device_file】");

        DeviceFile deviceFile = new DeviceFile();
        SyncResultLog syncResultLog = new SyncResultLog();
        
        DeviceCardManager deviceCardManager = new DeviceCardManager();
        DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
        Map<Integer,DeviceCode> mapDeviceCode = new HashMap<Integer, DeviceCode>();
        List<DeviceImeiStock> listImeiStock = new ArrayList<DeviceImeiStock>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = df.format(new Date());

        Date nowTime = new Date();

        try {
            SyncLastidRecord syncLastidRecord = syncLastidRecordMapper.getSyncLastidRecord();
            logger.info("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile 获取最后记录同步结果：{}" + syncLastidRecord);
            Integer lastTimeStamp = syncLastidRecord.getLastInfoFileId();
            String strLastDate = TimeStamp2Date(lastTimeStamp.toString(), "yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lastDate = sdf.parse(strLastDate);

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setStatus("OUT");  //并且为已经出库的
            
            String imsi = null;
            String strIccid = null;
            String strImei = null;
            String strSn = null;
            String strPackageOne = null;
          
			deviceInfo.setUpdatedDate(lastDate);
			List<DeviceInfo> listDeviceInfo = deviceInfoMapper
					.getDeviceInfoUserInSyncPhicalDevice(deviceInfo);
			if (listDeviceInfo == null) {
				logger.info("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile listDeviceInfo null");
				return;
			}

			for (DeviceInfo oInfo : listDeviceInfo) 
			{
				try
				{
					deviceFileSnapshot.clear();
					deviceFile.clear();
					logger.info("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile handle oInfo="
							+ oInfo.toString());
					if (lastDate.compareTo(oInfo.getUpdatedDate()) < 0)
					{
						lastDate = oInfo.getUpdatedDate();
					}

					imsi = "";
					strIccid = oInfo.getIccid();
					strImei = oInfo.getImei();
					strSn = StringUtils.isEmpty(oInfo.getSn()) ? oInfo.getImei()
							: oInfo.getSn();
					strPackageOne = oInfo.getOrderInfo().getPackageOne();
					
					deviceCardManager = this.getDeviceCardManagerByIccid(oInfo, deviceCardManager, mapDeviceCode, nowTime);

					//设备关系表
					deviceFileSnapshot.setCardId(deviceCardManager.getId());
					deviceFileSnapshot.setCardTime(timeNow);
					if (!StringUtils.isEmpty(strPackageOne)) {
						deviceFileSnapshot.setPackageId(Integer
								.valueOf(strPackageOne));
						deviceFile.setPackageId(Integer.valueOf(strPackageOne));
					}
					deviceFileSnapshot.setId(null);
					deviceFileSnapshot.setSn(oInfo.getSn());
					deviceFileSnapshot.setPackageStatu("UA");
					deviceFileSnapshot.setDeletedFlag("N");
					
					//设备清单表
					deviceFile.setId(null);
					deviceFile.setSn(strSn);
					deviceFile.setImei(strImei);
					deviceFile.setVerifCode(oInfo.getVcode());
					deviceFile.setDeviceCode(oInfo.getOrderInfo().getDeviceId());
					deviceFile.setBatchNo(oInfo.getOrderInfo().getBatch());
					deviceFile.setOperatorMerchantNo(oInfo.getOrderInfo()
							.getOperatorMerchantNo());
					deviceFile.setSendMerchantNo(oInfo.getOrderInfo()
							.getSendMerchantNo());
					deviceFile
							.setOrderMerchantNo(this
									.findOrderMerchantNoBySendOrderNo(oInfo
											.getOrderCode()));
					deviceFile.setInStorageTime(df.format(oInfo.getCreatedDate()));
					deviceFile.setOutStorageTime(df.format(oInfo.getUpdatedDate()));
					deviceFile.setCreatedDate(nowTime);
					deviceFile.setUpdatedDate(nowTime);
					deviceFile.setCreatedBy("admin");
					deviceFile.setUpdatedBy("admin");
					deviceFile.setOutStorageType("SC");
					deviceFile.setExternalFlag("IN");
					deviceFile.setDeletedFlag("N");
					deviceFile.setOrderCode(oInfo.getOrderCode());
					deviceFile.setCardId(deviceCardManager.getId());
					
					DeviceFileSnapshot snapshot = this.getDeviceFileSnapshotBySn(deviceFileSnapshot);
					DeviceFile record = this.getDeviceFileBySn(deviceFile);
					
					if(snapshot != null || record != null)
					{
						logger.error("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile sn重复:"
								+ imsi
								+ " iccid:"
								+ oInfo.getIccid()
								+ " imei:"
								+ oInfo.getImei()
								+ oInfo.getSn());
						ErrorCodeEnum errCodeEnum = ErrorCodeEnum.ERRCODE_DEVICE_ALREADY_EXISTS;
						this.writeSyncResultLog(oInfo, syncResultLog, errCodeEnum, nowTime, "FI", "FA");
					}
					
					this.writeDeviceFileSnapshot(deviceFileSnapshot, nowTime);
					this.writeDeviceFile(deviceFile, nowTime);
					
					this.writeSyncResultLog(oInfo, syncResultLog, ErrorCodeEnum.E_OK, nowTime, "FI", "SU");
					
					//插入imei库存记录
					this.writeDeviceImeiSockCache(listImeiStock, oInfo, mapDeviceCode, nowTime);
				}
				catch (Exception e)
				{
					logger.error(e.getMessage());
					logger.error("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile sn插入数据库失败:"
							+ imsi
							+ " iccid:"
							+ oInfo.getIccid()
							+ " imei:"
							+ oInfo.getImei()
							+ oInfo.getSn());
					this.writeSyncResultLog(oInfo, syncResultLog, ErrorCodeEnum.ERRCODE_FAILED_DEVICEFILE_INSERT, nowTime, "FI", "FA");
				}
				
			}
			
			this.writeDeviceImeiSockDb(listImeiStock);

			lastTimeStamp = (int) (lastDate.getTime() / 1000);
			if (lastTimeStamp > syncLastidRecord.getLastInfoFileId()) 
			{
				syncLastidRecord.setLastInfoFileId(lastTimeStamp);
				syncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
			}
      
        } 
        catch (Exception e) 
        {
            logger.error("SupplyChainSyncServiceImpl::SyncDeviceInfoToDeviceFile同步失败：" + e.getMessage(), e);
        }
    }

	private void writeDeviceImeiSockDb(List<DeviceImeiStock> listImeiStock)
	{
		if(listImeiStock == null)
			return;
		if(listImeiStock.size() == 0)
			return;
		deviceImeiStockMapper.batchInsertOnDuplicateKeyUpdate(listImeiStock);
	}
    
    private void writeDeviceImeiSockCache(List<DeviceImeiStock> listImeiStock,DeviceInfo deviceInfo, Map<Integer,DeviceCode> mapDeviceCode,Date nowTime)
    {
    	if(StringUtils.isEmpty(deviceInfo.getImei()))
    	{
    		return;
    	}
    	DeviceCode deviceCode = this.getDeviceCode(deviceInfo.getOrderInfo().getDeviceId(), mapDeviceCode);
    	if(deviceCode == null)
    	{
    		return;
    	}
    	
    	DeviceImeiStock imeiStock = new DeviceImeiStock();
    	imeiStock.setImei(deviceInfo.getImei());
    	imeiStock.setExternalFlag("IN");
    	imeiStock.setDevType(deviceCode.getTypeId());
    	imeiStock.setMerchantNo(deviceInfo.getOrderInfo().getSendMerchantNo());
    	imeiStock.setCreatedBy("admin");
    	imeiStock.setUpdatedBy("admin");
    	imeiStock.setCreatedDate(nowTime);
    	imeiStock.setUpdatedDate(nowTime);
    	listImeiStock.add(imeiStock);
    }
    
    private void writeDeviceFileSnapshot(DeviceFileSnapshot deviceFileSnapshot,Date nowTime)
    {
    	DeviceFileSnapshot snapshot = deviceFileSnapshotMapper.selectByPrimaryKey(deviceFileSnapshot.getSn());
    	if(snapshot != null)
    	{
    		deviceFileSnapshot.setUpdatedDate(nowTime);
    		deviceFileSnapshotMapper.updateByPrimaryKeySelective(deviceFileSnapshot);
    	}
    	else
    	{
    		deviceFileSnapshot.setCreatedBy("admin");
			deviceFileSnapshot.setUpdatedBy("admin");
			deviceFileSnapshot.setUpdatedDate(nowTime);
			deviceFileSnapshot.setCreatedDate(nowTime);
			deviceFileSnapshotMapper.insertSelective(deviceFileSnapshot);
    	}
    }
    
    private void writeDeviceFile(DeviceFile deviceFile,Date nowTime)
    {
    	DeviceFile record = deviceFileMapper.selectByUniqueKey(deviceFile);
    	if(record != null)
    	{
    		deviceFile.setId(record.getId());
    		deviceFile.setUpdatedDate(nowTime);
    		deviceFileMapper.updateByPrimaryKeySelective(deviceFile);
    	}
    	else
    	{
    		deviceFile.setCreatedBy("admin");
			deviceFile.setUpdatedBy("admin");
			deviceFile.setUpdatedDate(nowTime);
			deviceFile.setCreatedDate(nowTime);
			deviceFileMapper.insertSelective(deviceFile);
    	}
    }
    
    private void writeSyncResultLog(DeviceInfo deviceInfo,SyncResultLog syncResultLog,ErrorCodeEnum errorCodeEnum,
    		Date nowTime,String strFlag, String strResult)
    {
    	if(StringUtils.isEmpty(deviceInfo.getSn()))
    	{
    		return;
    	}
    	syncResultLog.setId(null);
    	syncResultLog.setErrorCode(errorCodeEnum.getCode());
		syncResultLog.setErrorMsg(errorCodeEnum.getDescrible());
		syncResultLog.setFlag("FI");
		syncResultLog.setResult("FA");
		syncResultLog.setSn(deviceInfo.getSn());
		syncResultLog.setIccid(deviceInfo.getIccid());
		syncResultLog.setImei(deviceInfo.getImei());
		syncResultLog.setDeletedFlag("N");
		syncResultLog.setCreatedBy("admin");
		syncResultLog.setUpdatedBy("admin");
		syncResultLog.setCreatedDate(nowTime);
		syncResultLog.setUpdatedDate(nowTime);
		syncResultLogMapper.insert(syncResultLog);	
    }
    
    private DeviceFile getDeviceFileBySn(DeviceFile record)
    {
    	DeviceFile deviceFile = deviceFileMapper.selectByUniqueKey(record);
    	if(deviceFile == null)
    	{
    		return null;
    	}
    	if("Y".equals(deviceFile.getDeletedFlag()))
    	{
    		return null;
    	}
    	return deviceFile;
    }
    
    private DeviceFileSnapshot getDeviceFileSnapshotBySn(DeviceFileSnapshot record)
    {
    	DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotMapper.selectByPrimaryKey(record.getSn());
    	if(deviceFileSnapshot == null)
    	{
    		return null;
    	}
    	if("Y".equals(deviceFileSnapshot.getDeletedFlag()))
    	{
    		return null;
    	}
    	return deviceFileSnapshot;
    }
    
    private DeviceCode getDeviceCode(Integer deviceCodeId, Map<Integer,DeviceCode> mapDeviceCode)
    {
    	DeviceCode deviceCode = mapDeviceCode.get(deviceCodeId);
    	if(deviceCode != null)
    	{
    		return deviceCode;
    	}
    	deviceCode = deviceCodeMapper.selectByPrimaryKey(deviceCodeId);
    	if(deviceCode != null)
    	{
    		mapDeviceCode.put(deviceCodeId, deviceCode);
    	}
    	return deviceCode;
    }
    
    private String getImsiFromFlowPlat(String strIccid)
    {
    	String strImsi = "";
    	try
    	{
    		FlowCardRequest flowCardRequest = new FlowCardRequest();
        	flowCardRequest.setKeyWord(strIccid.toUpperCase());
        	String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    		.format(new Date());
        	flowCardRequest.setTime(time);
        	flowCardRequest.setInvoker("glsx");
        	flowCardRequest.setVersion("1.0.0"); // 版本号
        	flowCardRequest.setConsumer("task-supplychain"); // 项目名称
        	FlowResponse<Flowcard> response;
        	response = flowCardServiceManager.getFlowCardByIccid(flowCardRequest);
        	logger.info("同步设备ICCID:" + strIccid.toUpperCase()
    				+ ",流量卡服务返回的code：" + response.getCode() + "返回错误信息："
    				+ response.getMessage());
        	logger.info("getFlowcardByIccid完整数据:" + response.toString());
        	if(response == null ||
        			!response.getCode().equals("1000") ||
        			response.getEntiy() == null)
        	{
        		logger.info("【flowCardServiceManager---->getFlowCardByIccid】通过iccid查询卡信息失败,参数:"
						+ flowCardRequest.toString());
        	}
        	else
        	{
        		strImsi = response.getEntiy().getImsi();
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return strImsi;	
    }
    
    private DeviceCardManager getDeviceCardManagerByIccid(DeviceInfo deviceInfo,DeviceCardManager deviceCardManager,Map<Integer,DeviceCode> mapDeviceCode,Date nowTime)
    {
    	if(deviceCardManager == null)
    	{
    		deviceCardManager = new DeviceCardManager();
    	}
    	deviceCardManager.setId(null);
    	if(deviceInfo == null)
    	{
    		return deviceCardManager;
    	}
    	String strIccid = deviceInfo.getIccid();
    	if(StringUtils.isEmpty(strIccid))
    	{
    		return deviceCardManager;
    	}
    	
    	Integer deviceCodeId = deviceInfo.getOrderInfo().getDeviceId();	
    	DeviceCode deviceCode = this.getDeviceCode(deviceCodeId, mapDeviceCode);
    	if(deviceCode == null)
    	{
    		return deviceCardManager;
    	}
    	//如果是GPS设备 有限从gps导入表中查 查不到再去卡库去查
    	String strImsi = null;
    	if(deviceCode.getTypeId() == 8 || deviceCode.getTypeId()==1 || deviceCode.getTypeId()==12501)
    	{
    		DeviceInfoGpsPreimport gpsDeviceInfo = new DeviceInfoGpsPreimport();
    		gpsDeviceInfo.setUpdatedDate(deviceInfo.getUpdatedDate());
    		gpsDeviceInfo.setSn(deviceInfo.getSn());
    		gpsDeviceInfo = deviceInfoGpsMapper.selectOne(gpsDeviceInfo);
    		if(gpsDeviceInfo != null)
    		{
    			strImsi = gpsDeviceInfo.getImsi();
    		}
    	}
    	//查看本身是否有imsi
    	if(!StringUtils.isEmpty(deviceInfo.getImsi())){
    		strImsi = deviceInfo.getImsi();
    	}
    	//如果获取不到imsi 直接到卡库查
    	if(StringUtils.isEmpty(strImsi))
    	{
    		strImsi = this.getImsiFromFlowPlat(strIccid);
    	}
    	
    	if(!StringUtils.isEmpty(strImsi))
    	{
    		deviceCardManager.setCompanyId(1);
    		deviceCardManager.setImsi(strImsi);
    		DeviceCardManager resultCard = deviceCardManagerMapper.selectByUniqueKey(deviceCardManager);
    		if(resultCard == null)
    		{
    			deviceCardManager.setCreatedBy("admin");
    			deviceCardManager.setUpdatedBy("admin");
    			deviceCardManager.setCreatedDate(nowTime);
    			deviceCardManager.setUpdatedDate(nowTime);
    			deviceCardManager.setIccid(strIccid);
    			deviceCardManagerMapper.insertSelective(deviceCardManager);
    		}
    		else
    		{
    			deviceCardManager.setIccid(strIccid);
    			deviceCardManager.setId(resultCard.getId());
    			deviceCardManager.setUpdatedDate(nowTime);
    			deviceCardManagerMapper.updateByPrimaryKeySelective(deviceCardManager);
    		}
    	}
    	return  deviceCardManager;
    }
    
    //根据发货单号获取下单商户号
    private String findOrderMerchantNoBySendOrderNo(String orderCode)
    {
    	try
    	{
    		MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        	merchantOrderDetail.setDispatchOrderNumber(orderCode);
        	merchantOrderDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
        	if(StringUtils.isEmpty(merchantOrderDetail))
        	{
        		return "";
        	}
        	MerchantOrder merchantOrder = new MerchantOrder();
        	merchantOrder.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
        	merchantOrder = merchantOrderMapper.selectOne(merchantOrder);
        	
        	return merchantOrder.getMerchantCode();
    	}	
    	catch (Exception e) 
        {
            logger.error("SupplyChainSyncServiceImpl::findOrderMerchantNoBySendOrderNo 调用失败：" + e.getMessage(), e);
        }
    	
    	return "";
    }


    /**
     * 同步库存
     */
    @Override
    public void SyncDeviceFileUnstockToFlowcatPlat() {

        logger.info("执行SyncDeviceFileUnstockToFlowcatPlat方法");
        SyncResultLog syncResultLog;
        SyncLastidRecord syncLastidRecord = syncLastidRecordMapper.getSyncLastidRecord();
        DeviceImeiStock deviceImeiStock = new DeviceImeiStock();
        deviceImeiStock.setId(syncLastidRecord.getLastFlowCardId());
        List<DeviceImeiStock> deviceFileUnstockList = deviceImeiStockMapper.selectDeviceImeiStockList(deviceImeiStock);

        List<com.glsx.oms.flowservice.api.entity.DeviceImeiStock> omsDeviceImeiStockList = new ArrayList<>();
        List<SyncResultLog> syncResultLogList = new ArrayList<>();
        com.glsx.oms.flowservice.api.entity.DeviceImeiStock omsDeviceImeiStock;

        if(deviceFileUnstockList != null && deviceFileUnstockList.size() > 0){
            for (DeviceImeiStock data : deviceFileUnstockList) {
                syncResultLog = new SyncResultLog();
                omsDeviceImeiStock = new com.glsx.oms.flowservice.api.entity.DeviceImeiStock();
                omsDeviceImeiStock.setImei(data.getImei());
                if (data.getExternalFlag().equals(DeviceImeiStockStatusEnum.DEVICE_IMEI_STOCK_STATUS_ENUM_EX.getCode())) {
                    omsDeviceImeiStock.setDeviceSource(1);
                } else if (data.getExternalFlag().equals(DeviceImeiStockStatusEnum.DEVICE_IMEI_STOCK_STATUS_ENUM_IN.getCode())) {
                    omsDeviceImeiStock.setDeviceSource(0);
                }
                omsDeviceImeiStock.setTypeId(data.getDevType());
                if(null != data && null != data.getMerchantNo() && !"null".equals(data.getMerchantNo())){
                    omsDeviceImeiStock.setMerchantId(Integer.valueOf(data.getMerchantNo()));
                }
                omsDeviceImeiStockList.add(omsDeviceImeiStock);

                syncResultLog.setIccid("11111111111111111");
                syncResultLog.setResult(SyncResultLogStatusEnum.SYNC_RESULT_LOG_STATUS_ENUM_SU.getCode());
                syncResultLog.setFlag(SyncResultLogFlagEnum.SYNC_RESULT_LOG_FLAG_ENUM_CA.getCode());
                syncResultLog.setImei(data.getImei());
                syncResultLog.setSn(data.getImei());
                syncResultLog.setDeletedFlag("N");
                syncResultLog.setCreatedBy("admin");
                syncResultLog.setUpdatedBy("admin");
                syncResultLog.setCreatedDate(new Date());
                syncResultLog.setUpdatedDate(new Date());
                syncResultLogList.add(syncResultLog);
            }
            try {
                DeviceImeiStockRequest deviceImeiStockRequest = new DeviceImeiStockRequest();

                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                deviceImeiStockRequest.setTime(time);  //时间
                deviceImeiStockRequest.setVersion("1.0.0"); //版本号
                deviceImeiStockRequest.setConsumer("task-supplychain");

                deviceImeiStockRequest.setList(omsDeviceImeiStockList);
                deviceService.batchSaveDeviceImei(deviceImeiStockRequest);
            } catch (Exception e) {
                for (int i = 0; i < syncResultLogList.size(); i++) {
                    syncResultLogList.get(i).setResult(SyncResultLogStatusEnum.SYNC_RESULT_LOG_STATUS_ENUM_FA.getCode());
                    syncResultLogList.get(i).setErrorCode(ErrorCodeEnum.ERRCODE_DEVICE_STOCK_FAILED_REQUEST_FLOWCARD.getCode());
                    syncResultLogList.get(i).setErrorMsg(ErrorCodeEnum.ERRCODE_DEVICE_STOCK_FAILED_REQUEST_FLOWCARD.getDescrible());
                }
            }
            if(syncResultLogList != null && syncResultLogList.size()>0){
                syncResultLogMapper.insertList(syncResultLogList);
            }
            syncLastidRecord.setLastFlowCardId(deviceFileUnstockList.get(deviceFileUnstockList.size() - 1).getId());
            syncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
        }
    }


 
    /**
     * @param
     * @return void
     * @throws
     * @Title: sysDeviceCategories
     * @Description: 刷新产品价格
     */
    public void sysProductPriceRefresh()  {
        logger.info("修改产品价格开始——————————————————————");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);
        long datelong = 0;
        try {
            date = sdf.parse(dateNowStr);
            datelong = date.getTime();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_TOMORROW.getCode());
        productHistoryPrice.setDeletedFlag("N");
        List<ProductHistoryPrice> productHistoryPriceList = productHistoryPriceMapper.select(productHistoryPrice);
        for(ProductHistoryPrice historyPrice : productHistoryPriceList){
            if(historyPrice.getTime() != null && historyPrice.getTime().getTime() == datelong){
                productHistoryPriceMapper.updateNowTypeByProductCode(historyPrice);
                productHistoryPriceMapper.updateTypeByProductCode(historyPrice);
            }
        }
    }


    static public String TimeStamp2Date(String timestampString, String formats) {

        if (StringUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }

        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }


    /**
     * @param
     * @return void
     * @throws
     * @Title: sysSettlementSendData
     * @Description: 对账结算数据同步
     */
    public void sysSettlementSendData(){
        logger.info("对账结算数据同步开始——————————————————————");
        //查询同步数据ID记录表
        SyncLastidRecord syncLastidRecord = syncLastidRecordMapper.getSyncLastidRecord();
        logger.info("对账结算数据同步最后ID：{}", syncLastidRecord.getSettlementId());
        Integer sum = 0;
        Integer settlementId = syncLastidRecord.getSettlementId();
        while (sum < 20000){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SettlementInfo settlementInfo = new SettlementInfo();
            settlementInfo.setId(settlementId);
            settlementInfo = settlementInfoMapper.selectOne(settlementInfo);
            if(settlementInfo == null){
                logger.info("暂无对账结算数据");
                break;
            }

            //根据产品查询物料信息
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductCode(settlementInfo.getProductCode());
            List<ProductDetail> productDetail1List = productDetailMapper.select(productDetail);

            //查询商户订单详情
            MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
            merchantOrderDetail.setMerchantOrderNumber(settlementInfo.getCustomerOrderNum());
            merchantOrderDetail.setProductCode(settlementInfo.getProductCode());
            MerchantOrderDetail merchantOrderDetail1Info = merchantOrderDetailMapper.selectOne(merchantOrderDetail);

            DealerUserInfo dealerUserInfo = new DealerUserInfo();
            dealerUserInfo.setName(merchantOrderDetail1Info.getCreatedBy());
            dealerUserInfo = dealerUserInfoMapper.selectOne(dealerUserInfo);

            //填充物料信息
            List<Material> materialList = new ArrayList<>();
            Material material;
            for(ProductDetail  list : productDetail1List){
                //每个物料的含税单价
                SalesSummarizingMaterialDetail salesSummarizingMaterialDetail = new SalesSummarizingMaterialDetail();
                salesSummarizingMaterialDetail.setSalesId(settlementInfo.getSalesId());
                salesSummarizingMaterialDetail.setMaterialCode(list.getMaterialCode());
                salesSummarizingMaterialDetail.setProductCode(settlementInfo.getProductCode());
                SalesSummarizingMaterialDetail  materialDetailInfo = salesSummarizingMaterialDetailMapper.selectOne(salesSummarizingMaterialDetail);
                material = new Material();
                material.setMaterialCode(list.getMaterialCode());
                if(materialDetailInfo != null && materialDetailInfo.getMaterialPrice() != null){
                    material.setMaterialPrice(materialDetailInfo.getMaterialPrice());
                }
                materialList.add(material);
            }

            OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderCode(merchantOrderDetail1Info.getDispatchOrderNumber());
            //根据物流单查询sn
            OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
            orderInfoDetail.setLogisticsId(settlementInfo.getLogisticsId().intValue());
            List<OrderInfoDetail> orderInfoDetailList = orderInfoDetailMapper.select(orderInfoDetail);

            for(OrderInfoDetail orderDate : orderInfoDetailList){
                //根据物流id定位到对应的物流信息
                Logistics logistics = new Logistics();
                logistics.setId(Long.valueOf(orderDate.getLogisticsId()));
                Logistics logisticsInfo = logisticsMapper.selectOne(logistics);

                Settlement settlement = new Settlement();
                settlement.setId(settlementInfo.getId());
                //客户订单
                settlement.setCustomerOrderNum(settlementInfo.getCustomerOrderNum());
                settlement.setSettlementDate(settlementInfo.getCreatedDate());
                if(orderInfo.getSendMerchantName() != null){
                    settlement.setSentMerchant(orderInfo.getSendMerchantName());
                }
                settlement.setDeliveryOrderNum(settlementInfo.getDeliveryOrderNum());
                settlement.setMaterialList(materialList);
                if(null != logisticsInfo && logisticsInfo.getOrderNumber() != null){
                    settlement.setLogisticsOrderNum(logisticsInfo.getOrderNumber());
                }
                settlement.setMerchantName(dealerUserInfo.getMerchantName());
                settlement.setSn(orderDate.getImei());
                settlement.setSentDate(sdf.format(orderDate.getUpdatedDate()));
                settlement.setCreatedBy("admin");
                settlement.setCreatedDate(new Date());
                settlement.setUpdatedBy("admin");
                settlement.setUpdatedDate(new Date());
                settlement.setDeletedFlag("N");

                //发送数据
                String str = JSON.toJSONString(settlement);
                kafkaProducer.sendObject(str.getBytes());
            }
            settlementId++;
            this.updateLastId(settlementInfo.getId());
            sum += orderInfoDetailList.size();
        }
        logger.info("对账结算数据同步结束——————————————————————");
    }


    private void updateLastId(Integer lastId) {
        lastId++;
        SyncLastidRecord synLastidInfo = new SyncLastidRecord();
        synLastidInfo.setId(1);
        synLastidInfo.setSettlementId(lastId);
        int result = syncLastidRecordMapper.updateSyncLastidRecord(synLastidInfo);
        if (result > 0) {
            logger.info("保存最后ID成功！");
        } else {
            logger.info("保存最后ID失败！");
        }
    }
    
  

}
