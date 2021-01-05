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
import cn.com.glsx.supplychain.mapper.DeviceCompanyManagerMapper;
import cn.com.glsx.supplychain.model.DeviceCompanyManager;

@Service
@Transactional
public class DeviceCompanyManagerService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceCompanyManagerMapper deviceCompanyMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	/**
	* 获取通过id获取合作公司信息
	* @param @id 
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceCompanyManager getDeviceCompanyById(Integer id) throws RpcServiceException
	{
		if(id==null)
		{
			logger.error("DeviceCompanyManagerService::getDeviceCompanyById 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCompanyManagerService::getDeviceCompanyById param id="+id);
		
		DeviceCompanyManager deviceCompany = null;
		
		String companyName = redisService.getDevCompany(id);
		if(!StringUtils.isEmpty(companyName))
		{
			deviceCompany = new DeviceCompanyManager();
			deviceCompany.setId(id);
			deviceCompany.setName(companyName);
			logger.info("DeviceCompanyManagerService::getDeviceCompanyById return deviceCompany=" + deviceCompany.toString());
			return deviceCompany;
		}
		
		try
		{
			deviceCompany = deviceCompanyMapper.selectByPrimaryKey(id);
			redisService.setDevCompany(deviceCompany.getId(), deviceCompany.getName());
		}
		catch(Exception e)
		{
			logger.error("DeviceCompanyManagerService::getDeviceCompanyById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCompanyManagerService::getDeviceCompanyById return deviceCompany=" + (StringUtils.isEmpty(deviceCompany)?"null":deviceCompany.toString()));
		return deviceCompany;
	}
	
	/**
	* 通过唯一约束查询合作公司信息
	* @param @record
	* @return @DeviceCompanyManager
	* @throws 
	*/
	public DeviceCompanyManager getDeviceCompanyByUniqueKey(DeviceCompanyManager record) throws RpcServiceException
	{
		DeviceCompanyManager deviceCompany = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getName()))
		{
			logger.error("DeviceCompanyManagerService::getDeviceCompanyByUniqueKey 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCompanyManagerService::getDeviceCompanyByUniqueKey param record="+record.toString());
		try
		{
			deviceCompany = deviceCompanyMapper.selectByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCompanyManagerService::getDeviceCompanyByUniqueKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCompanyManagerService::getDeviceCompanyByUniqueKey return deviceCompany="+(StringUtils.isEmpty(deviceCompany)?"null":deviceCompany.toString()));
		return deviceCompany;
	}
	
	
	/**
	* 添加合作公司信息
	* @param @record
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceCompanyManager addDeviceCompany(DeviceCompanyManager record) throws RpcServiceException
	{
		DeviceCompanyManager deviceCompany = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getName()))
		{
			logger.error("DeviceCompanyManagerService::addDeviceCompany 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCompanyManagerService::addDeviceCompany param record="+record.toString());
		
		try
		{
			deviceCompany = getDeviceCompanyByUniqueKey(record);
		}
		catch(RpcServiceException e)
		{
			logger.error("DeviceCompanyManagerService::addDeviceCompany 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceCompany))
		{
			logger.info("DeviceCompanyManagerService::addDeviceCompany return deviceCompany="+ deviceCompany.toString());
			return deviceCompany;
		}
		
		try
		{
			deviceCompanyMapper.insertSelective(record);
			redisService.setDevCompany(record.getId(), record.getName());
		}
		catch(RpcServiceException e)
		{
			logger.error("DeviceCompanyManagerService::addDeviceCompany 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCompanyManagerService::addDeviceCompany return record="+(StringUtils.isEmpty(record)?"null":record.toString()));
		return record;	
	}
}
