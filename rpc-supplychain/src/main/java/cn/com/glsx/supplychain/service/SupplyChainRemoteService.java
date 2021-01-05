package cn.com.glsx.supplychain.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.config.AttribInfoProperty;
import cn.com.glsx.supplychain.config.DeviceTypeProperty;
import cn.com.glsx.supplychain.enums.DeviceEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.LogisticsTypeEnum;
import cn.com.glsx.supplychain.enums.MerchantOrderKafkaMessageTypeEnum;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.enums.OrderStatusEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.kafka.MerchantOrderKafkaMessage;
import cn.com.glsx.supplychain.kafka.SendKafkaService;
import cn.com.glsx.supplychain.kafka.UpdateDispatchOrder;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.mapper.AttribInfoMapper;
import cn.com.glsx.supplychain.mapper.AttribManaMapper;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.mapper.FirmwareInfoMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.mapper.RequestLogMapper;
import cn.com.glsx.supplychain.mapper.WareHouseInfoMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderMapper;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceCardManager;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import cn.com.glsx.supplychain.model.DeviceFileVirtual;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.DeviceResetRecord;
import cn.com.glsx.supplychain.model.DeviceUserManager;
import cn.com.glsx.supplychain.model.DeviceVehicleManager;
import cn.com.glsx.supplychain.model.ExsysDeviceStatu;
import cn.com.glsx.supplychain.model.ExsysOrderInfo;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.RequestLog;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrderDetail;
import cn.com.glsx.supplychain.service.bs.AddressService;
import cn.com.glsx.supplychain.service.bs.LogisticsService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

@Service
@Transactional
public class SupplyChainRemoteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoService userinfoService;
	@Autowired
	private AttribManagerSevice attribManaService;
	@Autowired
	private DeviceTypeProperty deviceTypeProperty;
	@Autowired
	private AttribInfoProperty attribInfoProperty;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private DeviceFileService deviceFileService;
	@Autowired
	private DeviceFileVirtualService deviceFileVirtualService;
	@Autowired
	private DeviceUpdateRecordService deviceUpdateRecordService;
	@Autowired
	private DeviceFileSnapshotService deviceFileSnapshotService;
	@Autowired
    private SupplyChainExternalService supplyChainExternalService;
	@Autowired
	private DeviceManagerAdminRemoteService deviceManagerAdminRemoteService;
	@Autowired
	private DeviceCardManagerService deviceCardManagerService;
	@Autowired
	private DeviceUserManagerService deviceUserManagerService;
	@Autowired
	private DeviceVehicleManagerService deviceVehicleManagerService;
	@Autowired
	private DeviceCardManagerService deviceCardService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private DeviceTypeService deviceTypeService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private WareHouseInfoService wareHouseInfoService;
	@Autowired
	private MerchantOrderService merchantOrderService;
	@Autowired
	private FirmwareInfoMapper firmwareInfoMapper;

	@Autowired
	private RequestLogMapper requestLogMapper;
	@Autowired
	private SendKafkaService kafkaService;
	
	//@Autowired
    //private MerchantOrderMapper merchantOrderMapper;
	

	//用户登陆
	public UserInfo login(UserInfo userInfo) throws RpcServiceException
	{
		UserInfo resultUser = null;
		logger.info("SupplyChainRemoteService::login start userInfo:{}",userInfo);
		resultUser = userinfoService.getUserInfoByUserName(userInfo.getUserName());
		if(StringUtils.isEmpty(resultUser))
		{
			logger.info("SupplyChainRemoteService::login user is not exist!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(!resultUser.getPassword().equals(userInfo.getPassword()))
		{
			logger.info("SupplyChainRemoteService::login password error!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_PASSWORD_WRONG);
		}
		if(!StringUtils.isEmpty(resultUser.getWarehouseId()))
		{
			resultUser.setWareHouseInfo(wareHouseInfoService.getWarehouseById(resultUser.getWarehouseId()));
		}
		logger.info("SupplyChainRemoteService::login end resultUser:{}",resultUser);
		return resultUser;
	}
	

	//获取固件列表
	public List<FirmwareInfo> getFirmwareInfoList(FirmwareInfo firmwareInfo) throws RpcServiceException{
		
		logger.info("获取固件列表 机型ID:"+ firmwareInfo.getModel() + "配置ID:" + firmwareInfo.getConfigure() + "类型ID:" + firmwareInfo.getType() + "主板ID:" + firmwareInfo.getBoardVersion());
		
		List<FirmwareInfo> firmwareInfoList = null;
		
		try{
			
			firmwareInfoList = firmwareInfoMapper.listFirmwareInfo(firmwareInfo);
		
		}catch(Exception e){
			
			logger.error("获取数据库异常 错误信息:" + e.getMessage());
			
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		return firmwareInfoList;
	}
	
	
	
	//扫码入库
	public DeviceInfo scannerDeviceInfo(UserInfo userInfo,DeviceInfo deviceInfo) throws RpcServiceException{
		
		DeviceInfo resultInfo = null;
		logger.info("SupplyChainRemoteService::scannerDeviceInfo start userInfo:{},deviceInfo:{}",userInfo,deviceInfo);
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			AttribMana attribMana = attribManaService.getAttribManaByManaCode(deviceInfo.getAttribCode());
			logger.info("SupplyChainRemoteService::scannerDeviceInfo attribMana:{}",attribMana);
			if(StringUtils.isEmpty(attribMana))
			{
				logger.error("SupplyChainRemoteService::scannerDeviceInfo failed to get attribMana!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST);
			}
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::scannerDeviceInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::scannerDeviceInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			//参数验证
			/*if(StringUtils.isEmpty(deviceInfo.getSn()))
			{
				if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) || 
						attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
						attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()))
				{
					if(attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
					{
						logger.error("SupplyChainRemoteService::scannerDeviceInfo sn is empty!");
						throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY);
					}
				}
				if(attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
				{
					logger.error("SupplyChainRemoteService::scannerDeviceInfo sn is empty!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY);
				}
			}
			if(StringUtils.isEmpty(deviceInfo.getImei()))
			{
				if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) ||
						attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
						attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()) ||
						attribMana.getDevTypeId().equals(deviceTypeProperty.getTracker()))
				{
					if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
					{
						logger.error("SupplyChainRemoteService::scannerDeviceInfo imei is empty!");
						throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_IMEI_NOT_EXIST);
					}
				}
			}
			if(StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
				{
					if(!attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
					{
						logger.error("SupplyChainRemoteService::scannerDeviceInfo iccid is empty!");
						throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ICCID_IS_EMPTY);
					}
				}
			}*/
			
			//判断sn是否重复
			if(!StringUtils.isEmpty(deviceInfo.getSn()))
			{
				DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
				if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
				{
					String message = "sn/imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
							" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + (!StringUtils.isEmpty(tempDeviceInfo.getCreatedDate())?df.format(tempDeviceInfo.getCreatedDate()):"") +  "\n 当前入库记录:sn:" +
							deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
					logger.error("SupplyChainRemoteService::scannerDeviceInfo sn/imei重复:" + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
				
				DeviceFile tempDeviceFile = deviceFileService.getDeviceFileBySn(deviceInfo.getSn());
				if(!StringUtils.isEmpty(tempDeviceFile) && tempDeviceFile.getDeletedFlag().equals("N"))
				{
					String message = "sn/imei重复:\n 管理库存记录:sn:" + tempDeviceFile.getSn() + " 录入时间:" + (!StringUtils.isEmpty(tempDeviceFile.getCreatedDate())?df.format(tempDeviceFile.getCreatedDate()):"") + "\n 当前入库记录:sn:" + deviceInfo.getSn();
					logger.error("SupplyChainRemoteService::scannerDeviceInfo sn/imei重复:" + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			
			//判断iccid是否重复
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoByIccid(deviceInfo.getIccid());
				if(!StringUtils.isEmpty(tempDeviceInfo))
				{
					String message = "iccid重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
							" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + (!StringUtils.isEmpty(tempDeviceInfo.getCreatedDate())?df.format(tempDeviceInfo.getCreatedDate()):"") +  "\n 当前入库记录:sn:" +
							deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
					logger.error("SupplyChainRemoteService::scannerDeviceInfo iccid重复:" + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
				//检测到有虚拟设备的直接将虚拟设备表的数据删除
				DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByIccid(deviceInfo.getIccid());
				if(!StringUtils.isEmpty(deviceFileVirtual))
				{
					deviceFileVirtual.setDeletedFlag("N");
					deviceFileVirtual.setUpdatedBy(userInfo.getUserName());
					deviceFileVirtual.setUpdatedDate(new Date());
					deviceFileVirtualService.updateDeviceFileVirtualById(deviceFileVirtual);	
				}
				
				//检查iccid是否是广联卡 71234567:调试数据或者外部卡不需要验证
				if(!deviceInfo.getIccid().startsWith("71234567") && !attribMana.getCardSelfId().equals(attribInfoProperty.getCardSelfExtain()))
				{	
					if(StringUtils.isEmpty(attribMana.getVerifyIccid()) || attribMana.getVerifyIccid().equals("Y")){
						String imsi = this.getImsiByIccidFromFlowCardPlat(deviceInfo.getIccid().toUpperCase());
						if(StringUtils.isEmpty(imsi))
						{
							logger.error("SupplyChainRemoteService::scannerDeviceInfo iccid:is not glsx card");
							throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ICCID_IS_NOT_GLSX);
						}
						
						DeviceCardManager deviceCardManager = this.getCardManagerByImsi(imsi);
						if(!StringUtils.isEmpty(deviceCardManager))
						{
							DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
							if(!StringUtils.isEmpty(deviceSnapshot) && deviceSnapshot.getDeletedFlag().equals("N"))
							{
								String message = "iccid重复:\n 设备关系库存记录:iccid:" + deviceCardManager.getIccid() + " sn:" + deviceSnapshot.getSn() + "录入时间:" + (!StringUtils.isEmpty(deviceSnapshot.getCreatedDate())?df.format(deviceSnapshot.getCreatedDate()):"") +  "\n 当前入库记录:sn:" +
										deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
								logger.error("SupplyChainRemoteService::scannerDeviceInfo iccid重复:" + message);
								ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
								throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
							}
						}
					}	
				}
			}
			
			//检查imei是否重复
			if(!StringUtils.isEmpty(deviceInfo.getImei()))
			{
				DeviceInfo tempDeviceInfo =  deviceInfoService.getDeviceInfoByImei(deviceInfo.getImei());
				if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
				{
					String message = "imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
							" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + (!StringUtils.isEmpty(tempDeviceInfo.getCreatedDate())?df.format(tempDeviceInfo.getCreatedDate()):"") +  "\n 当前入库记录:sn:" +
							deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
					logger.error("SupplyChainRemoteService::scannerDeviceInfo imei重复:" + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			if(StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				deviceInfo.setIccid("");
			}
			if(StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setImei("");
			}
			deviceInfo.setWareHouseId(userInfo.getWarehouseId());
			deviceInfo.setWareHouseIdUp(userInfo.getWarehouseId());
			deviceInfo.setStatus(DeviceEnum.STATUS_IN.getValue());
			deviceInfo.setDeletedFlag("N");
			deviceInfo.setOrderCode("");
			deviceInfo.setCreatedBy(userInfo.getUserName());
			deviceInfo.setUpdatedBy(userInfo.getUserName());
			deviceInfo.setCreatedDate(new Date());
			deviceInfo.setUpdatedDate(new Date());
			resultInfo = deviceInfoService.addDeviceInfoOnDuplicateKey(deviceInfo);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		
		logger.info("SupplyChainRemoteService::scannerDeviceInfo end resultInfo:{}",resultInfo);
		return resultInfo;
	}
	
	public Page<DeviceInfo> pageStatAttrib(UserInfo userInfo,RpcPagination<DeviceInfo> pagination) throws RpcServiceException
	{
		Page<DeviceInfo> result;
		logger.info("SupplyChainRemoteService::pageStatAttrib start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::pageStatAttrib userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::pageStatAttrib invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			//非超级用户只可以看到本仓库的信息 否则看到全部信息
			if(userInfo.getIsSup() == 0)
			{
				pagination.getCondition().setWareHouseIdUp(userInfo.getWarehouseId());
			}
			
			//获取总数
			Integer total = deviceInfoService.getTotalAttrib(pagination.getCondition());
			pagination.setTotal(total);
			pagination.setPages((total%pagination.getPageSize() == 0)?(total/pagination.getPageSize()):(total/pagination.getPageSize() + 1));
			
			DeviceInfo condition = pagination.getCondition();
			condition.setPn(pagination.getPageSize()*(pagination.getPageNum()));
			condition.setPs(pagination.getPageSize());
			result = deviceInfoService.pageStatAttrib(condition);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::pageStatAttrib end result:{}",result);
		return result;
	}
	
	public Page<DeviceInfo> pageStatAttribDeviceInfos(UserInfo userInfo,RpcPagination<DeviceInfo> pagination) throws RpcServiceException
	{
		Page<DeviceInfo> result;
		logger.info("SupplyChainRemoteService::pageStatAttribDeviceInfos start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::pageStatAttribDeviceInfos userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::pageStatAttribDeviceInfos invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			
			//超级用户放开权限
			if(userInfo.getIsSup() == 0)
			{
				pagination.getCondition().setWareHouseIdUp(userInfo.getWarehouseId());
			}
			
			//获取总数
			Integer total = deviceInfoService.getTotalAttribDeviceInfos(pagination.getCondition());
			pagination.setTotal(total);
			pagination.setPages((total%pagination.getPageSize() == 0)?(total/pagination.getPageSize()):(total/pagination.getPageSize() + 1));
			
			DeviceInfo condition = pagination.getCondition();
			condition.setPn(pagination.getPageSize()*(pagination.getPageNum()));
			condition.setPs(pagination.getPageSize());
			result = deviceInfoService.pageStatAttribDeviceInfos(condition);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::pageStatAttribDeviceInfos end result:{}",result);
		return result;
	}
	
	public List<DeviceInfo> listExportAttribDeviceInfos(UserInfo userInfo,DeviceInfo record) throws RpcServiceException
	{
		List<DeviceInfo> result;
		logger.info("SupplyChainRemoteService::listExportAttribDeviceInfos start userInfo:{},record:{}",userInfo,record);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::listExportAttribDeviceInfos userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::listExportAttribDeviceInfos invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			if(userInfo.getIsSup() == 0)
			{
				record.setWareHouseIdUp(userInfo.getWarehouseId());
			}			
			result = deviceInfoService.listExportAttribDeviceInfos(record);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::listExportAttribDeviceInfos end result.size:"+result.size());
		return result;
	}
	
	public Page<OrderInfo> pageOrderInfo(UserInfo userInfo,RpcPagination<OrderInfo> pagination) throws RpcServiceException
	{
		Page<OrderInfo> result;
		logger.info("SupplyChainRemoteService::pageOrderInfo start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::pageOrderInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::pageOrderInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			//超级用户显示所有订单
			if(userInfo.getIsSup() == 0)
			{
				pagination.getCondition().setWarehouseId(userInfo.getWarehouseId());
			}	
			result = orderInfoService.pageOrderInfoFroDelivery(pagination);	
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::pageOrderInfo end result.size:"+result.size());
		return result;
	}
	
	public Page<OrderInfoDetail> pageOrderInfoDetail(UserInfo userInfo,RpcPagination<OrderInfoDetail> pagination) throws RpcServiceException
	{
		Page<OrderInfoDetail> result;
		logger.info("SupplyChainRemoteService::pageOrderInfoDetail start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			result = orderInfoService.pageOrderDetailForDelivery(pagination);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::pageOrderInfoDetail end result.size:"+result.size());
		return result;
	}
	
	public List<OrderInfo> listOrderInfo(UserInfo userInfo,OrderInfo record) throws RpcServiceException
	{
		List<OrderInfo> result;
		logger.info("SupplyChainRemoteService::listOrderInfo start userInfo:{},record:{}",userInfo,record);
		try
		{
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::listOrderInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::listOrderInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			
			record.setWarehouseId(userInfo.getWarehouseId());
			result = orderInfoService.getOrderList(record);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::listOrderInfo end result.size:"+result.size());
		return result;
	}
	
	public OrderInfo getOrderInfoByOrderCode(UserInfo userInfo,OrderInfo record) throws RpcServiceException
	{
		OrderInfo result;
		logger.info("SupplyChainRemoteService::getOrderInfoByOrderCode start userInfo:{},record:{}",userInfo,record);
		try
		{
			result = orderInfoService.getOrderInfoByOrderCode(record.getOrderCode());
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::getOrderInfoByOrderCode end result:{}",result);
		return result;
	}
	
	
	public Integer cancelDeviceInfo(UserInfo userInfo,DeviceInfo deviceInfo) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("SupplyChainRemoteService::cancelDeviceInfo start userInfo:{},deviceInfo:{}",userInfo,deviceInfo);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::cancelDeviceInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::cancelDeviceInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			
			//验证参数
			if(StringUtils.isEmpty(deviceInfo.getSn()))
			{
				logger.error("SupplyChainRemoteService::cancelDeviceInfo invalid param!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			//获取库存设备信息
			DeviceInfo tempDeviceInfo =  deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
			if(StringUtils.isEmpty(tempDeviceInfo) || (!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("Y")))
			{
				logger.error("SupplyChainRemoteService::cancelDeviceInfo record deviceinfo is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST);
			}
			//比较iccid是否一致
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				if(!deviceInfo.getIccid().equals(tempDeviceInfo.getIccid()))
				{
					logger.error("SupplyChainRemoteService::cancelDeviceInfo record deviceinfo iccid not match!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_ICCID_NOT_MATCH);
				}
			}
			//是否已经出库
			if(!StringUtils.isEmpty(tempDeviceInfo.getOrderCode()) || tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
			{
				logger.error("SupplyChainRemoteService::cancelDeviceInfo record deviceinfo already out!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_READY_OUT);
			}
			//是否具有权限 超级用户放开权限
			if(userInfo.getIsSup() == 0)
			{
				if(!tempDeviceInfo.getWareHouseIdUp().equals(userInfo.getWarehouseId()))
				{
					logger.error("SupplyChainRemoteService::cancelDeviceInfo invalid username!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
				}
			}
			
			tempDeviceInfo.setDeletedFlag("Y");
			tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
			tempDeviceInfo.setUpdatedDate(new Date());
			deviceInfoService.updateDeviceInfoBySn(tempDeviceInfo);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::scannerDeviceInfo end result:" + result);
		return result;
	}
	
	//excel导入出库数据
	public CheckImportDataVo excelDeviceInfoOutImport(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList) throws RpcServiceException
	{
		Integer deviceCount = 0;
		Address address = null;
		Logistics logistics = null;
		OrderInfoDetail orderInfoDetail = null;
		CheckImportDataVo checkImportData = new CheckImportDataVo();
		logger.info("SupplyChainRemoteService::excelDeviceInfoOutImport start userInfo:{},orderInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
		
		List<DeviceInfo> successList 		= new ArrayList<DeviceInfo>();
		List<DeviceInfo> failList	 		= new ArrayList<DeviceInfo>();
		Map<String,Integer> cacheSnMap		= new HashMap<String,Integer>();
		Map<String,Integer> cacheImeiMap	= new HashMap<String,Integer>();
		Map<String,Integer> cacheIccidMap	= new HashMap<String,Integer>();
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			if(StringUtils.isEmpty(deviceInfo))
			{
				continue;
			}
			if(StringUtils.isEmpty(deviceInfo.getSn()) && !StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setSn(deviceInfo.getImei());
			}
			if(!StringUtils.isEmpty(deviceInfo.getSn()))
			{
				Integer count = cacheSnMap.get(deviceInfo.getSn());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheSnMap.put(deviceInfo.getSn(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				Integer count = cacheIccidMap.get(deviceInfo.getIccid());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheIccidMap.put(deviceInfo.getIccid(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getImei()))
			{
				Integer count = cacheImeiMap.get(deviceInfo.getImei());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheImeiMap.put(deviceInfo.getImei(), count);
			}
		}
		
		String orderCode = orderInfo.getOrderCode();
		try
		{
			orderInfo = orderInfoService.getOrderInfoByOrderCode(orderInfo.getOrderCode());
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		
		//检查操作员权限
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutImport userName is not exist!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutImport invalid username!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		//检查订单
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + orderCode + "不存在或者无效!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + orderCode + "不存在或者无效!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + orderCode + "已完成!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + orderCode + " 已取消!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//超级用户放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + orderCode + "用户无权限!";
				logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		if((orderInfo.getAlreadyShipped() + deviceInfoList.size()) > orderInfo.getTotal())
		{
			String message = "订单:" + orderCode + " 设备数超出订单需求数量!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			logger.info("SupplyChainRemoteService::excelDeviceInfoOutCheck check deviceInfo:{}",deviceInfo);
			try
			{
				//检查字段是否文本内重复
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					Integer count = cacheSnMap.get(deviceInfo.getSn());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_SN_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					Integer count = cacheImeiMap.get(deviceInfo.getImei());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					Integer count = cacheIccidMap.get(deviceInfo.getIccid());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_ICCID_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				
				if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_OV.getValue()))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ORDER_COMPLETE.getValue());
					failList.add(deviceInfo);
					continue;
				}
				
				DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
				if(StringUtils.isEmpty(tempDeviceInfo))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getValue());
					failList.add(deviceInfo);
					continue;
				}
				if(tempDeviceInfo.getDeletedFlag().equals("Y"))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getValue());
					failList.add(deviceInfo);
					continue;
				}
				if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_READY_OUT.getValue());
					failList.add(deviceInfo);
					continue;
				}
				if(!StringUtils.isEmpty(tempDeviceInfo.getIccid()))
				{
					if(!tempDeviceInfo.getIccid().equals(deviceInfo.getIccid()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_ICCID_NOT_MATCH.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				//老工具扫描的数据不做仓库检测
				if(!tempDeviceInfo.getWareHouseIdUp().equals(5))
				{
					//超级用户放开权限
					if(userInfo.getIsSup() == 0)
					{
						if(!tempDeviceInfo.getWareHouseIdUp().equals(orderInfo.getWarehouseId()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_WAREHOUSE_TYPE_WRONG.getValue());
							failList.add(deviceInfo);
							continue;
						}
					}
					if(!tempDeviceInfo.getAttribCode().equals(orderInfo.getAttribCode()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ATTRIB_CODE_NOT_MATCH.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				
				//先一个一个导入后面在优化吧
				//插入物流明细
				address = deviceInfo.getLogistics().getReceiveAddress();
				address.setName(orderInfo.getContacts());
				address.setAddress(orderInfo.getAddress());
				address.setMobile(orderInfo.getMobile());
				address.setMerchantCode(orderInfo.getSendMerchantNo());
				address.setCreatedBy(userInfo.getUserName());
				address.setUpdatedBy(userInfo.getUserName());
				address.setDeletedFlag("N");
				address.setCreatedDate(new Date());
				address.setUpdatedDate(new Date());
				address.setId(null);
				address = addressService.addIfNotExist(address);
				
				logistics = deviceInfo.getLogistics();
				logistics.setCode(StringUtil.getOrderNo());
				logistics.setServiceCode(orderInfo.getOrderCode());
				logistics.setType(LogisticsTypeEnum.SEND_ORDER_NO.getCode());
				logistics.setOrderNumber(logistics.getOrderNumber());
				logistics.setReceiveId(address.getId());
				logistics.setCreatedBy(userInfo.getUserName());
				logistics.setUpdatedBy(userInfo.getUserName());
				logistics.setCreatedDate(new Date());
				logistics.setUpdatedDate(new Date());
				logistics.setDeletedFlag("N");
				logistics.setId(null);
				logistics = logisticsService.addIfNotExist(logistics);
				
				//插入订单明细
				orderInfoDetail = new OrderInfoDetail();
				orderInfoDetail.setOrderCode(orderInfo.getOrderCode());
				orderInfoDetail.setIccid(tempDeviceInfo.getIccid());
				orderInfoDetail.setImei(tempDeviceInfo.getImei());
				orderInfoDetail.setSn(tempDeviceInfo.getSn());
				orderInfoDetail.setAttribCode(tempDeviceInfo.getAttribCode());
				orderInfoDetail.setBatch(tempDeviceInfo.getBatch());
				orderInfoDetail.setWarehouseId(tempDeviceInfo.getWareHouseId());
				//orderInfoDetail.setWarehouseIdUp(userInfo.getWarehouseId());
				orderInfoDetail.setWarehouseIdUp(tempDeviceInfo.getWareHouseId());
				orderInfoDetail.setCreatedBy(userInfo.getUserName());
				orderInfoDetail.setUpdatedBy(userInfo.getUserName());
				orderInfoDetail.setCreatedDate(new Date());
				orderInfoDetail.setUpdatedDate(new Date());
				orderInfoDetail.setLogisticsId(logistics.getId().intValue());
				orderInfoService.addOrderInfoDetail(orderInfoDetail);
				
				//更细设备状态
				tempDeviceInfo.setStatus(DeviceEnum.STATUS_OUT.getValue());
				tempDeviceInfo.setWareHouseIdUp(tempDeviceInfo.getWareHouseId());
				//tempDeviceInfo.setWareHouseIdUp(userInfo.getWarehouseId());
				tempDeviceInfo.setOrderCode(orderInfo.getOrderCode());
				tempDeviceInfo.setDeletedFlag("N");
				tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
				tempDeviceInfo.setUpdatedDate(new Date());
				deviceInfoService.updateDeviceInfoBySn(tempDeviceInfo);
				
				//修改订单状态
				if(++deviceCount == orderInfo.getTotal())
				{
					orderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
					orderInfo.setUpdatedBy(userInfo.getUserName());
					orderInfo.setUpdatedDate(new Date());
					orderInfoService.update(orderInfo);
				}
				
				List<String> listDispatchOrderCode = new ArrayList<String>();
				listDispatchOrderCode.add(orderInfo.getOrderCode());
				kafkaService.notifyUpdateDispatchOrderInfo(listDispatchOrderCode);
			}
			catch(RpcServiceException e)
			{
				deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED.getValue());
				failList.add(deviceInfo);
				continue;
			}
			successList.add(deviceInfo);
		}
		
		checkImportData.setDeviceInfoSucessList(successList);
		checkImportData.setDeviceInfoFailList(failList);
		logger.info("SupplyChainRemoteService::excelDeviceInfoOutCheck end successList.size:{},failList.size:{}",successList.size(),failList.size());
		return checkImportData;
		
	}
	
	//excel导入出库数据校验
	public CheckImportDataVo excelDeviceInfoOutCheck(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList) throws RpcServiceException
	{
		CheckImportDataVo checkImportData = new CheckImportDataVo();
		logger.info("SupplyChainRemoteService::excelDeviceInfoOutCheck start userInfo:{},orderInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
	
		List<DeviceInfo> successList 		= new ArrayList<DeviceInfo>();
		List<DeviceInfo> failList	 		= new ArrayList<DeviceInfo>();
		Map<String,Integer> cacheSnMap		= new HashMap<String,Integer>();
		Map<String,Integer> cacheImeiMap	= new HashMap<String,Integer>();
		Map<String,Integer> cacheIccidMap	= new HashMap<String,Integer>();
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			if(StringUtils.isEmpty(deviceInfo))
			{
				continue;
			}
			if(StringUtils.isEmpty(deviceInfo.getSn()) && !StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setSn(deviceInfo.getImei());
			}
			if(!StringUtils.isEmpty(deviceInfo.getSn()))
			{
				Integer count = cacheSnMap.get(deviceInfo.getSn());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheSnMap.put(deviceInfo.getSn(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				Integer count = cacheIccidMap.get(deviceInfo.getIccid());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheIccidMap.put(deviceInfo.getIccid(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getImei()))
			{
				Integer count = cacheImeiMap.get(deviceInfo.getImei());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count++;
				cacheImeiMap.put(deviceInfo.getImei(), count);
			}
		}
		
		String orderCode = orderInfo.getOrderCode();
		try
		{
			orderInfo = orderInfoService.getOrderInfoByOrderCode(orderInfo.getOrderCode());
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		
		//检查操作员权限
		if(StringUtils.isEmpty(userInfo))
		{
			logger.error("SupplyChainRemoteService::cancelDeviceInfo userName is not exist!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
		}
		if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
		{
			logger.error("SupplyChainRemoteService::cancelDeviceInfo invalid username!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
		}
		//检查订单
		if(StringUtils.isEmpty(orderInfo))
		{
			String message = "订单:" + orderCode + "不存在或者无效!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getWarehouseId().equals(0))
		{
			String message = "订单:" + orderCode + "不存在或者无效!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_OV.getValue()))
		{
			String message = "订单:" + orderCode + "已完成!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_CL.getValue()))
		{
			String message = "订单:" + orderCode + " 已取消!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		//超级用户放开权限
		if(userInfo.getIsSup() == 0)
		{
			if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
			{
				String message = "订单:" + orderCode + "用户无权限!";
				logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
		}
		if((orderInfo.getAlreadyShipped() + deviceInfoList.size()) > orderInfo.getTotal())
		{
			String message = "订单:" + orderCode + " 设备数超出订单需求数量!";
			logger.error("SupplyChainRemoteService::excelDeviceInfoOutCheck " + message);
			ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
		}
		
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			logger.info("SupplyChainRemoteService::excelDeviceInfoOutCheck check deviceInfo:{}",deviceInfo);
			try
			{
				//检查字段是否文本内重复
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					Integer count = cacheSnMap.get(deviceInfo.getSn());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_SN_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					Integer count = cacheImeiMap.get(deviceInfo.getImei());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					Integer count = cacheIccidMap.get(deviceInfo.getIccid());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_ICCID_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				
				//检查物流公司和物流编码
				/*
				if(StringUtils.isEmpty(deviceInfo.getLogistics().getCompany()))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_LOGISTICS_NULL_CPY.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				
				if(StringUtils.isEmpty(deviceInfo.getLogistics().getOrderNumber()))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_LOGISTICS_NULL_NO.getDescrible());
					failList.add(deviceInfo);
					continue;
				}*/
				
				DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
				if(StringUtils.isEmpty(tempDeviceInfo))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				if(tempDeviceInfo.getDeletedFlag().equals("Y"))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_READY_OUT.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				if(!StringUtils.isEmpty(tempDeviceInfo.getIccid()))
				{
					if(!tempDeviceInfo.getIccid().equals(deviceInfo.getIccid()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_RECORD_IMEI_ICCID_NOT_MATCH.getDescrible());
						failList.add(deviceInfo);
						continue;
					}
				}
				//老工具扫描的数据不做仓库检测
				if(!tempDeviceInfo.getWareHouseIdUp().equals(5))
				{
					//超级用户放开权限
					if(userInfo.getIsSup() == 0)
					{
						if(!tempDeviceInfo.getWareHouseIdUp().equals(orderInfo.getWarehouseId()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_WAREHOUSE_TYPE_WRONG.getDescrible());
							failList.add(deviceInfo);
							continue;
						}
					}
					if(!tempDeviceInfo.getAttribCode().equals(orderInfo.getAttribCode()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ATTRIB_CODE_NOT_MATCH.getDescrible());
						failList.add(deviceInfo);
						continue;
					}
				}	
			}
			catch(RpcServiceException e)
			{
				deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED.getDescrible());
				failList.add(deviceInfo);
				continue;
			}
			
			successList.add(deviceInfo);
		}
		checkImportData.setDeviceInfoSucessList(successList);
		checkImportData.setDeviceInfoFailList(failList);
		logger.info("SupplyChainRemoteService::excelDeviceInfoOutCheck end successList.size:{},failList.size:{}",successList.size(),failList.size());
		return checkImportData;
	}
	
	//excel导入入库数据
	public CheckImportDataVo excelDeviceInfoImport(UserInfo userInfo,List<DeviceInfo> deviceInfoList) throws RpcServiceException
	{
		CheckImportDataVo checkImportData = new CheckImportDataVo();
		logger.info("SupplyChainRemoteService::scannerDeviceInfo start userInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
		
		List<DeviceInfo> successList 		= new ArrayList<DeviceInfo>();
		List<DeviceInfo> failList	 		= new ArrayList<DeviceInfo>();
		Map<String,Integer> cacheSnMap		= new HashMap<String,Integer>();
		Map<String,Integer> cacheImeiMap	= new HashMap<String,Integer>();
		Map<String,Integer> cacheIccidMap	= new HashMap<String,Integer>();
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			if(StringUtils.isEmpty(deviceInfo))
			{
				continue;
			}
			if(StringUtils.isEmpty(deviceInfo.getSn()) && !StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setSn(deviceInfo.getImei());
			}
			if(!StringUtils.isEmpty(deviceInfo.getSn()))
			{
				Integer count = cacheSnMap.get(deviceInfo.getSn());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheSnMap.put(deviceInfo.getSn(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				Integer count = cacheIccidMap.get(deviceInfo.getIccid());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheIccidMap.put(deviceInfo.getIccid(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getImei()))
			{
				Integer count = cacheImeiMap.get(deviceInfo.getImei());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheImeiMap.put(deviceInfo.getImei(), count);
			}
		}
		
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			logger.info("SupplyChainRemoteService::scannerDeviceInfo check deviceInfo:{}",deviceInfo);
			try
			{
				//检查字段是否文本内重复
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					Integer count = cacheSnMap.get(deviceInfo.getSn());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_SN_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					Integer count = cacheImeiMap.get(deviceInfo.getImei());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					Integer count = cacheIccidMap.get(deviceInfo.getIccid());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_ICCID_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				
				//检查硬件编码
				AttribMana attribMana = attribManaService.getAttribManaByManaCode(deviceInfo.getAttribCode());
				if(StringUtils.isEmpty(attribMana))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				//检查操作员权限
				userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
				if(StringUtils.isEmpty(userInfo))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				//参数验证
				if(StringUtils.isEmpty(deviceInfo.getSn()))
				{
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) || 
							attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()))
					{
						if(attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY.getDescrible());
							failList.add(deviceInfo);
							continue;
						}
						
					}
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY.getDescrible());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(StringUtils.isEmpty(deviceInfo.getImei()))
				{
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getTracker()))
					{
						if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_IMEI_NOT_EXIST.getDescrible());
							failList.add(deviceInfo);
							continue;					
						}
					}
				}
				if(StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
					{
						if(!attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_ICCID_IS_EMPTY.getDescrible());
							failList.add(deviceInfo);
							continue;		
						}
					}
				}
				
				//判断sn是否重复
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
					if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
					{
						String message = "sn/imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;	
						
					}
					
					DeviceFile tempDeviceFile = deviceFileService.getDeviceFileBySn(deviceInfo.getSn());
					if(!StringUtils.isEmpty(tempDeviceFile) && tempDeviceFile.getDeletedFlag().equals("N"))
					{
						String message = "sn/imei重复:\n 管理库存记录:sn:" + tempDeviceFile.getSn() + " 录入时间:" + tempDeviceFile.getCreatedDate() + "\n 当前入库记录:sn:" + deviceInfo.getSn();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;	
					}
				}
				//判断iccid是否重复
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoByIccid(deviceInfo.getIccid());
					if(!StringUtils.isEmpty(tempDeviceInfo))
					{
						String message = "iccid重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
					
					DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByIccid(deviceInfo.getIccid());
					if(!StringUtils.isEmpty(deviceFileVirtual))
					{
						String message = "iccid重复:\n 虚拟放卡库存记录:iccid:" + tempDeviceInfo.getIccid() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
					
					//检查iccid是否是广联卡 71234567:调试数据不需要验证
					if(!deviceInfo.getIccid().startsWith("71234567") && !attribMana.getCardSelfId().equals(attribInfoProperty.getCardSelfExtain()))
					{
						String imsi = this.getImsiByIccidFromFlowCardPlat(deviceInfo.getIccid().toUpperCase());
						if(StringUtils.isEmpty(imsi))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ICCID_IS_NOT_GLSX.getDescrible());
							failList.add(deviceInfo);
							continue;				
						}
						
						DeviceCardManager deviceCardManager = this.getCardManagerByImsi(imsi);
						if(!StringUtils.isEmpty(deviceCardManager))
						{
							DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
							if(!StringUtils.isEmpty(deviceSnapshot) && deviceSnapshot.getDeletedFlag().equals("N"))
							{
								String message = "iccid重复:\n 设备关系库存记录:iccid:" + deviceCardManager.getIccid() + " sn:" + deviceSnapshot.getSn() + "录入时间:" + deviceSnapshot.getCreatedDate() +  "\n 当前入库记录:sn:" +
										deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
								deviceInfo.setRemark(message);
								failList.add(deviceInfo);
								continue;
							}
						}
					}
				}
				//检查imei是否重复
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					DeviceInfo tempDeviceInfo =  deviceInfoService.getDeviceInfoByImei(deviceInfo.getImei());
					if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
					{
						String message = "imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
				}				
			}
			catch(RpcServiceException e)
			{
				deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED.getDescrible());
				failList.add(deviceInfo);
				continue;
			}
			
			if(StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				deviceInfo.setIccid("");	
			}
			if(StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setImei("");
			}
			deviceInfo.setWareHouseId(userInfo.getWarehouseId());
			deviceInfo.setWareHouseIdUp(userInfo.getWarehouseId());
			deviceInfo.setStatus("IN");
			deviceInfo.setDeletedFlag("N");
			deviceInfo.setOrderCode("");
			deviceInfo.setCreatedBy(userInfo.getUserName());
			deviceInfo.setUpdatedBy(userInfo.getUserName());
			deviceInfo.setCreatedDate(new Date());
			deviceInfo.setUpdatedDate(new Date());
			
			successList.add(deviceInfo);
		}
		
		try
		{
			deviceInfoService.batchAddDeviceInfoOnDuplicateKey(successList);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		
		checkImportData.setDeviceInfoSucessList(successList);
		checkImportData.setDeviceInfoFailList(failList);
		logger.info("SupplyChainRemoteService::scannerDeviceInfo end successList.size:{},failList.size:{}",successList.size(),failList.size());
		return checkImportData;
	}
	
	//excel导入数据校验
	public CheckImportDataVo excelDeviceInfoInCheck(UserInfo userInfo,List<DeviceInfo> deviceInfoList) throws RpcServiceException
	{
		CheckImportDataVo checkImportData = new CheckImportDataVo();
		logger.info("SupplyChainRemoteService::excelDeviceInfoImport start userInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
		
		List<DeviceInfo> successList 		= new ArrayList<DeviceInfo>();
		List<DeviceInfo> failList	 		= new ArrayList<DeviceInfo>();
		Map<String,Integer> cacheSnMap		= new HashMap<String,Integer>();
		Map<String,Integer> cacheImeiMap	= new HashMap<String,Integer>();
		Map<String,Integer> cacheIccidMap	= new HashMap<String,Integer>();
		
		//获取操作员信息
		try
		{
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::excelDeviceInfoImport userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::excelDeviceInfoImport invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		
		
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			if(StringUtils.isEmpty(deviceInfo))
			{
				continue;
			}
			if(StringUtils.isEmpty(deviceInfo.getSn()) && !StringUtils.isEmpty(deviceInfo.getImei()))
			{
				deviceInfo.setSn(deviceInfo.getImei());
			}
			if(!StringUtils.isEmpty(deviceInfo.getSn()))
			{
				Integer count = cacheSnMap.get(deviceInfo.getSn());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheSnMap.put(deviceInfo.getSn(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				Integer count = cacheIccidMap.get(deviceInfo.getIccid());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheIccidMap.put(deviceInfo.getIccid(), count);
			}
			if(!StringUtils.isEmpty(deviceInfo.getImei()))
			{
				Integer count = cacheImeiMap.get(deviceInfo.getImei());
				count = (StringUtils.isEmpty(count) || count == 0) ? 1:count+1;
				cacheImeiMap.put(deviceInfo.getImei(), count);
			}
		}
		logger.info("deviceInfoList:{}",deviceInfoList);
		logger.info("cacheImeiMap:{}",cacheImeiMap);
		
		for(DeviceInfo deviceInfo:deviceInfoList)
		{
			logger.info("SupplyChainRemoteService::excelDeviceInfoImport check deviceInfo:{}",deviceInfo);
			try
			{
				//检查字段是否文本内重复
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					Integer count = cacheIccidMap.get(deviceInfo.getIccid());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_ICCID_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					Integer count = cacheImeiMap.get(deviceInfo.getImei());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					Integer count = cacheSnMap.get(deviceInfo.getSn());
					if(count >= 2)
					{
						deviceInfo.setRemark(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_SN_EXCEL.getValue());
						failList.add(deviceInfo);
						continue;
					}
				}
				
				//检查硬件编码
				AttribMana attribMana = attribManaService.getAttribManaByManaCode(deviceInfo.getAttribCode());
				if(StringUtils.isEmpty(attribMana))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				//检查操作员权限
				userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
				if(StringUtils.isEmpty(userInfo))
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				if(userInfo.getRole() != 1 || userInfo.getWarehouseId() == null)
				{
					deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME.getDescrible());
					failList.add(deviceInfo);
					continue;
				}
				//参数验证
				/*if(StringUtils.isEmpty(deviceInfo.getSn()))
				{
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) || 
							attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()))
					{
						if(attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY.getDescrible());
							failList.add(deviceInfo);
							continue;
						}
						
					}
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
					{
						deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_SN_IS_EMPTY.getDescrible());
						failList.add(deviceInfo);
						continue;
					}
				}
				if(StringUtils.isEmpty(deviceInfo.getImei()))
				{
					if(attribMana.getDevTypeId().equals(deviceTypeProperty.getDvd()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getCloudmirror()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getRecord()) ||
							attribMana.getDevTypeId().equals(deviceTypeProperty.getTracker()))
					{
						if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_IMEI_NOT_EXIST.getDescrible());
							failList.add(deviceInfo);
							continue;					
						}
					}
				}
				if(StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					if(!attribMana.getOrNetId().equals(attribInfoProperty.getNetNo()))
					{
						if(!attribMana.getDevTypeId().equals(deviceTypeProperty.getObd()))
						{
							deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_DEVICE_ICCID_IS_EMPTY.getDescrible());
							failList.add(deviceInfo);
							continue;		
						}
					}
				}*/
				
				//判断sn是否重复
				if(!StringUtils.isEmpty(deviceInfo.getSn()))
				{
					DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
					if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
					{
						String message = "sn/imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;	
						
					}
					
					DeviceFile tempDeviceFile = deviceFileService.getDeviceFileBySn(deviceInfo.getSn());
					if(!StringUtils.isEmpty(tempDeviceFile) && tempDeviceFile.getDeletedFlag().equals("N"))
					{
						String message = "sn/imei重复:\n 管理库存记录:sn:" + tempDeviceFile.getSn() + " 录入时间:" + tempDeviceFile.getCreatedDate() + "\n 当前入库记录:sn:" + deviceInfo.getSn();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;	
					}
				}
				//判断iccid是否重复
				if(!StringUtils.isEmpty(deviceInfo.getIccid()))
				{
					DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoByIccid(deviceInfo.getIccid());
					if(!StringUtils.isEmpty(tempDeviceInfo))
					{
						String message = "iccid重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
					
					DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByIccid(deviceInfo.getIccid());
					if(!StringUtils.isEmpty(deviceFileVirtual))
					{
						String message = "iccid重复:\n 虚拟放卡库存记录:iccid:" + deviceFileVirtual.getIccid() + "录入时间:" + deviceFileVirtual.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
					
					//检查iccid是否是广联卡 71234567:调试数据不需要验证
					if(!deviceInfo.getIccid().startsWith("71234567") && !attribMana.getCardSelfId().equals(attribInfoProperty.getCardSelfExtain()))
					{
						if(StringUtils.isEmpty(attribMana.getVerifyIccid()) || attribMana.getVerifyIccid().equals("Y")){
							String imsi = this.getImsiByIccidFromFlowCardPlat(deviceInfo.getIccid().toUpperCase());
							if(StringUtils.isEmpty(imsi))
							{
								deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_ICCID_IS_NOT_GLSX.getDescrible());
								failList.add(deviceInfo);
								continue;				
							}
							
							DeviceCardManager deviceCardManager = this.getCardManagerByImsi(imsi);
							if(!StringUtils.isEmpty(deviceCardManager))
							{
								DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
								if(!StringUtils.isEmpty(deviceSnapshot) && deviceSnapshot.getDeletedFlag().equals("N"))
								{
									String message = "iccid重复:\n 设备关系库存记录:iccid:" + deviceCardManager.getIccid() + " sn:" + deviceSnapshot.getSn() + "录入时间:" + deviceSnapshot.getCreatedDate() +  "\n 当前入库记录:sn:" +
											deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
									deviceInfo.setRemark(message);
									failList.add(deviceInfo);
									continue;
								}
							}
						}	
					}
				}
				//检查imei是否重复
				if(!StringUtils.isEmpty(deviceInfo.getImei()))
				{
					DeviceInfo tempDeviceInfo =  deviceInfoService.getDeviceInfoByImei(deviceInfo.getImei());
					if(!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("N"))
					{
						String message = "imei重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() + 
								" iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "录入时间:" + tempDeviceInfo.getCreatedDate() +  "\n 当前入库记录:sn:" +
								deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
						deviceInfo.setRemark(message);
						failList.add(deviceInfo);
						continue;
					}
				}				
			}
			catch(RpcServiceException e)
			{
				deviceInfo.setRemark(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED.getDescrible());
				failList.add(deviceInfo);
				continue;
			}
			
			successList.add(deviceInfo);
		}
		
		checkImportData.setDeviceInfoSucessList(successList);
		checkImportData.setDeviceInfoFailList(failList);
		logger.info("SupplyChainRemoteService::excelDeviceInfoImport end successList.size:{},failList.size:{}",successList.size(),failList.size());
		return checkImportData;
	}
	

	
	public Integer deliveryDeviceInfo(UserInfo userInfo,OrderInfo orderInfo, DeviceInfo deviceInfo,
			Logistics logistics,Address address) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("SupplyChainRemoteImpl::deliveryDeviceInfo start userInfo:{},orderInfo:{},deviceInfo:{}",userInfo,orderInfo,deviceInfo);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			
			String orderCode = orderInfo.getOrderCode();
			
			orderInfo = orderInfoService.getOrderInfoByOrderCode(orderInfo.getOrderCode());
			if(StringUtils.isEmpty(orderInfo))
			{
				String message = "订单:" + orderCode + "不存在或者无效!";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(orderInfo.getWarehouseId().equals(0))
			{
				String message = "订单:" + orderCode + "不存在或者无效!";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_OV.getValue()))
			{
				String message = "订单:" + orderCode + "已完成!";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(orderInfo.getStatus().equals(OrderStatusEnum.STATUS_CL.getValue()))
			{
				String message = "订单:" + orderCode + " 已取消!";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			//如果是超级用户 放开权限
			if(userInfo.getIsSup() == 0)
			{
				if(!orderInfo.getWarehouseId().equals(userInfo.getWarehouseId()))
				{
					String message = "订单:" + orderCode + "用户无权限!";
					logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			
			Integer deviceCount = orderInfoService.getDeviceCountByOrderCode(orderInfo);
			if(deviceCount >= orderInfo.getTotal())
			{
				orderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
				orderInfoService.update(orderInfo);
				
				String message = "订单:" + orderCode + "已完成!";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			
			
			DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
			if(StringUtils.isEmpty(tempDeviceInfo))
			{
				String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "未入库";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);	
			}
			if(tempDeviceInfo.getDeletedFlag().equals("Y"))
			{
				String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "已被初始化,但未被入库";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
			{
				String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "已出库";
				logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
				ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
			}
			if(!StringUtils.isEmpty(tempDeviceInfo.getIccid()))
			{
				if(!tempDeviceInfo.getIccid().equals(deviceInfo.getIccid()))
				{
					String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "入库iccid:" + deviceInfo.getIccid() + " 库存iccid:" + tempDeviceInfo.getIccid() + " 不一致";
					logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			//老工具扫描的数据不做仓库检测
			if(!tempDeviceInfo.getWareHouseIdUp().equals(5))
			{
				//超级用户不做仓库验证
				if(userInfo.getIsSup() == 0)
				{
					if(!tempDeviceInfo.getWareHouseIdUp().equals(orderInfo.getWarehouseId()))
					{
						String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "订单仓库与设备现存仓库不匹配";
						logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
						ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
						throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
					}
				}
				if(!tempDeviceInfo.getAttribCode().equals(orderInfo.getAttribCode()))
				{
					String message = "订单:" + orderCode + " 设备sn/imei:" + deviceInfo.getSn() + "订单配置编码:" + orderInfo.getAttribCode() + " 设备编码:" + tempDeviceInfo.getAttribCode() + " 订单配置码与设备配置码不匹配";
					logger.error("SupplyChainRemoteService::deliveryDeviceInfo " + message);
					ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
				}
			}
			
			//插入物流明细
			address.setName(orderInfo.getContacts());
			address.setAddress(orderInfo.getAddress());
			address.setMobile(orderInfo.getMobile());
			address.setMerchantCode(orderInfo.getSendMerchantNo());
			address.setCreatedBy(userInfo.getUserName());
			address.setUpdatedBy(userInfo.getUserName());
			address.setCreatedDate(new Date());
			address.setUpdatedDate(new Date());
			address = addressService.addIfNotExist(address);
			
			
			logistics.setCode(StringUtil.getOrderNo());
			logistics.setServiceCode(orderInfo.getOrderCode());
			logistics.setReceiveId(address.getId());
			logistics.setType(LogisticsTypeEnum.SEND_ORDER_NO.getCode());
			logistics.setOrderNumber(logistics.getOrderNumber());
			logistics.setCreatedBy(userInfo.getUserName());
			logistics.setUpdatedBy(userInfo.getUserName());
			logistics.setCreatedDate(new Date());
			logistics.setUpdatedDate(new Date());
			logistics.setDeletedFlag("N");
			logistics = logisticsService.addIfNotExist(logistics);
			
			//插入订单明细
			OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
			orderInfoDetail.setOrderCode(orderInfo.getOrderCode());
			orderInfoDetail.setIccid(tempDeviceInfo.getIccid());
			orderInfoDetail.setImei(tempDeviceInfo.getImei());
			orderInfoDetail.setSn(tempDeviceInfo.getSn());
			orderInfoDetail.setAttribCode(tempDeviceInfo.getAttribCode());
			orderInfoDetail.setBatch(tempDeviceInfo.getBatch());
			orderInfoDetail.setWarehouseId(tempDeviceInfo.getWareHouseId());
			//orderInfoDetail.setWarehouseIdUp(userInfo.getWarehouseId());
			orderInfoDetail.setWarehouseIdUp(tempDeviceInfo.getWareHouseId());
			orderInfoDetail.setCreatedBy(userInfo.getUserName());
			orderInfoDetail.setUpdatedBy(userInfo.getUserName());
			orderInfoDetail.setCreatedDate(new Date());
			orderInfoDetail.setUpdatedDate(new Date());
			orderInfoDetail.setLogisticsId(logistics.getId().intValue());
			orderInfoService.addOrderInfoDetail(orderInfoDetail);
			
			//更细设备状态
			tempDeviceInfo.setStatus(DeviceEnum.STATUS_OUT.getValue());
			//tempDeviceInfo.setWareHouseIdUp(userInfo.getWarehouseId());
			tempDeviceInfo.setWareHouseIdUp(tempDeviceInfo.getWareHouseId());
			tempDeviceInfo.setOrderCode(orderInfo.getOrderCode());
			tempDeviceInfo.setDeletedFlag("N");
			tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
			tempDeviceInfo.setUpdatedDate(new Date());
			deviceInfoService.updateDeviceInfoBySn(tempDeviceInfo);
			
			//修改订单状态
			if(++deviceCount == orderInfo.getTotal())
			{
				orderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
				orderInfo.setUpdatedBy(userInfo.getUserName());
				orderInfo.setUpdatedDate(new Date());
				orderInfoService.update(orderInfo);
			}
			
			//发送通知同步ecMerchantOrder
			List<String> listDispatchOrder = new ArrayList<>();
			listDispatchOrder.add(orderInfo.getOrderCode());
			kafkaService.notifyUpdateDispatchOrderInfo(listDispatchOrder);
			
			result = orderInfo.getTotal() - deviceCount;
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::excelDeviceInfoImport end result:",result);
		return result;
	}
	
	public Integer canceDeliveryDeviceInfo(UserInfo userInfo,DeviceInfo deviceInfo) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("SupplyChainRemoteService::canceDeliveryDeviceInfo start userInfo:{},deviceInfo:{}",userInfo,deviceInfo);
		try
		{
			//获取操作员信息
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			//验证参数
			if(StringUtils.isEmpty(deviceInfo.getSn()))
			{
				logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo invalid param!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			
			//获取设备入库信息
			DeviceInfo tempDeviceInfo =  deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
			if(StringUtils.isEmpty(tempDeviceInfo) || (!StringUtils.isEmpty(tempDeviceInfo) && tempDeviceInfo.getDeletedFlag().equals("Y")))
			{
				logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo record deviceinfo is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST);
			}
			//超级用户放开权限
			if(userInfo.getIsSup() == 0)
			{
				if(!tempDeviceInfo.getWareHouseIdUp().equals(userInfo.getWarehouseId()))
				{
					logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo invalid username!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
				}
			}
			if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_IN.getValue()))
			{
				logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo device is not delivery!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_IS_NOT_OUT);
			}
			DeviceFileSnapshot snapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(deviceInfo.getSn());
			if(!StringUtils.isEmpty(snapshot))
			{
				if(snapshot.getPackageStatu().equals(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue()) ||
						!StringUtils.isEmpty(snapshot.getUserId()) ||
						!StringUtils.isEmpty(snapshot.getPackageUserId()))
				{
					logger.error("SupplyChainRemoteService::canceDeliveryDeviceInfo device is alread active!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_ACTIVE);
				}
				snapshot.setDeletedFlag("Y");
				snapshot.setUpdatedBy(userInfo.getUserName());
				snapshot.setUpdatedDate(new Date());
				deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(snapshot);
			}
			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(deviceInfo.getSn());
			if(!StringUtils.isEmpty(deviceFile))
			{
				deviceFile.setDeletedFlag("Y");
				deviceFile.setUpdatedBy(userInfo.getUserName());
				deviceFile.setUpdatedDate(new Date());
				deviceFileService.updateAllColDeviceFileById(deviceFile);
			}
			if(!StringUtils.isEmpty(tempDeviceInfo))
			{
				tempDeviceInfo.setOrderCode("");
				tempDeviceInfo.setStatus("IN");
				tempDeviceInfo.setUpdatedBy(userInfo.getUserName());
				tempDeviceInfo.setUpdatedDate(new Date());
				deviceInfoService.updateDeviceInfoBySn(tempDeviceInfo);
			}
			OrderInfoDetail orderDetail = orderInfoService.getOrderInfoDetailBySn(deviceInfo.getSn());
			if(!StringUtils.isEmpty(orderDetail))
			{
				orderInfoService.delOrderInfoDetailById(orderDetail);
				if(!StringUtils.isEmpty(orderDetail.getOrderCode()))
				{
					OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderCode(orderDetail.getOrderCode());
					if(!StringUtils.isEmpty(orderInfo))
					{
						orderInfo.setStatus(OrderStatusEnum.STATUS_UF.getValue());
						orderInfoService.update(orderInfo);
					}
				}
				//发送通知同步ecMerchantOrder
				List<String> listDispatchOrder = new ArrayList<>();
				listDispatchOrder.add(orderDetail.getOrderCode());
				kafkaService.notifyUpdateDispatchOrderInfo(listDispatchOrder);
			}	
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::canceDeliveryDeviceInfo end result:",result);
		return result;
	}
	
	public Integer dispatchDeviceInfo(UserInfo userInfo, WareHouseInfo toHouseInfo, DeviceInfo deviceInfo, AttribMana attribMana) throws RpcServiceException
	{
		//获取操作员信息
		Integer result = 1;
		logger.info("SupplyChainRemoteService::dispatchDeviceInfo start userInfo:{},toHouseInfo:{},deviceInfo:{},attribMana:{}",userInfo,toHouseInfo,deviceInfo,attribMana);
		try
		{
			userInfo = userinfoService.getUserInfoByUserName(userInfo.getUserName());
			if(StringUtils.isEmpty(userInfo))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo userName is not exist!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_NOT_EXIST);
			}
			if(userInfo.getRole() != 2 || userInfo.getWarehouseId() == null)
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo invalid username!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
			}
			toHouseInfo = wareHouseInfoService.getWarehouseById(toHouseInfo.getId());
			if(StringUtils.isEmpty(toHouseInfo))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo Can not find wareHouse!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_WAREHOUSE_NO_EXISTS);
			}
			if(!toHouseInfo.getBelong().equals("WA"))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo wareHouse type wrong!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_WAREHOUSE_TYPE_WRONG);
			}
			DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
			if(StringUtils.isEmpty(tempDeviceInfo))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo deviceinfo not in!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
			}
			if(!StringUtils.isEmpty(deviceInfo.getIccid()))
			{
				if(!deviceInfo.getIccid().equals(tempDeviceInfo.getIccid()))
				{
					logger.error("SupplyChainRemoteService::dispatchDeviceInfo iccid not match!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_ICCID_NOT_MATCH);
				}
			}
			if(tempDeviceInfo.getStatus().equals(DeviceEnum.STATUS_OUT.getValue()))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo deviceinfo ready out!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECORD_IMEI_READY_OUT);
			}
			if(!tempDeviceInfo.getAttribCode().equals(attribMana.getAttribCode()))
			{
				logger.error("SupplyChainRemoteService::dispatchDeviceInfo attribcode not match!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ATTRIB_CODE_NOT_MATCH);
			}
			//超级用户放开权限
			if(userInfo.getIsSup() == 0)
			{
				if(userInfo.getWarehouseId() != tempDeviceInfo.getWareHouseIdUp())
				{
					logger.error("SupplyChainRemoteService::dispatchDeviceInfo user has not pow to opt!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCUNT_INVALID_USERNAME);
				}
			}
			
			//更新设备表
			deviceInfo.setWareHouseIdUp(toHouseInfo.getId());
			deviceInfo.setUpdatedBy(userInfo.getUserName());
			deviceInfo.setUpdatedDate(null);
			deviceInfo.setId(tempDeviceInfo.getId());
			deviceInfoService.updateDeviceInfoById(deviceInfo);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::dispatchDeviceInfo end result:",result);
		return result;
	}
	
	public Integer handleExsysDeviceStatu(UserInfo userInfo,ExsysDeviceStatu exsysDeviceStatu) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("SupplyChainRemoteService::handleExsysDeviceStatu userInfo:{},exsysDeviceStatu:{}",userInfo,exsysDeviceStatu);
		try
		{
			if(StringUtils.isEmpty(exsysDeviceStatu.getUserflag()) 
					&& StringUtils.isEmpty(exsysDeviceStatu.getImsi())
					&& StringUtils.isEmpty(exsysDeviceStatu.getVehicleflag())
					&& StringUtils.isEmpty(exsysDeviceStatu.getActivetime()))
			{
				//初始化设备  这里第三方业务系统（GPS）上报初始化状态 没有必要向用户体系发通知 
				DeviceResetRecord condition = new DeviceResetRecord();
				condition.setSn(exsysDeviceStatu.getSn());
				condition.setCreatedBy(userInfo.getUserName());
				condition.setCreatedDate(new Date());
				condition.setUpdatedBy(userInfo.getUserName());
				condition.setUpdatedDate(new Date());
				condition.setRemark("第三方业务系统上报初始化指令初始化");
				deviceManagerAdminRemoteService.initDeviceFileByDeviceResetRecord(condition);
			}
			else
			{
				boolean bNeedUpdate = false;
				//关系变更   //这里的用户和业务系统商量 都是激活用户处理
				DeviceCardManager 		deviceCard 	= null;
				DeviceUserManager 		deviceUser	= null;
				DeviceVehicleManager 	deviceVehi	= null;
				
				Date activeDate = null;
				Date updateDate = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(!StringUtils.isEmpty(exsysDeviceStatu.getActivetime()))
				{
					activeDate = new Date(exsysDeviceStatu.getActivetime());
				}
				if(!StringUtils.isEmpty(exsysDeviceStatu.getUpdatetime()))
				{
					updateDate = new Date(exsysDeviceStatu.getUpdatetime());
				}
				else
				{
					updateDate = new Date();
				}
				
				if(!StringUtils.isEmpty(exsysDeviceStatu.getImsi()))
				{
					DeviceCardManager cardCondition = new DeviceCardManager();
					cardCondition.setCompanyId(1);
					cardCondition.setImsi(exsysDeviceStatu.getImsi());
					cardCondition.setCreatedBy(userInfo.getUserName());
					cardCondition.setCreatedDate(updateDate);
					cardCondition.setUpdatedBy(userInfo.getUserName());
					cardCondition.setUpdatedDate(updateDate);
					deviceCard = deviceCardManagerService.addDeviceCard(cardCondition);
				}
				if(!StringUtils.isEmpty(exsysDeviceStatu.getUserflag()) 
						&& !StringUtils.isEmpty(exsysDeviceStatu.getFlagtype()))
				{
					DeviceUserManager userCondition = new DeviceUserManager();
					userCondition.setCompanyId(1);
					userCondition.setFlagType(exsysDeviceStatu.getFlagtype());
					userCondition.setUserFlag(exsysDeviceStatu.getUserflag());
					userCondition.setCreatedBy(userInfo.getUserName());
					userCondition.setCreatedDate(updateDate);
					userCondition.setUpdatedBy(userInfo.getUserName());
					userCondition.setUpdatedDate(updateDate);
					deviceUser = deviceUserManagerService.addDeviceUser(userCondition);
				}
				if(!StringUtils.isEmpty(exsysDeviceStatu.getVehicleflag()) 
						&& !StringUtils.isEmpty(exsysDeviceStatu.getVehicleflagtype()))
				{
					DeviceVehicleManager vehiCondition = new DeviceVehicleManager();
					vehiCondition.setCompanyId(1);
					vehiCondition.setFlagType(exsysDeviceStatu.getVehicleflagtype());
					vehiCondition.setVehicleFlag(exsysDeviceStatu.getVehicleflag());
					vehiCondition.setCreatedBy(userInfo.getUserName());
					vehiCondition.setCreatedDate(updateDate);
					vehiCondition.setUpdatedBy(userInfo.getUserName());
					vehiCondition.setUpdatedDate(updateDate);
					deviceVehi = deviceVehicleManagerService.addDeviceVehicle(vehiCondition);
				}
				
				DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(exsysDeviceStatu.getSn());
				if(StringUtils.isEmpty(deviceFile) || deviceFile.getDeletedFlag().equals("Y"))
				{
					logger.info("SupplyChainRemoteService::handleExsysDeviceStatu Failed to get deviceFile");
					return result;
				}
				DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(exsysDeviceStatu.getSn());
				if(StringUtils.isEmpty(deviceFileSnapshot) || deviceFileSnapshot.getDeletedFlag().equals("Y"))
				{
					logger.info("SupplyChainRemoteService::handleExsysDeviceStatu Failed to get deviceFileSnapshot");
					return result;
				}
				if(!StringUtils.isEmpty(deviceCard))
				{
					DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(deviceCard.getImsi());
					if(!StringUtils.isEmpty(deviceFileVirtual) && deviceFileVirtual.getDeletedFlag().equals("N"))
					{
						deviceFileVirtual.setDeletedFlag("Y");
						deviceFileVirtual.setCreatedBy(userInfo.getUserName());
						deviceFileVirtual.setUpdatedBy(userInfo.getUserName());
						deviceFileVirtual.setCreatedDate(updateDate);
						deviceFileVirtual.setUpdatedDate(updateDate);
						deviceFileVirtualService.updateDeviceFileVirtualById(deviceFileVirtual);
					}
				}
				
				if(!StringUtils.isEmpty(activeDate))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
					deviceFileSnapshot.setPackageUserTime(formatter.format(activeDate));
				}
				
				//处理卡绑定关系
				if(StringUtils.isEmpty(deviceCard) && !StringUtils.isEmpty(deviceFileSnapshot.getCardId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setCardId(null);
					deviceFileSnapshot.setCardTime(null);
				}
				else if(!deviceCard.getId().equals(deviceFileSnapshot.getCardId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setCardId(deviceCard.getId());
					deviceFileSnapshot.setCardTime(formatter.format(updateDate));
					deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), deviceCard.getId(), userInfo.getUserName());
					//查询这张卡是否被其他设备占用 如果有解除绑定
					DeviceFileSnapshot otherSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCard.getId());
					if (!StringUtils.isEmpty(otherSnapshot) && !otherSnapshot.getSn().equals(deviceFileSnapshot.getSn()))
					{
						logger.info("SupplyChainRemoteService::handleExsysDeviceStatu otherSnapshot:{}", otherSnapshot);
	                    deviceUpdateRecordService.setDeviceUpdateRecord(otherSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), otherSnapshot.getCardId(), userInfo.getUserName());
	                    otherSnapshot.setCardId(null);
	                    otherSnapshot.setCardTime(null);
	                    deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(otherSnapshot);
					}
				}
				
				//处理用户绑定关系
				/*if(StringUtils.isEmpty(deviceUser) && !StringUtils.isEmpty(deviceFileSnapshot.getUserId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setUserId(null);
					deviceFileSnapshot.setUserTime(null);
				}
				else if(!StringUtil.isEmpty(deviceUser.getId()) && !deviceUser.getId().equals(deviceFileSnapshot.getUserId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setUserId(deviceUser.getId());
					deviceFileSnapshot.setUserTime(formatter.format(updateDate));				
	                deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue(), deviceUser.getId(), userInfo.getUserName());
				}*/
				
				//处理用户绑定关系(这里和业务系统商量 用户都作为激活用户处理)
				if(!StringUtil.isEmpty(deviceUser.getId()) && !deviceUser.getId().equals(deviceFileSnapshot.getPackageUserId()))
				{
					bNeedUpdate = true;					
					deviceFileSnapshot.setPackageUserId(deviceUser.getId());
					deviceFileSnapshot.setPackageUserTime(formatter.format(activeDate));
	                deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue(), deviceUser.getId(), userInfo.getUserName());
				}
				
				//处理车辆绑定关系
				if(StringUtils.isEmpty(deviceVehi) && !StringUtils.isEmpty(deviceFileSnapshot.getVehicleId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setVehicleId(null);
				}
				else if(!StringUtil.isEmpty(deviceVehi.getId()) && !deviceVehi.getId().equals(deviceFileSnapshot.getVehicleId()))
				{
					bNeedUpdate = true;
					deviceFileSnapshot.setVehicleId(deviceVehi.getId());				
	                deviceUpdateRecordService.setDeviceUpdateRecord(deviceFileSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue(), deviceVehi.getId(), userInfo.getUserName());
				}
				
				if(bNeedUpdate == true)
				{
					logger.info("updateDeviceFileSnapshotBySn::入参:{}", deviceFileSnapshot);
					deviceFileSnapshot.setUpdatedBy(userInfo.getUserName());
					deviceFileSnapshot.setUpdatedDate(updateDate);
					deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(deviceFileSnapshot);
				}	
			}
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::handleExsysDeviceStatu result:{}",result);
		return result;	
	}
	
	public Integer handleExsysOrderInfo(UserInfo userInfo,ExsysOrderInfo exsysOrderInfo) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("SupplyChainRemoteService::handleExsysOrderInfo start userInfo:{},exsysOrderInfo:{}",userInfo,exsysOrderInfo);
		try
		{
			Date updateDate = null;
			if(!StringUtils.isEmpty(exsysOrderInfo.getUpdatetime()))
			{
				updateDate = new Date(exsysOrderInfo.getUpdatetime());
			}
			else
			{
				updateDate = new Date();
			}
			String thirdOrderNo = exsysOrderInfo.getOrderno();
			MerchantOrder mrechantOrder = this.getMerchantOrderByRemark(thirdOrderNo);
			if(!StringUtils.isEmpty(mrechantOrder))
			{
				//在这里把订单状态做一个转换 需要业务转
				mrechantOrder.setStatus(Integer.valueOf(exsysOrderInfo.getOrderstatu()).byteValue());
				mrechantOrder.setUpdatedDate(updateDate);
				mrechantOrder.setUpdatedBy(userInfo.getUserName());
				this.updateMerchantOrderStatusByExsystem(mrechantOrder);	
			}
			else
			{
				if(StringUtils.isEmpty(exsysOrderInfo.getOrderno())
						||StringUtils.isEmpty(exsysOrderInfo.getOrdertimestamp())
						||StringUtils.isEmpty(exsysOrderInfo.getTomerchantno())
						||StringUtils.isEmpty(exsysOrderInfo.getExpectedtimestamp())
						||StringUtils.isEmpty(exsysOrderInfo.getProductno())
						||StringUtils.isEmpty(exsysOrderInfo.getProductquantity())
						||StringUtils.isEmpty(exsysOrderInfo.getContactaddress())
						||StringUtils.isEmpty(exsysOrderInfo.getContactname())
						||StringUtils.isEmpty(exsysOrderInfo.getContactphone()))
				{
					logger.error("SupplyChainRemoteService::handleExsysOrderInfo param error!");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				//订单创建 
				try
				{
					mrechantOrder = new MerchantOrder();
					mrechantOrder.setRemarks(exsysOrderInfo.getOrderno());
					mrechantOrder.setOrderTime(new Date(exsysOrderInfo.getOrdertimestamp()));
					mrechantOrder.setMerchantCode(exsysOrderInfo.getTomerchantno());
					mrechantOrder.setHopeTime(new Date(exsysOrderInfo.getExpectedtimestamp()));
					mrechantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
					mrechantOrder.setTotalOrder(exsysOrderInfo.getProductquantity());
					mrechantOrder.setCreatedBy(userInfo.getUserName());
					mrechantOrder.setUpdatedBy(userInfo.getUserName());
					MerchantOrderDetail orderDetail = new MerchantOrderDetail();
					orderDetail.setProductCode(exsysOrderInfo.getProductno());
					orderDetail.setOrderQuantity(exsysOrderInfo.getProductquantity());
					orderDetail.setProductAmount(0.00);
					
					Address address = new Address();
					address.setAddress(exsysOrderInfo.getContactaddress());
					address.setMobile(exsysOrderInfo.getContactphone());
					address.setName(exsysOrderInfo.getContactname());
					address.setMerchantCode(exsysOrderInfo.getTomerchantno());
					address = addressService.addIfNotExist(address);
					Logistics logistics = new Logistics();
					logistics.setReceiveId(address.getId());
					logistics.setReceiveAddress(address);
					logistics.setCreatedBy(userInfo.getUserName());
					logistics.setUpdatedBy(userInfo.getUserName());
					
					List<MerchantOrderDetail> orderDetailList = new ArrayList<MerchantOrderDetail>();
					orderDetailList.add(orderDetail);
					mrechantOrder.setMerchantOrderDetailList(orderDetailList);
					mrechantOrder.setLogistics(logistics);				
					merchantOrderService.add(mrechantOrder);
				}
				catch(Exception e)
				{
					logger.error("新增同步设备信息实体异常," + e.getMessage(),e);
					throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
				}		
			}
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::handleExsysOrderInfo result:{}",result);
		return result;	
	}
	
	public AttribMana getAttribManaByDeviceSn(DeviceInfo deviceInfo) throws RpcServiceException
	{
		AttribMana result = null;
		logger.info("SupplyChainRemoteService::getAttribManaByDeviceSn start deviceInfo:{}",deviceInfo);
		try
		{
			deviceInfo = deviceInfoService.getDeviceInfoBySn(deviceInfo.getSn());
			if(!StringUtils.isEmpty(deviceInfo))
			{
				if(!StringUtils.isEmpty(deviceInfo.getAttribCode()))
				{
					result = attribManaService.getAttribManaByManaCode(deviceInfo.getAttribCode());
				}
			}
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::getAttribManaByDeviceSn end result:",result);
		return result;
	}
	
	private DeviceCardManager getCardManagerByImsi(String imsi) throws RpcServiceException
	{
		DeviceCardManager deviceCardManager = null;
		try
		{
			DeviceCardManager record = new DeviceCardManager();
			record.setCompanyId(1);
			record.setImsi(imsi);
			deviceCardManager = deviceCardService.getDeviceCardByUniqueKey(record);
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		return deviceCardManager;
	}
	
	
	
	private String getImsiByIccidFromFlowCardPlat(String iccid) throws RpcServiceException
	{
		logger.info("SupplyChainRemoteService::getImsiByIccidFromFlowCardPlat iccid=" + iccid);
		String imsi = null;
		try
		{		
			FlowCardRequest flowCardRequest = new FlowCardRequest();
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			flowCardRequest.setTime(time);  //时间
            flowCardRequest.setVersion("2.2.0"); //版本号
            flowCardRequest.setConsumer("task-supplychain"); //项目名称
            flowCardRequest.setKeyWord(iccid.toUpperCase());      
            flowCardRequest.setInvoker("glsx");
            FlowResponse<Flowcard> flowCardFlowResponse = supplyChainExternalService.getFlowCardByIccidOrImsi(flowCardRequest);
            if(!StringUtils.isEmpty(flowCardFlowResponse.getEntiy()))
            {
            	imsi = flowCardFlowResponse.getEntiy().getImsi();
            }
		}
		catch(RpcServiceException e)
		{
			throw e;
		}
		logger.info("SupplyChainRemoteService::getImsiByIccidFromFlowCardPlat return  imsi=" + imsi);
		return imsi;
	}

	/**
	 * @Title: insertSelective
	 * @Description: 同步设备信息日志增加
	 * @param @param orderInfo
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int insert(RequestLog requestLog) throws RpcServiceException{
		logger.info("新增同步设备信息实体: " + JSONSerializer.toJSON(requestLog).toString());
		try {
			requestLog.setCreatedDate(new Date());
			requestLog.setUpdatedDate(new Date());
			return requestLogMapper.insert(requestLog);
		} catch (Exception e) {
			logger.error("新增同步设备信息实体异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getRequestLog 
	 * @Description: 查询同步设备信息
	 * @param @param requestLog
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return RequestLog
	 * @throws
	 */
	public RequestLog getRequestLog(RequestLog requestLog) throws RpcServiceException{
		logger.info("查询同步设备信息条件: " + JSONSerializer.toJSON(requestLog).toString());
		try {
			if(StringUtils.isEmpty(requestLog.getImei())){
				logger.error("IMEI条件为空");
				throw new RpcServiceException(DefaultErrorEnum.DATA_NULL);
			}
			return requestLogMapper.getRequestLog(requestLog);
		} catch (Exception e) {
			logger.error("查询同步设备信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
     * 更具remark查询订单 主要针对第三方业务系统传上来的他方订单号来查询订单
     * @param 
     * @return
     */
     private MerchantOrder getMerchantOrderByRemark(String remark) throws RpcServiceException
     {
    	 logger.info("SupplyChainRemoteService::getMerchantOrderByRemark start remark:" + remark);
    	 MerchantOrder result = null;
    	 try
    	 {
        	 MerchantOrder merchantOrder = new MerchantOrder();
        	 merchantOrder.setRemarks(remark);
        	 merchantOrder.setDeletedFlag("N");
        	 
        	 result = merchantOrderService.getMerchantOrderByRemarkForExsystem(remark);
        	// result = merchantOrderMapper.selectOne(merchantOrder); 
    	 }
    	 catch (Exception e)
    	 {
  			logger.error("查询同步设备信息异常," + e.getMessage(),e);
  			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED,e.getMessage());
  		}
    	logger.info("SupplyChainRemoteService::getMerchantOrderByRemark end result:{}", result); 
    	return result;
     }
     
     /**
      * 更具remark查询订单 主要针对第三方业务系统传上来的订单状态修改订单
      * @param 
      * @return
      */
     private void updateMerchantOrderStatusByExsystem(MerchantOrder merchantOrder) throws RpcServiceException
     {
    	 logger.info("SupplyChainRemoteService::updateMerchantOrderStatusByExsystem start merchantOrder:{}",merchantOrder);
    	 try 
    	 {
    		 int result = merchantOrderService.updateMerchantOrderStatusForExsystem(merchantOrder);
    		 if(result == 1)
    		 {
    			 logger.error("该订单已经发货 不能取消");
    			 throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ORDER_ALREADY_OUTGOING);
    		 }    		
    		// merchantOrderMapper.updateByPrimaryKeySelective(merchantOrder);
    	 }
    	 catch (Exception e) 
    	 {
 			logger.error("查询同步设备信息异常," + e.getMessage(),e);
 			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED,e.getMessage());
 		}
    	logger.info("SupplyChainRemoteService::updateMerchantOrderStatusByExsystem end");  
     }
}
