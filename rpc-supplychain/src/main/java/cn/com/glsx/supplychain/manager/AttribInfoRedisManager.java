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
import cn.com.glsx.supplychain.model.AttribInfo;

@Service
public class AttribInfoRedisManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	* 
	* @Title: getAttribInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public AttribInfo getAttribInfoById(Integer id)
	{
		AttribInfo attribInfo = null;
		logger.info("AttribInfoRedisManager::getAttribInfoById start id:" + id);
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.REDIS_ATTRI_INFO.getValue() + id);
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("AttribInfoRedisManager::getAttribInfoById end attribInfo:{}",attribInfo);
			return attribInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		attribInfo = (AttribInfo)JSONObject.toBean(jsonObject,AttribInfo.class);
		logger.info("AttribInfoRedisManager::getAttribInfoById end attribInfo:{}",attribInfo);
		return attribInfo;
	}
	
	
	/**
	* 
	* @Title: setAttribInfoById 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void setAttribInfoById(AttribInfo attribInfo)
	{
		logger.info("AttribInfoRedisManager::setAttribInfoById start attribInfo:{}",attribInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", attribInfo.getId());
		jsonObject.put("type", attribInfo.getType());
		jsonObject.put("name", attribInfo.getName());
		jsonObject.put("comment", attribInfo.getComment());
		jsonObject.put("deletedFlag", attribInfo.getDeletedFlag());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.REDIS_ATTRI_INFO.getValue() + attribInfo.getId(), redisString);
		logger.info("AttribInfoRedisManager::setAttribInfoById end redisString:" + redisString);
	}
}