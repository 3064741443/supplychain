package cn.com.glsx.supplychain.jxc.kafka;

import java.util.Date;

//商户订单导出条件搜索
public class ExportMerchantOrder {
	
	private String moMerchantOrder;
	private String orderNumber;
	private String productCode;
	private String productName;
	private String materialCode;
	private String materialName;
	private String dispatchOrderCode;
	private String orderMaterialCode;
	private String orderMaterialName;
	private String type;
	private Byte channel;
	private String merchantCode;
	private String merchantName;
	private Byte status;
	private Byte signStatus;
	private Date startDate;
	private Date endDate;
	private Date checkStartDate;
	private Date checkEndDate;
	private Byte productTypeId;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Byte getChannel() {
		return channel;
	}
	public void setChannel(Byte channel) {
		this.channel = channel;
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
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getCheckStartDate() {
		return checkStartDate;
	}
	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}
	public Date getCheckEndDate() {
		return checkEndDate;
	}
	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}
	public Byte getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Byte signStatus) {
		this.signStatus = signStatus;
	}
	public String getMoMerchantOrder() {
		return moMerchantOrder;
	}
	public void setMoMerchantOrder(String moMerchantOrder) {
		this.moMerchantOrder = moMerchantOrder;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Byte getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Byte productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getOrderMaterialName() {
		return orderMaterialName;
	}
	public void setOrderMaterialName(String orderMaterialName) {
		this.orderMaterialName = orderMaterialName;
	}
	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}
	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}
	@Override
	public String toString() {
		return "ExportMerchantOrder [moMerchantOrder=" + moMerchantOrder
				+ ", orderNumber=" + orderNumber + ", productCode="
				+ productCode + ", productName=" + productName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", orderMaterialCode=" + orderMaterialCode
				+ ", orderMaterialName=" + orderMaterialName + ", type=" + type
				+ ", channel=" + channel + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", status=" + status
				+ ", signStatus=" + signStatus + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", checkStartDate=" + checkStartDate
				+ ", checkEndDate=" + checkEndDate + ", productTypeId="
				+ productTypeId + "]";
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	
	
}
