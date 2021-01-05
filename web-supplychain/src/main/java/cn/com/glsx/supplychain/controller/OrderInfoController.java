package cn.com.glsx.supplychain.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.UtilsProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsBillDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTGenBillNumberDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTGhMerchantOrderConfigDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDetailDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoMerchantDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoSignDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTSpMerchantOrderDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.CheckChainSign;
import cn.com.glsx.supplychain.utils.WebUtils;

/**
 * @Title: 订单处理接口(针对终端扫码工具)
 * @Description: 
 * @author QL 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("orderInfo")
public class OrderInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	@Autowired
	private JxcOrderRemote jxcOrderRemote;
	
	@Autowired
	private UtilsProperty utilsProperty;

	/**
	 * @Title: 获取订单编码列表(针对终端扫码工具)
	 * @Description: 获取订单编码列表
	 * @author QL 
	 * @version V1.0  
	 * @Company: Didihu.com.cn
	 * @Copyright Copyright (c) 2017
	 */
	@RequestMapping("supplychain_list_order_code")
	@ResponseBody 
	public String listOrderCode(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::getOrderList begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::getOrderList end mapJson:" + mapJson);	
			return mapJson;
		}
		
		String strUserName		= request.getParameter("username");
		String strStatus		= request.getParameter("status");
		String orderCode		= request.getParameter("ordercode");
		
		UserInfo 	userInfo 	= new UserInfo();
		OrderInfo 	orderInfo	= new OrderInfo();
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		orderInfo.setStatus(strStatus);
		orderInfo.setOrderCode(orderCode);
		
		RpcResponse<List<OrderInfo>> rsp = supplyChainRemote.listOrderInfo(userInfo, orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("OrderInfoController::getOrderList end mapJson:" + mapJson);	
			 return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		List<OrderInfo> result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("total", String.valueOf(result.size()));
			objList = new ArrayList<Object>();
			for(OrderInfo item:result)
			{
				objMap = new HashMap<String,String>();
				objMap.put("ordercode", item.getOrderCode());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::getOrderList end mapJson:" + mapJson);
		return mapJson;		
	}
	
	/**
	* @Title: 获取订单信息(针对终端扫码工具)
	* @Description: 获取订单信息
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_get_order_info")
	@ResponseBody 
	public String getOrderInfoByOrderCode(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::getOrderInfoByOrderCode begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::getOrderInfoByOrderCode end mapJson:" + mapJson);	
			return mapJson;
		}
		
		String strUserName		= request.getParameter("username");
		String strOrderCode = request.getParameter("ordercode");
		
		if(StringUtils.isEmpty(strUserName) || StringUtils.isEmpty(strOrderCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::getOrderInfoByOrderCode end mapJson:" + mapJson);	
			return mapJson;
		}
		
		UserInfo 	userInfo 	= new UserInfo();
		OrderInfo 	orderInfo	= new OrderInfo();
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		orderInfo.setOrderCode(strOrderCode);
		
		RpcResponse<OrderInfo> rsp = supplyChainRemote.getOrderInfoByOrderCode(userInfo, orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("OrderInfoController::getOrderInfoByOrderCode end mapJson:" + mapJson);	
			 return mapJson;
		}
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		OrderInfo result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			objMap = new HashMap<String,String>();
			objMap.put("ordercode",result.getOrderCode());
			objMap.put("merchant",result.getSendMerchantNo());
			objMap.put("addr",result.getAddress());
			objMap.put("contacts",result.getContacts());
			objMap.put("mobile",result.getMobile());
			objMap.put("attcode",result.getAttribCode());
			objMap.put("batchno",result.getBatch());
			objMap.put("total",""+result.getTotal()+"");
			objMap.put("unde",""+(result.getTotal()-result.getAlreadyShipped())+"");
			objMap.put("remark",result.getRemark());
		}
		jsonMap.put("sub", objMap);
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::getOrderInfoByOrderCode end mapJson:" + mapJson);
		return mapJson;
	}
	
	/**
	* @Title: 获取订单列表（出库）(针对终端扫码工具)
	* @Description: 获取订单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_page_order_info")
	@ResponseBody 
	public String pageOrderInfo(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::pageOrderInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		OrderInfo 	orderInfo	= new OrderInfo();
		
		String strWarehouseId	= request.getParameter("warehouseid");
		String strStatus		= request.getParameter("status");
		String orderCode		= request.getParameter("ordercode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		
		if(StringUtils.isEmpty(strWarehouseId) ||
				StringUtils.isEmpty(curPage) ||
				StringUtils.isEmpty(pageSize))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		Integer intWarehouseId = (WebUtils.isCanParseInt(strWarehouseId))?Integer.valueOf(strWarehouseId):0;
		RpcPagination<JXCMTOrderInfoDTO> pagination = new RpcPagination<>();
		JXCMTOrderInfoDTO dtoCondition = new JXCMTOrderInfoDTO();
		dtoCondition.setDispatchOrderCode(orderCode);
		dtoCondition.setWarehouseId(intWarehouseId);
		dtoCondition.setStatus(strStatus);
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<JXCMTOrderInfoDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrder(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfo end mapJson:" + mapJson);	
			return mapJson;
       }
	   jsonMap.put("ret", "0");
	   jsonMap.put("err", "");
	   RpcPagination<JXCMTOrderInfoDTO> rpcResult = rpcResponse.getResult();
	   if(!StringUtils.isEmpty(rpcResult))
		{
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTOrderInfoDTO item:rpcResult.getList())
			{
				int total = (item.getTotal()!=null?item.getTotal():0);
				int sendQuantities = (item.getSendQuantities()!=null?item.getSendQuantities():0);
				int oweQuantities = total - sendQuantities;
				objMap = new HashMap<String,String>();
				objMap.put("ordercode", item.getDispatchOrderCode());
				objMap.put("unde", String.valueOf(oweQuantities));
				objMap.put("out", String.valueOf(sendQuantities));
				objMap.put("total", String.valueOf(total));
				objMap.put("statu", item.getStatus());
				objMap.put("ndscan", item.getNdScan());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageOrderInfo end mapJson:" + mapJson);
		return mapJson;	 
	}
	/*public String pageOrderInfo(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::pageOrderInfo begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageOrderInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 	userInfo 	= new UserInfo();
		OrderInfo 	orderInfo	= new OrderInfo();
		
		String strUserName		= request.getParameter("username");
		String strStatus		= request.getParameter("status");
		String orderCode		= request.getParameter("ordercode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		
		if(StringUtils.isEmpty(strUserName) ||
				StringUtils.isEmpty(curPage) ||
				StringUtils.isEmpty(pageSize))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageOrderInfo end mapJson:" + mapJson);	
			return mapJson;
		}
		
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		orderInfo.setOrderCode(orderCode);
		orderInfo.setStatus(strStatus);
		
		RpcPagination<OrderInfo> pagination = new RpcPagination<OrderInfo>();
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(orderInfo);
		RpcResponse<RpcPagination<OrderInfo>> rsp = supplyChainRemote.pageOrderInfo(userInfo, pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::pageOrderInfo end mapJson:" + mapJson);	
			 return mapJson;
		}
		
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<OrderInfo> result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("pn", String.valueOf(result.getPageNum()));
			jsonMap.put("ps", String.valueOf(result.getPageSize()));
			jsonMap.put("pages", String.valueOf(result.getPages()));
			jsonMap.put("total", String.valueOf(result.getTotal()));
			objList = new ArrayList<Object>();
			for(OrderInfo item:result.getList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("ordercode", item.getOrderCode());
				objMap.put("unde", String.valueOf(item.getTotal()-item.getAlreadyShipped()));
				objMap.put("out", String.valueOf(item.getAlreadyShipped()));
				objMap.put("total", String.valueOf(item.getTotal()));
				objMap.put("statu", item.getStatus());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::pageOrderInfo end mapJson:" + mapJson);
		return mapJson;	
	}*/
	
	/**
	* @Title: 获取订单详情列表(针对终端扫码工具)
	* @Description: 获取订单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("supplychain_page_order_info_detail")
	@ResponseBody 
	public String pageOrderInfoDetail(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::pageOrderInfoDetail begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 			userInfo 		= new UserInfo();
		OrderInfoDetail 	orderInfoDetail	= new OrderInfoDetail();
		
		String strUserName		= request.getParameter("username");
		String orderCode		= request.getParameter("ordercode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		String strIccid			= request.getParameter("iccid");
		String strSn			= request.getParameter("sn");
		String strImei			= request.getParameter("imei");
		if(StringUtils.isEmpty(orderCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			return mapJson;
		}
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		JXCMTOrderInfoDetailDTO dtoCondition = new JXCMTOrderInfoDetailDTO();
		dtoCondition.setSn(strSn);
		dtoCondition.setIccid(strIccid);
		dtoCondition.setImei(strImei);
		dtoCondition.setDispatchOrderCode(orderCode);
		dtoCondition.setPageNo(intPn);
		dtoCondition.setPageSize(intPs);
		RpcResponse<RpcPagination<JXCMTOrderInfoDetailDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrderDetail(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			return mapJson;
       }
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");	
		RpcPagination<JXCMTOrderInfoDetailDTO> result = rpcResponse.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("pn", String.valueOf(result.getPageNum()));
			jsonMap.put("ps", String.valueOf(result.getPageSize()));
			jsonMap.put("pages", String.valueOf(result.getPages()));
			jsonMap.put("total", String.valueOf(result.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTOrderInfoDetailDTO item:result.getList())
			{
				int sendQulities = (item.getSendQulities()==null?0:item.getSendQulities());
				objMap = new HashMap<String,String>();
				objMap.put("sn", item.getSn());
				objMap.put("iccid", item.getIccid());
				objMap.put("imei", item.getImei());
				objMap.put("batch", item.getBatch());
				objMap.put("createdate",  item.getUpdatedDate());
				objMap.put("logisticsno", item.getLogisticsNum());
				objMap.put("logisticscpy", item.getLogisticsCompany());
				objMap.put("sendqulities", String.valueOf(sendQulities));
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageOrderInfoDetail end mapJson:" + mapJson);
		return mapJson;	
	}
	/*public String pageOrderInfoDetail(HttpServletRequest request)
	{
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("DeviceInfoController::pageOrderInfoDetail begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			return mapJson;
		}
		UserInfo 			userInfo 		= new UserInfo();
		OrderInfoDetail 	orderInfoDetail	= new OrderInfoDetail();
		
		String strUserName		= request.getParameter("username");
		String orderCode		= request.getParameter("ordercode");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		String strIccid			= request.getParameter("iccid");
		String strSn			= request.getParameter("sn");
		String strImei			= request.getParameter("imei");
		
		if(StringUtils.isEmpty(orderCode))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("DeviceInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			return mapJson;
		}
		
		userInfo.setUserName(strUserName);
		WebUtils.setRequest(userInfo);
		orderInfoDetail.setOrderCode(orderCode);
		orderInfoDetail.setIccid(strIccid);
		orderInfoDetail.setImei(strImei);
		orderInfoDetail.setSn(strSn);
		
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		
		RpcPagination<OrderInfoDetail> pagination = new RpcPagination<OrderInfoDetail>();
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(orderInfoDetail);
		RpcResponse<RpcPagination<OrderInfoDetail>> rsp = supplyChainRemote.pageOrderInfoDetail(userInfo, pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("DeviceInfoController::pageOrderInfoDetail end mapJson:" + mapJson);	
			 return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		
		RpcPagination<OrderInfoDetail> result = rsp.getResult();
		if(!StringUtils.isEmpty(result))
		{
			jsonMap.put("pn", String.valueOf(result.getPageNum()));
			jsonMap.put("ps", String.valueOf(result.getPageSize()));
			jsonMap.put("pages", String.valueOf(result.getPages()));
			jsonMap.put("total", String.valueOf(result.getTotal()));
			objList = new ArrayList<Object>();
			for(OrderInfoDetail item:result.getList())
			{
				objMap = new HashMap<String,String>();
				objMap.put("sn", item.getSn());
				objMap.put("iccid", item.getIccid());
				objMap.put("imei", item.getImei());
				objMap.put("batch", (StringUtils.isEmpty(item.getOrderInfo()))?"":item.getOrderInfo().getBatch());
				objMap.put("createdate",  dateFormat.format(item.getCreatedDate()));
				objMap.put("logisticsno", (StringUtils.isEmpty(item.getLogistics()))?"":item.getLogistics().getOrderNumber());
				objMap.put("logisticscpy", (StringUtils.isEmpty(item.getLogistics()))?"":item.getLogistics().getCompany());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("DeviceInfoController::pageOrderInfoDetail end mapJson:" + mapJson);
		return mapJson;	
	}*/
	
	
	/**
	* @Title: 获取订单中的商户列表
	* @Description: 获取订单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_page_order_merchant")
	@ResponseBody 
	public String pageOrderMerchant(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::pageOrderMerchant begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageOrderMerchant end mapJson:" + mapJson);	
			return mapJson;
		}
		String strMerchantName		= request.getParameter("merchantname");
		String curPage				= request.getParameter("pn");
		String pageSize				= request.getParameter("ps");
		
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		
		JXCMTOrderInfoMerchantDTO dtoCondition = new JXCMTOrderInfoMerchantDTO();
		RpcPagination<JXCMTOrderInfoMerchantDTO> pagination = new RpcPagination<>();
		dtoCondition.setSendMerchantName(strMerchantName);
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<JXCMTOrderInfoMerchantDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrderMerchant(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("OrderInfoController::pageOrderMerchant end mapJson:" + mapJson);	
			 return mapJson;
        }
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<JXCMTOrderInfoMerchantDTO> rpcResult = rpcResponse.getResult();
		if(null != rpcResult){
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTOrderInfoMerchantDTO dto:rpcResult.getList()){
				objMap = new HashMap<String,String>();
				objMap.put("merchantname", dto.getSendMerchantName());
				objMap.put("merchantcode", dto.getSendMerchantNo());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageOrderMerchant end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 获取订单列表
	* @Description: 获取订单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_page_dispatch_orders")
	@ResponseBody 
	public String pageDispatchOrders(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::pageDispatchOrders begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageDispatchOrders end mapJson:" + mapJson);	
			return mapJson;
		}
		String strWarehouseId	= request.getParameter("warehouseid");
		String strOrderCode		= request.getParameter("ordercode");
		String strDeviceType	= request.getParameter("devicetype");
		String strDadanStatu    = request.getParameter("dadan");
		String strMerchantCode	= request.getParameter("merchantcode");
		String strOrderStart	= request.getParameter("orderstart");
		String strOrderEnd		= request.getParameter("orderEnd");
		String strDistribStart	= request.getParameter("distribstart");
		String strDistribEnd	= request.getParameter("distribend");
		String strStartSendTime = request.getParameter("sendtimestart");
		String strEndSendTime	= request.getParameter("sendtimeend");
		String strStatus		= request.getParameter("status");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		
		Integer intWarehouseId = (WebUtils.isCanParseInt(strWarehouseId))?Integer.valueOf(strWarehouseId):0;
		
		RpcPagination<JXCMTOrderInfoDTO> pagination = new RpcPagination<>();
		JXCMTOrderInfoDTO dtoCondition = new JXCMTOrderInfoDTO();
		dtoCondition.setDispatchOrderCode(strOrderCode);
		if(!StringUtils.isEmpty(strDeviceType)){
			dtoCondition.setDeviceTypeId(Integer.valueOf(strDeviceType));
		}
		dtoCondition.setSendMerchantNo(strMerchantCode);
		dtoCondition.setOrderTimeStart(strOrderStart);
		dtoCondition.setOrderTimeEnd(strOrderEnd);	
		dtoCondition.setOrderDistribTimeStart(strDistribStart);
		dtoCondition.setOrderDistribTimeEnd(strDistribEnd);
		dtoCondition.setStartSendTime(strStartSendTime);
		dtoCondition.setEndSendTime(strEndSendTime);
		dtoCondition.setStatus(strStatus);
		dtoCondition.setWarehouseId(intWarehouseId);
		dtoCondition.setBsQueryType("T");
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<JXCMTOrderInfoDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrder(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("OrderInfoController::pageDispatchOrders end mapJson:" + mapJson);	
			 return mapJson;
        }
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<JXCMTOrderInfoDTO> rpcResult = rpcResponse.getResult();
		if(null != rpcResult){
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTOrderInfoDTO dto:rpcResult.getList()){
				String bsRemark = "";
				String merchantChannel = "";
				if(!StringUtils.isEmpty(dto.getChannelName())){
					merchantChannel = dto.getChannelName();
				}
				if(!StringUtils.isEmpty(dto.getCheckRemark())){
					bsRemark +=" ";
					bsRemark += dto.getCheckRemark();
				}
				logger.info("channelName:{},checkRemark:{},bsRemark:{}",dto.getChannelName(),dto.getCheckRemark(),bsRemark);
				String vehicleInfo = "";
				int total = (dto.getTotal()!=null?dto.getTotal():0);
				int sendQuantities = (dto.getSendQuantities()!=null?dto.getSendQuantities():0);
				int oweQuantities = total - sendQuantities;
				int deviceTypeId = (dto.getDeviceTypeId()!=null?dto.getDeviceTypeId():0);
				int ornetId = (dto.getOrNetId()!=null?dto.getOrNetId():56);
				int cardSelfId = (dto.getCardSelfId()!=null?dto.getOrNetId():62);	
				if(!StringUtils.isEmpty(dto.getBsParentBrandName())){
					vehicleInfo += "品牌:";
					vehicleInfo += dto.getBsParentBrandName();
				}
				if(!StringUtils.isEmpty(dto.getBsSubBrandName())){
					vehicleInfo += "\n子品牌:";
					vehicleInfo += dto.getBsSubBrandName();
				}
				if(!StringUtils.isEmpty(dto.getBsAudiName())){
					vehicleInfo += "\n车系:";
					vehicleInfo += dto.getBsAudiName();
				}
				if(!StringUtils.isEmpty(dto.getBsMotorcycle())){
					vehicleInfo += "\n车型:";
					vehicleInfo += dto.getBsMotorcycle();
				}
				if(!StringUtils.isEmpty(dto.getBsRemark())){
					vehicleInfo += "\n车价/颜色:";
					vehicleInfo += dto.getBsRemark();
				}
				objMap = new HashMap<String,String>();
				objMap.put("ordercode", dto.getDispatchOrderCode());
				objMap.put("merchantOrder", dto.getMerchantOrder());
				objMap.put("sendmerchantno", dto.getSendMerchantNo());
				objMap.put("sendmerchantname", dto.getSendMerchantName());
				objMap.put("merchantchannel", merchantChannel);
				objMap.put("ordertime", dto.getOrderTime());
				objMap.put("distribtime", dto.getCreatedTime());
				objMap.put("dadan", "");
				objMap.put("total", String.valueOf(total));
				objMap.put("sendquantities", String.valueOf(sendQuantities));
				objMap.put("owequantities", String.valueOf(oweQuantities));
				objMap.put("attribcode", dto.getMaterialCode());
				objMap.put("devicetypename", dto.getDeviceTypeName());	
				objMap.put("devicetypeid", String.valueOf(deviceTypeId));
				objMap.put("mnum", dto.getMnumName());
				objMap.put("ornet", dto.getOrNet());
				objMap.put("ornetid", String.valueOf(ornetId));
				objMap.put("cardself", dto.getCardSelf());
				objMap.put("cardSelfid", String.valueOf(cardSelfId));
				objMap.put("contacts", dto.getContacts());
				objMap.put("mobile", dto.getMobile());
				objMap.put("address", dto.getAddress());
				objMap.put("parentbrandname", dto.getBsParentBrandName());
				objMap.put("subbrandname", dto.getBsSubBrandName());
				objMap.put("audiname", dto.getBsAudiName());
				objMap.put("motocycle", dto.getBsMotorcycle());
				objMap.put("bsremark", dto.getBsRemark());
				objMap.put("scanNo", dto.getNdScan());
				objMap.put("materialName", dto.getMaterialName());
				objMap.put("factmaterialcode", dto.getFactMaterialCode()!=null?dto.getFactMaterialCode():"");
				objMap.put("factmaterialname", dto.getFactMaterialName()!=null?dto.getFactMaterialName():"");
				objMap.put("orderRemark", bsRemark);
				objMap.put("fastenconfig", dto.getFastenConfigDesc());
				objMap.put("optionconfig", dto.getOptionConfigDesc());
				objMap.put("vehicle", vehicleInfo);
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageDispatchOrders end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 获取发货单对应订单的配置信息
	* @Description: 获取发货单对应订单的配置信息
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_get_gh_order_config")
	@ResponseBody 
	public String getGhOrderConfig(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::getGhOrderConfig begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::getGhOrderConfig end mapJson:" + mapJson);	
			return mapJson;
		}
		String strOrderCode	= request.getParameter("ordercode");
		JXCMTGhMerchantOrderConfigDTO dtoCondition = new JXCMTGhMerchantOrderConfigDTO();
		dtoCondition.setDispatchOrderCode(strOrderCode);
		RpcResponse<JXCMTGhMerchantOrderConfigDTO> rpcResponse = jxcOrderRemote.getGhMerchantOrderConfig(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
        	 jsonMap.put("ret", errCodeEnum.getCode());
			 jsonMap.put("err", errCodeEnum.getDescrible());
			 mapJson = JacksonUtils.mapToJson(jsonMap);
			 logger.info("OrderInfoController::getGhOrderConfig end mapJson:" + mapJson);	
			 return mapJson;
        }
        JXCMTGhMerchantOrderConfigDTO dtoResult = rpcResponse.getResult();
        jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		jsonMap.put("fastenconfig", dtoResult.getFastenConfigDesc());
		jsonMap.put("optionconfig", dtoResult.getOptionConfigDesc());
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::getGhOrderConfig end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 获取签收单页面的发货单列表
	* @Description: 获取签收单页面的发货单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_page_sign_orders")
	@ResponseBody 
	public String pageSignOrders(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		Map<String, String> paramsMap = WebUtils.parseMap(request.getParameterMap());
		logger.info("OrderInfoController::pageSignOrders begin:{}",paramsMap);
		String pubKey = utilsProperty.getPubkey();
		if(!CheckChainSign.vertifySignature(request,pubKey))
		{	
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_SIGN.getDescrible());	
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageSignOrders end mapJson:" + mapJson);	
			return mapJson;
		}
		String strWarehouseId	= request.getParameter("warehouseid");
		String strBillStatus	= request.getParameter("billstatus");
		String strMerchantCode	= request.getParameter("merchantcode");
		String strSendTimeStart	= request.getParameter("sendtimestart");
		String strSendTimeEnd	= request.getParameter("sendtimeend");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		Integer intWarehouseId = (WebUtils.isCanParseInt(strWarehouseId))?Integer.valueOf(strWarehouseId):0;
		RpcPagination<JXCMTOrderInfoSignDTO> pagination = new RpcPagination<>();
		JXCMTOrderInfoSignDTO dtoCondition = new JXCMTOrderInfoSignDTO();
		dtoCondition.setWarehouseId(intWarehouseId);
		dtoCondition.setBillStatus(strBillStatus);
		dtoCondition.setSendMerchantNo(strMerchantCode);
		dtoCondition.setSendTimeStart(WebUtils.getDateFromString(strSendTimeStart));
		dtoCondition.setSendTimeEnd(WebUtils.getDateFromString(strSendTimeEnd));
		if(!StringUtils.isEmpty(strSendTimeStart)){
			dtoCondition.setSendTimeStartS(strSendTimeStart);
		}
		if(!StringUtils.isEmpty(strSendTimeEnd)){
			dtoCondition.setSendTimeEndS(strSendTimeEnd);
		}
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<JXCMTOrderInfoSignDTO>> rpcResponse = jxcOrderRemote.pageSignOrders(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum){
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageSignOrders end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<JXCMTOrderInfoSignDTO> rpcResult = rpcResponse.getResult();
		if(null != rpcResult){
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTOrderInfoSignDTO dto:rpcResult.getList()){
				objMap = new HashMap<String,String>();
				objMap.put("merchantorder", dto.getMerchantOrder());
				objMap.put("ordercode", dto.getDispatchOrderCode());
				objMap.put("sendmerchantname", dto.getSendMerchantName());
				objMap.put("sendmerchantcode", dto.getSendMerchantNo());
				objMap.put("materialcode", dto.getMaterialCode());
				objMap.put("materialname", dto.getMaterialName());
				if(dto.getBillStatus().equals("U")){
					objMap.put("billstatus", "未打单");
				}else{
					objMap.put("billstatus", "已打单");
				}
				objMap.put("billsignnumber", dto.getBillSignNumber());
				objMap.put("sendtime", dto.getLogisticsSendTime());
				objMap.put("shipments", StringUtils.isEmpty(dto.getLogisticsShipmentsQuantity())?"0":String.valueOf(dto.getLogisticsShipmentsQuantity()));
				objMap.put("logisticsno", dto.getLogisticsNo());
				objMap.put("logisticscpy", dto.getLogisticsCpy());
				objMap.put("contacts", dto.getContacts());
				objMap.put("mobile", dto.getMobile());
				objMap.put("address", dto.getAddress());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageSignOrders end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 获取签收单页面的发货单列表
	* @Description: 获取签收单页面的发货单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping(value="jxc_gen_bill_number",method={RequestMethod.POST})
	@ResponseBody 
	public String genBillNumber(HttpServletRequest request,@RequestBody String orderInfoSigns){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String,String> objMap = null;
		String strUserName	= request.getParameter("username");
		String billType		= request.getParameter("billtype");
		String total 		= request.getParameter("total");
		if(StringUtils.isEmpty(strUserName)){
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::genBillNumber end mapJson:" + mapJson);	
			return mapJson;
		}
		Integer uiTotal = (WebUtils.isCanParseInt(total))?Integer.valueOf(total):0;
		
		List<Map<String, String>> mapOrderInfoSigns = JacksonUtils.jsonToList(orderInfoSigns);
		logger.info("OrderInfoController::genBillNumber start orderInfoSigns:" + orderInfoSigns);
		List<JXCMTOrderInfoSignDTO> listOrderInfoSigns = new ArrayList<>();
		JXCMTOrderInfoSignDTO subDtoSign = null;
		for(Map<String, String> mapItem:mapOrderInfoSigns){
			subDtoSign = new JXCMTOrderInfoSignDTO();
			subDtoSign.setSendMerchantNo(mapItem.get("sendmerchantcode"));
			subDtoSign.setSendMerchantName(mapItem.get("sendmerchantname"));
			subDtoSign.setContacts(mapItem.get("contacts"));
			subDtoSign.setMobile(mapItem.get("mobile"));
			subDtoSign.setAddress(mapItem.get("address"));
			subDtoSign.setMerchantOrder(mapItem.get("merchantorder"));
			subDtoSign.setDispatchOrderCode(mapItem.get("ordercode"));
			subDtoSign.setMaterialCode(mapItem.get("materialcode"));
			subDtoSign.setMaterialName(mapItem.get("materialname"));
			subDtoSign.setLogisticsShipmentsQuantity(Integer.valueOf(mapItem.get("shipments")));
			subDtoSign.setLogisticsSendTime(mapItem.get("sendtime"));
			subDtoSign.setLogisticsNo(mapItem.get("logisticsno"));
			subDtoSign.setLogisticsCpy(mapItem.get("logisticscpy"));
			listOrderInfoSigns.add(subDtoSign);
		}
		
		if(!uiTotal.equals(listOrderInfoSigns.size()))
		{
			jsonMap.put("ret", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getCode());
			jsonMap.put("err", ErrorCodeEnum.ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::genBillNumber end mapJson:" + mapJson);	
			return mapJson;
		}
		
		JXCMTGenBillNumberDTO dtoCondition = new JXCMTGenBillNumberDTO();
		dtoCondition.setListOrderInfoSigns(listOrderInfoSigns);
		dtoCondition.setUserName(strUserName);
		dtoCondition.setBillType(billType);
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.genBillNumber(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum){
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::genBillNumber end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");	
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::genBillNumber end mapJson:" + mapJson);
		return mapJson;		
	}
	
	/**
	* @Title: 获取签收单列表
	* @Description: 获取签收单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_page_sign_bill_number")
	@ResponseBody 
	public String pageSignBillNumber(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		String strWarehouseId	= request.getParameter("warehouseid");
		String strBillNumber	= request.getParameter("billnumber");
		String billType		= request.getParameter("billtype");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		Integer intWarehouseId = (WebUtils.isCanParseInt(strWarehouseId))?Integer.valueOf(strWarehouseId):0;
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		RpcPagination<JXCMTBsBillDTO> pagination = new RpcPagination<>();
		JXCMTBsBillDTO dtoCondition = new JXCMTBsBillDTO();
		dtoCondition.setWarehouseId(intWarehouseId);
		dtoCondition.setBillSignNumber(strBillNumber);
		dtoCondition.setBillType(billType);
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<JXCMTBsBillDTO>> rpcResponse = jxcOrderRemote.pageSignBillNumber(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum){
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pageSignBillNumber end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<JXCMTBsBillDTO> rpcResult = rpcResponse.getResult();
		if(null != rpcResult){
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTBsBillDTO dto:rpcResult.getList()){
				objMap = new HashMap<String,String>();
				objMap.put("billnumber", dto.getBillSignNumber());
				objMap.put("sendmerchantno", dto.getSendMerchantNo());
				objMap.put("sendmerchantname", dto.getSendMerchantName());
				objMap.put("contacts", dto.getContacts());
				objMap.put("mobile", dto.getMobile());
				objMap.put("address", dto.getAddress());
				objMap.put("signurl", dto.getSignUrl());
				objMap.put("createdate", dto.getCreatedDate());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pageSignBillNumber end mapJson:" + mapJson);
		return mapJson;	
	}
	
	/**
	* @Title: 带PO单的订单列表
	* @Description: 带PO单的订单列表
	* @author QL 
	* @version V1.0  
	* @Company: Didihu.com.cn
	* @Copyright Copyright (c) 2017
	*/
	@RequestMapping("jxc_page_po_merchant_order")
	@ResponseBody 
	public String pagePoMerchantOrder(HttpServletRequest request){
		String mapJson = "";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Object> objList = null;
		Map<String,String> objMap = null;
		String strWarehouseId	= request.getParameter("warehouseid");
		String strMerchantOrder	= request.getParameter("merchantOrder");
		String curPage			= request.getParameter("pn");
		String pageSize			= request.getParameter("ps");
		Integer intPn = (WebUtils.isCanParseInt(curPage))?Integer.valueOf(curPage):0;
		Integer intPs = (WebUtils.isCanParseInt(pageSize))?Integer.valueOf(pageSize):1;
		RpcPagination<JXCMTSpMerchantOrderDTO> pagination = new RpcPagination<>();
		JXCMTSpMerchantOrderDTO dtoCondition = new JXCMTSpMerchantOrderDTO();
		if(!StringUtils.isEmpty(strMerchantOrder)){
			dtoCondition.setMerchantOrder(strMerchantOrder);
		}
		dtoCondition.setUrlDispatchBills("aaaa");
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(intPn);
		pagination.setPageSize(intPs);
		RpcResponse<RpcPagination<JXCMTSpMerchantOrderDTO>> rpcResponse = jxcOrderRemote.pageBsMerchantOrderBSP(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum){
			jsonMap.put("ret", errCodeEnum.getCode());
			jsonMap.put("err", errCodeEnum.getDescrible());
			mapJson = JacksonUtils.mapToJson(jsonMap);
			logger.info("OrderInfoController::pagePoMerchantOrder end mapJson:" + mapJson);	
			return mapJson;
		}
		jsonMap.put("ret", "0");
		jsonMap.put("err", "");
		RpcPagination<JXCMTSpMerchantOrderDTO> rpcResult = rpcResponse.getResult();
		if(null != rpcResult){
			jsonMap.put("pn", String.valueOf(rpcResult.getPageNum()));
			jsonMap.put("ps", String.valueOf(rpcResult.getPageSize()));
			jsonMap.put("pages", String.valueOf(rpcResult.getPages()));
			jsonMap.put("total", String.valueOf(rpcResult.getTotal()));
			objList = new ArrayList<Object>();
			for(JXCMTSpMerchantOrderDTO dto:rpcResult.getList()){
				objMap = new HashMap<String,String>();
				objMap.put("merchantorder", dto.getMerchantOrder());
				objMap.put("merchantname", dto.getMerchantName());
				objMap.put("contacts", dto.getBsAddressDto().getName());
				objMap.put("mobile", dto.getBsAddressDto().getMobile());
				String strAddress = "";
				if(!StringUtils.isEmpty(dto.getBsAddressDto().getProvinceName())){
					strAddress += dto.getBsAddressDto().getProvinceName();
				}
				if(!StringUtils.isEmpty(dto.getBsAddressDto().getCityName())){
					strAddress += dto.getBsAddressDto().getCityName();
				}
				if(!StringUtils.isEmpty(dto.getBsAddressDto().getAreaName())){
					strAddress += dto.getBsAddressDto().getAreaName();
				}
				objMap.put("address", strAddress);
				objMap.put("signurl", dto.getUrlDispatchBills());
				objList.add(objMap);
			}
			jsonMap.put("subs", objList);
		}
		mapJson = JacksonUtils.mapToJson(jsonMap);
		logger.info("OrderInfoController::pagePoMerchantOrder end mapJson:" + mapJson);
		return mapJson;	
	}
	
}
