package cn.com.glsx.supplychain.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.ExsysDeviceStatu;
import cn.com.glsx.supplychain.model.ExsysIdentify;
import cn.com.glsx.supplychain.model.ExsysOrderInfo;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.WebUtils;
import cn.com.glsx.supplychain.vo.ExsysDeviceStatuVo;
import cn.com.glsx.supplychain.vo.ExsysMessageVo;
import cn.com.glsx.supplychain.vo.ExsysOrderInfoVo;

/**
 * @Title: 分发设备信息到外部业务系统对接 接口
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("synchro")
public class ExdispatchController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	/**
	* @Title: 分发设备信息到外部业务系统对接 接口
	* @Description: 
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="extern_system")
	@ResponseBody 
	public String handleExternSystemMessage(HttpServletRequest request,@RequestBody String message)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("ExdispatchController::handleExternSystemMessage begin:{},message:{}",paramsMap,message);
		String strFunction 		= request.getParameter("function");
		String strToSysFlag		= request.getParameter("tosysflag");
		String strFromSysFlag	= request.getParameter("fromsysflag");
		String strIsSign		= request.getParameter("issign");
		String strSign			= request.getParameter("sign");
		String strMessage		= request.getParameter("data");
		if(StringUtils.isEmpty(strMessage))
		{
			strMessage = message;
		}
		if(!strToSysFlag.equals("SUP"))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SYSFLAG.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SYSFLAG.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("ExdispatchController::handleExternSystemMessage end mapJson:" + mapJson);	
			return mapJson;
		}
		ExsysIdentify condition = new ExsysIdentify();
		condition.setSystemFlag(strFromSysFlag);
		condition.setType("DV");
		UserInfo userInfo 	= new UserInfo();
		userInfo.setUserName(strFromSysFlag);
		WebUtils.setRequest(userInfo);
		RpcResponse<ExsysIdentify> rsp = supplyChainRemote.getExsysIdentifyBySystemFlag(userInfo, condition);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("ExdispatchController::handleExternSystemMessage end mapJson:" + mapJson);	
			return mapJson;
		}
		ExsysIdentify exsysIdentify = rsp.getResult();
		if(StringUtils.isEmpty(exsysIdentify))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SYSFLAG.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SYSFLAG.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("ExdispatchController::handleExternSystemMessage end mapJson:" + mapJson);	
			return mapJson;
		}
		
		if(strFunction.equals("syncDeviceInfoStatu"))
		{
			mapJson = handleSyncDeviceInfoStatu(strMessage,strFromSysFlag);
		}
		else if(strFunction.equals("syncOrderinfos"))
		{
			mapJson = handleSyncOrderInfos(strMessage,strFromSysFlag);
		}
		
		return mapJson;
	}
	
	private String handleSyncDeviceInfoStatu(String message,String fromSystemFlag)
	{
		logger.info("ExdispatchController::handleSyncDeviceInfoStatu start message:{},fromSystemFlag:{}",message,fromSystemFlag);
		UserInfo userInfo = null;
		ExsysDeviceStatu exsysDeviceStatu = null;
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try
		{
			ExsysMessageVo exsysMessage = (ExsysMessageVo) JacksonUtils.jsonToBean(message, ExsysMessageVo.class);		
			if(!StringUtils.isEmpty(exsysMessage.getInfos()))
			{
				for(ExsysDeviceStatuVo deviceStatu:exsysMessage.getInfos())
				{
					logger.info("ExdispatchController::handleSyncDeviceInfoStatu handle deviceStatu:{}",deviceStatu);
					userInfo 	= new UserInfo();
					//后面要求他们把updateuser发过来
					userInfo.setUserName(fromSystemFlag);  
					WebUtils.setRequest(userInfo);
					exsysDeviceStatu = new ExsysDeviceStatu();
					BeanUtils.copyProperties(exsysDeviceStatu, deviceStatu);
					RpcResponse<Integer> rsp = supplyChainRemote.handleExsysDeviceStatu(userInfo, exsysDeviceStatu);
					ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
					if(!StringUtils.isEmpty(errCodeEnum))
					{
						jsonMap.put("ret", errCodeEnum.getCode());
						jsonMap.put("err", errCodeEnum.getDescrible());
						mapJson = JacksonUtils.mapToJson(jsonMap);
						logger.info("ExdispatchController::handleSyncDeviceInfoStatu end mapJson:" + mapJson);	
						return mapJson;
					}
				}
			}
			jsonMap.put("ret", "0");
			jsonMap.put("err", "");
			mapJson = JacksonUtils.mapToJson(jsonMap);
		}
		catch(Exception e)
		{
			logger.error("ExdispatchController::handleSyncDeviceInfoStatu jason parse error! " + e.getMessage());
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_DEVICE_INVALID_JSON_FORMAT.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_DEVICE_INVALID_JSON_FORMAT.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);	
		}
		logger.info("ExdispatchController::handleSyncDeviceInfoStatu end mapJson:" + mapJson);	
		return mapJson;
	}
	
	private String handleSyncOrderInfos(String message,String fromSystemFlag)
	{
		logger.info("ExdispatchController::handleSyncOrderInfos start message:{},fromSystemFlag:{}",message,fromSystemFlag);
		UserInfo userInfo = null;
		ExsysDeviceStatu exsysDeviceStatu = null;
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try
		{
			ExsysMessageVo exsysMessage = (ExsysMessageVo) JacksonUtils.jsonToBean(message, ExsysMessageVo.class);
			if(!StringUtils.isEmpty(exsysMessage.getOrderinfos()))
			{
				for(ExsysOrderInfoVo exOrderInfo:exsysMessage.getOrderinfos())
				{
					userInfo 	= new UserInfo();
					userInfo.setUserName(fromSystemFlag);  
					WebUtils.setRequest(userInfo);
					ExsysOrderInfo exsysOrderInfo = new ExsysOrderInfo();
					BeanUtils.copyProperties(exsysOrderInfo, exOrderInfo);
					RpcResponse<Integer> rsp = supplyChainRemote.handleExsysOrderInfo(userInfo, exsysOrderInfo);
					ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
					if(!StringUtils.isEmpty(errCodeEnum))
					{
						jsonMap.put("ret", errCodeEnum.getCode());
						jsonMap.put("err", errCodeEnum.getDescrible());
						mapJson = JacksonUtils.mapToJson(jsonMap);
						logger.info("ExdispatchController::handleSyncOrderInfos end mapJson:" + mapJson);	
						return mapJson;
					}
				}
			}
			jsonMap.put("ret", "0");
			jsonMap.put("err", "");
			mapJson = JacksonUtils.mapToJson(jsonMap);
		}
		catch(Exception e)
		{
			logger.error("ExdispatchController::handleSyncOrderInfos jason parse error! " + e.getMessage());
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_DEVICE_INVALID_JSON_FORMAT.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_DEVICE_INVALID_JSON_FORMAT.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);	
		}
		logger.info("ExdispatchController::handleSyncOrderInfos end mapJson:" + mapJson);	
		return mapJson;
	}
}
