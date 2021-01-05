package cn.com.glsx.supplychain.service;

import java.util.Date;
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
import cn.com.glsx.supplychain.mapper.DeviceUpdateRecordMapper;
import cn.com.glsx.supplychain.mapper.DeviceUpdateRecordPresnapshotMapper;
import cn.com.glsx.supplychain.model.DeviceUpdateRecord;
import cn.com.glsx.supplychain.model.DeviceUpdateRecordPresnapshot;

/**
 * @Title: DeviceUpdateRecordService
 * @Description: 设备更换记录
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class DeviceUpdateRecordService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceUpdateRecordMapper updateRecordMapper;
	
	@Autowired
	private DeviceUpdateRecordPresnapshotMapper updateRecordSnapshotMapper;
	
	/** 
	* @Title: pageDeviceUpdateRecord 
	* @Description:设备更换记录列表
	* @param:  pagination,record
	* @return: Page<DeviceUpdateRecord>
	* @throws: RpcServiceException
	*/
	public Page<DeviceUpdateRecord> pageDeviceUpdateRecord(RpcPagination<DeviceUpdateRecord> pagination,DeviceUpdateRecord record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(pagination) || StringUtils.isEmpty(record))
		{
			logger.error("DeviceUpdateRecordService::pageDeviceUpdateRecord 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceUpdateRecordService::pageDeviceUpdateRecord param pagination.getPageNum=" + pagination.getPageNum() +" pagination.getPageSize=" + pagination.getPageSize()+ " record="+record.toString());	
		
		Page<DeviceUpdateRecord> oPage = null;
		try
		{
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			oPage = updateRecordMapper.pageDeviceUpdateRecord(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::pageDeviceUpdateRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceUpdateRecordService::pageDeviceUpdateRecord return oPage=" + (StringUtils.isEmpty(oPage)?"null":oPage.toString()));
		
		return oPage;
	}
	
	/** 
	* @Title: exportDeviceUpdateRecord 
	* @Description:设备更换记录列表导出
	* @param:  record
	* @return: List<DeviceUpdateRecord>
	* @throws: RpcServiceException
	*/
	public List<DeviceUpdateRecord> exportDeviceUpdateRecord(DeviceUpdateRecord record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceUpdateRecordService::exportDeviceUpdateRecord 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceUpdateRecordService::exportDeviceUpdateRecord param record="+record.toString());
		
		List<DeviceUpdateRecord> oList = null;
		
		try
		{
			oList = updateRecordMapper.exportDeviceUpdateRecord(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::exportDeviceUpdateRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceUpdateRecordService::exportDeviceUpdateRecord return oList.size=" + oList.size());
		
		return oList;
	}
	
	/** 
	* @Title: exportDeviceUpdateRecord 
	* @Description:设备更换记录列表导出
	* @param:  record
	* @return: List<DeviceUpdateRecord>
	* @throws: RpcServiceException
	*/
	public List<DeviceUpdateRecord> listDeviceUpdateRecord(DeviceUpdateRecord record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceUpdateRecordService::listDeviceUpdateRecord 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceUpdateRecordService::listDeviceUpdateRecord param record="+record.toString());
		
		List<DeviceUpdateRecord> oList = null;
		
		try
		{
			oList = updateRecordMapper.listDeviceUpdateRecord(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::listDeviceUpdateRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceUpdateRecordService::listDeviceUpdateRecord return oList.size=" + oList.size());
		
		return oList;
	}
	
	/** 
	* @Title: addDeviceUpdateRecord 
	* @Description:添加设备重置记录
	* @param:  record
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public DeviceUpdateRecord addDeviceUpdateRecord(DeviceUpdateRecord record) throws RpcServiceException
	{
		DeviceUpdateRecord deviceUpdateRecord = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()) || StringUtils.isEmpty(record.getFlagType()))
		{
			logger.error("DeviceUpdateRecordService::addDeviceUpdateRecord 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceUpdateRecordService::addDeviceUpdateRecord param record=" + record.toString());
		
		try
		{
			updateRecordMapper.insertSelective(record);
			deviceUpdateRecord = updateRecordMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::addDeviceUpdateRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceUpdateRecordService::addDeviceUpdateRecord return deviceUpdateRecord=" + (StringUtils.isEmpty(deviceUpdateRecord)?"null":deviceUpdateRecord.toString()));
		return deviceUpdateRecord;
	}
	
	/** 
	* @Title:  updateDeviceUpdateRecord 
	* @Description:修改设备重置记录
	* @param:  record
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public DeviceUpdateRecord updateDeviceUpdateRecord(DeviceUpdateRecord record) throws RpcServiceException
	{
		DeviceUpdateRecord deviceUpdateRecord = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()))
		{
			logger.error("DeviceUpdateRecordService::updateDeviceUpdateRecord 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceUpdateRecordService::updateDeviceUpdateRecord param record=" + record.toString());
		
		try
		{
			updateRecordMapper.updateByPrimaryKeySelective(record);
			deviceUpdateRecord = updateRecordMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::updateDeviceUpdateRecord 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceUpdateRecordService::updateDeviceUpdateRecord return deviceUpdateRecord=" + (StringUtils.isEmpty(deviceUpdateRecord)?"null":deviceUpdateRecord.toString()));
		return deviceUpdateRecord;
	}
	
	/** 
	* @Title:  batchInsertOnDuplicateKeyUpdate 
	* @Description:批量插入修改记录
	* @param:  
	* @return: 
	* @throws: RpcServiceException
	*/
	/*public void batchInsertOnDuplicateKeyUpdate(List<DeviceUpdateRecord> listRecord) throws RpcServiceException
	{
		if(StringUtils.isEmpty(listRecord))
		{
			logger.error("DeviceUpdateRecordService::batchInsertOnDuplicateKeyUpdate 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceUpdateRecordService::batchInsertOnDuplicateKeyUpdate listRecord{}" + listRecord);
		if(listRecord.size() == 0)
		{
			return;
		}
		
		try
		{
			updateRecordMapper.batchInsertOnDuplicateKeyUpdate(listRecord);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("DeviceUpdateRecordService::batchInsertOnDuplicateKeyUpdate 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}*/
	
	/** 
	* @Title:  setDeviceUpdateRecord 
	* @Description:添加设置修改记录
	* @param:  record
	* @return: DeviceResetRecord
	* @throws: RpcServiceException
	*/
	public void setDeviceUpdateRecord(String deviceSn,String flagType,Integer updateId,String strOperator) throws RpcServiceException {
		
		logger.info("DeviceUpdateRecordService::setDeviceUpdateRecord start sn=" + deviceSn);
		
		if(StringUtils.isEmpty(deviceSn) || StringUtils.isEmpty(flagType) || StringUtils.isEmpty(updateId) || updateId == 0)
		{
			logger.error("DeviceUpdateRecordService::setDeviceUpdateRecord param invalid");
			return;
		}
		
		logger.info("DeviceUpdateRecordService::setDeviceUpdateRecord deviceSn=" + " flagType=" + flagType + " updateId=" + updateId);
		
		try
		{
			Date timeDate = new Date();
			DeviceUpdateRecordPresnapshot preCondition = new DeviceUpdateRecordPresnapshot();
			preCondition.setSn(deviceSn);
			preCondition.setFlagType(flagType);
			DeviceUpdateRecordPresnapshot preSnapshot= updateRecordSnapshotMapper.selectByUniqueKey(preCondition);
			
			logger.info("DeviceUpdateRecordService::setDeviceUpdateRecord preSnapshot=" + (StringUtils.isEmpty(preSnapshot)?"null":preSnapshot.toString()));
			
			if(StringUtils.isEmpty(preSnapshot))
			{
				preCondition.setPreFlagId(updateId);
				updateRecordSnapshotMapper.insert(preCondition);
			}
			else
			{
				if(updateId.equals(preSnapshot.getPreFlagId()))
				{
					return;
				}
				
				//生成修改记录插入到记录表中
				DeviceUpdateRecord updateRecord = new DeviceUpdateRecord();
				updateRecord.setSn(deviceSn);
				updateRecord.setPreFlagId(preSnapshot.getPreFlagId());
				updateRecord.setFlagId(updateId);
				updateRecord.setFlagType(flagType);
				updateRecord.setCreatedBy(strOperator);
				updateRecord.setUpdatedBy(strOperator);
				updateRecord.setCreatedDate(timeDate);
				updateRecord.setUpdatedDate(timeDate);
				updateRecord.setDeletedFlag("N");
				updateRecordMapper.insert(updateRecord);	
				
				//修改修改前记录快照表
				preSnapshot.setPreFlagId(updateId);
				updateRecordSnapshotMapper.updateByPrimaryKeySelective(preSnapshot);
			}
	        
		}
		catch(Exception e)
		{
			logger.error("DeviceUpdateRecordService::setDeviceUpdateRecord 数据库操作数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceUpdateRecordService::setDeviceUpdateRecord return ok!");
    }
	
	
}
