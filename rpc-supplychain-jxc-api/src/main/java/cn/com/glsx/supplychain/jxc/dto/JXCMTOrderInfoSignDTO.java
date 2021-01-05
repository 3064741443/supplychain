package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTOrderInfoSignDTO implements Serializable{
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = false, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "merchantOrder", notes = "子订单号", dataType = "string", required = false, example = "JXC202007241346310752")
	private String merchantOrder;
	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号", dataType = "string", required = false, example = "")
	private String sendMerchantNo;
	@ApiModelProperty(name = "sendMerchantName", notes = "发往商户名称", dataType = "string", required = false, example = "")
	private String sendMerchantName;
	@ApiModelProperty(name = "address", notes = "地址", dataType = "string", required = false, example = "")
    private String address;
	@ApiModelProperty(name = "contacts", notes = "联系人", dataType = "string", required = false, example = "")
    private String contacts;
	@ApiModelProperty(name = "mobile", notes = "联系电话", dataType = "string", required = false, example = "")
    private String mobile;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "billSignNumber", notes = "签收单单据号", dataType = "string", required = false, example = "")
	private String billSignNumber;
	@ApiModelProperty(name = "logisticsShipmentsQuantity", notes = "发货数量", dataType = "int", required = false, example = "")
	private Integer logisticsShipmentsQuantity;
	@ApiModelProperty(name = "logisticsSendTime", notes = "发货时间", dataType = "string", required = false, example = "")
	private String logisticsSendTime;
	@ApiModelProperty(name = "logisticsNo", notes = "物流单号", dataType = "string", required = false, example = "")
	private String logisticsNo;
	@ApiModelProperty(name = "logisticsCpy", notes = "物流公司", dataType = "string", required = false, example = "")
    private String logisticsCpy;
	@ApiModelProperty(name = "billStatus", notes = "打单状态 U:未打单 A:已打单", dataType = "string", required = false, example = "")
	private String billStatus;
	@ApiModelProperty(name = "sendTimeStart", notes = "搜索条件开始时间", dataType = "object", required = false, example = "")
	private Date sendTimeStart;
	@ApiModelProperty(name = "sendTimeEnd", notes = "搜索条件结束时间", dataType = "object", required = false, example = "")
	private Date sendTimeEnd;
	@ApiModelProperty(name = "sendTimeStartS", notes = "搜索条件开始时间字符串格式", dataType = "object", required = false, example = "")
	private String sendTimeStartS;
	@ApiModelProperty(name = "sendTimeEndS", notes = "搜索条件结束时间字符串格式", dataType = "object", required = false, example = "")
	private String sendTimeEndS;
	@ApiModelProperty(name = "warehouseId", notes = "搜索条件所属仓库id", dataType = "string", required = false, example = "")
	private Integer warehouseId;
	
	
	public String getSendTimeStartS() {
		return sendTimeStartS;
	}
	public void setSendTimeStartS(String sendTimeStartS) {
		this.sendTimeStartS = sendTimeStartS;
	}
	public String getSendTimeEndS() {
		return sendTimeEndS;
	}
	public void setSendTimeEndS(String sendTimeEndS) {
		this.sendTimeEndS = sendTimeEndS;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public String getSendMerchantName() {
		return sendMerchantName;
	}
	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
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
	public String getBillSignNumber() {
		return billSignNumber;
	}
	public void setBillSignNumber(String billSignNumber) {
		this.billSignNumber = billSignNumber;
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
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public Date getSendTimeStart() {
		return sendTimeStart;
	}
	public void setSendTimeStart(Date sendTimeStart) {
		this.sendTimeStart = sendTimeStart;
	}
	public Date getSendTimeEnd() {
		return sendTimeEnd;
	}
	public void setSendTimeEnd(Date sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	@Override
	public String toString() {
		return "JXCMTOrderInfoSignDTO [dispatchOrderCode=" + dispatchOrderCode
				+ ", merchantOrder=" + merchantOrder + ", sendMerchantNo="
				+ sendMerchantNo + ", sendMerchantName=" + sendMerchantName
				+ ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", billSignNumber="
				+ billSignNumber + ", logisticsShipmentsQuantity="
				+ logisticsShipmentsQuantity + ", logisticsSendTime="
				+ logisticsSendTime + ", logisticsNo=" + logisticsNo
				+ ", logisticsCpy=" + logisticsCpy + ", billStatus="
				+ billStatus + ", sendTimeStart=" + sendTimeStart
				+ ", sendTimeEnd=" + sendTimeEnd + ", sendTimeStartS="
				+ sendTimeStartS + ", sendTimeEndS=" + sendTimeEndS
				+ ", warehouseId=" + warehouseId + "]";
	}
	
	
	
}
