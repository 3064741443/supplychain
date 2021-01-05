package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCLogisticsDTO implements Serializable{
	@ApiModelProperty(name = "code", notes = "物流编号", dataType = "string", required = true, example = "")
	private String code;
	@ApiModelProperty(name = "orderNumber", notes = "物流单号", dataType = "string", required = true, example = "")
	private String orderNumber;
	@ApiModelProperty(name = "company", notes = "物流公司", dataType = "string", required = true, example = "")
    private String company;
	@ApiModelProperty(name = "deliveryType", notes = "L:物流配送  O:线下配送", dataType = "string", required = false, example = "")
    private String deliveryType;
	@ApiModelProperty(name = "shipmentsQuantity", notes = "物流配送数量", dataType = "int", required = true, example = "")
    private Integer shipmentsQuantity;
	@ApiModelProperty(name = "sendTime", notes = "发货时间", dataType = "string", required = false, example = "")
	private String sendTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
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
		return "JXCLogisticsDTO{" +
				"code='" + code + '\'' +
				", orderNumber='" + orderNumber + '\'' +
				", company='" + company + '\'' +
				", deliveryType='" + deliveryType + '\'' +
				", shipmentsQuantity=" + shipmentsQuantity +
				", sendTime='" + sendTime + '\'' +
				'}';
	}
}
