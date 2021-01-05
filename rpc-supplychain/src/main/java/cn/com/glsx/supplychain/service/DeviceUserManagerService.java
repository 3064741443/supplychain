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
import cn.com.glsx.supplychain.mapper.DeviceUserManagerMapper;
import cn.com.glsx.supplychain.model.DeviceUserManager;

@Service
@Transactional
public class DeviceUserManagerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceUserManagerMapper deviceUserMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	/**
	* 获取通过id设备用户管理信息
	* @param @id 
	* @return @DeviceUserManager
	* @throws 
	*/
	public DeviceUserManager getDeviceUserById(Integer id) throws RpcServiceException
	{
		DeviceUserManager deviceUser = null;
		if(id==null)
		{
			logger.error("DeviceUserManagerService::getDeviceUserById 参数不能为空");
			return deviceUser;
		}
		logger.info("DeviceUserManagerService::getDeviceUserById param id="+id);
		
		deviceUser = redisService.getDevUserInfo(id);
		if(!StringUtils.isEmpty(deviceUser))
		{
			logger.info("DeviceUserManagerService::getDeviceUserById return deviceCard=" + deviceUser.toString());
			return deviceUser;
		}
		
		try
		{
			deviceUser = deviceUserMapper.selectByPrimaryKey(id);
			redisService.setDevUserInfo(deviceUser);
		}
		catch(Exception e)
		{
			logger.error("DeviceUserManagerService::getDeviceUserById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceUserManagerService::getDeviceUserById return deviceUser=" + (StringUtils.isEmpty(deviceUser)?"null":deviceUser.toString()));
		return deviceUser;
	}
	
	/**
	* 通过唯一约束查询设备用户管理信息
	* @param @record
	* @return @DeviceUserManager
	* @throws 
	*/
	public DeviceUserManager getDeviceUserByUniqueKey(DeviceUserManager record) throws RpcServiceException
	{
		DeviceUserManager deviceUser = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getUserFlag()) ||
				StringUtils.isEmpty(record.getFlagType()))
		{
			logger.error("DeviceUserManagerService::getDeviceUserByUniqueKey 参数不能为空");
			return deviceUser;
		}
		logger.info("DeviceUserManagerService::getDeviceUserByUniqueKey param record="+record.toString());
		try
		{
			deviceUser = deviceUserMapper.selectByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceUserManagerService::getDeviceUserByUniqueKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceUserManagerService::getDeviceUserByUniqueKey return deviceUser="+(StringUtils.isEmpty(deviceUser)?"null":deviceUser.toString()));
		return deviceUser;
	}
	
	
	/**
	* 添加设备用户管理信息
	* @param @record
	* @return @DeviceUserManager
	* @throws 
	*/
	public DeviceUserManager addDeviceUser(DeviceUserManager record) throws RpcServiceException
	{
		DeviceUserManager deviceUser = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getUserFlag()) ||
				StringUtils.isEmpty(record.getFlagType()))
		{
			logger.error("DeviceUserManagerService::addDeviceUser 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceUserManagerService::addDeviceUser param record="+record.toString());
		
		try
		{
			deviceUser = getDeviceUserByUniqueKey(record);
		}
		catch(RpcServiceException e)
		{
			logger.error("DeviceUserManagerService::addDeviceUser 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceUser))
		{
			logger.info("DeviceUserManagerService::addDeviceUser return deviceCard="+ deviceUser.toString());
			return deviceUser;
		}
		
		try
		{
			deviceUserMapper.insertSelective(record);
			redisService.setDevUserInfo(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceUserManagerService::addDeviceUser 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceUserManagerService::addDeviceUser return record="+(StringUtils.isEmpty(record)?"null":record.toString()));
		return record;	
	}
	
	
	
}
