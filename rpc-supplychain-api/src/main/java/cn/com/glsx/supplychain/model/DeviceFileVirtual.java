package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DeviceFileVirtual implements Serializable{
    private Integer id;

    private String sn;
    
    private Integer deviceCode;

    private String phone;

    private String iccid;

    private String imsi;

    private String operatorMerchantNo;

    private String verifCode;

    private String batchNo;

    private Integer packageId;

    private Integer androidPackageId;

    private String sendMerchantNo;

    private String manufacturerCode;
    
    private String outStorageType;
    
    private Integer firmwareId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    
    public Integer getFirmwareId() {
		return firmwareId;
	}

	public void setFirmwareId(Integer firmwareId) {
		this.firmwareId = firmwareId;
	}

	public String getOutStorageType() {
		return outStorageType;
	}

	public void setOutStorageType(String outStorageType) {
		this.outStorageType = outStorageType;
	}

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
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
        this.sn = sn == null ? null : sn.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getOperatorMerchantNo() {
        return operatorMerchantNo;
    }

    public void setOperatorMerchantNo(String operatorMerchantNo) {
        this.operatorMerchantNo = operatorMerchantNo == null ? null : operatorMerchantNo.trim();
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode == null ? null : verifCode.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

    public String getSendMerchantNo() {
        return sendMerchantNo;
    }

    public void setSendMerchantNo(String sendMerchantNo) {
        this.sendMerchantNo = sendMerchantNo == null ? null : sendMerchantNo.trim();
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode == null ? null : manufacturerCode.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
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
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

	@Override
	public String toString() {
		return "DeviceFileVirtual [id=" + id + ", sn=" + sn + ", deviceCode="
				+ deviceCode + ", phone=" + phone + ", iccid=" + iccid
				+ ", imsi=" + imsi + ", operatorMerchantNo="
				+ operatorMerchantNo + ", verifCode=" + verifCode
				+ ", batchNo=" + batchNo + ", packageId=" + packageId
				+ ", androidPackageId=" + androidPackageId
				+ ", sendMerchantNo=" + sendMerchantNo + ", manufacturerCode="
				+ manufacturerCode + ", outStorageType=" + outStorageType
				+ ", deletedFlag=" + deletedFlag + "]";
	}

	
    
    
}