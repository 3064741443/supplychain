package cn.com.glsx.supplychain.kafka;

import java.util.Date;

//商户订单导出条件搜索
public class ExportMerchantOrder {

	private String orderNumber;
	private String materialCode;
	private String type;
	private Byte channel;
	private String merchantCode;
	private String merchantName;
	private Byte status;
	private Date startDate;
	private Date endDate;
	private Date checkStartDate;
	private Date checkEndDate;
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
	@Override
	public String toString() {
		return "MerchantOrderExport [orderNumber=" + orderNumber
				+ ", materialCode=" + materialCode + ", type=" + type
				+ ", channel=" + channel + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", status=" + status
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", checkStartDate=" + checkStartDate + ", checkEndDate="
				+ checkEndDate + "]";
	}
	
	
}
