package cn.com.glsx.supplychain.jst.vo;

import javax.persistence.Transient;
import java.io.Serializable;

@SuppressWarnings("serial")
public class LogisticsVO implements Serializable{

	//物流单号
	private String orderNumber;
	//物流公司
	private String company;

	/**
	 * 配送方式：O：线上配送  F:线下配送
	 */
	@Transient
	private  String shipType;

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

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
		return "LogisticsVO{" +
				"orderNumber='" + orderNumber + '\'' +
				", company='" + company + '\'' +
				", shipType='" + shipType + '\'' +
				'}';
	}


}
