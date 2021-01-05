package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MerchantOrderKafkaMessage implements Serializable{
	
	private Byte messageType;
	
	private SignMerchantOrder signMerchantOrder;
	
	private UpdateDispatchOrder updateDispatchOrder;
	
	public Byte getMessageType() {
		return messageType;
	}

	public void setMessageType(Byte messageType) {
		this.messageType = messageType;
	}

	public SignMerchantOrder getSignMerchantOrder() {
		return signMerchantOrder;
	}

	public void setSignMerchantOrder(SignMerchantOrder signMerchantOrder) {
		this.signMerchantOrder = signMerchantOrder;
	}

	public UpdateDispatchOrder getUpdateDispatchOrder() {
		return updateDispatchOrder;
	}

	public void setUpdateDispatchOrder(UpdateDispatchOrder updateDispatchOrder) {
		this.updateDispatchOrder = updateDispatchOrder;
	}

	
}
