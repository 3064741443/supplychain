package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class DeviceCode extends PageInfo implements Serializable{
    private Integer id;

    private Integer deviceCode;

    private String deviceName;

    private Integer merchantId;
    
    @Transient
    private String merchantName;

    private Integer typeId;

    private String status;

    private String manufacturerCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String remark;
    
    @Transient
    private DeviceType deviceType;

    
    
    public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "DeviceCode ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (deviceCode != null ? "deviceCode=" + deviceCode + ", " : "")
				+ (deviceName != null ? "deviceName=" + deviceName + ", " : "")
				+ (merchantId != null ? "merchantId=" + merchantId + ", " : "")
				+ (merchantName != null ? "merchantName=" + merchantName + ", "
						: "")
				+ (typeId != null ? "typeId=" + typeId + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (manufacturerCode != null ? "manufacturerCode="
						+ manufacturerCode + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", "
						: "")
				+ (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "")
				+ (updatedDate != null ? "updatedDate=" + updatedDate + ", "
						: "")
				+ (deletedFlag != null ? "deletedFlag=" + deletedFlag + ", "
						: "")
				+ (remark != null ? "remark=" + remark + ", " : "")
				+ (deviceType != null ? "deviceType=" + deviceType.toString() : "") + "]";
	}

	
	
	

	
    
    
}