package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderSignDTO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单", dataType = "string", required = false, example = "")
    private String merchantOrder;
	@ApiModelProperty(name = "merchantCode", notes = "商户号", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	@ApiModelProperty(name = "factoryName", notes = "生产厂家", dataType = "string", required = false, example = "")
    private String factoryName;
	@ApiModelProperty(name = "materialCode", notes = "物料编号", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "shipmentQuantity", notes = "发货数量", dataType = "string", required = false, example = "")
    private Integer shipmentQuantity;
	@ApiModelProperty(name = "shipmentTime", notes = "发货日期", dataType = "string", required = false, example = "")
    private String shipmentTime;
	@ApiModelProperty(name = "logisticsNo", notes = "物流单号", dataType = "string", required = false, example = "")
    private String logisticsNo;
	@ApiModelProperty(name = "logisticsCpy", notes = "物流公司", dataType = "string", required = false, example = "")
    private String logisticsCpy;
	@ApiModelProperty(name = "remark", notes = "订单备注", dataType = "string", required = false, example = "")
    private String remark;
	@ApiModelProperty(name = "address", notes = "收获地址", dataType = "string", required = false, example = "")
    private String address;
	@ApiModelProperty(name = "contacts", notes = "收获人", dataType = "string", required = false, example = "")
	private String contacts;
	@ApiModelProperty(name = "mobile", notes = "联系人", dataType = "string", required = false, example = "")
	private String mobile;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
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
	public Integer getShipmentQuantity() {
		return shipmentQuantity;
	}
	public void setShipmentQuantity(Integer shipmentQuantity) {
		this.shipmentQuantity = shipmentQuantity;
	}
	public String getShipmentTime() {
		return shipmentTime;
	}
	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderSignDTO [merchantOrder=" + merchantOrder
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", factoryName=" + factoryName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", shipmentQuantity=" + shipmentQuantity
				+ ", shipmentTime=" + shipmentTime + ", logisticsNo="
				+ logisticsNo + ", logisticsCpy=" + logisticsCpy + ", remark="
				+ remark + ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + "]";
	}
	
	

}
