package cn.com.glsx.supplychain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.DeviceFileSnapshotMapper;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;

@Service
@Transactional
public class DeviceFileSnapshotService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceFileSnapshotMapper snapshotMapper;
	
	/** 
	* @Title getDeviceFileSnapshotBySn  
	* @Description 根据sn获取设备当前快照
	* @param sn
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot getDeviceFileSnapshotBySn(String sn) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		logger.info("DeviceFileSnapshotService::getDeviceFileSnapshotBySn param sn=" + sn);
		
		try
		{
			deviceFileSnapshot = snapshotMapper.selectByPrimaryKey(sn);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshotService::getDeviceFileSnapshotBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshotService::getDeviceFileSnapshotBySn return deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title updateDeviceFileSnapshotBySn  
	* @Description 根据sn修改设备当前快照(条件更新)
	* @param record
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot updateDeviceFileSnapshotBySn(DeviceFileSnapshot record) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		if(StringUtils.isEmpty(record)||StringUtils.isEmpty(record.getSn()))
		{
			logger.error("DeviceFileSnapshot::updateDeviceFileSnapshotBySn 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileSnapshot::updateDeviceFileSnapshotBySn param=" + record.toString());
		
		try
		{
			snapshotMapper.updateByPrimaryKeySelective(record);
			deviceFileSnapshot = snapshotMapper.selectByPrimaryKey(record.getSn());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::updateDeviceFileSnapshotBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::updateDeviceFileSnapshotBySn return deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title updateAllColDeviceFileSnapshotBySn  
	* @Description 根据sn修改设备当前快照(全更新)
	* @param record
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot updateAllColDeviceFileSnapshotBySn(DeviceFileSnapshot record) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		if(StringUtils.isEmpty(record)||StringUtils.isEmpty(record.getSn()))
		{
			logger.error("DeviceFileSnapshot::updateAllColDeviceFileSnapshotBySn 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileSnapshot::updateAllColDeviceFileSnapshotBySn param=" + record.toString());
		
		try
		{
			snapshotMapper.updateByPrimaryKey(record);
			deviceFileSnapshot = snapshotMapper.selectByPrimaryKey(record.getSn());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::updateAllColDeviceFileSnapshotBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::updateAllColDeviceFileSnapshotBySn return deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title addDeviceFileSnapshotByDuplicateKey  
	* @Description 添加设备当前快照如果存在则修改
	* @param record
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot addDeviceFileSnapshotByDuplicateKey(DeviceFileSnapshot record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()))
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotByDuplicateKey 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceFileSnapshot::addDeviceFileSnapshotByDuplicateKey param record=" + record.toString());
		
		try
		{
			snapshotMapper.insertOnDuplicateKeyUpdate(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotByDuplicateKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::addDeviceFileSnapshotByDuplicateKey return record=" + record.toString());
		return record;
	}
	
	/** 
	* @Title addDeviceFileSnapshotBySn  
	* @Description 添加设备当前快照
	* @param record
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot addDeviceFileSnapshotBySn(DeviceFileSnapshot record) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()))
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotBySn 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceFileSnapshot::addDeviceFileSnapshotBySn param record=" + record.toString());
		
		try
		{
			deviceFileSnapshot = snapshotMapper.selectByPrimaryKey(record.getSn());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceFileSnapshot))
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotBySn 设备关系:" + record.getSn() + "已经存在!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICEFILE_SNAPSHOT_EXIST);
		}
		
		try
		{
			snapshotMapper.insertSelective(record);
			deviceFileSnapshot = snapshotMapper.selectByPrimaryKey(record.getSn());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::addDeviceFileSnapshotBySn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::addDeviceFileSnapshotBySn return deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title getDeviceFileSnapshotByCardId  
	* @Description 检查卡是否绑定在设备上
	* @param cardId
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot getDeviceFileSnapshotByCardId(Integer cardId) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		if(StringUtils.isEmpty(cardId))
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotByCardId 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotByCardId param cardId=" + cardId);
		
		try
		{
			deviceFileSnapshot = snapshotMapper.selectByCardId(cardId);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotByCardId 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotByCardId return  deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title getDeviceFileSnapshotByVehicleId  
	* @Description 检查车上是否绑定在设备
	* @param vehicleId
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public DeviceFileSnapshot getDeviceFileSnapshotByVehicleId(Integer vehicleId) throws RpcServiceException
	{
		DeviceFileSnapshot deviceFileSnapshot = null;
		if(StringUtils.isEmpty(vehicleId))
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotByVehicleId 参数错误");
			return deviceFileSnapshot;
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotByVehicleId param vehicleId=" + vehicleId);
		
		try
		{
			deviceFileSnapshot = snapshotMapper.selectByVehicleId(vehicleId);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotByVehicleId 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotByVehicleId return  deviceFileSnapshot=" + (StringUtils.isEmpty(deviceFileSnapshot)?"null":deviceFileSnapshot.toString()));
		return deviceFileSnapshot;
	}
	
	/** 
	* @Title getDeviceFileSnapshotList 
	* @Description 获取设备快照列表
	* @param record
	* @return List<DeviceFileSnapshot>
	* @throws RpcServiceException
	*/
	public List<DeviceFileSnapshot> getDeviceFileSnapshotList(DeviceFileSnapshot record) throws RpcServiceException
	{
		List<DeviceFileSnapshot> oList = null;
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotList 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotList param record=" + record.toString());
		
		try
		{
			oList = snapshotMapper.listDeviceFileSnapshot(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotList 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotList return oList.size=" + (StringUtils.isEmpty(oList)?"null":oList.size()));
		return oList;
	}
	
	/** 
	* @Title countDeviceFileSnapshot 
	* @Description 统计设备快照列表
	* @param record
	* @return DeviceFileSnapshot
	* @throws RpcServiceException
	*/
	public Integer countDeviceFileSnapshot(DeviceFileSnapshot record) throws RpcServiceException
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(record))
		{
			logger.error("DeviceFileSnapshot::countDeviceFileSnapshot 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileSnapshot::countDeviceFileSnapshot param record=" + record.toString());
		
		try
		{
			ret = snapshotMapper.countDeviceFileSnapshot(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::countDeviceFileSnapshot 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileSnapshot::getDeviceFileSnapshotList return ret="+ret);
		return ret;
	}

	/**
	 * @Title batchAddDeviceFileSnapshot
	 * @Description 批量添加关系设备
	 * @param deviceSnapshotList
	 * @return Snapshotlist
	 * @throws RpcServiceException
	 */
	public void batchAddDeviceFileSnapshot(List<DeviceFileSnapshot> deviceSnapshotList) throws RpcServiceException
	{
		if(StringUtils.isEmpty(deviceSnapshotList) || deviceSnapshotList.size() == 0)
		{
			logger.error("DeviceFileSnapshot::batchAddDeviceFileSnapshot 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileSnapshot::batchAddDeviceFileSnapshot param deviceSnapshotList=" + deviceSnapshotList.size());
		try
		{
			 snapshotMapper.batchInsertOnDuplicateKeyUpdate(deviceSnapshotList);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::batchAddDeviceFileSnapshot 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	
	public List<DeviceFileSnapshot> getDeviceFileSnapshotBySns(List<String> sns) throws RpcServiceException
	{
		if(null == sns || sns.isEmpty()){
			return null;
		}
		try
		{
			return snapshotMapper.getDeviceFileSnapshotBySns(sns);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotBySns 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
    
	public List<DeviceFileSnapshot> getDeviceFileSnapshotByIccids(List<String> iccids) throws RpcServiceException
	{
		if(null == iccids || iccids.isEmpty()){
			return null;
		}
		try
		{
			return snapshotMapper.getDeviceFileSnapshotByIccids(iccids);	
		}
		catch(Exception e)
		{
			logger.error("DeviceFileSnapshot::getDeviceFileSnapshotByIccids 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	public Map<Integer,DeviceFileSnapshot> getDeviceFileSnapshotByCardIds(List<Integer> listCardIds){
		Map<Integer,DeviceFileSnapshot> mapResult = new HashMap<>();
		if(listCardIds == null || listCardIds.isEmpty()){
			return mapResult;
		}
		List<DeviceFileSnapshot> listDeviceFileSnapshot = snapshotMapper.getDeviceFileSnapshotByCardIds(listCardIds);
		if(listDeviceFileSnapshot == null){
			return mapResult;
		}
		for(DeviceFileSnapshot snapshot:listDeviceFileSnapshot){
			mapResult.put(snapshot.getCardId(), snapshot);
		}
		return mapResult;
	}

}
