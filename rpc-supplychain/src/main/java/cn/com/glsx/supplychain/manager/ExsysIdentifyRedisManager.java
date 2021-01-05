package cn.com.glsx.supplychain.manager;

import net.sf.json.JSONObject;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.enums.RedisEnum;
import cn.com.glsx.supplychain.model.ExsysIdentify;

/**
* 
* @Title: ExsysIdentifyRedisManager 
* @Description: 数据分发外部系统定义缓存
* @throws
*/
@Service
public class ExsysIdentifyRedisManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	* 
	* @Title: getExsysIdentifyBySystemFlag 
	* @Description: 
	* @param @systemFlag
	* @return String
	* @throws
	*/
	public ExsysIdentify getExsysIdentifyBySystemFlag(String systemFlag,String strType)
	{
		ExsysIdentify exsysIdentify = null;
		logger.info("ExsysIdentifyRedisManager::getExsysIdentifyBySystemFlag start systemFlag:" + systemFlag + " strType:" + strType);
		String redisString=redisClient.opsForValue().get(RedisEnum.REDIS_EXSYSTEM_INFO.getValue() + systemFlag + "_" + strType);
		if(!StringUtils.isEmpty(redisString))
		{
			JSONObject jsonObject = JSONObject.fromObject(redisString);
			exsysIdentify = (ExsysIdentify)JSONObject.toBean(jsonObject,ExsysIdentify.class);
		}
		logger.info("ExsysIdentifyRedisManager::getExsysIdentifyBySystemFlag end exsysIdentify:{}",exsysIdentify);
		return exsysIdentify;
	}
	
	/**
	* 
	* @Title: setExsysIdentifyBySystemFlag 
	* @Description: 
	* @param @systemFlag
	* @return String
	* @throws
	*/
	public void setExsysIdentifyBySystemFlag(ExsysIdentify exsysIdentify)
	{
		logger.info("ExsysIdentifyRedisManager::setExsysIdentifyBySystemFlag start exsysIdentify:{}",exsysIdentify);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", exsysIdentify.getId());
		jsonObject.put("systemFlag", exsysIdentify.getSystemFlag());
		jsonObject.put("type", exsysIdentify.getType());
		jsonObject.put("isSign", exsysIdentify.getIsSign());
		jsonObject.put("pubSign", exsysIdentify.getPubSign());
		jsonObject.put("methodUrl", exsysIdentify.getMethodUrl());
		jsonObject.put("createdBy", exsysIdentify.getCreatedBy());
		jsonObject.put("createdDate", exsysIdentify.getCreatedDate());
		jsonObject.put("updatedBy", exsysIdentify.getUpdatedBy());
		jsonObject.put("updatedDate", exsysIdentify.getUpdatedDate());
		jsonObject.put("deletedFlag", exsysIdentify.getDeletedFlag());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.REDIS_EXSYSTEM_INFO.getValue()+ exsysIdentify.getSystemFlag() + "_" + exsysIdentify.getType() , redisString);
		logger.info("ExsysIdentifyRedisManager::setExsysIdentifyBySystemFlag end redisString:" + redisString);
	}
}
