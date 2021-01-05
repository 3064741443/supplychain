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
import cn.com.glsx.supplychain.model.AttribMana;

@Service
public class AttribManaRedisManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	* 
	* @Title: getAttribManaByCode 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public AttribMana getAttribManaByCode(String attribCode)
	{
		AttribMana attribMana = null;
		logger.info("AttribManaRedisManager::getAttribManaByCode start attribCode:" + attribCode);
		String redisString = "";
		redisString=redisClient.opsForValue().get(RedisEnum.REDIS_ATTRI_MANA.getValue() + attribCode);
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("AttribManaRedisManager::getAttribManaByCode end attribMana:{}",attribMana);
			return attribMana;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		attribMana = (AttribMana)JSONObject.toBean(jsonObject,AttribMana.class);
		logger.info("AttribManaRedisManager::getAttribManaByCode end attribMana:{}",attribMana);
		return attribMana;
	}
	
	/**
	* 
	* @Title: setAttribManaByCode 
	* @Description: 
	* @param 
	* @return 
	* @throws
	*/
	public void setAttribManaByCode(AttribMana attribMana)
	{
		logger.info("AttribManaRedisManager::setAttribManaByCode start attribMana:{}",attribMana);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", attribMana.getId());
		jsonObject.put("attribCode", attribMana.getAttribCode());
		jsonObject.put("type", attribMana.getType());
		jsonObject.put("model", attribMana.getModel());
		jsonObject.put("configure", attribMana.getConfigure());
		jsonObject.put("msize", attribMana.getMsize());
		jsonObject.put("deletedFlag", attribMana.getDeletedFlag());
		jsonObject.put("mcuVersion", attribMana.getMcuVersion());
		jsonObject.put("fastenerVersion", attribMana.getFastenerVersion());
		jsonObject.put("softVersion", attribMana.getSoftVersion());
		jsonObject.put("boardVersion", attribMana.getBoardVersion());
		jsonObject.put("devTypeId", attribMana.getDevTypeId());
		jsonObject.put("devMnumId", attribMana.getDevMnumId());
		jsonObject.put("orNetId", attribMana.getOrNetId());
		jsonObject.put("cardSelfId", attribMana.getCardSelfId());
		jsonObject.put("sourceId", attribMana.getSourceId());
		jsonObject.put("screenId", attribMana.getScreenId());
		jsonObject.put("orOpenId", attribMana.getOrOpenId());
		jsonObject.put("verifyIccid", attribMana.getVerifyIccid());
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.REDIS_ATTRI_MANA.getValue() + attribMana.getAttribCode(), redisString);
		logger.info("AttribManaRedisManager::setAttribManaByCode end redisString:" + redisString);
	}
}
