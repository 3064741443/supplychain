package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogisticsVO implements Serializable{

	//物流单号
	private String orderNumber;
	//物流公司
	private String company;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "LogisticsVO [orderNumber=" + orderNumber + ", company="
				+ company + "]";
	}
	
	
}
