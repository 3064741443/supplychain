package cn.com.glsx.supplychain.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.UtilsProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.LogisticsTypeEnum;
import cn.com.glsx.supplychain.jxc.dto.JXCMTCheckImportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOderDeviceDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcDeviceRemote;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.CheckChainSign;
import cn.com.glsx.supplychain.utils.WebUtils;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;


/**
 * @Title: 设备出入库调拨接口(针对终端扫码工具)
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("deviceInfo")
public class DeviceInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	@Autowired
	private JxcDeviceRemote jxcDeviceRemote;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	
	@Autowired
	private UtilsProperty utilsProperty;
	
	/**
	* @Title: 获取设备类型接口(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_list_devicetype")
	@ResponseBody 
	public String listDeviceType(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = new ArrayList<Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::listDeviceType begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		String strSource = request.getParameter("source");
		Integer uiSource = (strSource==null?0:Integer.valueOf(strSource));
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(request.getParameter("username"));
		WebUtils.setRequest(userInfo);
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			return mapJson;
		}
		JXCMTDeviceTypeDTO dtoCondition = new JXCMTDeviceTypeDTO();
		RpcResponse<List<JXCMTDeviceTypeDTO>> rpcResponseDeviceType = jxcCommonRemote.listDeviceType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponseDeviceType.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponseDeviceType.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			 return mapJson;
        }
        List<JXCMTDeviceTypeDTO> listDeviceType = rpcResponseDeviceType.getResult();
        
        RpcResponse<List<JXCMTDeviceTypeDTO>> rpcResponseDeviceTypeSupport = jxcCommonRemote.listSupportDispatchDeviceType(dtoCondition);
		errCodeEnum = (JXCErrorCodeEnum) rpcResponseDeviceType.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponseDeviceTypeSupport.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			 return mapJson;
        }
        List<JXCMTDeviceTypeDTO> listDeviceTypeSupport = rpcResponseDeviceTypeSupport.getResult();
        Integer count = listDeviceType.size() - listDeviceTypeSupport.size();
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("ttl", String.valueOf(count));
		jsonMap.put("cnt", String.valueOf(count));
		boolean beSupport = false;
		for(JXCMTDeviceTypeDTO item:listDeviceType){
			beSupport = false;
			for(JXCMTDeviceTypeDTO sub:listDeviceTypeSupport){
				if(item.getId().equals(sub.getId())){
					beSupport = true;
				}
			}
			if(beSupport && uiSource!=1){
				continue;
			}
			objMap = new HashMap<String,String>();
			objMap.put("id", StringUtils.isEmpty(item.getId())?"":String.valueOf(item.getId()));
			objMap.put("name", StringUtils.isEmpty(item.getName())?"":item.getName());
			objList.add(objMap);
		}
		jsonMap.put("obj", objList);
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);
		return mapJson;	
	}
	/*public String listDeviceType(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = new ArrayList<Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::listDeviceType begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
			
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(request.getParameter("username"));
		WebUtils.setRequest(userInfo);
		DeviceType deviceType = new DeviceType();
		deviceType.setName(request.getParameter("typename"));
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			return mapJson;
		}
		RpcResponse<List<DeviceType>> rsp = supplyChainRemote.listDeviceType(userInfo, deviceType);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			return mapJson;
		}
		
		List<DeviceType> result = rsp.getResult();
		Integer count = result.size();
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("ttl", String.valueOf(count));
		jsonMap.put("cnt", String.valueOf(count));
		for(DeviceType item:result)
		{
			objMap = new HashMap<String,String>();
			objMap.put("id", StringUtils.isEmpty(item.getId())?"":String.valueOf(item.getId()));
			objMap.put("name", StringUtils.isEmpty(item.getName())?"":item.getName());
			objList.add(objMap);
		}
		jsonMap.put("obj", objList);
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);
		return mapJson;	
	}*/
	
	/**
	* @Title: 设备出入库扫码出入库接口(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_scanner_deviceinfo")
	@ResponseBody 
	public String scannerDeviceInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::scannerDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::scannerDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String strIccid			= request.getParameter("iccid");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strBatchNo		= request.getParameter("batchno");
		String strAttCode		= request.getParameter("attcode");
		String strSign			= request.getParameter("sign");
		
		if(!StringUtils.isEmpty(strImei))
		{
			if(!WebUtils.isNumberAndLetter(strImei))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::scannerDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(!StringUtils.isEmpty(strIccid))
		{
			if(!WebUtils.isNumberAndLetter(strIccid))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_ICCID_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_ICCID_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::scannerDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(!StringUtils.isEmpty(strSn))
		{
			if(!WebUtils.isNumberAndLetter(strSn))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::scannerDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(StringUtils.isEmpty(strAttCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::scannerDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setIccid(strIccid);
		deviceInfo.setImei(strImei);
		deviceInfo.setBatch(strBatchNo);
		deviceInfo.setAttribCode(strAttCode);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		
		RpcResponse<DeviceInfo> rsp = supplyChainRemote.scannerDeviceInfo(userInfo, deviceInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", rsp.getMessage());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);	
			return mapJson;
		}
		deviceInfo = rsp.getResult();
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(deviceInfo))
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			objMap = new HashMap<String,String>();
			objMap.put("iccid", deviceInfo.getIccid());
			objMap.put("imei", deviceInfo.getImei());
			objMap.put("sn", deviceInfo.getSn());
			objMap.put("attribCode", deviceInfo.getAttribCode());
			objMap.put("batch", deviceInfo.getBatch());
			objMap.put("status", deviceInfo.getStatus());
			objMap.put("orderCode", deviceInfo.getOrderCode());
			objMap.put("createdDate", dateFormat.format(deviceInfo.getCreatedDate()));
			objMap.put("createdBy", deviceInfo.getCreatedBy());
			objMap.put("updatedDate", dateFormat.format(deviceInfo.getUpdatedDate()));
			objMap.put("updatedBy", deviceInfo.getUpdatedBy());
			objMap.put("deletedFlag", deviceInfo.getDeletedFlag());
			objMap.put("wareHouseId", StringUtils.isEmpty(deviceInfo.getWareHouseId())?"":String.valueOf(deviceInfo.getWareHouseId()));
			objMap.put("wareHouseIdUp", StringUtils.isEmpty(deviceInfo.getWareHouseIdUp())?"":String.valueOf(deviceInfo.getWareHouseIdUp()));
		}
		jsonMap.put("deviceinfo", objMap);
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::listDeviceType end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 设备出入库excel入库数据检测接口(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="supplychain_excel_deviceinfo_in_check",method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String excelDeviceInfoInCheck(HttpServletRequest request,@RequestBody String deviceInfos)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
			
		String total 		= request.getParameter("total");
		String userName		= request.getParameter("username"); 
		//String deviceInfos 	= request.getParameter("deviceinfos");
		
		if(StringUtils.isEmpty(total) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(deviceInfos))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoInCheck end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer uiTotal = (WebUtils.isCanParseInt(total))?Integer.valueOf(total):0;
		
		List<Map<String, String>> deviceInfoMaps = JacksonUtils.jsonToList(deviceInfos);
		logger.info("DeviceInfoController::excelDeviceInfoInCheck start total:" + total);
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
		for(Map<String, String> mapItem:deviceInfoMaps)
		{
			DeviceInfo subObject = new DeviceInfo();
			subObject.setIccid(mapItem.get("iccid"));
			subObject.setImei(mapItem.get("imei"));
			subObject.setSn(mapItem.get("sn"));
			subObject.setAttribCode(mapItem.get("attribCode"));
			subObject.setBatch(mapItem.get("batchno"));
			deviceInfoList.add(subObject);
		}
		
		if(!uiTotal.equals(deviceInfoList.size()))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoInCheck end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		userInfo.setUserName(userName);
		WebUtils.setRequest(userInfo);
		RpcResponse<CheckImportDataVo> rsp = supplyChainRemote.excelDeviceInfoInCheck(userInfo, deviceInfoList);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", rsp.getMessage());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoInCheck end mapJson:" + mapJson);	
			return mapJson;
		}
		CheckImportDataVo importData = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(importData))
		{
			jsonMap.put("total", String.valueOf(deviceInfoList.size()));
			jsonMap.put("succount", String.valueOf(importData.getDeviceInfoSucessList().size()));
			jsonMap.put("falcount", String.valueOf(importData.getDeviceInfoFailList().size()));
			List<Object> failList = new ArrayList<Object>();
			List<Object> succList = new ArrayList<Object>();
			for(DeviceInfo failItem:importData.getDeviceInfoFailList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", failItem.getIccid());
				objMap.put("imei", failItem.getImei());
				objMap.put("sn", failItem.getSn());
				objMap.put("attribCode", failItem.getAttribCode());
				objMap.put("batch", failItem.getBatch());
				objMap.put("remark", failItem.getRemark());
				failList.add(objMap);	
			}
			for(DeviceInfo succItem:importData.getDeviceInfoSucessList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", succItem.getIccid());
				objMap.put("imei", succItem.getImei());
				objMap.put("sn", succItem.getSn());
				objMap.put("attribCode", succItem.getAttribCode());
				objMap.put("batch", succItem.getBatch());
				objMap.put("remark", succItem.getRemark());
				succList.add(objMap);		
			}
			//jsonMap.put("sucess", succList);
			jsonMap.put("fail", failList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::excelDeviceInfoInCheck end total:" + deviceInfoList.size() + " success-count:" + importData.getDeviceInfoSucessList().size() + " fail-count:" + importData.getDeviceInfoFailList().size());
		return mapJson;	
	}
	
	/**
	* @Title: 设备出入库excel入库接口(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="supplychain_excel_deviceinfo_import",method={RequestMethod.POST})
	@ResponseBody
	public String excelDeviceInfoImport(HttpServletRequest request,@RequestBody String deviceInfos)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		
		String total 		= request.getParameter("total");
		String userName		= request.getParameter("username"); 
		//String deviceInfos 	= request.getParameter("deviceinfos");
		
		if(StringUtils.isEmpty(total) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(deviceInfos))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoImport end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer uiTotal = (WebUtils.isCanParseInt(total))?Integer.valueOf(total):0;
		
		List<Map<String, String>> deviceInfoMaps = JacksonUtils.jsonToList(deviceInfos);
		logger.info("DeviceInfoController::excelDeviceInfoImport start total:" + total);
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
		for(Map<String, String> mapItem:deviceInfoMaps)
		{
			DeviceInfo subObject = new DeviceInfo();
			subObject.setIccid(mapItem.get("iccid"));
			subObject.setImei(mapItem.get("imei"));
			subObject.setSn(mapItem.get("sn"));
			subObject.setAttribCode(mapItem.get("attribCode"));
			subObject.setBatch(mapItem.get("batchno"));
			subObject.setVcode(mapItem.get("vcode"));
			deviceInfoList.add(subObject);
		}
		if(!uiTotal.equals(deviceInfoList.size()))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoImport end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		userInfo.setUserName(userName);
		WebUtils.setRequest(userInfo);
		RpcResponse<CheckImportDataVo> rsp = supplyChainRemote.excelDeviceInfoImport(userInfo, deviceInfoList);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoImport end mapJson:" + mapJson);	
			return mapJson;
		}
		CheckImportDataVo importData = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(importData))
		{
			jsonMap.put("total", String.valueOf(deviceInfoList.size()));
			jsonMap.put("succount", String.valueOf(importData.getDeviceInfoSucessList().size()));
			jsonMap.put("falcount", String.valueOf(importData.getDeviceInfoFailList().size()));
			List<Object> failList = new ArrayList<Object>();
			List<Object> succList = new ArrayList<Object>();
			for(DeviceInfo failItem:importData.getDeviceInfoFailList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", failItem.getIccid());
				objMap.put("imei", failItem.getImei());
				objMap.put("sn", failItem.getSn());
				objMap.put("attribCode", failItem.getAttribCode());
				objMap.put("batch", failItem.getBatch());
				objMap.put("remark", failItem.getRemark());
				failList.add(objMap);	
			}
			for(DeviceInfo succItem:importData.getDeviceInfoSucessList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", succItem.getIccid());
				objMap.put("imei", succItem.getImei());
				objMap.put("sn", succItem.getSn());
				objMap.put("attribCode", succItem.getAttribCode());
				objMap.put("batch", succItem.getBatch());
				objMap.put("remark", succItem.getRemark());
				succList.add(objMap);		
			}
			//jsonMap.put("sucess", succList);
			jsonMap.put("fail", failList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::excelDeviceInfoImport end total:" + deviceInfoList.size() + " success-count:" + importData.getDeviceInfoSucessList().size() + " fail-count:" + importData.getDeviceInfoFailList().size());
		return mapJson;		
	}
	
	/**
	* @Title: 设备根据imei/sn删除仓库库存接口(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_cancel_deviceinfo")
	@ResponseBody 
	public String cancelDeviceInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::cancelDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String strIccid			= request.getParameter("iccid");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strSign			= request.getParameter("sign");
		
		if(!StringUtils.isEmpty(strImei))
		{
			if(!WebUtils.isNumberAndLetter(strImei))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(!StringUtils.isEmpty(strIccid))
		{
			if(!WebUtils.isNumberAndLetter(strIccid))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_ICCID_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_ICCID_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(!StringUtils.isEmpty(strSn))
		{
			if(!WebUtils.isNumberAndLetter(strSn))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		
		deviceInfo.setIccid(strIccid);
		deviceInfo.setImei(strImei);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		
		RpcResponse<Integer> rsp = supplyChainRemote.cancelDeviceInfo(userInfo, deviceInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", rsp.getMessage());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::cancelDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}
	
	/**
	* @Title: 分页查询统计设备类型编号数量(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_page_stat_attrib")
	@ResponseBody 
	public String pageStatAttrib(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		List<Object> objList = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::pageStatAttrib begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageStatAttrib end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String attribCode		= request.getParameter("attribcode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		
		if(StringUtils.isEmpty(strUserName) || StringUtils.isEmpty(curPage) || StringUtils.isEmpty(pageSize))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageStatAttrib end mapJson:" + mapJson);	
			return mapJson;
		}
		
		 Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		 Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		 
		 userInfo.setUserName(strUserName);
		 WebUtils.setRequest(userInfo);
		 deviceInfo.setAttribCode(attribCode);
		 
		 RpcPagination<DeviceInfo> pagination = new RpcPagination<DeviceInfo>();
		 pagination.setPageNum(intPn);
		 pagination.setPageSize(intPs);
		 pagination.setCondition(deviceInfo);
		 RpcResponse<RpcPagination<DeviceInfo>> rsp = supplyChainRemote.pageStatAttrib(userInfo, pagination);
		 ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		 if(!StringUtils.isEmpty(errCodeEnum))
		 {
			 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::pageStatAttrib end mapJson:" + mapJson);	
			 return mapJson;
		 }
		 
		 jsonMap.put("ret", "0");
		 jsonMap.put("err", "");
		 RpcPagination<DeviceInfo> result = rsp.getResult();
		 if(!StringUtils.isEmpty(result))
		 {
			 jsonMap.put("pn", String.valueOf(result.getPageNum()));
			 jsonMap.put("ps", String.valueOf(result.getPageSize()));
			 jsonMap.put("pages", String.valueOf(result.getPages()));
			 jsonMap.put("total", String.valueOf(result.getTotal()));
			 objList = new ArrayList<Object>();
			 for(DeviceInfo item:result.getList())
			 {
				 objMap = new HashMap<String,String>();
				 objMap.put("attribcode", item.getAttribCode());
				 objMap.put("attribCount", String.valueOf(item.getAttribCount()));
				 objList.add(objMap);
			 }
			 jsonMap.put("subs", objList);
		 }
		 mapJson = JacksonUtils.mapToJson(jsonMap);
		 logger.info("DeviceInfoController::pageStatAttrib end mapJson:" + mapJson);
		 return mapJson; 
	}
	
	/**
	* @Title: 分页查询入库明细(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_page_stat_attrib_deviceinfos")
	@ResponseBody 
	public String pageStatAttribDeviceInfos(HttpServletRequest request)
	{
		String mapJson = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		List<Object> objList = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::pageStatAttribDeviceInfos begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageStatAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String attribCode		= request.getParameter("attribcode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		String iccid			= request.getParameter("iccid");
		String imei				= request.getParameter("imei");
		String sn				= request.getParameter("sn");
		
		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(attribCode) || 
				StringUtils.isEmpty(curPage) ||
				StringUtils.isEmpty(pageSize))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageStatAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setAttribCode(attribCode);
		deviceInfo.setIccid(iccid);
		deviceInfo.setImei(imei);
		deviceInfo.setSn(sn);
		
		RpcPagination<DeviceInfo> pagination = new RpcPagination<DeviceInfo>();
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(deviceInfo);
		RpcResponse<RpcPagination<DeviceInfo>> rsp = supplyChainRemote.pageStatAttribDeviceInfos(userInfo, pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageStatAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		 jsonMap.put("err", "");
		 RpcPagination<DeviceInfo> result = rsp.getResult();
		 if(!StringUtils.isEmpty(result))
		 {
			 jsonMap.put("pn", String.valueOf(result.getPageNum()));
			 jsonMap.put("ps", String.valueOf(result.getPageSize()));
			 jsonMap.put("pages", String.valueOf(result.getPages()));
			 jsonMap.put("total", String.valueOf(result.getTotal()));
			 objList = new ArrayList<Object>();
			 for(DeviceInfo item:result.getList())
			 {
				 objMap = new HashMap<String,String>();
				 objMap.put("sn", item.getSn());
				 objMap.put("imei", item.getImei());
				 objMap.put("iccid", item.getIccid());
				 objMap.put("batchno", item.getBatch());
				 objMap.put("createdate", dateFormat.format(item.getCreatedDate()));
				 objList.add(objMap);
			 }
			 jsonMap.put("subs", objList);
		 }
		 mapJson = JacksonUtils.mapToJson(jsonMap);
		 logger.info("DeviceInfoController::pageStatAttribDeviceInfos end mapJson:" + mapJson);
		 return mapJson; 
	}
	
	/**
	* @Title: 分页查询入库明细（导出用）(针对终端扫码工具)
	* @Description: 扫码入库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_list_export_attrib_deviceinfos")
	@ResponseBody 
	public String listExportAttribDeviceInfos(HttpServletRequest request)
	{
		String mapJson = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		List<Object> objList = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::listExportAttribDeviceInfos begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listExportAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String attribCode		= request.getParameter("attribcode");
		String strId			= request.getParameter("id");
		String strCount			= request.getParameter("count");
		String iccid			= request.getParameter("iccid");
		String imei				= request.getParameter("imei");
		String sn				= request.getParameter("sn");
		
		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(attribCode) || 
				StringUtils.isEmpty(strId) ||
				StringUtils.isEmpty(strCount))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listExportAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer id = (WebUtils.isCanParseInt(strId))?Integer.valueOf(strId):0;
		Integer count = (WebUtils.isCanParseInt(strCount))?Integer.valueOf(strCount):1;
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setAttribCode(attribCode);
		deviceInfo.setIccid(iccid);
		deviceInfo.setImei(imei);
		deviceInfo.setSn(sn);
		deviceInfo.setId(id);
		deviceInfo.setAttribCount(count);
		RpcResponse<List<DeviceInfo>> rsp = supplyChainRemote.listExportAttribDeviceInfos(userInfo, deviceInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::listExportAttribDeviceInfos end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		List<DeviceInfo> result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("count", String.valueOf(result.size()));
			objList = new ArrayList<Object>();
			for(DeviceInfo item:result)
			{
				objMap = new HashMap<String,String>();
				objMap.put("id", ""+item.getId()+"");
				objMap.put("sn", item.getSn());
				objMap.put("imei", item.getImei());
				objMap.put("iccid", item.getIccid());
				objMap.put("batchno", item.getBatch());
				objMap.put("attribcode", item.getAttribCode());
				objMap.put("createdate", dateFormat.format(item.getCreatedDate()));
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::listExportAttribDeviceInfos end mapJson:" + mapJson);
		return mapJson; 
	}
	
	/**
	 * @Title: 设备出入库扫码出库接口(针对终端扫码工具)
	 * @Description: 扫码出库
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_delivery_deviceinfo")
	@ResponseBody 
	public String deliveryDeviceInfo(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::deliveryDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		String strUserName		= request.getParameter("username");
		String strIccid			= request.getParameter("iccid");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strOrderCode		= request.getParameter("ordercode");
		String strManaCode		= request.getParameter("attcode");
		String strLogisticsno	= request.getParameter("logisticsno");
		String strLogisticscpy  = request.getParameter("logisticscpy");
		JXCMTOderDeviceDTO dtoCondition = new JXCMTOderDeviceDTO();
		List<JXCMTDeviceInfoDTO> listDeviceInfoDto = new ArrayList<>();
		JXCMTDeviceInfoDTO dtoDevice = new JXCMTDeviceInfoDTO();
		dtoDevice.setIccid(strIccid);
		dtoDevice.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		dtoDevice.setImei(strImei);
		listDeviceInfoDto.add(dtoDevice);
		dtoCondition.setUserName(strUserName);
		dtoCondition.setDispatchOrderCode(strOrderCode);
		dtoCondition.setLogisticsCpy(strLogisticscpy);
		dtoCondition.setLogisticsNo(strLogisticsno);
		dtoCondition.setListDeviceInfos(listDeviceInfoDto);;
		RpcResponse<Integer> rpcResponse = jxcDeviceRemote.dispatchDeviceDispatch(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponse.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);	
			 return mapJson;
        }
        Integer result = rpcResponse.getResult();
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("unde", String.valueOf(result));
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}
	/*public String deliveryDeviceInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::deliveryDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		OrderInfo	orderInfo	= new OrderInfo();
		Logistics	logistics	= new Logistics();
		Address		address		= new Address();
		
		String strUserName		= request.getParameter("username");
		String strIccid			= request.getParameter("iccid");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strOrderCode		= request.getParameter("ordercode");
		String strManaCode		= request.getParameter("attcode");
		String strLogisticsno	= request.getParameter("logisticsno");
		String strLogisticscpy  = request.getParameter("logisticscpy");

		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(strOrderCode) ||
				StringUtils.isEmpty(strManaCode) ||
				StringUtils.isEmpty(strLogisticsno))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setIccid(strIccid);
		deviceInfo.setImei(strImei);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		
		orderInfo.setOrderCode(strOrderCode);
		
		logistics.setServiceCode(strOrderCode);
		logistics.setCompany(strLogisticscpy);
		logistics.setOrderNumber(strLogisticsno);
		

		RpcResponse<Integer> rsp = supplyChainRemote.deliveryDeviceInfo(userInfo, orderInfo, deviceInfo, logistics, address);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", rsp.getMessage());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		Integer result = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("unde", String.valueOf(result));
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::deliveryDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}*/
	
	/**
	 * @Title: 取消出库接口(针对终端扫码工具)
	 * @Description: 扫码出库
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_cance_delivery_deviceinfo")
	@ResponseBody 
	public String canceDeliveryDeviceInfo(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::canceDeliveryDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		String strUserName		= request.getParameter("username");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strSign			= request.getParameter("sign");
		JXCMTOderDeviceDTO dtoCondition = new JXCMTOderDeviceDTO();
		dtoCondition.setUserName(strUserName);
		List<JXCMTDeviceInfoDTO> listDeviceInfos = new ArrayList<>();
		JXCMTDeviceInfoDTO dtoDeviceInfo = new JXCMTDeviceInfoDTO();
		dtoDeviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		listDeviceInfos.add(dtoDeviceInfo);
		dtoCondition.setListDeviceInfos(listDeviceInfos);
		RpcResponse<Integer> rpcResponse = jxcDeviceRemote.cancelDispatchDevice(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponse.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
			 return mapJson;
        }
        Integer result = rpcResponse.getResult();
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("unde", String.valueOf(result));
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}
	/*public String canceDeliveryDeviceInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::canceDeliveryDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		DeviceInfo 	deviceInfo	= new DeviceInfo();
		
		String strUserName		= request.getParameter("username");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String strSign			= request.getParameter("sign");
		
		if(!StringUtils.isEmpty(strImei))
		{
			if(!WebUtils.isNumberAndLetter(strImei))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_IMEI_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		if(!StringUtils.isEmpty(strSn))
		{
			if(!WebUtils.isNumberAndLetter(strSn))
			{
				jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getCode());
				jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALIDE_SN_FORMAT.getDescrible());	
				mapJson = JacksonUtils.mapToJson(jsonMap);
				logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
				return mapJson;
			}
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setImei(strImei);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		RpcResponse<Integer> rsp = supplyChainRemote.canceDeliveryDeviceInfo(userInfo, deviceInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		Integer result = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::canceDeliveryDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}*/
	
	/**
	* @Title: 设备出入库excel出库数据检测接口(针对终端扫码工具)
	* @Description: 扫码出库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="supplychain_excel_deviceinfo_out_check",method={RequestMethod.POST})
	@ResponseBody
	public String excelDeviceInfoOutCheck(HttpServletRequest request,@RequestBody String deviceInfos)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		String total 		= request.getParameter("total");
		String userName		= request.getParameter("username"); 
		String orderCode	= request.getParameter("orderCode");
	
		if(StringUtils.isEmpty(total) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(deviceInfos) || StringUtils.isEmpty(orderCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoOutCheck end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer uiTotal = (WebUtils.isCanParseInt(total))?Integer.valueOf(total):0;
		List<Map<String, String>> deviceInfoMaps = JacksonUtils.jsonToList(deviceInfos);
		logger.info("DeviceInfoController::excelDeviceInfoOutCheck start total:" + total);
		List<JXCMTDeviceInfoDTO> deviceInfoList = new ArrayList<JXCMTDeviceInfoDTO>();
		JXCMTDeviceInfoDTO subObject = null;
		for(Map<String, String> mapItem:deviceInfoMaps)
		{
			subObject = new JXCMTDeviceInfoDTO();
			subObject.setIccid(mapItem.get("iccid"));
			subObject.setImei(mapItem.get("imei"));
			subObject.setSn(mapItem.get("sn"));	
			if(StringUtils.isEmpty(subObject.getSn())){
				subObject.setSn(mapItem.get("imei"));
			}
			deviceInfoList.add(subObject);
		}
		
		if(!uiTotal.equals(deviceInfoList.size()))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoOutCheck end mapJson:" + mapJson);	
			return mapJson;
		}
		
		JXCMTOderDeviceDTO dtoCondition = new JXCMTOderDeviceDTO();
		dtoCondition.setUserName(userName);
		dtoCondition.setDispatchOrderCode(orderCode);
		dtoCondition.setListDeviceInfos(deviceInfoList);
		RpcResponse<JXCMTCheckImportDTO> rpcResponse = jxcDeviceRemote.checkBatchDispatchDevice(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponse.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::excelDeviceInfoOutCheck end mapJson:" + mapJson);	
			 return mapJson;
        }
        JXCMTCheckImportDTO importData = rpcResponse.getResult();

		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(importData))
		{
			jsonMap.put("total", String.valueOf(deviceInfoList.size()));
			jsonMap.put("succount", String.valueOf(importData.getListDeviceInfoSuccess().size()));
			jsonMap.put("falcount", String.valueOf(importData.getListDeviceInfoFailed().size()));
			List<Object> failList = new ArrayList<Object>();
			List<Object> succList = new ArrayList<Object>();
			for(JXCMTDeviceInfoDTO failItem:importData.getListDeviceInfoFailed())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", failItem.getIccid());
				objMap.put("imei", failItem.getImei());
				objMap.put("sn", failItem.getSn());
				objMap.put("remark", failItem.getFailedReason());
				failList.add(objMap);	
			}
			for(JXCMTDeviceInfoDTO succItem:importData.getListDeviceInfoSuccess())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", succItem.getIccid());
				objMap.put("imei", succItem.getImei());
				objMap.put("sn", succItem.getSn());			
				objMap.put("remark", succItem.getFailedReason());
				succList.add(objMap);		
			}
			jsonMap.put("fail", failList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::excelDeviceInfoOutCheck end total:" + deviceInfoList.size() + " success-count:" + importData.getListDeviceInfoSuccess().size() + " fail-count:" + importData.getListDeviceInfoFailed().size());
		return mapJson;		
	}
	
	/**
	* @Title: 设备出入库excel出库数据接口(针对终端扫码工具)
	* @Description: 扫码出库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="supplychain_excel_deviceinfo_out_import",method={RequestMethod.POST})
	@ResponseBody
	public String excelDeviceInfoOutImport(HttpServletRequest request,@RequestBody String deviceInfos)
	{
		Address address = null;
		Logistics logistics = null;
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		String total 		= request.getParameter("total");
		String userName		= request.getParameter("username"); 
		String orderCode	= request.getParameter("orderCode");
		String logisticscpy = request.getParameter("logisticscpy");
		String logisticsno	= request.getParameter("logisticsno");

		if(StringUtils.isEmpty(total) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(deviceInfos) || StringUtils.isEmpty(orderCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoOutImport end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer uiTotal = (WebUtils.isCanParseInt(total))?Integer.valueOf(total):0;
		List<Map<String, String>> deviceInfoMaps = JacksonUtils.jsonToList(deviceInfos);
		logger.info("DeviceInfoController::excelDeviceInfoOutImport start total:" + total);
		List<JXCMTDeviceInfoDTO> deviceInfoList = new ArrayList<>();
		JXCMTDeviceInfoDTO subObject = null;
		for(Map<String, String> mapItem:deviceInfoMaps)
		{
			subObject = new JXCMTDeviceInfoDTO();
			subObject.setIccid(mapItem.get("iccid"));
			subObject.setImei(mapItem.get("imei"));
			subObject.setSn(mapItem.get("sn"));
			if(StringUtils.isEmpty(subObject.getSn())){
				subObject.setSn(mapItem.get("imei"));
			}
			deviceInfoList.add(subObject);
		}
		
		if(!uiTotal.equals(deviceInfoList.size()))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::excelDeviceInfoOutImport end mapJson:" + mapJson);	
			return mapJson;
		}
		
		JXCMTOderDeviceDTO dtoCondition = new JXCMTOderDeviceDTO();
		dtoCondition.setUserName(userName);
		dtoCondition.setDispatchOrderCode(orderCode);
		dtoCondition.setListDeviceInfos(deviceInfoList);
		dtoCondition.setLogisticsCpy(logisticscpy);
		dtoCondition.setLogisticsNo(logisticsno);
		dtoCondition.setListDeviceInfos(deviceInfoList);
		
		RpcResponse<JXCMTCheckImportDTO> rpcResponse = jxcDeviceRemote.importBatchDispatchDevice(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponse.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::excelDeviceInfoOutCheck end mapJson:" + mapJson);	
			 return mapJson;
        }
        JXCMTCheckImportDTO importData = rpcResponse.getResult();

		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		if(!StringUtils.isEmpty(importData))
		{
			jsonMap.put("total", String.valueOf(importData.getParatope()));
			jsonMap.put("succount", String.valueOf(importData.getListDeviceInfoSuccess().size()));
			jsonMap.put("falcount", String.valueOf(importData.getListDeviceInfoFailed().size()));
			List<Object> failList = new ArrayList<Object>();
			List<Object> succList = new ArrayList<Object>();
			for(JXCMTDeviceInfoDTO failItem:importData.getListDeviceInfoFailed())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", failItem.getIccid());
				objMap.put("imei", failItem.getImei());
				objMap.put("sn", failItem.getSn());
				objMap.put("logisticsno", logisticsno);
				objMap.put("logisticscpy", logisticscpy);
				objMap.put("remark", failItem.getFailedReason());
				failList.add(objMap);	
			}
			for(JXCMTDeviceInfoDTO succItem:importData.getListDeviceInfoSuccess())
			{
				objMap = new HashMap<String,String>();
				objMap.put("iccid", succItem.getIccid());
				objMap.put("imei", succItem.getImei());
				objMap.put("sn", succItem.getSn());
				objMap.put("logisticsno", logisticsno);
				objMap.put("logisticscpy", logisticscpy);
				objMap.put("remark", "");
				succList.add(objMap);		
			}
			jsonMap.put("fail", failList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::excelDeviceInfoOutImport end total:" + deviceInfoList.size() + " success-count:" + importData.getListDeviceInfoSuccess().size() + " fail-count:" + importData.getListDeviceInfoFailed().size());
		return mapJson;		
	}
	
	/**
	* @Title: 不扫码出库(针对终端扫码工具)
	* @Description: 不扫码出库
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="supplychain_delivery_direct")
	@ResponseBody
	public String deliveryDirect(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::deliveryDirect begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::deliveryDirect end mapJson:" + mapJson);	
			return mapJson;
		}
		String strUserName		= request.getParameter("username");
		String strOrderCode		= request.getParameter("ordercode");
		String strLogisticsno	= request.getParameter("logisticsno");
		String strLogisticscpy  = request.getParameter("logisticscpy");
		String strSendTime		= request.getParameter("sendtime");
		String strCount			= request.getParameter("count");
		JXCMTOderDeviceDTO dtoCondition = new JXCMTOderDeviceDTO();
		dtoCondition.setUserName(strUserName);
		dtoCondition.setDispatchOrderCode(strOrderCode);
		dtoCondition.setLogisticsCpy(strLogisticscpy);
		dtoCondition.setLogisticsNo(strLogisticsno);
		dtoCondition.setSendQulities(Integer.valueOf(strCount));
		dtoCondition.setSendTime(strSendTime);
		RpcResponse<Integer> rpcResponse = jxcDeviceRemote.deliveryDirect(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", rpcResponse.getMessage());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::deliveryDirect end mapJson:" + mapJson);	
			 return mapJson;
        }
        Integer result = rpcResponse.getResult();
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("unde", String.valueOf(result));
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::deliveryDirect end mapJson:" + mapJson);
		return mapJson;
	}
	
	
	/**
	 * @Title: 设备出入库调拨接口(针对终端扫码工具)
	 * @Description: 扫码调拨
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_dispatch_deviceinfo")
	@ResponseBody
	public String dispatchDeviceInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::dispatchDeviceInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::dispatchDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		String strUserName		= request.getParameter("username");
		String strIccid			= request.getParameter("iccid");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		String wid				= request.getParameter("wid");
		String attcode			= request.getParameter("attcode");
		
		
		if(StringUtils.isEmpty(strUserName))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::dispatchDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo userInfo = new UserInfo();
		WareHouseInfo toHouseInfo = new WareHouseInfo();
		DeviceInfo deviceInfo = new DeviceInfo();
		AttribMana attribMana = new AttribMana();
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		toHouseInfo.setId(Integer.valueOf(request.getParameter("wid")));
		deviceInfo.setIccid(strIccid);
		deviceInfo.setImei(strImei);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		attribMana.setAttribCode(attcode);
		RpcResponse<Integer> rsp = supplyChainRemote.dispatchDeviceInfo(userInfo, toHouseInfo, deviceInfo,attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::dispatchDeviceInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		Integer result = rsp.getResult();
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::dispatchDeviceInfo end mapJson:" + mapJson);
		return mapJson;
	}
	
	/**
	 * @Title: 通过SN 或者imei获取设备编码信息
	 * @Description: 扫码调拨
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_get_device_mana")
	@ResponseBody
	public String getDeviceMana(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::getDeviceMana begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();	
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getDeviceMana end mapJson:" + mapJson);	
			return mapJson;
		}
		String strUserName		= request.getParameter("username");
		String strImei			= request.getParameter("imei");
		String strSn			= request.getParameter("sn");
		if(StringUtils.isEmpty(strUserName))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getDeviceMana end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo userInfo = new UserInfo();
		DeviceInfo deviceInfo = new DeviceInfo();
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		deviceInfo.setImei(strImei);
		deviceInfo.setSn(StringUtils.isEmpty(strSn)?strImei:strSn);
		RpcResponse<AttribMana> rsp = supplyChainRemote.getAttribManaByDeviceSn(userInfo, deviceInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getDeviceMana end mapJson:" + mapJson);	
			return mapJson;
		}
		
		AttribMana result = rsp.getResult();
		if(StringUtils.isEmpty(result))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_RECORD_IMEI_NOT_EXIST.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::getDeviceMana end mapJson:" + mapJson);	
			return mapJson;
		}
		
		jsonMap.put("attribcode", result.getAttribCode());
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
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::getDeviceMana end mapJson:" + mapJson);
		return mapJson;
		
	}
}
