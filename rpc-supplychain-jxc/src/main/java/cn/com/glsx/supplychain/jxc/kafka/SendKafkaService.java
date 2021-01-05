package cn.com.glsx.supplychain.jxc.kafka;

import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderKafkaMessageTypeEnum;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	
	public void notifyGenSignDispatchOrderInfo(GenBills genBills){
		
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(Constants.TASK_CFG_ID_JXC_BILL_GEN);
		kafkaMessage.setUserName(genBills.getUserName());
		kafkaMessage.setGenBills(genBills);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}
	
	public void notifyMerchantOrderExport(String userName,Integer taskConfigId,ExportMerchantOrder export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setMerchantOrderExport(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyBsMdbTransferOrder(String userName, Integer taskConfigId, ExportBsMdbTransferOrder export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportMdbTransferOrder(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}


	public void notifyJXCMdbTransferOrder(String userName, Integer taskConfigId, ExportJxcMdbTransferOrder export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportJxcMdbTransferOrder(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyStkMerchantStock(String userName, Integer taskConfigId, ExportSTKMerchantStock export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportSTKMerchantStock(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}


	public void notifyMerchantWarningWaresale(String userName, Integer taskConfigId, ExportMerchantWarningWaresale export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportMerchantWarningWaresale(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyMerchantWarningDeviceSn(String userName, Integer taskConfigId, ExportWarningDeviceSn export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportWarningDeviceSn(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}


	public void notifyMerchantStockSnStat(String userName, Integer taskConfigId, ExportMerchantStockSnStat export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportMerchantStockSnStat(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyMerchantStockSn(String userName, Integer taskConfigId, ExportMerchantStockSn export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportMerchantStockSn(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyJXCMTOrderInfo(String userName, Integer taskConfigId, ExportJXCMTOrderInfo export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportJXCMTOrderInfo(export);
		kafkaMessage.setUserName(userName);
		sendExportMerchantOrderKafkaMessage(kafkaMessage);
	}

	public void notifyDeviceFile(String userName, Integer taskConfigId, ExportDeviceFile export)
	{
		OperatorExportKafkaMessage kafkaMessage = new OperatorExportKafkaMessage();
		kafkaMessage.setTaskCfgId(taskConfigId);
		kafkaMessage.setExportDeviceFile(export);
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
