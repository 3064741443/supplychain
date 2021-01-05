package cn.com.glsx.supplychain.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.dto.RpcDebugInfo;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.remote.RpcDebugInfoRemote;
import cn.com.glsx.supplychain.utils.WebUtils;

/**
 * @Title: rpc调试后台接口
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("RpcDebugInfo")
public class RpcDebugInfoController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RpcDebugInfoRemote rpcDebugInfoRemote;
	
	@RequestMapping("operatorRedis")
	@ResponseBody
	public Map<String,Object> operatorRedis(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("RpcDebugInfoController::operatorRedis begin:{}",paramsMap);
		String strKey		 = paramsMap.get("key");
		String strOption	 = paramsMap.get("option");
		
		RpcDebugInfo rpcDebugInfo = new RpcDebugInfo();
		rpcDebugInfo.setOptModule("redis");
		rpcDebugInfo.setOptFunction(strOption);
		rpcDebugInfo.setRedisKey(strKey);
		
		RpcResponse<RpcDebugInfo> rsp = rpcDebugInfoRemote.operatorRedis(rpcDebugInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("RpcDebugInfoController::operatorRedis end mapJson:" + mapJson);	
			return jsonMap;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		rpcDebugInfo = rsp.getResult();
		if(!StringUtils.isEmpty(rpcDebugInfo))
		{
			jsonMap.put("result", rpcDebugInfo);
			//String strResult = JacksonUtils.beanToJson(rpcDebugInfo);
			//jsonMap.put("result", strResult);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("RpcDebugInfoController::operatorRedis end mapJson:" + mapJson);
		return jsonMap;
	}
	
	
}
