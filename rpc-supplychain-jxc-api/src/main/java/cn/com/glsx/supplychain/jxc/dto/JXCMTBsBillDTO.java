package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsBillDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "billSignNumber", notes = "单据编号", dataType = "string", required = true, example = "")
	private String billSignNumber;
	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号", dataType = "string", required = true, example = "张小姐")
	private String sendMerchantNo;
	@ApiModelProperty(name = "sendMerchantName", notes = "发往商户名称", dataType = "string", required = true, example = "张小姐")
	private String sendMerchantName;
	@ApiModelProperty(name = "address", notes = "地址", dataType = "string", required = false, example = "")
    private String address;
	@ApiModelProperty(name = "contacts", notes = "联系人", dataType = "string", required = false, example = "")
    private String contacts;
	@ApiModelProperty(name = "mobile", notes = "联系电话", dataType = "string", required = false, example = "")
    private String mobile;
	@ApiModelProperty(name = "signUrl", notes = "签收单下载地址", dataType = "string", required = false, example = "")
	private String signUrl;
	@ApiModelProperty(name = "warehouseId", notes = "仓库id", dataType = "int", required = false, example = "")
	private Integer warehouseId;
	@ApiModelProperty(name = "billType", notes = "SIGN:签收单 READ:备货单", dataType = "string", required = false, example = "")
	private String billType;
	@ApiModelProperty(name = "createdDate", notes = "创建时间", dataType = "string", required = false, example = "")
	private String createdDate;
	
	public String getBillSignNumber() {
		return billSignNumber;
	}
	public void setBillSignNumber(String billSignNumber) {
		this.billSignNumber = billSignNumber;
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
	public String getSignUrl() {
		return signUrl;
	}
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "JXCMTBsBillDTO [billSignNumber=" + billSignNumber
				+ ", sendMerchantNo=" + sendMerchantNo + ", sendMerchantName="
				+ sendMerchantName + ", address=" + address + ", contacts="
				+ contacts + ", mobile=" + mobile + ", signUrl=" + signUrl
				+ ", warehouseId=" + warehouseId + ", billType=" + billType
				+ ", createdDate=" + createdDate + "]";
	}
	
	
	
	
	
}
