package cn.com.glsx.supplychain.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.oreframework.jms.kafka.consumer.KafkaConsumer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.ExsysDispatchLog;
import cn.com.glsx.supplychain.model.ExsysIdentify;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;
import cn.com.glsx.supplychain.thread.ExsysDispatchDvThread;
import cn.com.glsx.supplychain.utils.WebUtils;

@Component
public class ExsysDispatchKafkaDvConsum extends KafkaConsumer{
	
	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(256);
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainRemote supplyChainRemote;

	public ExsysDispatchKafkaDvConsum() {
		super("dispatch_deviceinfo_to_exsystem",false);
	}
	
	@Override
	public void processMessage(Object obj)
	{
		logger.info("kafka接受的消息对象：{}", new String(getMessage()));
		String kafkaDfrom = new String(getMessage());
		List<ExsysDispatchLog> listExsysDispatchLog = new ArrayList<ExsysDispatchLog>();
		Map<String,ExsysIdentify> mapExsysIdentify = new HashMap<String,ExsysIdentify>();
		List<ExsysDispatchLog> listKafkaMessage = JacksonUtils.getList(kafkaDfrom, ExsysDispatchLog.class);
		if(StringUtils.isEmpty(listKafkaMessage))
		{
			logger.info("kafka消息为空");
			return;
		}
		
		for(ExsysDispatchLog exsysDispatchLog:listKafkaMessage)
		{
			if(StringUtils.isEmpty(exsysDispatchLog.getSn()))
			{
				logger.info("sn为空");
				return;
			}
			if(StringUtils.isEmpty(exsysDispatchLog.getSystemFlag()))
			{
				logger.info("系统标识为空");
				return;
			}
			if(StringUtils.isEmpty(exsysDispatchLog.getModuleFlag()))
			{
				logger.info("模块标识为空");
				return;
			}
			
			setExsysIdentifyBySystemFlag(exsysDispatchLog,mapExsysIdentify);	
			listExsysDispatchLog.add(exsysDispatchLog);
		}
		
		ExsysDispatchDvThread exsysDispatchDvThread = new ExsysDispatchDvThread();
		exsysDispatchDvThread.setListExsysDispatchLog(listExsysDispatchLog);
		exsysDispatchDvThread.setSupplyChainRemote(supplyChainRemote);
		executor.execute(exsysDispatchDvThread);
		logger.info("kafka处理消息完毕");
	}
	
	private void setExsysIdentifyBySystemFlag(ExsysDispatchLog condition,Map<String,ExsysIdentify> mapExsysIdentify)
	{
		ExsysIdentify result = null;
		String strKey = condition.getSystemFlag() + "DV";
		result = mapExsysIdentify.get(strKey);
		if(StringUtils.isEmpty(result))
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(condition.getUpdatedBy());
			WebUtils.setRequest(userInfo);
			ExsysIdentify exsysIdentify = new ExsysIdentify();
			exsysIdentify.setSystemFlag(condition.getSystemFlag());
			exsysIdentify.setType("DV");
			RpcResponse<ExsysIdentify> rsp = supplyChainRemote.getExsysIdentifyBySystemFlag(userInfo, exsysIdentify);
			ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
			if(!StringUtils.isEmpty(errCodeEnum))
			{
				logger.info("rpc调用失败  errcode:" + errCodeEnum.getCode() + " errdesc:" + errCodeEnum.getDescrible());
				supplyChainRemote.saveExsysDispatchLog(userInfo, condition);
				return ;
			}
			result = rsp.getResult();
			if(!StringUtils.isEmpty(result))
			{
				mapExsysIdentify.put(strKey, result);
			}
		}
		if(!StringUtils.isEmpty(result))
		{
			condition.setExsysIdentify(result);
		}
	}
	
}
