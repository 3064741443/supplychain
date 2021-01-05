package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckSubSnVO implements Serializable{

	public String orderCode;
	
	public String sn;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public String toString() {
		return "CheckSubSnVO [orderCode=" + orderCode + ", sn=" + sn + "]";
	}
	
	
	
}
