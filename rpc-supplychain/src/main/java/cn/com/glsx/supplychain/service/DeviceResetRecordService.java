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
import cn.com.glsx.supplychain.mapper.DeviceResetRecordMapper;
import cn.com.glsx.supplychain.model.DeviceResetRecord;

/**
 * @Title: DeviceUpdateRecordService
 * @Description: 设备初始化服务
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class DeviceResetRecordService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceResetRecordMapper resetRecordMapper;
	
	/** 
	* @Title: pageDeviceResetRecord 
	* @Description:设备初始化记录列表
	* @param:  pagination,record
	* @return: Page<DeviceResetRecord>
	* @throws: RpcServiceException
	*/
	public Page<DeviceResetRecord> pageDeviceResetRecord(RpcPagination<DeviceResetRecord> pagination,DeviceResetRecord record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(pagination) || StringUtils.isEmpty(record))
		{
			logger.error("DeviceResetRecordService::pageDeviceResetRecord 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceResetRecordService::pageDeviceResetRecord param pagination.getPageNum=" + pagination.getPageNum() + " pagination.getPageSize=" + pagination.getPageSize() + " record="+record.toString());	
		
		Page<DeviceResetRecord> oPage = null;
		try
		{
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			oPage = resetRecordMapper.pageDeviceResetRecord(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceResetRecordService::pageDeviceResetRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceResetRecordService::pageDeviceResetRecord return oPage=" + (StringUtils.isEmpty(oPage)?"null":oPage.toString()));
		
		return oPage;
	}
	
	/** 
	* @Title: exportDeviceResetRecord 
	* @Description:设备初始化记录列表导出
	* @param:  record
	* @return: List<DeviceUpdateRecord>
	* @throws: RpcServiceException
	*/
	public List<DeviceResetRecord> exportDeviceResetRecord(DeviceResetRecord record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceResetRecordService::exportDeviceResetRecord 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceResetRecordService::exportDeviceResetRecord param record="+record.toString());
		
		List<DeviceResetRecord> oList = null;
		
		try
		{
			oList = resetRecordMapper.exportDeviceResetRecord(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceResetRecordService::exportDeviceResetRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceResetRecordService::exportDeviceResetRecord return oList.size=" + oList.size());
		
		return oList;
	}
	
	/** 
	* @Title: addDeviceResetRecord 
	* @Description:添加设备重置记录
	* @param:  record
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public DeviceResetRecord addDeviceResetRecord(DeviceResetRecord record) throws RpcServiceException
	{
		DeviceResetRecord deviceResetRecord = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()) || StringUtils.isEmpty(record.getRemark()))
		{
			logger.error("DeviceResetRecordService::addDeviceResetRecord 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceResetRecordService::addDeviceResetRecord param record=" + record.toString());
		
		try
		{
			resetRecordMapper.insertSelective(record);
			deviceResetRecord = resetRecordMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceResetRecordService::addDeviceResetRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceResetRecordService::addDeviceResetRecord return deviceResetRecord=" + (StringUtils.isEmpty(deviceResetRecord)?"null":deviceResetRecord.toString()));
		return deviceResetRecord;
	}
	
	/** 
	* @Title: updateDeviceResetRecord 
	* @Description:修改设备重置记录
	* @param:  record
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public DeviceResetRecord updateDeviceResetRecord(DeviceResetRecord record) throws RpcServiceException
	{
		DeviceResetRecord deviceResetRecord = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()))
		{
			logger.error("DeviceResetRecordService::updateDeviceResetRecord 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceResetRecordService::updateDeviceResetRecord param record=" + record.toString());
		
		try
		{
			resetRecordMapper.updateByPrimaryKeySelective(record);
			deviceResetRecord = resetRecordMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceResetRecordService::updateDeviceResetRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceResetRecordService::updateDeviceResetRecord return deviceResetRecord=" + (StringUtils.isEmpty(deviceResetRecord)?"null":deviceResetRecord.toString()));
		return deviceResetRecord;
	}
}
