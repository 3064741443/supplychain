package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BsLogisticsVo implements Serializable{

	private String orderNumber;
    private String company;
    private String accept;
    private Integer shipmentsQuantity;
	private String sendTime;
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
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public Integer getShipmentsQuantity() {
		return shipmentsQuantity;
	}
	public void setShipmentsQuantity(Integer shipmentsQuantity) {
		this.shipmentsQuantity = shipmentsQuantity;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "JXCMTBsLogisticsDTO [orderNumber=" + orderNumber + ", company="
				+ company + ", accept=" + accept + ", shipmentsQuantity="
				+ shipmentsQuantity + ", sendTime=" + sendTime + "]";
	}

}
