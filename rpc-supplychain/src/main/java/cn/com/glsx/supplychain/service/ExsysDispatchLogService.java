package cn.com.glsx.supplychain.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.ExsysDispatchLogMapper;
import cn.com.glsx.supplychain.model.DeviceInfoGpsPreimport;
import cn.com.glsx.supplychain.model.ExsysDispatchLog;

@Service
public class ExsysDispatchLogService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExsysDispatchLogMapper exsysDispatchLogMapper;
	@Autowired
	private DeviceInfoGpsPreimportService deviceInfoGpsPreimportService;
	
	/**
	* 
	* @Title: saveExsysDispatchLog 
	* @Description: 
	* @param sn systemflag
	* @return 
	* @throws
	*/
	public Integer saveExsysDispatchLog(ExsysDispatchLog exSysDispatchLog) throws RpcServiceException
	{
		try
		{
			ExsysDispatchLog condition = new ExsysDispatchLog();
			condition.setSystemFlag(exSysDispatchLog.getSystemFlag());
			condition.setSn(exSysDispatchLog.getSn());
			condition.setModuleFlag(exSysDispatchLog.getModuleFlag());
			ExsysDispatchLog result = exsysDispatchLogMapper.selectOne(condition);
			if(!StringUtils.isEmpty(result))
			{
				exSysDispatchLog.setId(result.getId());	
				exsysDispatchLogMapper.updateByPrimaryKeySelective(exSysDispatchLog);
			}
			else
			{
				exsysDispatchLogMapper.insertSelective(exSysDispatchLog);
			}
			
			DeviceInfoGpsPreimport gpsPreImport = new DeviceInfoGpsPreimport();
			if(exSysDispatchLog.getRetCode().equals("505"))
			{
				gpsPreImport.setResult("FA");
				gpsPreImport.setResultDesc("网络不通");
				gpsPreImport.setSn(exSysDispatchLog.getSn());
				gpsPreImport.setOrderCode(exSysDispatchLog.getDispatchNo());
			}
			else
			{
				gpsPreImport.setResult("SU");
				gpsPreImport.setResultDesc(exSysDispatchLog.getRetDesc());
				gpsPreImport.setSn(exSysDispatchLog.getSn());
				gpsPreImport.setOrderCode(exSysDispatchLog.getDispatchNo());
			}
			deviceInfoGpsPreimportService.updateDeviceInfoGpsPerimportStatus(gpsPreImport);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return 0;
	}
	
	
	/**
	* 
	* @Title: delExsysDispatchLog 
	* @Description: 
	* @param sn systemflag
	* @return 
	* @throws
	*/
	public Integer delExsysDispatchLog(ExsysDispatchLog exSysDispatchLog) throws RpcServiceException
	{
		try
		{
			ExsysDispatchLog condition = new ExsysDispatchLog();
			condition.setSystemFlag(exSysDispatchLog.getSystemFlag());
			condition.setSn(exSysDispatchLog.getSn());
			condition.setModuleFlag(exSysDispatchLog.getModuleFlag());
			exsysDispatchLogMapper.delete(condition);
		}
		catch(Exception e)
		{
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return 0;
	}
	
	public ExsysDispatchLog getExsysDispatchLog(ExsysDispatchLog exSysDispatchLog) throws RpcServiceException
	{
		ExsysDispatchLog result = null;
		try
		{
			ExsysDispatchLog condition = new ExsysDispatchLog();
			condition.setSn(exSysDispatchLog.getSn());
			condition.setSystemFlag(exSysDispatchLog.getSystemFlag());
			result = exsysDispatchLogMapper.selectOne(condition);
			if(!StringUtils.isEmpty(result))
			{
				result.setOperatorname(exSysDispatchLog.getOperatorname());
				result.setTimestamp(exSysDispatchLog.getTimestamp());
				result.setRemark(exSysDispatchLog.getRemark());
			}
		}
		catch(Exception e)
		{
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return result;
	}
}
