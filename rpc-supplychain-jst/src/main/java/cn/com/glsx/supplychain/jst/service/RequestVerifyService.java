package cn.com.glsx.supplychain.jst.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.convert.BaseDtoRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import cn.com.glsx.supplychain.jst.mapper.RequestVerifyMapper;
import cn.com.glsx.supplychain.jst.model.RequestVerify;
import cn.com.glsx.supplychain.jst.redis.RequestVerifyRedis;

@Service
public class RequestVerifyService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RequestVerifyRedis verifyRedis;
	@Autowired
	private RequestVerifyMapper verifyMapper;
	/**
	* 请求验证
	* @param @consumer @version("必填") @time（"选填"）
	* @return false:失败 true:成功
	* @throws 
	*/
	public boolean verifyRequest(BaseDTO baseDTO)
	{
		if(StringUtils.isEmpty(baseDTO))
		{
			return false;
		}
		if(StringUtils.isEmpty(baseDTO.getConsumer()) || StringUtils.isEmpty(baseDTO.getVersion()))
		{
			return false;
		}
		RequestVerify model = BaseDtoRpcConvert.convertDTO(baseDTO);
		RequestVerify verify = verifyRedis.getRequestVerify(model);
		if(!StringUtils.isEmpty(verify))
		{
			return true;
		}
		
		verify = verifyMapper.selectOne(model);
		if(!StringUtils.isEmpty(verify))
		{
			return true;
		}
		return false;
	}

}
