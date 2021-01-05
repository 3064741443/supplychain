package glsx.com.cn.task.model;

import java.util.Date;


public class DeviceFile{
    private Integer id;

    private String sn;
    
    private String imei;
    
    private Integer firmwareId;
    
    private Integer cardId;

    private Integer deviceCode;

    private String orderCode;

    private String verifCode;

    private String batchNo;

    private Integer packageId;

    private Integer androidPackageId;

    private String operatorMerchantNo;

    private String sendMerchantNo;
    
    private String orderMerchantNo;

    private String inStorageTime;

    private String outStorageTime;

    private String outStorageType;

    private String terminalDiscode;

    private String externalFlag;  //IN:内部卡内部设备 EX:内部卡外部设备,AX:外部卡外部设备

    private String manufacturerCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    
    
    
	public String getOrderMerchantNo() {
		return orderMerchantNo;
	}

	public void setOrderMerchantNo(String orderMerchantNo) {
		this.orderMerchantNo = orderMerchantNo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getFirmwareId() {
		return firmwareId;
	}

	public void setFirmwareId(Integer firmwareId) {
		this.firmwareId = firmwareId;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getVerifCode() {
		return verifCode;
	}

	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getAndroidPackageId() {
		return androidPackageId;
	}

	public void setAndroidPackageId(Integer androidPackageId) {
		this.androidPackageId = androidPackageId;
	}

	public String getOperatorMerchantNo() {
		return operatorMerchantNo;
	}

	public void setOperatorMerchantNo(String operatorMerchantNo) {
		this.operatorMerchantNo = operatorMerchantNo;
	}

	public String getSendMerchantNo() {
		return sendMerchantNo;
	}

	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}

	public String getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(String inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public String getOutStorageTime() {
		return outStorageTime;
	}

	public void setOutStorageTime(String outStorageTime) {
		this.outStorageTime = outStorageTime;
	}

	public String getOutStorageType() {
		return outStorageType;
	}

	public void setOutStorageType(String outStorageType) {
		this.outStorageType = outStorageType;
	}

	public String getTerminalDiscode() {
		return terminalDiscode;
	}

	public void setTerminalDiscode(String terminalDiscode) {
		this.terminalDiscode = terminalDiscode;
	}

	public String getExternalFlag() {
		return externalFlag;
	}

	public void setExternalFlag(String externalFlag) {
		this.externalFlag = externalFlag;
	}

	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "DeviceFile [id=" + id + ", sn=" + sn + ", firmwareId="
				+ firmwareId + ", cardId=" + cardId + ", deviceCode="
				+ deviceCode + ", orderCode=" + orderCode + ", verifCode="
				+ verifCode + ", batchNo=" + batchNo + ", packageId="
				+ packageId + ", androidPackageId=" + androidPackageId
				+ ", operatorMerchantNo=" + operatorMerchantNo
				+ ", sendMerchantNo=" + sendMerchantNo + ", inStorageTime="
				+ inStorageTime + ", outStorageTime=" + outStorageTime
				+ ", outStorageType=" + outStorageType + ", terminalDiscode="
				+ terminalDiscode + ", externalFlag=" + externalFlag
				+ ", manufacturerCode=" + manufacturerCode + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}

	public void clear()
	{
		id 			= null;
		sn 			= null;
		firmwareId	= null;
		cardId		= null;
		deviceCode	= null;
		orderCode	= null;
		verifCode	= null;
		batchNo		= null;
		packageId	= null;
		androidPackageId = null;
		operatorMerchantNo = null;
		sendMerchantNo = null;
		inStorageTime = null;
		outStorageType = null;
		terminalDiscode = null;
		externalFlag = null;
		manufacturerCode = null;
		createdBy = null;
		createdDate = null;
		updatedBy = null;
		updatedDate = null;
		deletedFlag = null;
	}
    
    
    
    
}