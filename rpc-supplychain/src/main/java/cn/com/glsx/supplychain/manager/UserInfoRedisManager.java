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
import cn.com.glsx.supplychain.model.UserInfo;

@Service
public class UserInfoRedisManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	* 
	* @Title: getUserInfoByName 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public UserInfo getUserInfoByName(String userName)
	{
		UserInfo userInfo = null;
		logger.info("UserInfoRedisManager::getUserInfoByName start name:" + userName);
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.REDIS_USER_INFO_NAME.getValue() + userName);
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("UserInfoRedisManager::getUserInfoByName end userInfo:{}",userInfo);
			return userInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		userInfo = (UserInfo)JSONObject.toBean(jsonObject,UserInfo.class);
		logger.info("UserInfoRedisManager::getUserInfoByName end userInfo:{}",userInfo);
		return userInfo;	
	}
	
	/**
	* 
	* @Title: setUserInfoByName 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void setUserInfoByName(UserInfo userInfo)
	{
		logger.info("UserInfoRedisManager::setUserInfoByName start userInfo:{}",userInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", userInfo.getId());
		jsonObject.put("userName", userInfo.getUserName());
		jsonObject.put("password", userInfo.getPassword());
		jsonObject.put("role", userInfo.getRole());
		jsonObject.put("warehouseId", userInfo.getWarehouseId());
		jsonObject.put("deletedFlag", userInfo.getDeletedFlag());
		jsonObject.put("isSup", userInfo.getIsSup());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.REDIS_USER_INFO_NAME.getValue() + userInfo.getUserName(), redisString);
		logger.info("UserInfoRedisManager::setUserInfoByName end redisString:" + redisString);
	}
	
	/**
	* 
	* @Title: delUserInfoByName 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void delUserInfoByName(String userName)
	{
		logger.info("UserInfoRedisManager::delUserInfoByName start userName:" + userName); 
		redisClient.delete(RedisEnum.REDIS_USER_INFO_NAME.getValue() + userName);
		logger.info("UserInfoRedisManager::delUserInfoByName end"); 
	}
	
	/**
	* 
	* @Title: getUserInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public UserInfo getUserInfoById(Integer id)
	{
		UserInfo userInfo = null;
		logger.info("UserInfoRedisManager::getUserInfoById start id:" + id);
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.REDIS_USER_INFO_ID.getValue() + id);
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("UserInfoRedisManager::getUserInfoById end userInfo:{}",userInfo);
			return userInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		userInfo = (UserInfo)JSONObject.toBean(jsonObject,UserInfo.class);
		logger.info("UserInfoRedisManager::getUserInfoById end userInfo:{}",userInfo);
		return userInfo;	
	}
	
	/**
	* 
	* @Title: setUserInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void setUserInfoById(UserInfo userInfo)
	{
		logger.info("UserInfoRedisManager::setUserInfoById start userInfo:{}",userInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", userInfo.getId());
		jsonObject.put("userName", userInfo.getUserName());
		jsonObject.put("password", userInfo.getPassword());
		jsonObject.put("role", userInfo.getRole());
		jsonObject.put("warehouseId", userInfo.getWarehouseId());
		jsonObject.put("deletedFlag", userInfo.getDeletedFlag());
		jsonObject.put("isSup", userInfo.getIsSup());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.REDIS_USER_INFO_ID.getValue() + userInfo.getId(), redisString);
		logger.info("UserInfoRedisManager::setUserInfoById end redisString:" + redisString);
	}
	
	/**
	* 
	* @Title: delUserInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void delUserInfoById(Integer id)
	{
		logger.info("UserInfoRedisManager::delUserInfoById start id:" + id); 
		redisClient.delete(RedisEnum.REDIS_USER_INFO_ID.getValue() + id);
		logger.info("UserInfoRedisManager::delUserInfoById end"); 
	}
	
}
