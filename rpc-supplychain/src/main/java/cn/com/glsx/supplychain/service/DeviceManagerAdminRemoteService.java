package cn.com.glsx.supplychain.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.glsx.supplychain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.DeviceEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.mapper.DeviceCardManagerMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileSnapshotMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileVirtualMapper;
import cn.com.glsx.supplychain.mapper.DeviceImeiStockMapper;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.mapper.DeviceResetRecordMapper;
import cn.com.glsx.supplychain.mapper.DeviceUpdateRecordMapper;


/** 
* @Title: DeviceManagerAdminRemoteService  
* @Description:设备管理综合业务接口
* @param:  deviceCode
* @return: Integer
* @throws: RpcServiceException
*/
@Service
@Transactional
public class DeviceManagerAdminRemoteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceFileSnapshotMapper deviceFileSnapshotMapper;
	
	@Autowired
	private DeviceUpdateRecordMapper deviceUpdateRecordMapper;
	
	@Autowired
	private DeviceFileMapper deviceFileMapper;
	
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Autowired
	private DeviceFileVirtualMapper deviceFileVirtualMapper;
	
	@Autowired
	private DeviceResetRecordMapper deviceResetRecordMapper;
	
	@Autowired
	private DeviceCardManagerMapper deviceCardManagerMapper;
	
	@Autowired
	private DeviceImeiStockMapper deviceImeiStockMapper;
	
	@Autowired
	private DeviceUpdateRecordService deviceUpdateRecordService;

	@Autowired
	private DeviceCodeService deviceCodeService;

	/** 
	* @Title: initDeviceFileByDeviceResetRecord  
	* @Description:根据添加初始化记录初始化设备
	* @param:  deviceResetRecord
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public DeviceResetRecord initDeviceFileByDeviceResetRecord(DeviceResetRecord deviceResetRecord) throws RpcServiceException
	{
		if(StringUtils.isEmpty(deviceResetRecord) || StringUtils.isEmpty(deviceResetRecord.getSn()))
		{
			logger.error("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord param deviceResetRecord:{}", deviceResetRecord);
		
		String deviceSn = deviceResetRecord.getSn();
		try
		{
			//清除库存表中的数据 使设备能够再次扫码入库		
			//DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoByImei(deviceSn);
			DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(deviceSn);
			if(!StringUtils.isEmpty(deviceInfo))
			{
				deviceInfo.setImei("");
				deviceInfo.setIccid("");
				deviceInfo.setOrderCode("");
				deviceInfo.setStatus(DeviceEnum.STATUS_IN.getValue());
				deviceInfo.setDeletedFlag("Y");
				deviceInfo.setUpdatedBy(deviceResetRecord.getUpdatedBy());
				deviceInfo.setUpdatedDate(new Date());
				deviceInfoMapper.update(deviceInfo);
				logger.info("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord initial deviceInfo ok! deviceInfo{}",deviceInfo);
			}
			
			//清除绑定关系
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotMapper.selectByPrimaryKey(deviceSn);
			
			if(!StringUtils.isEmpty(deviceFileSnapshot))
			{
				//判断是否虚拟卡入库 如果是则删除
				if(!StringUtils.isEmpty(deviceFileSnapshot.getCardId()))
				{
					DeviceCardManager cardManager = deviceCardManagerMapper.selectByPrimaryKey(deviceFileSnapshot.getCardId());
					if(!StringUtils.isEmpty(cardManager))
					{
						if(!StringUtils.isEmpty(cardManager.getImsi()))
						{
							DeviceFileVirtual deviceFileVirtual = deviceFileVirtualMapper.selectByImsi(cardManager.getImsi());
							if(!StringUtils.isEmpty(deviceFileVirtual))
							{
								deviceFileVirtual.setDeletedFlag("Y");
								deviceFileVirtualMapper.updateByPrimaryKeySelective(deviceFileVirtual);
							}
						}

					}
				}
				//记录修改关系
				try
				{
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue(), deviceFileSnapshot.getFirmwareId(), deviceResetRecord.getUpdatedBy());
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceFileSnapshot.getCardId(), deviceResetRecord.getUpdatedBy());
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_USER.getValue(), deviceFileSnapshot.getUserId(), deviceResetRecord.getUpdatedBy());
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue(), deviceFileSnapshot.getPackageUserId(), deviceResetRecord.getUpdatedBy());
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue(), deviceFileSnapshot.getVehicleId(), deviceResetRecord.getUpdatedBy());
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_PACK.getValue(), deviceFileSnapshot.getPackageId(), deviceResetRecord.getUpdatedBy());
				}
				catch(RpcServiceException e)
				{
					logger.error("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord 插入修改记录表错误" + e.getMessage());
					throw e;
				}
				
				deviceFileSnapshot.setPackageId(null);
				deviceFileSnapshot.setAndroidPackageId(null);
				deviceFileSnapshot.setPackageUserId(null);
				deviceFileSnapshot.setUserId(null);
				deviceFileSnapshot.setPackageUserTime(null);
				deviceFileSnapshot.setUserTime(null);
				deviceFileSnapshot.setCardId(null);
				deviceFileSnapshot.setCardTime(null);
				deviceFileSnapshot.setFirmwareId(null);
				deviceFileSnapshot.setVehicleId(null);	
				deviceFileSnapshot.setDeletedFlag("Y");
				deviceFileSnapshot.setPackageStatu("IN");
				deviceFileSnapshot.setUpdatedBy(deviceResetRecord.getUpdatedBy());
				deviceFileSnapshot.setUpdatedDate(new Date());
				
				deviceFileSnapshotMapper.updateByPrimaryKey(deviceFileSnapshot);
				logger.info("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord initial deviceFileSnapshot ok! deviceFileSnapshot{}",deviceFileSnapshot);
				
				//清除实际的设备数据
				DeviceFile record = new DeviceFile();
				record.setSn(deviceSn);
				DeviceFile deviceFile = deviceFileMapper.selectByUniqueKey(record);
				if(!StringUtils.isEmpty(deviceFile))
				{				
					deviceFile.setDeviceCode(null);
					deviceFile.setVerifCode(null);
					deviceFile.setBatchNo(null);
					deviceFile.setPackageId(null);
					deviceFile.setAndroidPackageId(null);
					deviceFile.setOperatorMerchantNo(null);
					deviceFile.setSendMerchantNo(null);
					deviceFile.setInStorageTime(null);
					deviceFile.setOutStorageTime(null);
					deviceFile.setOutStorageType(null);
					deviceFile.setTerminalDiscode(null);
					deviceFile.setExternalFlag(null);
					deviceFile.setManufacturerCode(null);
					deviceFile.setFirmwareId(null);
					deviceFile.setCardId(null);
					deviceFile.setDeletedFlag("Y");
					deviceFile.setUpdatedBy(deviceResetRecord.getUpdatedBy());
					deviceFile.setUpdatedDate(new Date());
					deviceFileMapper.updateByPrimaryKey(deviceFile);
					logger.info("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord initial deviceFile ok! deviceFile{}",deviceFile);
				}		
			}
			
			deviceResetRecordMapper.insertSelective(deviceResetRecord);
			deviceResetRecord = deviceResetRecordMapper.selectByPrimaryKey(deviceResetRecord.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceManagerAdminRemoteService::initDeviceFileByDeviceResetRecord return deviceResetRecord:{}", deviceResetRecord);
		
		return deviceResetRecord;
	}
	
	/** 
	* @Title: importDeviceFileForInnerDevice  
	* @Description:内部卡内部设备导入
	* @param:  deviceCode
	* @return: void
	* @throws: RpcServiceException
	*/
	public void importDeviceFileForInnerDevice(String operatorName,String strDeviceCode, List<DeviceListImport> importList) throws RpcServiceException
	{
		if(StringUtils.isEmpty(strDeviceCode) || StringUtils.isEmpty(importList))
		{
			logger.error("DeviceManagerAdminRemoteService::importDeviceFileForInnerDevice 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		operatorName = (StringUtils.isEmpty(operatorName)?"admin":operatorName);
		logger.info("DeviceManagerAdminRemoteService::importDeviceFileForInnerDevice param operatorName=" + operatorName + " strDeviceCode="+ strDeviceCode + " importList.size=" + importList.size());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowTime = new Date();
		String timeNow = df.format(nowTime);
		
		try
		{
			//批量插入流量卡并且获取卡id
			Map<String,Integer> mapCardImsi = new HashMap<String,Integer>();
			List<DeviceCardManager> listDeviceCard = new ArrayList<DeviceCardManager>();
			List<DeviceFileSnapshot> listDeviceSnapshot = new ArrayList<DeviceFileSnapshot>();
			List<DeviceFile> listDeviceFile = new ArrayList<DeviceFile>();
			List<DeviceImeiStock> listDeviceStock = new ArrayList<DeviceImeiStock>();
	//		List<DeviceUpdateRecord> listUpdateRecord = new ArrayList<DeviceUpdateRecord>();
			List<String> listImsis = new ArrayList<String>();
			
			for(DeviceListImport item:importList)
			{
				DeviceCardManager deviceCardManager = new DeviceCardManager();
				deviceCardManager.setId(null);
				deviceCardManager.setIccid(item.getIccid());
				deviceCardManager.setImsi(item.getImsi()); 
				deviceCardManager.setCompanyId(1);
				deviceCardManager.setDeletedFlag("N");
				deviceCardManager.setCreatedBy(operatorName);
				deviceCardManager.setUpdatedBy(operatorName);
				deviceCardManager.setCreatedDate(nowTime);
				deviceCardManager.setUpdatedDate(nowTime);
				listDeviceCard.add(deviceCardManager);
				listImsis.add(item.getImsi());

				DeviceImeiStock imeiStock = new DeviceImeiStock();
				imeiStock.setImei(item.getImei());
				imeiStock.setExternalFlag(DeviceEnum.STATUS_IN.getValue());
				DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(Integer.valueOf(strDeviceCode));
				imeiStock.setDevType(deviceCode.getTypeId());
				imeiStock.setMerchantNo(item.getMentchantNo());
				imeiStock.setCreatedBy(operatorName);
				imeiStock.setUpdatedBy(operatorName);
				imeiStock.setCreatedDate(nowTime);
				imeiStock.setUpdatedDate(nowTime);
				listDeviceStock.add(imeiStock);
			}
			
			deviceCardManagerMapper.batchInsertOnDuplicateKeyUpdate(listDeviceCard);
			
			deviceImeiStockMapper.batchInsertOnDuplicateKeyUpdate(listDeviceStock);
			
			Map<String,Object> mapParam = new HashMap<String,Object>();
			mapParam.put("companyId", 1);
			mapParam.put("imsis", listImsis);
			
			listDeviceCard = deviceCardManagerMapper.getSampleColumByCollectImsi(mapParam);
			
			for(DeviceCardManager item:listDeviceCard)
			{
				mapCardImsi.put(item.getImsi(), item.getId());
			}
			
			for(DeviceListImport item:importList)
			{
				DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
				DeviceFile deviceFile = new DeviceFile();
			//	DeviceUpdateRecord recordCard = new DeviceUpdateRecord();
			//	DeviceUpdateRecord recordPackage = new DeviceUpdateRecord();
				Integer cardId = mapCardImsi.get(item.getImsi());
				deviceFileSnapshot.setId(null);
				deviceFileSnapshot.setCardId(cardId);
				deviceFileSnapshot.setCardTime(timeNow);
				deviceFileSnapshot.setSn(item.getImei());
				deviceFileSnapshot.setPackageId(Integer.valueOf(item.getPackageNo()));
				deviceFileSnapshot.setPackageStatu("UA");
				deviceFileSnapshot.setDeletedFlag("N");
				deviceFileSnapshot.setCreatedBy(operatorName);
				deviceFileSnapshot.setUpdatedBy(operatorName);
				deviceFileSnapshot.setCreatedDate(nowTime);
				deviceFileSnapshot.setUpdatedDate(nowTime);
				listDeviceSnapshot.add(deviceFileSnapshot);
				
				deviceFile.setId(null);
				deviceFile.setSn(item.getImei());
				deviceFile.setDeviceCode(Integer.valueOf(strDeviceCode));
				deviceFile.setBatchNo(item.getBatchNo());
				deviceFile.setPackageId(Integer.valueOf(item.getPackageNo()));
				deviceFile.setOperatorMerchantNo(null);
				deviceFile.setVerifCode(item.getVcode());
				deviceFile.setSendMerchantNo(item.getMentchantNo());
				deviceFile.setInStorageTime(timeNow);
				deviceFile.setOutStorageTime(timeNow);
				deviceFile.setOutStorageType("SC");
				deviceFile.setExternalFlag("IN");
				deviceFile.setDeletedFlag("N");
				deviceFile.setOrderCode(null);	
				deviceFile.setCardId(cardId);
				deviceFile.setCreatedBy(operatorName);
				deviceFile.setUpdatedBy(operatorName);
				deviceFile.setCreatedDate(nowTime);
				deviceFile.setUpdatedDate(nowTime);
				listDeviceFile.add(deviceFile);
				
//				recordCard.setSn(deviceFileSnapshot.getSn());
//				recordCard.setFlagType("CA");
//				recordCard.setPreFlagId(deviceFileSnapshot.getCardId());
//				recordCard.setCreatedBy(operatorName);
//				recordCard.setUpdatedBy(operatorName);
//				recordCard.setCreatedDate(nowTime);
//				recordCard.setUpdatedDate(nowTime);
//				listUpdateRecord.add(recordCard);
				
//				recordPackage.setSn(deviceFileSnapshot.getSn());
//				recordPackage.setFlagType("PA");
//				recordPackage.setPreFlagId(Integer.valueOf(item.getPackageNo()));
//				recordPackage.setCreatedBy(operatorName);
//				recordPackage.setUpdatedBy(operatorName);
//				recordPackage.setCreatedDate(nowTime);
//				recordPackage.setUpdatedDate(nowTime);
//				listUpdateRecord.add(recordPackage);
			}
			
			deviceFileMapper.batchInsertOnDuplicateKeyUpdate(listDeviceFile);
			logger.info("录入的信息为:{}",listDeviceSnapshot);
			deviceFileSnapshotMapper.batchInsertOnDuplicateKeyUpdate(listDeviceSnapshot);
//			deviceUpdateRecordMapper.batchInsertOnDuplicateKeyUpdate(listUpdateRecord);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("DeviceManagerAdminRemoteService::importDeviceListByDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceManagerAdminRemoteService::importDeviceListByDeviceCode return ok");
		
		
		/*
		try
		{
			DeviceFile deviceFile = new DeviceFile();
			DeviceCardManager deviceCardManager = new DeviceCardManager();
			DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
			DeviceUpdateRecord updateRecord = new DeviceUpdateRecord();
			
			for(DeviceListImport item:importList)
			{
				logger.info("DeviceManagerAdminRemoteService::importDeviceFileForInnerDevice handle item=" + item.toString());
				
				deviceCardManager.setId(null);
				deviceCardManager.setIccid(item.getIccid());
				deviceCardManager.setImsi(item.getImsi()); 
				deviceCardManager.setCompanyId(1);
				DeviceCardManager resultCard = deviceCardManagerMapper.selectByUniqueKey(deviceCardManager);
				if(resultCard != null)
				{
					deviceCardManager.setId(resultCard.getId());	
					deviceCardManager.setUpdatedDate(nowTime);
					deviceCardManagerMapper.updateByPrimaryKeySelective(deviceCardManager);
				}
				else
				{
					deviceCardManager.setCreatedBy("admin");
					deviceCardManager.setUpdatedBy("admin");
					deviceCardManager.setCreatedDate(nowTime);
					deviceCardManager.setUpdatedDate(nowTime);
					deviceCardManagerMapper.insertSelective(deviceCardManager);
				}
				
				//设备关系设置
				deviceFileSnapshot.setId(null);
				deviceFileSnapshot.setCardId(deviceCardManager.getId());
				deviceFileSnapshot.setCardTime(timeNow);
				deviceFileSnapshot.setSn(item.getImei());
				deviceFileSnapshot.setPackageId(Integer.valueOf(item.getPackageNo()));
				deviceFileSnapshot.setPackageStatu("UA");
				deviceFileSnapshot.setDeletedFlag("N");
				
				//设备清单数据设置
				deviceFile.setId(null);
				deviceFile.setSn(item.getImei());
				deviceFile.setDeviceCode(Integer.valueOf(strDeviceCode));
				deviceFile.setBatchNo(item.getBatchNo());
				deviceFile.setPackageId(Integer.valueOf(item.getPackageNo()));
				deviceFile.setOperatorMerchantNo(null);
				deviceFile.setVerifCode(item.getVcode());
				deviceFile.setSendMerchantNo(item.getMentchantNo());
				deviceFile.setInStorageTime(timeNow);
				deviceFile.setOutStorageTime(timeNow);
				deviceFile.setCreatedDate(nowTime);
				deviceFile.setUpdatedDate(nowTime);
				deviceFile.setCreatedBy("admin");
				deviceFile.setUpdatedBy("admin");
				deviceFile.setOutStorageType("SC");
				deviceFile.setExternalFlag("IN");
				deviceFile.setDeletedFlag("N");
				deviceFile.setOrderCode(null);	
				deviceFile.setCardId(deviceCardManager.getId());
				
				//检查录入条件 
				//获取设备关系
				DeviceFileSnapshot snapshot = deviceFileSnapshotMapper.selectByPrimaryKey(deviceFileSnapshot.getSn());
				
				//获取设备清单
				DeviceFile record = deviceFileMapper.selectByUniqueKey(deviceFile);
				
				if((snapshot != null && "N".equals(snapshot.getDeletedFlag())) || (record != null && "N".equals(snapshot.getDeletedFlag())))
				{
					logger.error("DeviceManagerAdminRemoteService::importDeviceFileForInnerDevice 插入失败 imei:" + item.getImei() + "已经存在库中!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICEFILE_EXIST);
				}
				
				//插入关系数据
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
				
				//插入设备清单数据
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
				
				//插入修改记录（卡）
				updateRecord.setId(null);
				updateRecord.setSn(deviceFileSnapshot.getSn());
				updateRecord.setFlagType("CA");
				DeviceUpdateRecord deviceUpdateRecord = deviceUpdateRecordMapper.selectLastRecord(updateRecord);
				if(deviceUpdateRecord ==null || (deviceUpdateRecord!=null && deviceUpdateRecord.getFlagId() != null))
				{
					updateRecord.setPreFlagId(deviceFileSnapshot.getCardId());
					updateRecord.setCreatedBy("admin");
					updateRecord.setUpdatedBy("admin");
					updateRecord.setCreatedDate(nowTime);
					updateRecord.setUpdatedDate(nowTime);
					deviceUpdateRecordMapper.insertSelective(updateRecord);
				}
				else
				{
					updateRecord.setId(deviceUpdateRecord.getId());
					updateRecord.setUpdatedDate(nowTime);
					deviceUpdateRecordMapper.updateByPrimaryKeySelective(updateRecord);
				}
				
				//插入修改记录（套餐）
				updateRecord.setId(null);
				updateRecord.setSn(deviceFileSnapshot.getSn());
				updateRecord.setFlagType("PA");
				DeviceUpdateRecord deviceUpdateRecordTemp = deviceUpdateRecordMapper.selectLastRecord(updateRecord);
				if(deviceUpdateRecordTemp ==null || (deviceUpdateRecordTemp!=null && deviceUpdateRecordTemp.getFlagId() != null))
				{
					updateRecord.setPreFlagId(Integer.valueOf(item.getPackageNo()));
					updateRecord.setCreatedBy("admin");
					updateRecord.setUpdatedBy("admin");
					updateRecord.setCreatedDate(nowTime);
					updateRecord.setUpdatedDate(nowTime);
					deviceUpdateRecordMapper.insertSelective(updateRecord);
				}
				else
				{
					updateRecord.setId(deviceUpdateRecord.getId());
					updateRecord.setFlagId(Integer.valueOf(item.getPackageNo()));
					updateRecord.setUpdatedDate(nowTime);
					deviceUpdateRecordMapper.updateByPrimaryKeySelective(updateRecord);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("DeviceManagerAdminRemoteService::importDeviceListByDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceManagerAdminRemoteService::importDeviceListByDeviceCode return ok");
		*/
	}
}
