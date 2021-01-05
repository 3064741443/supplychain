package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderExportBspDTO implements Serializable{

	//子订单信息
	private String merchantOrderNum;
	private Integer channelId;
	private String merchantName;
	private String channelName;
	private String materialCode;	
    private String materialName;
    private String mdeviceTypeName;
    private String checkRemark;
    private Date checkTime;	
	private String checkBy;
	private String subjectName;
    //发货信息
    private String dispatchOrderCode;
    private String MotorcycleDesc;
    private String dispatchOrderStatus;
    private Integer bsCheckQuantity;
    private Integer bsSendQuantity;
    private Integer bsOweQuantity;
    private String bsParentBrandName;
    private String bsSubBrandName;
    private String bsAudiName;
    private String bsMotorcycle;
    private String bsRemark;
    private String address;
    private String contacts;
    private String mobile;
    private String warehouseName;
    //物流信息
    private String orderNumber;
    private String company;
    private Integer shipmentsQuantity;
    private String sendTime;
	private String fastenConfigDesc;
	private String optionConfigDesc;

	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}

	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}

	public String getOptionConfigDesc() {
		return optionConfigDesc;
	}

	public void setOptionConfigDesc(String optionConfigDesc) {
		this.optionConfigDesc = optionConfigDesc;
	}

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	public String getMdeviceTypeName() {
		return mdeviceTypeName;
	}
	public void setMdeviceTypeName(String mdeviceTypeName) {
		this.mdeviceTypeName = mdeviceTypeName;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getMotorcycleDesc() {
		return MotorcycleDesc;
	}
	public void setMotorcycleDesc(String motorcycleDesc) {
		MotorcycleDesc = motorcycleDesc;
	}
	public String getDispatchOrderStatus() {
		return dispatchOrderStatus;
	}
	public void setDispatchOrderStatus(String dispatchOrderStatus) {
		this.dispatchOrderStatus = dispatchOrderStatus;
	}
	public Integer getBsCheckQuantity() {
		return bsCheckQuantity;
	}
	public void setBsCheckQuantity(Integer bsCheckQuantity) {
		this.bsCheckQuantity = bsCheckQuantity;
	}
	public Integer getBsSendQuantity() {
		return bsSendQuantity;
	}
	public void setBsSendQuantity(Integer bsSendQuantity) {
		this.bsSendQuantity = bsSendQuantity;
	}
	public Integer getBsOweQuantity() {
		return bsOweQuantity;
	}
	public void setBsOweQuantity(Integer bsOweQuantity) {
		this.bsOweQuantity = bsOweQuantity;
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
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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
	public String getBsParentBrandName() {
		return bsParentBrandName;
	}
	public void setBsParentBrandName(String bsParentBrandName) {
		this.bsParentBrandName = bsParentBrandName;
	}
	public String getBsSubBrandName() {
		return bsSubBrandName;
	}
	public void setBsSubBrandName(String bsSubBrandName) {
		this.bsSubBrandName = bsSubBrandName;
	}
	public String getBsAudiName() {
		return bsAudiName;
	}
	public void setBsAudiName(String bsAudiName) {
		this.bsAudiName = bsAudiName;
	}
	public String getBsMotorcycle() {
		return bsMotorcycle;
	}
	public void setBsMotorcycle(String bsMotorcycle) {
		this.bsMotorcycle = bsMotorcycle;
	}
	public String getBsRemark() {
		return bsRemark;
	}
	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getMerchantOrderNum() {
		return merchantOrderNum;
	}
	public void setMerchantOrderNum(String merchantOrderNum) {
		this.merchantOrderNum = merchantOrderNum;
	}
    
    
}
