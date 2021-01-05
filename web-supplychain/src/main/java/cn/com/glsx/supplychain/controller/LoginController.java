package cn.com.glsx.supplychain.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.UtilsProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.CheckChainSign;
import cn.com.glsx.supplychain.utils.WebUtils;

/**
 * @Title: 扫码终端登录(针对终端扫码工具)
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("login")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	@Autowired
	private UtilsProperty utilsProperty;

	/**
	 * @Title: 扫码终端登录(针对终端扫码工具)
	 * @Description: 扫码终端登录
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_login")
	@ResponseBody 
	public String login(HttpServletRequest request)
	{	
		String mapJson = "";
		UserInfo userInfo = new UserInfo();
		Map<String,Object> jsonMap = new HashMap<String,Object>();	
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("LoginController::login begin:{}",paramsMap);
		
		String pubKey = utilsProperty.getPubkey();	
		logger.info("LoginController::login pubKey:{}",pubKey);
		userInfo.setUserName(request.getParameter("username"));
		userInfo.setPassword(request.getParameter("password"));
		WebUtils.setRequest(userInfo);
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("LoginController::login end mapJson:" + mapJson);	
			return mapJson;
		}
		
		RpcResponse<UserInfo> rsp = supplyChainRemote.login(userInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();	
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("LoginController::login end mapJson:" + mapJson);	
			return mapJson;
		}
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		UserInfo result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("warehouseid",""+result.getWarehouseId()+"");
			jsonMap.put("role", ""+result.getRole()+"");
			jsonMap.put("warehousenm", result.getWareHouseInfo().getName());
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("LoginController::login end mapJson:" + mapJson);	
		return mapJson;
	}
}
