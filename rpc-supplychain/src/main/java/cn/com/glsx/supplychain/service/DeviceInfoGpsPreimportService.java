package cn.com.glsx.supplychain.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.DeviceInfoGpsPreimportMapper;
import cn.com.glsx.supplychain.model.DeviceInfoGpsPreimport;

@Service
@Transactional
public class DeviceInfoGpsPreimportService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceInfoGpsPreimportMapper gpsPreimportMapper;
	
	public Integer batchAddDeviceInfoGpsPerimport(List<DeviceInfoGpsPreimport> gpsDeviceList) throws RpcServiceException
	{
		try
		{
			return gpsPreimportMapper.batchAddDeviceInfoGpsPerimport(gpsDeviceList);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Page<DeviceInfoGpsPreimport> pageDeviceInfoGpsPerimport(int pageNum, int pageSize,DeviceInfoGpsPreimport gpsDevice) throws RpcServiceException
	{
		try
		{
			PageHelper.startPage(pageNum, pageSize);
			return gpsPreimportMapper.pageDeviceInfoGpsPreImport(gpsDevice);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Integer updateDeviceInfoGpsPerimportStatus(DeviceInfoGpsPreimport record) throws RpcServiceException
	{
		Integer result = 0;
		if(StringUtils.isEmpty(record.getSn()) ||
				StringUtils.isEmpty(record.getOrderCode()) ||
				StringUtils.isEmpty(record.getResult()) ||
				StringUtils.isEmpty(record.getResultDesc()))
		{
			return result;
		}
		try
		{
			gpsPreimportMapper.updateDeviceInfoGpsPreImportStatu(record);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
		return result;
	}
}
