package cn.com.glsx.supplychain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.DeviceTypeRedisManager;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceTypeMapper;
import cn.com.glsx.supplychain.model.DeviceType;


/**
 * @Title: DeviceTypeService.java
 * @Description: 设备大类service
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class DeviceTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceTypeMapper deviceTypeMapper;
	
	@Autowired
	private DeviceTypeRedisManager deviceTypeRedis;
	
	
	/** 
	 * @Title: getDeviceTypeNameById  
	 * @Description:根据ID获取设备类型名称
	 * @param: id
	 * @return: String
	 * @throws: RpcServiceException
	 */
	public String getDeviceTypeNameById(Integer id) throws RpcServiceException
	{
		if(id==null)
		{
			logger.error("DeviceTypeService::getDeviceTypeNameById 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceTypeService::getDeviceTypeNameById param id="+id);
		
		String typeName = deviceTypeRedis.getDeviceTypeById(id);
		
		if(!StringUtils.isEmpty(typeName))
		{
			logger.info("DeviceTypeService::getDeviceTypeNameById return typeName="+typeName);
			return typeName;
		}
		
		try
		{
			DeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(id);
			if(!StringUtils.isEmpty(deviceType))
			{
				typeName = deviceType.getName();
				deviceTypeRedis.setDeviceTypeById(deviceType);			
			}
		}
		catch(Exception e)
		{
			logger.error("DeviceTypeService::getDeviceTypeNameById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceTypeService::getDeviceTypeNameById return typeName="+typeName);
		return typeName;
	}
	
	/**
	* @Title:pageDeviceType
	* @Description:获取设备大类列表(带分页)
	* @param: pagination,record
	* @return:Page<DeviceType>
	* @throws: RpcServiceException
	*/
	public Page<DeviceType> pageDeviceType(RpcPagination<DeviceType> pagination,DeviceType record)throws RpcServiceException
	{
		if(StringUtils.isEmpty(pagination) || StringUtils.isEmpty(record)){
			
			logger.error("DeviceTypeService::pageDeviceType 传入参数错误！");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceTypeService::pageDeviceType param pagination.getPageNum="+pagination.getPageNum() + " pagination.getPageSize=" + pagination.getPageSize() +" record="+record.toString());
		
		Page<DeviceType> page = null;
		
		try
		{
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			page = deviceTypeMapper.pageDeviceType(record); 
		}
		catch(Exception e)
		{
			logger.error("DeviceTypeService::pageDeviceType 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceTypeService::pageDeviceType return page.size="+(StringUtils.isEmpty(page)?"null":page.size()));
		return page;
	}
	
	/**
	* @Title:listDeviceType
	* @Description:获取设备大类列表
	* @param: pagination,record
	* @return:Page<DeviceType>
	* @throws: RpcServiceException
	*/
	public List<DeviceType> listDeviceType(DeviceType record)throws RpcServiceException
	{
		if(StringUtils.isEmpty(record)){
			
			logger.error("DeviceTypeService::listDeviceType 传入参数错误！");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceTypeService::listDeviceType param pagination record="+record.toString());
		
		List<DeviceType> oList = null;
		
		try
		{
			oList = deviceTypeMapper.listDeviceType(record); 
		}
		catch(Exception e)
		{
			logger.error("DeviceTypeService::listDeviceType 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceTypeService::listDeviceType return oList.size="+(StringUtils.isEmpty(oList)?"null":oList.size()));
		return oList;
	}
	
}
