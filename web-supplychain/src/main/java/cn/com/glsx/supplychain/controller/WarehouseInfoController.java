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
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.CheckChainSign;
import cn.com.glsx.supplychain.utils.WebUtils;

/**
 * @Title: 仓库处理接口(针对终端扫码工具)
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("warehouseInfo")
public class WarehouseInfoController {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	@Autowired
	private UtilsProperty utilsProperty;
	
	/**
	 * @Title: 仓库处理接口(针对终端扫码工具)
	 * @Description: 获取仓库列表
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_get_warehouse_list")
	@ResponseBody 
	public String getWarehouseList(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::getWarehouseList begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseList end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		WareHouseInfo wareHouseInfo = new WareHouseInfo();
		String strUserName		= request.getParameter("username");
		String strBelong		= request.getParameter("belong");
		
		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(strBelong))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseList end mapJson:" + mapJson);	
			return mapJson;
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		wareHouseInfo.setBelong(strBelong);
		
		RpcResponse<List<WareHouseInfo>> rsp = supplyChainRemote.getWareHouseInfoList(userInfo, wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseList end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		List<WareHouseInfo> result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("total", String.valueOf(result.size()));
			objList = new ArrayList<Object>();
			for(WareHouseInfo item:result)
			{
				objMap = new HashMap<String,String>();
				objMap.put("wid", ""+item.getId()+"");
				objMap.put("wname", item.getName());
				objMap.put("belong", item.getBelong());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::getWarehouseList end mapJson:" + mapJson);
		return mapJson;
	}
	
	/**
	 * @Title: 仓库处理接口(针对终端扫码工具)
	 * @Description: 获取仓库信息
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_get_warehouse_info")
	@ResponseBody
	public String getWarehouseInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::getWarehouseInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		String strUserName		= request.getParameter("username");
		String strWid			= request.getParameter("wid");
		
		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(strWid))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo userInfo = new UserInfo();
		WareHouseInfo wareHouseInfo = new WareHouseInfo();
		userInfo.setUserName(request.getParameter("username"));
		WebUtils.setRequest(userInfo);
		wareHouseInfo.setId(Integer.valueOf(strWid));
		RpcResponse<WareHouseInfo> rsp = supplyChainRemote.getWareHouseInfo(userInfo, wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getWarehouseInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		
		WareHouseInfo result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("wid", "" + result.getId() + "");
			jsonMap.put("wname", result.getName());
			jsonMap.put("belong", result.getBelong());
			jsonMap.put("address", result.getAddress());
			jsonMap.put("mobile", result.getMobile());
			jsonMap.put("contacts", result.getContacts());
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::getWarehouseInfo end mapJson:" + mapJson);
		return mapJson;

	}
	
}
