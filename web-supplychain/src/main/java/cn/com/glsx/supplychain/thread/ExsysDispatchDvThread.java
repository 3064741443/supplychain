package cn.com.glsx.supplychain.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oreframework.util.http.HttpUtil;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.model.ExsysDispatchLog;
import cn.com.glsx.supplychain.model.ExsysIdentify;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.utils.WebUtils;

public class ExsysDispatchDvThread extends Thread{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<ExsysDispatchLog> listExsysDispatchLog;
	
	private SupplyChainRemote supplyChainRemote;

	public List<ExsysDispatchLog> getListExsysDispatchLog() {
		return listExsysDispatchLog;
	}

	public void setListExsysDispatchLog(List<ExsysDispatchLog> listExsysDispatchLog) {
		this.listExsysDispatchLog = listExsysDispatchLog;
	}

	public SupplyChainRemote getSupplyChainRemote() {
		return supplyChainRemote;
	}

	public void setSupplyChainRemote(SupplyChainRemote supplyChainRemote) {
		this.supplyChainRemote = supplyChainRemote;
	}
	
	@Override
	public void run()
	{		
		logger.info("ExsysDispatchDvThread::run handle listExsysDispatchLog:{}",listExsysDispatchLog);
		if(StringUtils.isEmpty(listExsysDispatchLog) ||
				StringUtils.isEmpty(supplyChainRemote))
		{
			return;
		}
		for(ExsysDispatchLog exsysDispatchLog:listExsysDispatchLog)
		{
			try
			{
				ExsysIdentify exsysIdentify = exsysDispatchLog.getExsysIdentify();
				if(StringUtils.isEmpty(exsysIdentify))
				{
					continue;
				}
				
				UserInfo userInfo = new UserInfo();
				userInfo.setUserName(exsysDispatchLog.getUpdatedBy());
				WebUtils.setRequest(userInfo);
				
				String url = exsysIdentify.getMethodUrl() + "function=syncDeviceInfos&tosysflag=" + exsysIdentify.getSystemFlag() + "&fromsysflag=SUP&issign=0&sign=";
				Map<String,Object> mapData = new HashMap<String,Object>();
				List<Object> ListDeviceInfos = new ArrayList<Object>();
				Map<String,Object> mapDeviceInfo = new HashMap<String,Object>();
				mapData.put("total",1);
				mapDeviceInfo.put("sn", exsysDispatchLog.getSn());
				mapDeviceInfo.put("iccid", exsysDispatchLog.getIccid());
				mapDeviceInfo.put("imei", exsysDispatchLog.getImei());
				mapDeviceInfo.put("imsi",exsysDispatchLog.getImsi());
				mapDeviceInfo.put("simphone", exsysDispatchLog.getSimPhone());
				mapDeviceInfo.put("cardno", exsysDispatchLog.getCardNo());
				mapDeviceInfo.put("packageno", exsysDispatchLog.getPackageNo());
				mapDeviceInfo.put("tomerchantno", exsysDispatchLog.getToMerchantNo());
				mapDeviceInfo.put("operatormerchantno", exsysDispatchLog.getOperatormerchantNo());
				mapDeviceInfo.put("factoryno", exsysDispatchLog.getFactoryNo());
				mapDeviceInfo.put("factoryname", exsysDispatchLog.getFactoryName());
				mapDeviceInfo.put("vcode", exsysDispatchLog.getVerifCode());
				mapDeviceInfo.put("dipatchno", exsysDispatchLog.getDispatchNo());
				mapDeviceInfo.put("productno", exsysDispatchLog.getProductNo());
				mapDeviceInfo.put("productname", exsysDispatchLog.getProductName());
				mapDeviceInfo.put("orderno", exsysDispatchLog.getOrderNo());
				mapDeviceInfo.put("thirdorderno", exsysDispatchLog.getThirdOrderNo());
				mapDeviceInfo.put("intimestamp", exsysDispatchLog.getInTimestamp());
				mapDeviceInfo.put("outtime", exsysDispatchLog.getOutTimestamp());
				mapDeviceInfo.put("mnumname", exsysDispatchLog.getMnumName());
				mapDeviceInfo.put("devtype", exsysDispatchLog.getDeviceType());
				mapDeviceInfo.put("devtypename", exsysDispatchLog.getDeviceTypeName());
				mapDeviceInfo.put("devicecode", exsysDispatchLog.getDeviceCode());
				mapDeviceInfo.put("devicecodename", exsysDispatchLog.getDeviceCodeName());
				mapDeviceInfo.put("moduleflag", exsysDispatchLog.getModuleFlag());
				mapDeviceInfo.put("softversion",exsysDispatchLog.getSoftversion());
				ListDeviceInfos.add(mapDeviceInfo);
				mapData.put("deviceinfos", ListDeviceInfos);
				String mapJson = JacksonUtils.mapToJson(mapData);
				String result = HttpUtil.postJson(url, mapJson, 3000, "utf-8");
				logger.info("ExsysDispatchDvThread::run url" + url);
				logger.info("ExsysDispatchDvThread::run mapJson" + mapJson);
				logger.info("ExsysDispatchDvThread::run result" + result);
				Map<String, Object> mapResult = JacksonUtils.jsonToMap(result, false);
				Integer retCode = (Integer)mapResult.get("retcode");
				String retDesc = (String)mapResult.get("retdesc");
				if(!retCode.equals(0))
				{		
					exsysDispatchLog.setRetCode(String.valueOf(retCode));
					exsysDispatchLog.setRetDesc(retDesc);
					exsysDispatchLog.setResentCount(exsysDispatchLog.getResentCount()+1);
					supplyChainRemote.saveExsysDispatchLog(userInfo, exsysDispatchLog);
					logger.info("ExsysDispatchDvThread::run handle failed retCode:"+retCode + " retDesc:" + retDesc);
				}
				else
				{
					exsysDispatchLog.setRetCode(String.valueOf(retCode));
					exsysDispatchLog.setRetDesc(retDesc);
					supplyChainRemote.saveExsysDispatchLog(userInfo, exsysDispatchLog);
					logger.info("ExsysDispatchDvThread::run handle ok!");
				}
			}
			catch(Exception e)
			{
				UserInfo userInfo = new UserInfo();
				userInfo.setUserName(exsysDispatchLog.getUpdatedBy());
				WebUtils.setRequest(userInfo);
				exsysDispatchLog.setRetCode(String.valueOf(505));
				exsysDispatchLog.setRetDesc("网络请求出问题");
				exsysDispatchLog.setResentCount(exsysDispatchLog.getResentCount()+1);
				supplyChainRemote.saveExsysDispatchLog(userInfo, exsysDispatchLog);
				logger.error("ExsysDispatchDvThread::run system error");
			}	
		}
	}
	
}
