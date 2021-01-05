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
import cn.com.glsx.supplychain.jxc.model.JXCMTAttribInfo;

@Service
public class JXCAttribInfoRedisManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	//private RedisService redisClient;
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
	public JXCMTAttribInfo getJXCMTAttribInfoById(Integer id)
	{
		JXCMTAttribInfo attribInfo = null;
		logger.info("JXCAttribInfoRedisManager::getJXCMTAttribInfoById start id:" + id);
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.JXCREDIS_ATTRI_INFO.getValue() + id);
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("JXCAttribInfoRedisManager::getJXCMTAttribInfoById end attribInfo:{}",attribInfo);
			return attribInfo;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		attribInfo = (JXCMTAttribInfo)JSONObject.toBean(jsonObject,JXCMTAttribInfo.class);
		logger.info("JXCAttribInfoRedisManager::getJXCMTAttribInfoById end attribInfo:{}",attribInfo);
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
	public void setJXCMTAttribInfoById(JXCMTAttribInfo attribInfo)
	{
		logger.info("JXCAttribInfoRedisManager::setJXCMTAttribInfoById start attribInfo:{}",attribInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", attribInfo.getId());
		jsonObject.put("type", attribInfo.getType());
		jsonObject.put("name", attribInfo.getName());
		jsonObject.put("comment", attribInfo.getComment());
		jsonObject.put("deletedFlag", attribInfo.getDeletedFlag());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.JXCREDIS_ATTRI_INFO.getValue() + attribInfo.getId(), redisString);
		logger.info("JXCAttribInfoRedisManager::setJXCMTAttribInfoById end redisString:" + redisString);
	}
}
