package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsLogisticsDTO implements Serializable{

	@ApiModelProperty(name = "orderNumber", notes = "物流单号", dataType = "string", required = false, example = "")
	private String orderNumber;
	@ApiModelProperty(name = "company", notes = "物流公司", dataType = "string", required = false, example = "")
    private String company;
	@ApiModelProperty(name = "accept", notes = "是否签收Y:签收 N:不签收", dataType = "string", required = false, example = "")
    private String accept;
	@ApiModelProperty(name = "shipmentsQuantity", notes = "发货数量", dataType = "int", required = false, example = "")
    private Integer shipmentsQuantity;
	@ApiModelProperty(name = "sendTime", notes = "发货时间", dataType = "string", required = false, example = "")
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
