package cn.com.glsx.supplychain.jxc.kafka;

import java.util.Date;

//商务调拨订单导出条件搜索
public class ExportBsMdbTransferOrder {
	private Date startTime;
	private Date endTime;
	private String tranOrderCode;
	private String serviceProviderName;
	private String  materialCode;
	private String materialName;
	private String orderStatus;
	private String orderSource;

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTranOrderCode() {
		return tranOrderCode;
	}

	public void setTranOrderCode(String tranOrderCode) {
		this.tranOrderCode = tranOrderCode;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "ExportBsMdbTransferOrder{" +
				"startTime=" + startTime +
				", endTime=" + endTime +
				", tranOrderCode='" + tranOrderCode + '\'' +
				", serviceProviderName='" + serviceProviderName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", orderSource='" + orderSource + '\'' +
				'}';
	}
}
