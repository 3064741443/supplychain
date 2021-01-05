package glsx.com.cn.task.kafka;

import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendKafkaService {

	private static final Logger logger = LoggerFactory.getLogger(SendKafkaService.class);
	@Autowired
    private KafkaProducer kafkaProducer;
	
	public void sendSignMerchantOrderKafkaMessage(MerchantOrderKafkaMessage message)
	{
		logger.info("SendKafkaService::sendSignMerchantOrderKafkaMessage message:{}",message);
		try
		{	
			String strJson = JacksonUtils.beanToJson(message);
			kafkaProducer.send("supplychain_send_sign_merchant_order",strJson.getBytes());
			logger.info("SendKafkaService::sendSignMerchantOrderKafkaMessage success send message");
		}
		catch(Exception e)
		{
			logger.error("SendKafkaService::sendSignMerchantOrderKafkaMessage failed send message!");
		}
	}
}
