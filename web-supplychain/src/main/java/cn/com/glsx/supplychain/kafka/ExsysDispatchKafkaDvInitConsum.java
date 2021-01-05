package cn.com.glsx.supplychain.kafka;

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
import cn.com.glsx.supplychain.thread.ExsysDispatchDvInitThread;
import cn.com.glsx.supplychain.utils.WebUtils;

@Component
public class ExsysDispatchKafkaDvInitConsum extends KafkaConsumer{

private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(256);
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SupplyChainRemote supplyChainRemote;
	
	public ExsysDispatchKafkaDvInitConsum() {
		super("dispatch_initdevice_to_exsystem",false);
	}
	
	@Override
	public void processMessage(Object obj)
	{
		logger.info("kafka接受的消息对象：{}", new String(getMessage()));
		String kafkaDfrom = new String(getMessage());
		
		ExsysDispatchLog exsysDispatchLog = (ExsysDispatchLog)JacksonUtils.jsonToBean(kafkaDfrom, ExsysDispatchLog.class);
		
		if(StringUtils.isEmpty(exsysDispatchLog.getSn()))
		{
			return;
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(exsysDispatchLog.getOperatorname());
		WebUtils.setRequest(userInfo);
		RpcResponse<ExsysDispatchLog> rsp = supplyChainRemote.getExsysDispatchLog(userInfo, exsysDispatchLog);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.error("errcode:{},describle:{}",errCodeEnum.getCode(),errCodeEnum.getDescrible());
			return;
		}
		exsysDispatchLog = rsp.getResult();
		if(StringUtils.isEmpty(exsysDispatchLog))
		{
			logger.info("sn不在以往发送记录里");
			return;
		}
		
		cancelExsysDispatchLog(exsysDispatchLog);
		
		ExsysIdentify exsysIdentify = getExsysIdentifyBySystemFlag(exsysDispatchLog);
		ExsysDispatchDvInitThread dispatchThread = new ExsysDispatchDvInitThread();
		dispatchThread.setExsysDispatchLog(exsysDispatchLog);
		dispatchThread.setExsysIdentify(exsysIdentify);	
		executor.execute(dispatchThread);
		logger.info("kafka处理消息完毕");	
	}
	
	private ExsysIdentify getExsysIdentifyBySystemFlag(ExsysDispatchLog condition)
	{
		ExsysIdentify result = null;
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(condition.getUpdatedBy());
		WebUtils.setRequest(userInfo);
		
		ExsysIdentify exsysIdentify = new ExsysIdentify();
		exsysIdentify.setSystemFlag(condition.getSystemFlag());
		exsysIdentify.setType("IL");
		
		RpcResponse<ExsysIdentify> rsp = supplyChainRemote.getExsysIdentifyBySystemFlag(userInfo, exsysIdentify);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.info("ExsysDispatchKafkaDvInitConsum::getExsysIdentifyBySystemFlag rpc调用失败  errcode:" + errCodeEnum.getCode() + " errdesc:" + errCodeEnum.getDescrible());
			supplyChainRemote.saveExsysDispatchLog(userInfo, condition);
			return result;
		}
		result = rsp.getResult();
		return result;
	}
	
	private void cancelExsysDispatchLog(ExsysDispatchLog condition)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(condition.getUpdatedBy());
		WebUtils.setRequest(userInfo);
		
		condition.setResentCount(condition.getResentMax());
		RpcResponse<Integer> rsp = supplyChainRemote.saveExsysDispatchLog(userInfo, condition);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.info("ExsysDispatchKafkaDvInitConsum::cancelExsysDispatchLog rpc调用失败  errcode:" + errCodeEnum.getCode() + " errdesc:" + errCodeEnum.getDescrible());
			supplyChainRemote.saveExsysDispatchLog(userInfo, condition);
		}
	}
}
