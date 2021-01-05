package cn.com.glsx.supplychain.jst.redis;

import net.sf.json.JSONObject;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.enums.RedisEnum;
import cn.com.glsx.supplychain.jst.model.RequestVerify;

@Service
public class RequestVerifyRedis {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	 * 
	 * @Title: getRequestVerify 
	 * @Description: 获取请求验证信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public RequestVerify getRequestVerify(RequestVerify verify)
	{
		logger.info("RequestVerifyRedis::getRequestVerify start verify:{}",verify);
		RequestVerify retVerify = null;
		String redisString = "";
		
		redisString = redisClient.opsForValue().get(RedisEnum.SUPPLYCHAIN_JST_RPCREQUEST_VERIFY_CONSUMER.getValue() + verify.getConsumer() + verify.getVersion());
		if(StringUtils.isEmpty(redisString))
		{
			logger.info("RequestVerifyRedis::getRequestVerify end retVerify:{}",retVerify);
			return retVerify;
		}
		JSONObject jsonObject = JSONObject.fromObject(redisString);
		retVerify = (RequestVerify)JSONObject.toBean(jsonObject,RequestVerify.class);
		
		logger.info("RequestVerifyRedis::getRequestVerify end retVerify:{}",retVerify);
		return retVerify;
	}
	
	/**
	 * 
	 * @Title: getRequestVerify 
	 * @Description: 设置请求验证信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public void setRequestVerify(RequestVerify verify)
	{
		logger.info("RequestVerifyRedis::setRequestVerify start verify:{}",verify);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("consumer", verify.getConsumer());
		jsonObject.put("version", verify.getVersion());
		
		String redisString = jsonObject.toString();
		redisClient.opsForValue().set(RedisEnum.SUPPLYCHAIN_JST_RPCREQUEST_VERIFY_CONSUMER.getValue() + verify.getConsumer() + verify.getVersion(), redisString);
		logger.info("RequestVerifyRedis::setRequestVerify end redisString:",redisString);
	}
	
}
