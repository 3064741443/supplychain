package cn.com.glsx.supplychain.service;

import java.util.List;

import com.glsx.platform.goods.common.service.ServicePackageService;
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
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceCodeMapper;
import cn.com.glsx.supplychain.model.DeviceCode;

@Service
@Transactional
public class DeviceCodeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceCodeMapper deviceCodeMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;

	/**
	* @Title pageDeviceCode  
	* @Description 设备分类(小类)列表(带分页)
	* @param pagination，deviceCode
	* @return Page<DeviceCode>
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public Page<DeviceCode> pageDeviceCode(RpcPagination<DeviceCode> pagination,DeviceCode deviceCode)throws RpcServiceException
	{
		if(StringUtils.isEmpty(pagination) || StringUtils.isEmpty(deviceCode))
		{
			logger.error("DeviceCodeService::pageDeviceCode 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceCodeService::pageDeviceCode param pagination.getPageNum=" + pagination.getPageNum() + " pagination.getPageSize=" + pagination.getPageSize() + " deviceCode=" + deviceCode.toString());
		
		Page<DeviceCode> oPage = null;
		
		try
		{
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			oPage = deviceCodeMapper.pageDeviceCode(deviceCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::pageDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCodeService::pageDeviceCode return oPage.size=" + (StringUtils.isEmpty(oPage)?"null":oPage.size()));
		return oPage;
	}
	
	/** 
	* @Title listDeviceCode  
	* @Description 设备分类(小类)列表
	* @param  deviceCode
	* @return List<DeviceCode>
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public List<DeviceCode> listDeviceCode(DeviceCode deviceCode) throws RpcServiceException
	{
		if(StringUtils.isEmpty(deviceCode))
		{
			logger.error("DeviceCodeService::listDeviceCode 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCodeService::listDeviceCode param deviceCode=" + deviceCode.toString());
		
		List<DeviceCode> oList = null;	
		try
		{
			oList = deviceCodeMapper.listDeviceCode(deviceCode);
				
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::listDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCodeService::listDeviceCode return oList.size=" + (StringUtils.isEmpty(oList)?"null":oList.size()));
		return oList;
	}
	
	/** 
	* @Title listDeviceCodeByIds  
	* @Description 根据ID集合获取设备分类(小类)列表
	* @param  deviceCode
	* @return List<DeviceCode>
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public List<DeviceCode> listDeviceCodeByIds(List<Integer> ids) throws RpcServiceException
	{
		List<DeviceCode> oList = null;
		if(StringUtils.isEmpty(ids) || ids.size() == 0)
		{
			logger.info("DeviceCodeService::listDeviceCodeByIds ids=null and return null");
			return oList;
		}
		logger.info("DeviceCodeService::listDeviceCodeByIds param ids=" + ids.toString());
		
		try
		{
			oList = deviceCodeMapper.listDeviceCodeByIds(ids);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::listDeviceCodeByIds 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCodeService::listDeviceCodeByIds return oList.size=" + (StringUtils.isEmpty(oList)?"":oList.size()));
		return oList;
	}
	
	/** 
	* @Title exportDeviceCode  
	* @Description 导出设备分类列表(小类)
	* @param record
	* @return List<DeviceCode>
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public List<DeviceCode> exportDeviceCode(DeviceCode record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceCodeService::exportDeviceCode 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCodeService::exportDeviceCode param record="+record.toString());
		
		List<DeviceCode> oList = null;
		
		try
		{
			oList = deviceCodeMapper.exportDeviceCode(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::exportDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCodeService::listDeviceCode return oList=" + (StringUtils.isEmpty(oList)?"null":oList.toString()));
		return oList;
	}
	
	/** 
	* @Title getDeviceCode  
	* @Description 通过deviceCode查询
	* @param code
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode getDeviceCodeByDeviceCode(Integer code) throws RpcServiceException
	{
		DeviceCode deviceCode = null;
		if(StringUtils.isEmpty(code))
		{
			logger.error("DeviceCodeService::getDeviceCodeByDeviceCode code is null and return null");
			return deviceCode;
		}
		
		logger.info("DeviceCodeService::getDeviceCodeByDeviceCode param code=" + code);
		deviceCode = redisService.getDeviceCode(code);
		
		if(!StringUtils.isEmpty(deviceCode))
		{
			logger.info("DeviceCodeService::getDeviceCodeByDeviceCode return deviceCode=" + deviceCode.toString());
			return deviceCode;
		}
		
		try
		{
			DeviceCode record = new DeviceCode();
			record.setDeviceCode(code);
			deviceCode = deviceCodeMapper.selectByUniqueKey(record);
			redisService.setDeviceCode(deviceCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::getDeviceCodeByDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCodeService::getDeviceCodeByDeviceCode return deviceCode" + (StringUtils.isEmpty(deviceCode) ? "null":deviceCode.toString()));
		return deviceCode;
	}
	
	/** 
	* @Title getDeviceCodeById  
	* @Description 通过id查询
	* @param id
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode getDeviceCodeById(Integer id) throws RpcServiceException
	{
		DeviceCode deviceCode = null;
		logger.info("DeviceCodeService::getDeviceCodeById param id=" + id);
		try
		{
			deviceCode = deviceCodeMapper.selectByPrimaryKey(id);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::getDeviceCodeById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCodeService::getDeviceCodeById return deviceCode" + (StringUtils.isEmpty(deviceCode) ? "null":deviceCode.toString()));
		return deviceCode;
	}
	
	/** 
	* @Title getDeviceCodeByManufacturerCode  
	* @Description 通过厂商码获取到设备编码信息
	* @param  manufacturerCode
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode getDeviceCodeByManufacturerCode(String manufacturerCode) throws RpcServiceException
	{
		DeviceCode deviceCode = null;
		logger.info("DeviceCodeService::getDeviceCodeByManufacturerCode param manufacturerCode=" + manufacturerCode);
		if(StringUtils.isEmpty(manufacturerCode))
		{
			return deviceCode;
		}
		
		try
		{
			deviceCode = deviceCodeMapper.selectByManufacturerCode(manufacturerCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::getDeviceCodeByManufacturerCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCodeService::getDeviceCodeByManufacturerCode return deviceCode" + (StringUtils.isEmpty(deviceCode) ? "null":deviceCode.toString()));
		return deviceCode;
	}
	
	/** 
	* @Title addDeviceCode  
	* @Description 设备分类添加和修改
	* @param record
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode addDeviceCode(DeviceCode record)throws RpcServiceException
	{
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getDeviceName())||
			StringUtils.isEmpty(record.getMerchantId()) || StringUtils.isEmpty(record.getTypeId()))
		{
			logger.error("DeviceCodeService::addDeviceCode 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCodeService::addDeviceCode param record="+record.toString());
		
		DeviceCode deviceCode = null;
		//判断设备是否已经存在
		try
		{
			deviceCode = deviceCodeMapper.selectByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::addDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceCode))
		{
			logger.info("DeviceCodeService::addDeviceCode 新增设备分类:" + deviceCode.getDeviceName() + " 已经存在");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICE_DEVICENAME_ALREADY_USED);
		}
		
		try
		{
			record.setDeviceCode(999999999);
			deviceCodeMapper.insertSelective(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::addDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		deviceCode = new DeviceCode();
		deviceCode.setId(record.getId());
		deviceCode.setDeviceCode(record.getId());
		
		try
		{
			deviceCodeMapper.updateByPrimaryKeySelective(deviceCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::addDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		record.setDeviceCode(record.getId());
		redisService.setDeviceCode(record);
		
		logger.info("DeviceCodeService::addDeviceCode return record=" + record.toString());
		
		return record;
	}
	
	/** 
	* @Title updateDeviceCode  
	* @Description 设备分类修改
	* @param record
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode updateDeviceCode(DeviceCode record)throws RpcServiceException
	{
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()))
		{
			logger.error("DeviceCodeService::updateDeviceCode 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCodeService::updateDeviceCode param record="+record.toString());
		
		DeviceCode deviceCode = null;
		
		if(record.getDeletedFlag().equals("Y")){
			
			try
			{
				deviceCode = delDeviceCode(record);
			}
			catch(Exception e)
			{
				logger.error("DeviceCodeService::updateDeviceCode 调用方法执行出错");
				throw e;
			}
			
			return deviceCode;
		}
		
		try
		{
			deviceCodeMapper.updateByPrimaryKeySelective(record);
			deviceCode = deviceCodeMapper.selectByPrimaryKey(record.getId());
			redisService.setDeviceCode(deviceCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::updateDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCodeService::updateDeviceCode return deviceCode=" + deviceCode.toString());
		return deviceCode;
	}
	
	/** 
	* @Title delDeviceCode  
	* @Description 设备分类删除
	* @param record
	* @return DeviceCode
	* @throws RpcServiceException
	* @author QL.QuanLiu
	*/
	public DeviceCode delDeviceCode(DeviceCode record)throws RpcServiceException
	{
		DeviceCode deviceCode = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getDeletedFlag())||
				!record.getDeletedFlag().equals("Y"))
		{
			logger.error("DeviceCodeService::delDeviceCode 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCodeService::delDeviceCode param record=" + record.toString()); 
		
		try
		{
			deviceCodeMapper.updateByPrimaryKeySelective(record);
			deviceCode = deviceCodeMapper.selectByPrimaryKey(record.getId());
			redisService.delDeviceCode(deviceCode.getDeviceCode());
		}
		catch(Exception e)
		{
			logger.error("DeviceCodeService::delDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCodeService::delDeviceCode return deviceCode=" + deviceCode.toString());
		
		return deviceCode;
	}
}
