package cn.com.glsx.supplychain.jxc.manager;

import net.sf.json.JSONObject;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jxc.enums.RedisEnum;
import cn.com.glsx.supplychain.jxc.model.JXCMTUseInfo;

@Service
public class JXCUserInfoRedisManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//@Autowired
	//private RedisService redisClient;
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
	public JXCMTUseInfo getUserInfoByName(String userName)
	{
		JXCMTUseInfo userInfo = null;
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.JXCREDIS_USERINFO_NAME.getValue() + userName);
		if(StringUtils.isEmpty(redisString))
		{
			return userInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		userInfo = (JXCMTUseInfo)JSONObject.toBean(jsonObject,JXCMTUseInfo.class);
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
	public void setUserInfoByName(JXCMTUseInfo userInfo)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", userInfo.getId());
		jsonObject.put("userName", userInfo.getUserName());
		jsonObject.put("password", userInfo.getPassword());
		jsonObject.put("role", userInfo.getRole());
		jsonObject.put("warehouseId", userInfo.getWarehouseId());
		jsonObject.put("deletedFlag", userInfo.getDeletedFlag());
		jsonObject.put("isSup", userInfo.getIsSup());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.JXCREDIS_USERINFO_NAME.getValue() + userInfo.getUserName(), redisString);
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
		redisClient.delete(RedisEnum.JXCREDIS_USERINFO_NAME.getValue() + userName);
	}
	
	/**
	* 
	* @Title: getUserInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public JXCMTUseInfo getUserInfoById(Integer id)
	{
		JXCMTUseInfo userInfo = null;
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.JXCREDIS_USERINFO_ID.getValue() + id);
		if(StringUtils.isEmpty(redisString))
		{
			return userInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		userInfo = (JXCMTUseInfo)JSONObject.toBean(jsonObject,JXCMTUseInfo.class);
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
	public void setUserInfoById(JXCMTUseInfo userInfo)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", userInfo.getId());
		jsonObject.put("userName", userInfo.getUserName());
		jsonObject.put("password", userInfo.getPassword());
		jsonObject.put("role", userInfo.getRole());
		jsonObject.put("warehouseId", userInfo.getWarehouseId());
		jsonObject.put("deletedFlag", userInfo.getDeletedFlag());
		jsonObject.put("isSup", userInfo.getIsSup());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.JXCREDIS_USERINFO_ID.getValue() + userInfo.getId(), redisString);
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
		redisClient.delete(RedisEnum.JXCREDIS_USERINFO_ID.getValue() + id);
	}
}
