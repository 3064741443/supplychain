package cn.com.glsx.supplychain.kafka;

import java.util.List;

import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.MerchantOrderKafkaMessageTypeEnum;
import cn.com.glsx.supplychain.service.bs.AfterSaleOrderServcie;

@Service
public class SendKafkaService {

	private static final Logger logger = LoggerFactory.getLogger(SendKafkaService.class);
	@Autowired
    private KafkaProducer kafkaProducer;
	
	public void notifyUpdateDispatchOrderInfo(List<String> listDispatchCodes)
	{
		MerchantOrderKafkaMessage kafkaMessage = new MerchantOrderKafkaMessage();
		UpdateDispatchOrder updateDispatchOrder = new UpdateDispatchOrder();
		updateDispatchOrder.setDispatchOrderCodses(listDispatchCodes);
		kafkaMessage.setMessageType(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_UPEC.getCode());
		kafkaMessage.setUpdateDispatchOrder(updateDispatchOrder);
		sendSignMerchantOrderKafkaMessage(kafkaMessage);
	}
	
	public void notifyMerchantOrderExport(String userName,String platName,ExportMerchantOrder export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		if(platName.equals("PLAT"))
		{
			kafkaMessage.setTaskCfgId(Constants.TASK_CFG_ID_MERCHANT_ORDER);
		}
		else
		{
			kafkaMessage.setTaskCfgId(Constants.TASK_CFG_ID_JXC_ORDER);
		}
		kafkaMessage.setMerchantOrderExport(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}
	
	public void sendSignMerchantOrderKafkaMessage(MerchantOrderKafkaMessage message)
	{
		logger.info("SendKafkaService::sendSignMerchantOrderKafkaMessage message:{}",message);
		try
		{	
			String strJson = JacksonUtils.beanToJson(message);
			kafkaProducer.send("supplychain_send_sign_merchant_order",strJson.getBytes());
			logger.info("SendKafkaService::sendSignMerchantOrderKafkaMessage success send message strJson:{}",strJson);
		}
		catch(Exception e)
		{
			logger.error("SendKafkaService::sendSignMerchantOrderKafkaMessage failed send message!");
		}
	}
	
	private void sendExportMerchantOrderKafkaMessage(OperatorExportKafkaMessage message)
	{
		logger.info("SendKafkaService::sendExportMerchantOrderKafkaMessage message:{}",message);
		try
		{
			String strJson = JacksonUtils.beanToJson(message);
			kafkaProducer.send("supplychain_send_export_cmd_message",strJson.getBytes());
			logger.info("SendKafkaService::sendExportMerchantOrderKafkaMessage success send message strJson:{}",strJson);
		}
		catch(Exception e)
		{
			logger.error("SendKafkaService::sendSignMerchantOrderKafkaMessage failed send message!");
		}
	}
}
