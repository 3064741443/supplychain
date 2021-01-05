package cn.com.glsx.supplychain.service;

import java.util.ArrayList;
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
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceCardManagerMapper;
import cn.com.glsx.supplychain.model.DeviceCardManager;

@Service
@Transactional
public class DeviceCardManagerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceCardManagerMapper deviceCardMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	/**
	* 获取通过id设备卡管理信息
	* @param @id 
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceCardManager getDeviceCardById(Integer id) throws RpcServiceException
	{
		DeviceCardManager deviceCard = null;
		if(id==null)
		{
			logger.error("DeviceCardManagerService::getDeviceCardById 参数不能为空");
			return deviceCard;
		}
		logger.info("DeviceCardManagerService::getDeviceCardById param id="+id);
		
		deviceCard = redisService.getDevCardInfo(id);
		if(!StringUtils.isEmpty(deviceCard))
		{
			logger.info("DeviceCardManagerService::getDeviceCardById return deviceCard=" + deviceCard.toString());
			return deviceCard;
		}
		
		try
		{
			deviceCard = deviceCardMapper.selectByPrimaryKey(id);
			redisService.setDevCardInfo(deviceCard);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::getDeviceCardById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCardManagerService::getDeviceCardById return deviceCard=" + (StringUtils.isEmpty(deviceCard)?"null":deviceCard.toString()));
		return deviceCard;
	}
	
	/**
	* 通过唯一约束查询设备卡管理信息
	* @param @record
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceCardManager getDeviceCardByUniqueKey(DeviceCardManager record) throws RpcServiceException
	{
		DeviceCardManager deviceCard = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getImsi()))
		{
			logger.error("DeviceCardManagerService::getDeviceCardByUniqueKey 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCardManagerService::getDeviceCardByUniqueKey param record="+record.toString());
		try
		{
			deviceCard = deviceCardMapper.selectByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::getDeviceCardByUniqueKey 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCardManagerService::getDeviceCardByUniqueKey return deviceCard="+(StringUtils.isEmpty(deviceCard)?"null":deviceCard.toString()));
		return deviceCard;
	}
	
	
	/**
	 * 根据iccid查询设备卡管理信息
	 * @param @record
	 * @return @DeviceCardManager
	 * @throws
	 */
	public DeviceCardManager getDeviceCardByIccid(DeviceCardManager record) throws RpcServiceException
	{
		DeviceCardManager deviceCard = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getIccid()))
		{
			logger.error("DeviceCardManagerService::getDeviceCardByIccid 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCardManagerService::getDeviceCardByIccid param record="+record.toString());
		
		try
		{
			deviceCard = deviceCardMapper.selectByIccid(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::getDeviceCardByIccid 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceCardManagerService::getDeviceCardByIccid return deviceCard="+(StringUtils.isEmpty(deviceCard)?"null":deviceCard.toString()));
		return deviceCard;
	}
	
	/**
	* 添加设备卡管理信息
	* @param @record
	* @return @DeviceCardManager
	* @throws 
	*/
	public DeviceCardManager addDeviceCard(DeviceCardManager record) throws RpcServiceException
	{
		DeviceCardManager deviceCard = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getCompanyId()) || StringUtils.isEmpty(record.getImsi()))
		{
			logger.error("DeviceCardManagerService::addDeviceCard 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCardManagerService::addDeviceCard param record="+record.toString());
		
		try
		{
			deviceCard = getDeviceCardByUniqueKey(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::addDeviceCard 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(deviceCard))
		{
			logger.info("DeviceCardManagerService::addDeviceCard return deviceCard="+ deviceCard.toString());
			return deviceCard;
		}
		
		try
		{
			deviceCardMapper.insertSelective(record);
			redisService.setDevCardInfo(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::addDeviceCard 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceCardManagerService::addDeviceCard return record="+(StringUtils.isEmpty(record)?"null":record.toString()));
		return record;	
	}

	/**
	 * 根据IMSI修改设备卡管理信息
	 * @param @record
	 * @return @DeviceCardManager
	 * @throws
	 */
	public DeviceCardManager updateIccidByImsi(DeviceCardManager record) throws RpcServiceException
	{
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getImsi()))
		{
			logger.error("DeviceCardManagerService::update 参数不能为空");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceCardManagerService::updateDeviceCardByImsi param record="+record.toString());
		try
		{
			deviceCardMapper.updateIccidByImsi(record);
			redisService.setDevCardInfo(record);
		}
		catch(Exception e)
		{
			logger.error("DeviceCardManagerService::addDeviceCard 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}

		logger.info("DeviceCardManagerService::addDeviceCard return record="+(StringUtils.isEmpty(record)?"null":record.toString()));
		return record;
	}
	
	
	public Map<String,DeviceCardManager> getDeviceCardManagerWithListIdsByImsis(List<String> listImsi,List<Integer> listCardIds){
		Map<String,DeviceCardManager> mapResult = new HashMap<>();
		if(listImsi == null || listImsi.isEmpty()){
			return mapResult;
		}
		if(listCardIds == null){
			return mapResult;
		}
		List<DeviceCardManager> listDeviceCardManagers = deviceCardMapper.getDeviceCardManagerByImsis(listImsi);
		if(null == listDeviceCardManagers || listDeviceCardManagers.isEmpty()){
			return mapResult;
		}
		for(DeviceCardManager card:listDeviceCardManagers){
			mapResult.put(card.getImsi(), card);
			listCardIds.add(card.getId());
		}
		return 	mapResult;
	}
	
}
