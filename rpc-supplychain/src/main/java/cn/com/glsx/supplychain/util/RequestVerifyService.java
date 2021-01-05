package cn.com.glsx.supplychain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.RequestVerifyMapper;
import cn.com.glsx.supplychain.model.RequestVerify;
import cn.com.glsx.supplychain.model.SupplyRequest;

@Service
public class RequestVerifyService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	@Autowired
	private RequestVerifyMapper verifyMapper;
	
	/**
	* 请求验证
	* @param @consumer @version("必填") @time（"选填"）
	* @return false:失败 true:成功
	* @throws 
	*/
	public boolean verifyRequest(SupplyRequest request)
	{
		boolean ret = false;
		if(StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getConsumer()) || StringUtils.isEmpty(request.getVersion()))
		{
			logger.error("RequestVerifyUtils::verifyRequest 参数不能为空 ret=" + ret);
			return ret;
		}
		
		logger.error("RequestVerifyUtils::verifyRequest request=" + request.toString());
		
		RequestVerify verify = new RequestVerify();
		verify.setConsumer(request.getConsumer());
		verify.setVersion(request.getVersion());
		
		RequestVerify requestVerify = redisService.getRequestVerify(verify);
		
		if(!StringUtils.isEmpty(requestVerify))
		{
			ret = true;
		}
		else
		{
			ret = (verifyMapper.countVerifyConsumers(verify) > 0)?true:false;
		}
		
		return ret;	
	}
}
