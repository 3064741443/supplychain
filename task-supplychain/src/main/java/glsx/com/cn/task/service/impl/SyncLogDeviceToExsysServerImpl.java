package glsx.com.cn.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glsx.cloudframework.core.util.StringUtils;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import cn.com.glsx.insurance.core.utils.JacksonUtils;
import glsx.com.cn.task.common.Constants;
import glsx.com.cn.task.manager.FlowCardServiceManager;
import glsx.com.cn.task.mapper.ExsysDispatchLogMapper;
import glsx.com.cn.task.model.ExsysDispatchLog;
import glsx.com.cn.task.service.SyncLogDeviceToExsysServer;

@Service
public class SyncLogDeviceToExsysServerImpl implements SyncLogDeviceToExsysServer{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExsysDispatchLogMapper dispatchMapper;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
    private FlowCardServiceManager flowCardServiceManager;

	@Override
	public void dispatchLogDeviceToExsysServer() 
	{	
		try
		{
			logger.info("执行dispatchLogDeviceToExsysServer方法");
			List<ExsysDispatchLog> deviceList = dispatchMapper.listFailedExsysDispatchLog();
			logger.info("deviceList.size=" + deviceList.size());
			if(deviceList.size() == 0)
			{
				return;
			}
			for(ExsysDispatchLog dispatchLog:deviceList)
			{
				if(StringUtils.isEmpty(dispatchLog.getImsi()) && !StringUtils.isEmpty(dispatchLog.getIccid()))
				{
					FillCardInfo(dispatchLog);
				}
				dispatchLog.setUpdatedDate(new Date());
			}
			batchSendKafka(deviceList);
			logger.info("执行dispatchLogDeviceToExsysServer方法 ok!");
		}
		catch(Exception e)
		{
			logger.error("同步失败" + e.getMessage(), e);
		}
	}
	
	private void batchSendKafka(List<ExsysDispatchLog> listKafkaDispatchLog)
	{
		List<ExsysDispatchLog> listLog = new ArrayList<ExsysDispatchLog>();
		int i=0;
		for(ExsysDispatchLog dispatchLog:listKafkaDispatchLog)
		{
			listLog.add(dispatchLog);
			i++;
			if(i%Constants.batch_dispatch_log_step == 0)
			{
				String strJson = JacksonUtils.beanToJson(listLog);
				logger.info("发送kafka数据 strJson" + strJson);
				kafkaProducer.send("dispatch_deviceinfo_to_exsystem",
						strJson.getBytes());
				listLog.clear();
			}
		}
		String strJson = JacksonUtils.beanToJson(listLog);
		logger.info("发送kafka数据 strJson" + strJson);
		kafkaProducer.send("dispatch_deviceinfo_to_exsystem",
				strJson.getBytes());
	}
	
	private void FillCardInfo(ExsysDispatchLog dispatchLog) throws Exception
	{
		//非GPS不参与分发
//		if(!dispatchLog.getDeviceType().equals(8))
//		{
//			return;
//		}
		FlowCardRequest flowCardRequest = new FlowCardRequest();
		String strIccid = dispatchLog.getIccid();
		if(StringUtils.isEmpty(strIccid))
		{
			return;
		}

		flowCardRequest.setKeyWord(strIccid.toUpperCase());
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		flowCardRequest.setTime(time);  //时间
		flowCardRequest.setInvoker("glsx");
		flowCardRequest.setVersion("1.0.0"); //版本号
		flowCardRequest.setConsumer("task-supplychain"); //项目名称
		FlowResponse<Flowcard> response = flowCardServiceManager.getFlowCardByIccid(flowCardRequest);
		logger.info("SyncLogDeviceToExsysServerImpl::FillCardInfo 同步设备ICCID:" + strIccid.toUpperCase() + ",流量卡服务返回的code：" + response.getCode() + "返回错误信息：" + response.getMessage());
		if (response == null || !response.getCode().equals("1000") || response.getEntiy() == null)
		{
			logger.info("SyncLogDeviceToExsysServerImpl::FillCardInfo 【flowCardServiceManager---->getFlowCardByIccid】通过iccid查询卡信息失败,参数:" + flowCardRequest.toString());
			return;
		}
		dispatchLog.setImsi(response.getEntiy().getImsi());
		dispatchLog.setSimPhone(response.getEntiy().getCardNo());
		dispatchLog.setCardNo(response.getEntiy().getCardNo());
	}

}
