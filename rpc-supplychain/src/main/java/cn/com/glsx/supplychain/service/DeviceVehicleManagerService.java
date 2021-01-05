package cn.com.glsx.supplychain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceVehicleManagerMapper;
import cn.com.glsx.supplychain.model.DeviceVehicleManager;

@Service
@Transactional
public class DeviceVehicleManagerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceVehicleManagerMapper deviceVehicleMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	/**
	* 获取通过id设备车辆管理信息
	* @param @id 
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceVehicleManager getDeviceVehicleById(Integer id) throws RpcServiceException
	{
		if(id==null)
		{
			logger.error("DeviceVehicleManagerService::getDeviceVehicleById 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceVehicleManagerService::getDeviceVehicleById param id="+id);
		
		DeviceVehicleManager deviceVehicle = redisService.getVehicleInfo(id);
		if(!StringUtils.isEmpty(deviceVehicle))
		{
			logger.info("DeviceVehicleManagerService::getDeviceVehicleById return deviceVehicle=" + deviceVehicle.toString());
			return deviceVehicle;
		}
		
		try
		{
			deviceVehicle = deviceVehicleMapper.selectByPrimaryKey(id);
			redisService.setVehicleInfo(deviceVehicle);
		}
		catch(Exception e)
		{
			logger.error("DeviceVehicleManagerService::getDeviceVehicleById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceVehicleManagerService::getDeviceVehicleById return deviceVehicle=" + (StringUtils.isEmpty(deviceVehicle)?"null":deviceVehicle.toString()));
		return deviceVehicle;
	}
	
	/**
	* 通过唯一约束查询设备车辆管理信息
	* @param @record
	* @return @DeviceVehicleManager
	* @throws 
	*/
	public DeviceVehicleManager getDeviceVehicleByUniqueKey(DeviceVehicleManager record) throws RpcServiceException
	{
		DeviceVehicleManager deviceVehicle = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getVehicleFlag()) ||
				StringUtils.isEmpty(record.getFlagType()))
		{
			logger.error("DeviceVehicleManagerService::getDeviceVehicleByUniqueKey 参数不能为空");
			return deviceVehicle;
		}
		logger.info("DeviceVehicleManagerService::getDeviceVehicleByUniqueKey param record="+record.toString());
		try
		{
			deviceVehicle = deviceVehicleMapper.selectByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceVehicleManagerService::getDeviceVehicleByUniqueKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceVehicleManagerService::getDeviceVehicleByUniqueKey return deviceCard="+(StringUtils.isEmpty(deviceVehicle)?"null":deviceVehicle.toString()));
		return deviceVehicle;
	}
	
	
	/**
	* 添加设备车辆管理信息
	* @param @record
	* @return @DeviceVehicleManager
	* @throws 
	*/
	public DeviceVehicleManager addDeviceVehicle(DeviceVehicleManager record) throws RpcServiceException
	{
		DeviceVehicleManager deviceVehicle = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getVehicleFlag()) ||
				StringUtils.isEmpty(record.getFlagType()))
		{
			logger.error("DeviceVehicleManagerService::addDeviceVehicle 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceVehicleManagerService::addDeviceCard param record="+record.toString());
		
		try
		{
			deviceVehicle = getDeviceVehicleByUniqueKey(record);
		}
		catch(RpcServiceException e)
		{
			logger.error("DeviceVehicleManagerService::addDeviceVehicle 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceVehicle))
		{
			logger.info("DeviceVehicleManagerService::addDeviceVehicle return deviceCard="+ deviceVehicle.toString());
			return deviceVehicle;
		}
		
		try
		{
			deviceVehicleMapper.insertSelective(record);
			redisService.setVehicleInfo(record);
		}
		catch(RpcServiceException e)
		{
			logger.error("DeviceVehicleManagerService::addDeviceVehicle 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceVehicleManagerService::addDeviceVehicle return record="+(StringUtils.isEmpty(record)?"null":record.toString()));
		return record;	
	}
}
