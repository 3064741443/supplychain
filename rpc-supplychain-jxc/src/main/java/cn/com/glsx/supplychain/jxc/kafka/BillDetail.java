package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BillDetail implements Serializable{

	private String merchantOrder;
	private String dispatchOrderCode;
	private String materialCode;
	private String materialName;
	private Integer logisticsShipmentsQuantity;
	private String logisticsSendTime;
	private String logisticsNo;
	private String logisticsCpy;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
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
	public Integer getLogisticsShipmentsQuantity() {
		return logisticsShipmentsQuantity;
	}
	public void setLogisticsShipmentsQuantity(Integer logisticsShipmentsQuantity) {
		this.logisticsShipmentsQuantity = logisticsShipmentsQuantity;
	}
	public String getLogisticsSendTime() {
		return logisticsSendTime;
	}
	public void setLogisticsSendTime(String logisticsSendTime) {
		this.logisticsSendTime = logisticsSendTime;
	}
	public String getLogisticsNo() {
		return logisticsNo;
	}
	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	public String getLogisticsCpy() {
		return logisticsCpy;
	}
	public void setLogisticsCpy(String logisticsCpy) {
		this.logisticsCpy = logisticsCpy;
	}
	@Override
	public String toString() {
		return "BillDetail [merchantOrder=" + merchantOrder
				+ ", dispatchOrderCode=" + dispatchOrderCode
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", logisticsShipmentsQuantity="
				+ logisticsShipmentsQuantity + ", logisticsSendTime="
				+ logisticsSendTime + ", logisticsNo=" + logisticsNo
				+ ", logisticsCpy=" + logisticsCpy + "]";
	}
	
}
