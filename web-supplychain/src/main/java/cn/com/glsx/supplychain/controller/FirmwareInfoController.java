package cn.com.glsx.supplychain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;


@Controller
@RequestMapping("firmwareInfo")
public class FirmwareInfoController {
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	@RequestMapping("supplychain_application_version")
	@ResponseBody 
	public String getVersion(HttpServletRequest request){
		
		FirmwareInfo firmwareInfo = new FirmwareInfo();
		
		String strModel 		= request.getParameter("model");
		String strType			= request.getParameter("type");
		String strBoardVersion	= request.getParameter("boardversion");
		String strConfigure		= request.getParameter("configure");
		
		Integer iModel			= (strModel != null) ? Integer.parseInt(strModel):null;
		Integer iType			= (strType != null) ? Integer.parseInt(strType):null;
		Integer iBoardVersion	= (strBoardVersion != null)?Integer.parseInt(strBoardVersion):null;
		Integer iConfigure		= (strConfigure != null)?Integer.parseInt(strConfigure):null;
		
		firmwareInfo.setModel(iModel);
		firmwareInfo.setType(iType);
//		firmwareInfo.setBoardVersion(iBoardVersion);
		firmwareInfo.setConfigure(iConfigure);
		
		RpcResponse<List<FirmwareInfo>> firmwareInfoResult = supplyChainRemote.getFirmwareInfoList(firmwareInfo);
		
		JSONObject json = new JSONObject();
		
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) firmwareInfoResult.getError();
		
		if(errCodeEnum == null){
			
			json.put("ret", "0");
			json.put("err", "");
			
			List<FirmwareInfo> list = firmwareInfoResult.getResult();
			
			JSONArray array = new JSONArray();
			
			for(FirmwareInfo info : list){
				
				array.add(info.getFastenerVersion());
			}
			
			json.put("appversions", JSONArray.fromObject(array).toString());
		}else{
			
			json.put("ret", errCodeEnum.getCode());
			json.put("err", errCodeEnum.getDescrible());
		}
		
		return json.toString();	
	}
}
