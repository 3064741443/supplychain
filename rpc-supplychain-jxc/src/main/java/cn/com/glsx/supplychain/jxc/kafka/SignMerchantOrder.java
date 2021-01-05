package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SignMerchantOrder implements Serializable{
	
	public String orderCode;
	
	public Long LogiticsId;

	
	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public Long getLogiticsId() {
		return LogiticsId;
	}


	public void setLogiticsId(Long logiticsId) {
		LogiticsId = logiticsId;
	}


	@Override
	public String toString() {
		return "SignMerchantOrder [orderCode=" + orderCode
				+ ", LogiticsId=" + LogiticsId + "]";
	}


	
	

}
