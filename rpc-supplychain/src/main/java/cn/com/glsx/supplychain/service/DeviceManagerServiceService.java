package cn.com.glsx.supplychain.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.com.glsx.supplychain.remote.DeviceManagerService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.util.UpdateDeviceFileRecordThread;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ExternalFlagEnum;
import cn.com.glsx.supplychain.enums.OutStorageTypeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.mapper.DeviceCardManagerMapper;
import cn.com.glsx.supplychain.mapper.DeviceCodeMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileSnapshotMapper;
import cn.com.glsx.supplychain.mapper.DeviceFileVirtualMapper;
import cn.com.glsx.supplychain.mapper.DeviceTypeMapper;
import cn.com.glsx.supplychain.mapper.DeviceUpdateRecordMapper;
import cn.com.glsx.supplychain.mapper.DeviceUserManagerMapper;
import cn.com.glsx.supplychain.mapper.DeviceVehicleManagerMapper;
import cn.com.glsx.supplychain.mapper.FirmwareInfoMapper;
import cn.com.glsx.supplychain.model.DeviceCardManager;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import cn.com.glsx.supplychain.model.DeviceFileVirtual;
import cn.com.glsx.supplychain.model.DeviceUpdateRecord;
import cn.com.glsx.supplychain.model.DeviceUserManager;
import cn.com.glsx.supplychain.model.DeviceVehicleManager;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;

@Service
@Transactional
public class DeviceManagerServiceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(256);
    
    @Autowired
	private DeviceFileService deviceFileService;
    
    @Autowired
    private DeviceCodeService deviceCodeService;
	
	@Autowired
	private DeviceFileVirtualService deviceFileVirtualService;
	
	@Autowired
	private DeviceFileSnapshotService deviceFileSnapshotService;
	
	@Autowired
	private DeviceCardManagerService deviceCardManagerService;
	
	@Autowired
	private DeviceUserManagerService deviceUserManagerService;
	
	@Autowired
	private DeviceVehicleManagerService deviceVehicleManagerService;
	
	@Autowired
	private FirmwareInfoService firmwareInfoService;

	@Autowired
	DeviceUpdateRecordService deviceUpdateRecordService;
	  
	/**
     * @param 
     * @return 
     * @Title updateDeviceFileSoftVersion
     * @Description 绑定设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	public void updateDeviceFileSoftVersion(SupplyDeviceFileRequest request) throws RpcServiceException
	{
		try
		{
			Date timeDate = new Date();		
			DeviceFileSnapshot deviceFileSnapshot = null;
			FirmwareInfo record = new FirmwareInfo();
			record.setConfigure(17);
			record.setModel(18);
			record.setType(16);
			record.setSoftVersion(request.getSoftVersion());
			record.setCreatedBy(request.getConsumer());
			record.setCreatedDate(new Date());
			record.setUpdatedBy(request.getConsumer());
			record.setUpdatedDate(new Date());
			FirmwareInfo firmwareInfo = firmwareInfoService.addFrimwareInfoByVersion(record);
			
			deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备不存在 sn=",request.getSn());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFileSnapshot.getDeletedFlag()))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备已被初始化 sn=",request.getSn());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_INITIAL);
			}
			
			DeviceFileSnapshot updateCondition = new DeviceFileSnapshot();
			updateCondition.setSn(request.getSn());
			updateCondition.setFirmwareId(firmwareInfo.getId());
			updateCondition.setUpdatedBy(request.getConsumer());
			updateCondition.setUpdatedDate(timeDate);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(updateCondition);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue(), deviceFileSnapshot.getFirmwareId(), request.getConsumer());
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue(), firmwareInfo.getId(), request.getConsumer());
		}
		catch(RpcServiceException e)
	  	{
			throw e;
	  	}
	}

    /**
     * @param 
     * @return 
     * @Title bindDeviceFileToVehicleFlag
     * @Description 绑定设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	public void bindDeviceFileToVehicleFlag(SupplyDeviceFileRequest request) throws RpcServiceException
	{
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			//如果车辆绑定其他设备 或者 设备未入库 则返回失败
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			if(!StringUtils.isEmpty(deviceFileSnapshot.getVehicleId()))
			{
				//设备已被绑定车辆
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "已被绑定在其他车辆上!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_VEHICLE_ALREAD_BAND);
			}
		
			//获取车辆信息
			DeviceVehicleManager record = new DeviceVehicleManager();
			record.setVehicleFlag(request.getVehicleFlag());
			record.setFlagType(request.getFlagType().getValue());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(new Date());
			record.setUpdatedDate(new Date());
			DeviceVehicleManager deviceVehicleManager = deviceVehicleManagerService.addDeviceVehicle(record);
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
			newDeviceFileSnapshot.setVehicleId(deviceVehicleManager.getId());
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			newDeviceFileSnapshot.setSn(deviceFileSnapshot.getSn());
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue(), deviceVehicleManager.getId(), request.getConsumer());
		}
		catch(RpcServiceException e)
	  	{
			throw e;
	  	}
	}

    /**
     * @param 
     * @return 
     * @Title unBindDeviceFileToVehicleFlag
     * @Description 解绑设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	 public void unBindDeviceFileToVehicleFlag(SupplyDeviceFileRequest request) throws RpcServiceException
	 {
		 try
		 {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			
			//如果设备不存在返回失败
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			
			//获取车辆信息
			DeviceVehicleManager record = new DeviceVehicleManager();
			record.setVehicleFlag(request.getVehicleFlag());
			record.setFlagType(request.getFlagType().getValue());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(new Date());
			record.setUpdatedDate(new Date());
			DeviceVehicleManager deviceVehicleManager = deviceVehicleManagerService.addDeviceVehicle(record);
			
			//设备车辆绑定不一致
			if(!deviceFileSnapshot.getVehicleId().equals(deviceVehicleManager.getId()))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + " 车辆id:" + deviceVehicleManager.getId() + "不一致");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ATYPISM);
			}
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
			newDeviceFileSnapshot.setSn(deviceFileSnapshot.getSn());
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			newDeviceFileSnapshot.setVehicleId(null);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue(), deviceVehicleManager.getId(), request.getConsumer());			
		 }
		 catch(RpcServiceException e)
	  	 {
			throw e;
	  	 }
	 }

    /**
     * @param 
     * @return 
     * @Title bindCardToDeviceFile
     * @Description 绑定卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	 public void bindCardToDeviceFile(SupplyDeviceFileRequest request) throws RpcServiceException
	 {
		 try
		 {
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			//如果设备绑定其他卡或者设备未入库 返回失败
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::bindCardToDeviceFile 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::bindCardToDeviceFile 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::bindCardToDeviceFile 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			if(!StringUtils.isEmpty(deviceFileSnapshot.getCardId()))
			{
				//设备已被绑定其他卡
				logger.error("DeviceManagerServiceService::bindCardToDeviceFile 设备sn:" + request.getSn() + "已被绑定在其他车辆上!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_CARD_ALREAD_BAND);
			}
			
			//获取流量卡信息
			DeviceCardManager record = new DeviceCardManager();
			record.setImsi(request.getImsi());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(timeDate);
			record.setUpdatedDate(timeDate);
			DeviceCardManager deviceCardManager = deviceCardManagerService.addDeviceCard(record);
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();	
			newDeviceFileSnapshot.setCardId(deviceCardManager.getId());
			newDeviceFileSnapshot.setCardTime(timeString);
			newDeviceFileSnapshot.setSn(request.getSn());	
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceCardManager.getId(), request.getConsumer());

		 }
		 catch(RpcServiceException e)
	  	 {
			throw e;
	  	 }
	 }

    /**
     * @param 
     * @return 
     * @Title unBindCardToDeviceFile
     * @Description 解绑卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	public void unBindCardToDeviceFile(SupplyDeviceFileRequest request) throws RpcServiceException
	{
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			//如果设备不存在返回失败
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::unBindCardToDeviceFile 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::unBindCardToDeviceFile 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::unBindCardToDeviceFile 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}

			//获取流量卡信息
			DeviceCardManager record = new DeviceCardManager();
			record.setImsi(request.getImsi());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(timeDate);
			record.setUpdatedDate(timeDate);
			DeviceCardManager deviceCardManager = deviceCardManagerService.addDeviceCard(record);
			
			//流量卡绑定不一致
			if(!deviceFileSnapshot.getCardId().equals(deviceCardManager.getId()))
			{
				logger.error("DeviceManagerServiceService::bindDeviceFileToVehicleFlag 设备sn:" + request.getSn() + " 车辆id:" + deviceCardManager.getId() + "不一致");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ATYPISM);
			}
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
			newDeviceFileSnapshot.setCardId(null);
			newDeviceFileSnapshot.setCardTime(null);	
			newDeviceFileSnapshot.setSn(deviceFileSnapshot.getSn());	
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceCardManager.getId(), request.getConsumer());

		}
		catch(RpcServiceException e)
  	 	{
			throw e;
  	 	}
	}
    /**
     * @param 
     * @return 
     * @Title bindUserToDeviceFile
     * @Description 绑定用户到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	 public void bindUserToDeviceFile(SupplyDeviceFileRequest request) throws RpcServiceException
	 {
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			//设备已被绑定 设备未入库 设备未激活 则返回失败
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::bindUserToDeviceFile 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::bindUserToDeviceFile 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::bindUserToDeviceFile 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			if(!StringUtils.isEmpty(deviceFileSnapshot.getUserId()))
			{
				//设备已被其他用户绑定
				logger.error("DeviceManagerServiceService::bindUserToDeviceFile 设备sn:" + request.getSn() + "已被绑定其他用户!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_VEHICLE_ALREAD_BAND);
			}
			
			//获取用户信息
			DeviceUserManager record = new DeviceUserManager();
			record.setUserFlag(request.getUserFlag());
			record.setFlagType(request.getUserFlagType().getValue());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(new Date());
			record.setUpdatedDate(new Date());
			DeviceUserManager deviceUserManager = deviceUserManagerService.addDeviceUser(record);
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();	
			newDeviceFileSnapshot.setSn(deviceFileSnapshot.getSn());
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			newDeviceFileSnapshot.setUserId(deviceUserManager.getId());
			newDeviceFileSnapshot.setUserTime(timeString);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_USER.getValue(), deviceUserManager.getId(), request.getConsumer());

		}
		catch(RpcServiceException e)
   	 	{
			throw e;
   	 	}
	 }
	
    /**
     * @param 
     * @return 
     * @Title unBindUserToDeviceFile
     * @Description 
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
	 public void unBindUserToDeviceFile(SupplyDeviceFileRequest request) throws RpcServiceException
	 {
		 try
		 {
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeDate = new Date();
			String timeString = df.format(timeDate);
			
			//设备未入库 设备被初始化 
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFile))
			{
				logger.error("DeviceManagerServiceService::unBindUserToDeviceFile 设备sn:" + request.getSn() + "未入库!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if("Y".equals(deviceFile.getDeletedFlag()))
			{
				//已经被初始化的设备 返回失败
				logger.error("DeviceManagerServiceService::unBindUserToDeviceFile 设备sn:" + request.getSn() + "已被初始化!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
			if(StringUtils.isEmpty(deviceFileSnapshot))
			{
				//数据异常返回异常
				logger.error("DeviceManagerServiceService::unBindUserToDeviceFile 设备sn:" + request.getSn() + "快照关系异常!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			
			//获取用户信息
			DeviceUserManager record = new DeviceUserManager();
			record.setUserFlag(request.getUserFlag());
			record.setFlagType(request.getUserFlagType().getValue());
			record.setCompanyId(request.getCompanyId());
			record.setCreatedBy(request.getConsumer());
			record.setUpdatedBy(request.getConsumer());
			record.setCreatedDate(new Date());
			record.setUpdatedDate(new Date());
			DeviceUserManager deviceUserManager = deviceUserManagerService.addDeviceUser(record);
			
			//设备车辆绑定不一致
			if(StringUtils.isEmpty(deviceFileSnapshot.getUserId()) && !deviceFileSnapshot.getUserId().equals(deviceUserManager.getId()))
			{
				logger.error("DeviceManagerServiceService::unBindUserToDeviceFile 设备sn:" + request.getSn() + " 绑定用户id:" + deviceUserManager.getId() + "不一致");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ATYPISM);
			}
			
			DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
			newDeviceFileSnapshot.setSn(deviceFileSnapshot.getSn());	
			newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
			newDeviceFileSnapshot.setUpdatedDate(timeDate);
			newDeviceFileSnapshot.setUserId(null);
			newDeviceFileSnapshot.setUserTime(null);
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(newDeviceFileSnapshot);
			deviceUpdateRecordService.setDeviceUpdateRecord(request.getSn(), UpdateRecordEnum.UPDATE_RECORD_USER.getValue(), deviceUserManager.getId(), request.getConsumer());
		 }
		 catch(RpcServiceException e)
   	 	 {
			throw e;
   	 	 }
	 }

    /**
     * @param 
     * @return 
     * @Title activeDevicePackage
     * @Description 激活设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
     public void activeDevicePackage(SupplyDeviceFileRequest request) throws RpcServiceException
     {
    	 try
    	 {
    		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    		 Date nowDate = new Date();
    		 String timeDate = df.format(nowDate);
    		 boolean isCheckImsiDeviceType = true;
 			
    		//如果设备未入库或者被初始化返回失败
 			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());

 			//obd类设备不传imsi 通过设备信息反补imsi
 			if(!StringUtils.isEmpty(deviceFile))
 			{
 				if(!StringUtils.isEmpty(deviceFile.getDeviceCode()))
 				{
 					DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(deviceFile.getDeviceCode());
 					if(!StringUtils.isEmpty(deviceCode))
 		 			{
 		 				if(!StringUtil.isCheckImsiDeviceType(deviceCode.getTypeId()))
 		 				{
 		 					isCheckImsiDeviceType = false;
 		 					 			
 		 				}
 		 			}
 				}
 			}
 			if(!isCheckImsiDeviceType)
 			{
 				Integer cardId = deviceFile.getCardId();
				DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardById(cardId);
				if(!StringUtils.isEmpty(deviceCardManager))
				{
					request.setImsi(deviceCardManager.getImsi());
				}		
 			}

			 //获取用户信息
			 DeviceUserManager record = new DeviceUserManager();
			 record.setUserFlag(request.getUserFlag());
			 record.setFlagType(request.getUserFlagType().getValue());
			 record.setCompanyId(request.getCompanyId());
			 record.setCreatedBy(request.getConsumer());
			 record.setUpdatedBy(request.getConsumer());
			 record.setCreatedDate(nowDate);
			 record.setUpdatedDate(nowDate);
			 DeviceUserManager deviceUserManager = deviceUserManagerService.addDeviceUser(record);
			 logger.info("DeviceManagerServiceImpl::activeDevicePackage deviceUserManager:" + deviceUserManager.toString());

			 //获取卡信息
			 DeviceCardManager cardRecord = new DeviceCardManager();
			 cardRecord.setImsi(request.getImsi());
			 cardRecord.setCompanyId(request.getCompanyId());
			 record.setCreatedBy(request.getConsumer());
			 record.setUpdatedBy(request.getConsumer());
			 record.setCreatedDate(nowDate);
			 record.setUpdatedDate(nowDate);
			 DeviceCardManager deviceCardManager = deviceCardManagerService.addDeviceCard(cardRecord);
			 logger.info("DeviceManagerServiceImpl::activeDevicePackage deviceCardManager:" + deviceCardManager.toString());

			 FirmwareInfo firmRecord = new FirmwareInfo();
			 firmRecord.setModel(18);
			 firmRecord.setConfigure(17);
			 firmRecord.setType(16);
			 firmRecord.setSoftVersion(request.getSoftVersion());
			 FirmwareInfo firmwareInfo = firmwareInfoService.addFrimwareInfoByVersion(firmRecord);
			 Integer frimwareId = StringUtils.isEmpty(firmwareInfo)?31:firmwareInfo.getId();
			 logger.info("DeviceManagerServiceImpl::activeDevicePackage frimwareId:" + frimwareId);

			 //查看卡是否被别的设备绑定 如果被绑定解除绑定
			 DeviceFileSnapshot otherSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
			 if(!StringUtils.isEmpty(otherSnapshot) && "N".equals(otherSnapshot.getDeletedFlag()))
			 {
				 logger.info("DeviceManagerServiceImpl::activeDevicePackage otherSnapshot:" + otherSnapshot.toString());
				 if(!otherSnapshot.getSn().equals(request.getSn()))
				 {
					 deviceUpdateRecordService.setDeviceUpdateRecord(otherSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), otherSnapshot.getCardId(), request.getConsumer());
					 otherSnapshot.setCardId(null);
					 otherSnapshot.setCardTime(null);             
					 deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(otherSnapshot);
				 }
			 }
 			
 			//其他情况如果imsi不填 报错
			if(StringUtils.isEmpty(request.getImsi()))
			{
				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "参数错误,imsi为空!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
 			
 			DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(request.getImsi());
 			if(StringUtils.isEmpty(deviceFile) && StringUtils.isEmpty(deviceFileVirtual))
 			{
 				//设备未入库
 				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "未入库!");
 				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
 			}
 			if(!StringUtils.isEmpty(deviceFile) && "Y".equals(deviceFile.getDeletedFlag()))
 			{
 				//设备已被初始化
 				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "已被初始化!");
 				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_INITIAL);
 			}
 			
 			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
 			if(!StringUtils.isEmpty(deviceFileSnapshot) && PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue().equals(deviceFileSnapshot.getPackageStatu()))
 			{
 				//设备已经被激活
 				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "已被激活!");
 				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_ACTIVE);
 			}
 			
 			if(!StringUtils.isEmpty(deviceFileVirtual) && "N".equals(deviceFileVirtual.getDeletedFlag()))
 			{
 				logger.info("DeviceManagerServiceImpl::activeDevicePackage handle 内部卡外部设备激活");
 				//1.同步到物理设备表
 				DeviceFile newDeviceFile = new DeviceFile();
 				newDeviceFile.setSn(request.getSn());
 				newDeviceFile.setDeviceCode(deviceFileVirtual.getDeviceCode());
 				newDeviceFile.setOperatorMerchantNo(deviceFileVirtual.getOperatorMerchantNo());
 				newDeviceFile.setVerifCode(deviceFileVirtual.getVerifCode());
 				newDeviceFile.setPackageId(deviceFileVirtual.getPackageId());
 				newDeviceFile.setAndroidPackageId(deviceFileVirtual.getAndroidPackageId());
 				newDeviceFile.setSendMerchantNo(deviceFileVirtual.getSendMerchantNo());
 				newDeviceFile.setManufacturerCode(deviceFileVirtual.getManufacturerCode());
 				newDeviceFile.setOutStorageType(OutStorageTypeEnum.OUT_STORAGE_TYPE_EXCEL.getValue());
 				newDeviceFile.setDeletedFlag("N");
 				newDeviceFile.setExternalFlag(ExternalFlagEnum.EXTERNALFLAG_EX.getValue());
 				newDeviceFile.setFirmwareId(deviceFileVirtual.getFirmwareId());
 				newDeviceFile.setCreatedBy(request.getConsumer());
 				newDeviceFile.setUpdatedBy(request.getConsumer());
 				newDeviceFile.setCreatedDate(nowDate);
 				newDeviceFile.setUpdatedDate(nowDate);

 				deviceFileService.addDeviceFileByDuplicateKey(newDeviceFile);
 				
 				//2.同步设备关系表
 				DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
 				newDeviceFileSnapshot.setSn(request.getSn());
 				newDeviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
 				newDeviceFileSnapshot.setPackageId(deviceFileVirtual.getPackageId());
 				newDeviceFileSnapshot.setAndroidPackageId(deviceFileVirtual.getAndroidPackageId());
 				newDeviceFileSnapshot.setUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setUserTime(timeDate);
 				newDeviceFileSnapshot.setPackageUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setPackageUserTime(timeDate);
 				newDeviceFileSnapshot.setCardId(deviceCardManager.getId());
 				newDeviceFileSnapshot.setCardTime(timeDate);
 				newDeviceFileSnapshot.setFirmwareId(frimwareId);
 				newDeviceFileSnapshot.setDeletedFlag("N");
 				newDeviceFileSnapshot.setCreatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setCreatedDate(nowDate);
 				newDeviceFileSnapshot.setUpdatedDate(nowDate);
 				
 				deviceFileSnapshotService.addDeviceFileSnapshotByDuplicateKey(newDeviceFileSnapshot);
 				
 				//删除虚拟表数据
 				DeviceFileVirtual updateVirtual = new DeviceFileVirtual();
 				updateVirtual.setId(deviceFileVirtual.getId());
 				updateVirtual.setUpdatedBy(request.getConsumer());
 				updateVirtual.setUpdatedDate(nowDate);
 				updateVirtual.setDeletedFlag("Y");
 				deviceFileVirtualService.updateDeviceFileVirtualById(updateVirtual);
 				
 				//3.添加修改记录表
 				//this.addDeviceUpdateRecordOnActivePackage(newDeviceFile, newDeviceFileSnapshot, request.getConsumer());
 				executor.execute(new UpdateDeviceFileRecordThread(newDeviceFile,newDeviceFileSnapshot,request.getConsumer()));
 			}
 			else if (StringUtils.isEmpty(deviceFileVirtual) && !StringUtils.isEmpty(deviceFile))
 			{
 				logger.info("DeviceManagerServiceImpl::activeDevicePackage handle 内部卡内部设备激活");
 				
 				DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
 				newDeviceFileSnapshot.setSn(deviceFile.getSn());
 				newDeviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
 				newDeviceFileSnapshot.setPackageId(deviceFile.getPackageId());
 				newDeviceFileSnapshot.setAndroidPackageId(deviceFile.getAndroidPackageId());
 				newDeviceFileSnapshot.setUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setUserTime(timeDate);
 				newDeviceFileSnapshot.setPackageUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setPackageUserTime(timeDate);
 				newDeviceFileSnapshot.setCardId(deviceCardManager.getId());
 				newDeviceFileSnapshot.setCardTime(timeDate);
 				newDeviceFileSnapshot.setFirmwareId(frimwareId);
 				newDeviceFileSnapshot.setDeletedFlag("N");
 				newDeviceFileSnapshot.setCreatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setCreatedDate(nowDate);
 				newDeviceFileSnapshot.setUpdatedDate(nowDate);
 				
 				//1.修改当前关系表
 				deviceFileSnapshotService.addDeviceFileSnapshotByDuplicateKey(newDeviceFileSnapshot);
 				
 				//2.添加修改记录表
 				//this.addDeviceUpdateRecordOnActivePackage(deviceFile, newDeviceFileSnapshot, request.getConsumer());
 				executor.execute(new UpdateDeviceFileRecordThread(deviceFile,newDeviceFileSnapshot,request.getConsumer()));
 			}
 			else
 			{
 				logger.info("DeviceManagerServiceImpl::activeDevicePackage handle 外部卡外部设备激活");
 				//1.虚拟同步到物理设备表
 				DeviceFile newDeviceFile = new DeviceFile();
 				newDeviceFile.setSn(request.getSn());
 				newDeviceFile.setDeviceCode(request.getDeviceCode());
 				newDeviceFile.setPackageId(request.getPackageId());
 				newDeviceFile.setOutStorageType(OutStorageTypeEnum.OUT_STORAGE_TYPE_AUTO.getValue());
 				newDeviceFile.setDeletedFlag("N");
 				newDeviceFile.setExternalFlag(ExternalFlagEnum.EXTERNALFLAG_AX.getValue());
 				newDeviceFile.setFirmwareId(frimwareId);
 		        newDeviceFile.setCreatedBy(request.getConsumer());
 				newDeviceFile.setUpdatedBy(request.getConsumer());
 				newDeviceFile.setCreatedDate(nowDate);
 				newDeviceFile.setUpdatedDate(nowDate);
 				
 				deviceFileService.addDeviceFileByDuplicateKey(newDeviceFile);
 				
 				//2.同步设备关系表
 				DeviceFileSnapshot newDeviceFileSnapshot = new DeviceFileSnapshot();
 				newDeviceFileSnapshot.setSn(request.getSn());
 				newDeviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
 				newDeviceFileSnapshot.setPackageId(request.getPackageId());
 				newDeviceFileSnapshot.setAndroidPackageId(request.getAndroidPackageId());
 				newDeviceFileSnapshot.setUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setUserTime(timeDate);
 				newDeviceFileSnapshot.setPackageUserId(deviceUserManager.getId());
 				newDeviceFileSnapshot.setPackageUserTime(timeDate);
 				newDeviceFileSnapshot.setCardId(deviceCardManager.getId());
 				newDeviceFileSnapshot.setCardTime(timeDate);
 				newDeviceFileSnapshot.setFirmwareId(frimwareId);
 				newDeviceFileSnapshot.setDeletedFlag("N");
 				newDeviceFileSnapshot.setCreatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setUpdatedBy(request.getConsumer());
 				newDeviceFileSnapshot.setCreatedDate(nowDate);
 				newDeviceFileSnapshot.setUpdatedDate(nowDate);
 				
 				deviceFileSnapshotService.addDeviceFileSnapshotByDuplicateKey(newDeviceFileSnapshot);
 				
 				//3.添加修改记录表
 				//this.addDeviceUpdateRecordOnActivePackage(newDeviceFile, newDeviceFileSnapshot, request.getConsumer());
 				executor.execute(new UpdateDeviceFileRecordThread(newDeviceFile,newDeviceFileSnapshot,request.getConsumer()));
 			}
 			
    	 }
    	 catch(RpcServiceException e)
    	 {
    		 throw e;
    	 }
    	 
     }
     
     /**
      * 反补记录
      *
      * @param 
      * @param 
      */
     public void handleDeniesDeviceInfos(DeviceFileSnapshot virtualSnapshot,String strRealDeviceSn) throws RpcServiceException
     {
    	 try
    	 {
    		logger.info("DeviceManagerServiceService::handleDeniesDeviceInfos param:{}" + virtualSnapshot + "realDeviceSn:" + strRealDeviceSn);
	     	DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(virtualSnapshot.getSn());
	     	if(StringUtils.isEmpty(deviceFile))
	     	{
	     		logger.error("DeviceManagerServiceService::handleDeniesDeviceInfos deviceFile is null sn:" + virtualSnapshot.getSn());
	     		return;
	     	}
	     	
	     	DeviceFile deviceFileDel = new DeviceFile();
	     	deviceFileDel.setId(deviceFile.getId());
	     	deviceFileDel.setDeletedFlag("Y");

	     	deviceFile.setId(null);
	     	deviceFile.setSn(strRealDeviceSn);
	     	
	     	DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
	     	deviceFileSnapshot.setSn(strRealDeviceSn);
	     	deviceFileSnapshot.setPackageStatu(virtualSnapshot.getPackageStatu());
	     	deviceFileSnapshot.setPackageId(virtualSnapshot.getPackageId());
	     	deviceFileSnapshot.setAndroidPackageId(virtualSnapshot.getAndroidPackageId());
	     	deviceFileSnapshot.setPackageUserId(virtualSnapshot.getPackageUserId());
	     	deviceFileSnapshot.setPackageUserTime(virtualSnapshot.getPackageUserTime());
	     	deviceFileSnapshot.setUserId(virtualSnapshot.getUserId());
	     	deviceFileSnapshot.setUserTime(virtualSnapshot.getUserTime());
	     	deviceFileSnapshot.setCardId(virtualSnapshot.getCardId());
	     	deviceFileSnapshot.setCardTime(virtualSnapshot.getCardTime());
	     	deviceFileSnapshot.setFirmwareId(virtualSnapshot.getFirmwareId());
	     	deviceFileSnapshot.setVehicleId(virtualSnapshot.getVehicleId());
	     	deviceFileSnapshot.setCreatedBy(virtualSnapshot.getCreatedBy());
	     	deviceFileSnapshot.setCreatedDate(virtualSnapshot.getCreatedDate());
	     	deviceFileSnapshot.setUpdatedBy(virtualSnapshot.getUpdatedBy());
	     	deviceFileSnapshot.setUpdatedDate(virtualSnapshot.getUpdatedDate());
	     	deviceFileSnapshot.setDeletedFlag(virtualSnapshot.getDeletedFlag());
	     	
	     	//清除原来虚拟设备的关系数据
	     	virtualSnapshot.setPackageStatu("IN");
	     	virtualSnapshot.setPackageId(null);
	     	virtualSnapshot.setAndroidPackageId(null);
	     	virtualSnapshot.setPackageUserId(null);
	     	virtualSnapshot.setPackageUserTime(null);
	     	virtualSnapshot.setUserId(null);
	     	virtualSnapshot.setUserTime(null);
	     	virtualSnapshot.setCardId(null);
	     	virtualSnapshot.setCardTime(null);
	     	virtualSnapshot.setFirmwareId(null);
	     	virtualSnapshot.setVehicleId(null);
	     	virtualSnapshot.setDeletedFlag("Y");
	     	
	     	deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(virtualSnapshot);
	     	
	     	deviceFileService.updateDeviceFileById(deviceFileDel);
	     	
	     	//反补设备数据
	     	deviceFileService.addDeviceFile(deviceFile);
	     	//反补设备关系数据
	     	deviceFileSnapshotService.addDeviceFileSnapshotByDuplicateKey(deviceFileSnapshot);
	     	
    	 }
    	 catch(RpcServiceException e)
    	 {
    		 throw e;
    	 }
    	
    	 logger.info("DeviceManagerServiceService::handleDeniesDeviceInfos handle ok!");
     }


    /**
     * 插入修改记录列表
     *
     * @param 
     * @param 
     */
    public void addDeviceUpdateRecordOnActivePackage(DeviceFile deviceFile,DeviceFileSnapshot deviceSnapshot,String strOperator) throws RpcServiceException
    {
    	if(StringUtils.isEmpty(deviceSnapshot))
    	{
    		logger.error("DeviceManagerServiceService::addDeviceUpdateRecordOnActivePackage param null");
            return;
    	}
    	
    	logger.info("DeviceManagerServiceService::addDeviceUpdateRecordOnActivePackage deviceSnapshot=" + deviceSnapshot.toString());
    	
    	try
    	{
    		//deviceUpdateRecordService.setDeviceUpdateRecord(deviceFile.getSn(), UpdateRecordEnum.UPDATE_RECORD_PACK.getValue(), deviceFile.getPackageId(), strOperator);
    		//deviceUpdateRecordService.setDeviceUpdateRecord(deviceFile.getSn(), UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue(), deviceFile.getFirmwareId(), strOperator);
    		deviceUpdateRecordService.setDeviceUpdateRecord(deviceFile.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceFile.getCardId(), strOperator);		
    		deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_PACK.getValue(), deviceSnapshot.getPackageId(), strOperator);
    		deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue(), deviceSnapshot.getPackageUserId(), strOperator);
    		deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_USER.getValue(), deviceSnapshot.getUserId(), strOperator);
    		deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceSnapshot.getCardId(), strOperator);
    		//deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue(), deviceSnapshot.getVehicleId(), strOperator);
    		//deviceUpdateRecordService.setDeviceUpdateRecord(deviceSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue(), deviceSnapshot.getFirmwareId(), strOperator);
    	}
    	catch (RpcServiceException e) 
    	{
            throw e;
        }
    	logger.info("DeviceManagerServiceService::addDeviceUpdateRecord ok!");
    }
    
}
