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

public class ExsysDispatchDvInitThread extends Thread{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ExsysDispatchLog exsysDispatchLog;
	
	private ExsysIdentify exsysIdentify;

	public ExsysDispatchLog getExsysDispatchLog() {
		return exsysDispatchLog;
	}

	public void setExsysDispatchLog(ExsysDispatchLog exsysDispatchLog) {
		this.exsysDispatchLog = exsysDispatchLog;
	}

	public ExsysIdentify getExsysIdentify() {
		return exsysIdentify;
	}

	public void setExsysIdentify(ExsysIdentify exsysIdentify) {
		this.exsysIdentify = exsysIdentify;
	}
	
	@Override
	public void run()
	{
		try
		{
			logger.info("ExsysDispatchDvInitThread::run handle exsysDispatchLog:{}",exsysDispatchLog);
			if(StringUtils.isEmpty(exsysDispatchLog)
					|| StringUtils.isEmpty(exsysIdentify))
			{
				return;
			}
			
			String url = exsysIdentify.getMethodUrl() + "function=syncInitialCmd&tosysflag=" + exsysIdentify.getSystemFlag() + "&fromsysflag=SUP&issign=0&sign=";
			Map<String,Object> mapData = new HashMap<String,Object>();
			List<Object> ListDeviceInfos = new ArrayList<Object>();
			Map<String,Object> mapDeviceInfo = new HashMap<String,Object>();
			mapData.put("total",1);
			mapDeviceInfo.put("sn", exsysDispatchLog.getSn());
			mapDeviceInfo.put("operatorname", exsysDispatchLog.getOperatorname());
			mapDeviceInfo.put("timestamp", exsysDispatchLog.getTimestamp());
			mapDeviceInfo.put("remark", exsysDispatchLog.getRemark());
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
				logger.info("ExsysDispatchDvInitThread::run handle failed retCode:"+retCode + " retDesc:" + retDesc);
			}
			else
			{
				logger.info("ExsysDispatchDvThread::run handle ok!");
			}
		}
		catch(Exception e)
		{
			logger.error("ExsysDispatchDvThread::run system error:" + e.getMessage());
		}
	}
	
}
