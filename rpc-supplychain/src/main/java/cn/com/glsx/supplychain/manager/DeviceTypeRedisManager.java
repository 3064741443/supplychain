package cn.com.glsx.supplychain.manager;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.enums.RedisEnum;
import cn.com.glsx.supplychain.model.DeviceType;

@Service
public class DeviceTypeRedisManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	* 
	* @Title: getDeviceTypeById 
	* @Description: 从缓存中根据id获取设备大类型名称
	* @param @id
	* @return String
	* @throws
	*/
	public String getDeviceTypeById(Integer id)
	{
		logger.info("DeviceTypeRedisManager::getDeviceTypeById start id:" + id);
		String strValue = "";
		if(!StringUtils.isEmpty(id))
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_TYPE_INFO.getValue() + id);
		}
		logger.info("SupplyChainRedisService::getDeviceType end strValue:" + strValue);
		return strValue;
	}
	
	/**
	 * 
	 * @Title: setDeviceTypeById 
	 * @Description: 从缓存中根据id设置设备大类型名称
	 * @param 
	 * @return 
	 * @throws
	 */
	 public void setDeviceTypeById(DeviceType deviceType)
	 {
		 logger.info("DeviceTypeRedisManager::setDeviceTypeById start deviceType:{}", deviceType);
		 if(!StringUtils.isEmpty(deviceType) && !StringUtils.isEmpty(deviceType.getName()) && !StringUtils.isEmpty(deviceType.getId()))
		 {
			 redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_TYPE_INFO.getValue() + deviceType.getId(), deviceType.getName());
		 }
		 logger.info("DeviceTypeRedisManager::setDeviceTypeById end name:" + deviceType.getName());
	 }
	 
	 /**
	 * 
	 * @Title: delDeviceType 
	 * @Description: 从缓存中根据id删除设备大类型名称
	 * @param 
	 * @return 
	 * @throws
	 */
	 public void delDeviceType(Integer id)
	 {
		 logger.info("DeviceTypeRedisManager::delDeviceType start id:" + id);
		 if(!StringUtils.isEmpty(id))
		 {
			 redisClient.delete(RedisEnum.REDIS_DEVICE_TYPE_INFO.getValue() + id);
		 }
		 logger.info("DeviceTypeRedisManager::delDeviceType end"); 
	 }
}
