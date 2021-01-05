package cn.com.glsx.supplychain.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.UtilsProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.CheckChainSign;
import cn.com.glsx.supplychain.utils.WebUtils;

/**
 * @Title: 获取设备配置相关接口(针对终端扫码工具)
 * @Description:
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("attribInfo")
public class AttribInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	@Autowired
	private UtilsProperty utilsProperty;
	
	
	/**
	 * @Title: 获取设备配置相关接口(针对终端扫码工具)
	 * @Description: 获取设备类型配置编码列表
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_list_attrib_codes")
	@ResponseBody
	public String listAttribCodes(HttpServletRequest request) 
	{
		String mapJson = "";
		UserInfo userInfo = new UserInfo();
		AttribMana attribMana = new AttribMana();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = new ArrayList<Object>();
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("AttribInfoController::listAttribCodes begin:{}",paramsMap);
		String pubKey 		= utilsProperty.getPubkey();
		String userName		= request.getParameter("username");
		String deviceTypeId	= request.getParameter("devicetypeid");
		String attcodes		= request.getParameter("attcode");
		
		Integer devTypeId = 0;
		if(WebUtils.isCanParseInt(deviceTypeId))
		{
			devTypeId = Integer.parseInt(deviceTypeId);
		}

		userInfo.setUserName(userName);
		WebUtils.setRequest(userInfo);
		attribMana.setDevTypeId(devTypeId.equals(0)?null:Integer.valueOf(deviceTypeId));
		attribMana.setAttribCode(attcodes);
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("AttribInfoController::listAttribCodes end mapJson:" + mapJson);	
			return mapJson;
		}
		
		RpcResponse<List<AttribMana>> rsp = supplyChainRemote.listAttribManaCodes(userInfo, attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("AttribInfoController::listAttribCodes end mapJson:" + mapJson);	
			return mapJson;
		}
		
		List<AttribMana> result = rsp.getResult();
		Integer count = result.size();
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("ttl", String.valueOf(count));
		jsonMap.put("cnt", String.valueOf(count));
		for(AttribMana item:result)
		{
			objList.add(item.getAttribCode());
		}
		jsonMap.put("attcodes", objList);
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("AttribInfoController::listAttribCodes end mapJson:",mapJson);
		return mapJson;
	}
	
	
	/**
	 * @Title: 获取设备配置相关接口(针对终端扫码工具)
	 * @Description: 获取设备类型配置编码信息
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_get_attrib_code_info")
	@ResponseBody
	public String getAttribCodeInfo(HttpServletRequest request)
	{
		
		String mapJson = "";
		UserInfo userInfo = new UserInfo();
		AttribMana attribMana = new AttribMana();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("AttribInfoController::getAttribCodeInfo begin:{}",paramsMap);
		String pubKey 		= utilsProperty.getPubkey();
		String userName		= request.getParameter("username");
		String attribCode	= request.getParameter("attcode");
		userInfo.setUserName(userName);
		WebUtils.setRequest(userInfo);
		attribMana.setAttribCode(attribCode);
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("AttribInfoController::getAttribCodeInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		RpcResponse<AttribMana> rsp = supplyChainRemote.getAttribManaByManaCode(userInfo, attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("AttribInfoController::getAttribCodeInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		AttribMana result = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("modelid", ""+result.getModel()+"");
			jsonMap.put("typeid", ""+result.getType()+"");
			jsonMap.put("configureid", ""+result.getConfigure()+"");
			jsonMap.put("msizeid", ""+result.getMsize()+"");
			jsonMap.put("devtypeid", ""+result.getDevTypeId()+"");
			jsonMap.put("devmnumid", ""+result.getDevMnumId()+"");
			jsonMap.put("ornetid", ""+result.getOrNetId()+"");
			jsonMap.put("cardselfid", ""+result.getCardSelfId()+"");
			jsonMap.put("sourceid", ""+result.getSourceId()+"");
			jsonMap.put("screenid", ""+result.getScreenId()+"");
			jsonMap.put("oropenid", ""+result.getOrOpenId()+"");
			jsonMap.put("model", result.getModelName());
			jsonMap.put("type", result.getTypeName());
			jsonMap.put("configure", result.getConfigureName());
			jsonMap.put("boardversion", result.getBoardVersion());
			jsonMap.put("softversion", result.getSoftVersion());
			jsonMap.put("msize", result.getMsizeName());
			jsonMap.put("devtypename", result.getDevTypeName());
			jsonMap.put("devmnumname", result.getDevMnumName());
			jsonMap.put("ornetname", result.getOrNetName());
			jsonMap.put("cardselfname", result.getCardSelfName());
			jsonMap.put("sourcename", result.getSourceName());
			jsonMap.put("screenname", result.getScreenName());
			jsonMap.put("oropenname", result.getOrOpenName());
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("AttribInfoController::getAttribCodeInfo end mapJson:",mapJson);
		return mapJson;
	}
}
