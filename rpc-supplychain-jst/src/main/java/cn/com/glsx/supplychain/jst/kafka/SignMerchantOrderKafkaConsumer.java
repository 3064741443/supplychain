package cn.com.glsx.supplychain.jst.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.oreframework.jms.kafka.consumer.KafkaConsumer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.jst.enums.MerchantOrderKafkaMessageTypeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantStockDeviceEnum;
import cn.com.glsx.supplychain.jst.mapper.BsMerchantOrderMapper;
import cn.com.glsx.supplychain.jst.mapper.BsMerchantStockDeviceMapper;
import cn.com.glsx.supplychain.jst.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.jst.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;
import cn.com.glsx.supplychain.jst.model.OrderInfo;
import cn.com.glsx.supplychain.jst.model.OrderInfoDetail;
import cn.com.glsx.supplychain.jst.service.BsMerchantStockDeviceService;
import cn.com.glsx.supplychain.jst.service.EcMerchantOrderService;
import cn.com.glsx.supplychain.jst.service.OrderService;
import cn.com.glsx.supplychain.jst.thread.SignMerchantOrderThread;
import cn.com.glsx.supplychain.jst.thread.UpdateDispatchOrderThread;
import cn.com.glsx.supplychain.jst.util.JstUtils;

@Component
public class SignMerchantOrderKafkaConsumer extends KafkaConsumer{
	
	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(256);

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderService orderService;
	@Autowired
	private BsMerchantStockDeviceService stockDeviceService;
	@Autowired
	private EcMerchantOrderService ecMerchantOrderService;
	
	public SignMerchantOrderKafkaConsumer() {
		super("supplychain_send_sign_merchant_order",false);
	}
	
	@Override
	public void processMessage(Object message)
	{
		logger.info("kafka接受的消息对象：{}", new String(getMessage()));
		String kafkaDfrom = new String(getMessage());
		MerchantOrderKafkaMessage kafkaMessage = (MerchantOrderKafkaMessage)JacksonUtils.jsonToBean(kafkaDfrom, MerchantOrderKafkaMessage.class);
		if(StringUtils.isEmpty(kafkaMessage.getMessageType()))
		{
			logger.info("messageType 为空");
			return;
		}
		if(kafkaMessage.getMessageType().equals(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_SIGN.getCode()))
		{
			SignMerchantOrderThread subThread = new SignMerchantOrderThread();
			subThread.setOrderService(orderService);
			subThread.setStockDeviceService(stockDeviceService);
			subThread.setSignMerchantOrder(kafkaMessage.getSignMerchantOrder());
			executor.execute(subThread);
		}
		else if(kafkaMessage.getMessageType().equals(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_UPEC.getCode()))
		{
			UpdateDispatchOrderThread subThread = new UpdateDispatchOrderThread();
			subThread.setEcMerchantOrderService(ecMerchantOrderService);
			subThread.setOrderService(orderService);
			subThread.setUpdateDispatchOrder(kafkaMessage.getUpdateDispatchOrder());
			executor.execute(subThread);
		}
		logger.info("kafka处理消息完毕");
	}

}
