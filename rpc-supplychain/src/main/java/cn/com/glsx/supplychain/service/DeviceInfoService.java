package cn.com.glsx.supplychain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.model.DeviceInfo;

@Service
@Transactional
public class DeviceInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	public Integer updateDeviceInfoById(DeviceInfo record) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("DeviceInfoService::updateDeviceInfoById start record:{}", record);
		try
		{
			deviceInfoMapper.update(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::updateDeviceInfoById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::updateDeviceInfoById end result:{}",result);
		return result;
	} 
	
	public Integer updateDeviceInfoBySn(DeviceInfo record) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("DeviceInfoService::updateDeviceInfoBySn start record:{}", record);
		try
		{
			deviceInfoMapper.updateSelectBySn(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::updateDeviceInfoBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::updateDeviceInfoBySn end result:{}",result);
		return result;
	}
	
	public DeviceInfo getDeviceInfoBySn(String sn) throws RpcServiceException
	{
		DeviceInfo deviceInfo = null;
		logger.info("DeviceInfoService::getDeviceInfoBySn start sn:" + sn);
		try
		{
			deviceInfo = deviceInfoMapper.getDeviceInfoBySn(sn);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::getDeviceInfoBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getDeviceInfoBySn end deviceInfo:{}",deviceInfo);
		return deviceInfo;
	}
	
	public DeviceInfo getDeviceInfoByIccid(String iccid) throws RpcServiceException
	{
		DeviceInfo deviceInfo = null;
		logger.info("DeviceInfoService::getDeviceInfoByIccid start iccid:" + iccid);
		try
		{
			deviceInfo = deviceInfoMapper.getDeviceInfoByIccid(iccid);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::getDeviceInfoByIccid 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getDeviceInfoByIccid end deviceInfo:{}",deviceInfo);
		return deviceInfo;
	}
	
	public DeviceInfo getDeviceInfoByImei(String imei) throws RpcServiceException
	{
		DeviceInfo deviceInfo = null;
		logger.info("DeviceInfoService::getDeviceInfoByImei start imei:" + imei);
		try
		{
			deviceInfo = deviceInfoMapper.getDeviceInfoByImei(imei);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::getDeviceInfoByImei 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getDeviceInfoByImei end deviceInfo:{}",deviceInfo);
		return deviceInfo;
	}
	
	public DeviceInfo addDeviceInfoOnDuplicateKey(DeviceInfo record) throws RpcServiceException
	{
		DeviceInfo deviceInfo = null;
		logger.info("DeviceInfoService::addDeviceInfoOnDuplicateKey start record:{}", record);
		try
		{
			deviceInfoMapper.addDeviceInfoOnDuplicateKey(record);
			deviceInfo = deviceInfoMapper.getDeviceInfoBySn(record.getSn());
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::addDeviceInfoOnDuplicateKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::addDeviceInfoOnDuplicateKey end deviceInfo:{}",deviceInfo);
		return deviceInfo;
	}
	
	public void batchAddDeviceInfoOnDuplicateKey(List<DeviceInfo> deviceInfoList) throws RpcServiceException
	{
		if(StringUtils.isEmpty(deviceInfoList) || deviceInfoList.size() == 0)
		{
			return;
		}
		logger.info("DeviceInfoService::batchAddDeviceInfoOnDuplicateKey start deviceInfoList.size:{}", deviceInfoList.size());
		try
		{
			deviceInfoMapper.batchAddDeviceInfoOnDuplicateKey(deviceInfoList);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::batchAddDeviceInfoOnDuplicateKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::batchAddDeviceInfoOnDuplicateKey end");
	}
	
	public Integer getTotalAttrib(DeviceInfo record) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("DeviceInfoService::getTotalAttrib start record:{}", record);
		try
		{
			result = deviceInfoMapper.getTotalAttrib(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::getTotalAttrib 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getTotalAttrib end result:" + result);
		return result;
	}
	
	public Integer getTotalAttribDeviceInfos(DeviceInfo record) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("DeviceInfoService::getTotalAttribDeviceInfos start record:{}", record);
		try
		{
			record.setDeletedFlag("N");
			result = deviceInfoMapper.getTotalAttribDeviceInfos(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::getTotalAttribDeviceInfos 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getTotalAttribDeviceInfos end result:" + result);
		return result;
	}
	
	public Page<DeviceInfo> pageStatAttrib(DeviceInfo record) throws RpcServiceException
	{
		Page<DeviceInfo> result;
		logger.info("DeviceInfoService::pageStatAttrib start record:{}", record);
		try
		{
			result = deviceInfoMapper.pageStatAttrib(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::pageStatAttrib 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::getTotalAttrib end result:{}",result);
		return result;
	}
	
	public Page<DeviceInfo> pageStatAttribDeviceInfos(DeviceInfo record) throws RpcServiceException
	{
		Page<DeviceInfo> result;
		logger.info("DeviceInfoService::pageStatAttribDeviceInfos start record:{}", record);
		try
		{
			record.setDeletedFlag("N");
			result = deviceInfoMapper.pageStatAttribDeviceInfos(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::pageStatAttribDeviceInfos 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::pageStatAttribDeviceInfos end result:{}",result);
		return result;
	}
	
	public List<DeviceInfo> listExportAttribDeviceInfos(DeviceInfo record) throws RpcServiceException
	{
		List<DeviceInfo> result;
		logger.info("DeviceInfoService::listExportAttribDeviceInfos start record:{}", record);
		try
		{
			record.setDeletedFlag("N");
			result = deviceInfoMapper.listExportAttribDeviceInfos(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::listExportAttribDeviceInfos 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceInfoService::pageStatAttribDeviceInfos end result.size:" + result.size());
		return result;
	}
	
	public List<DeviceInfo> listDeviceInfoBySn(List<String> sns) throws RpcServiceException
	{
		try
		{
			return deviceInfoMapper.listDeviceInfoBySn(sns);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::listDeviceInfoBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	public List<DeviceInfo> listDeviceInfoByIccids(List<String> iccids) throws RpcServiceException
	{
		try
		{
			return deviceInfoMapper.listDeviceInfoByIccids(iccids);
		}
		catch(Exception e)
		{
			logger.error("DeviceInfoService::listDeviceInfoByIccids 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
}
