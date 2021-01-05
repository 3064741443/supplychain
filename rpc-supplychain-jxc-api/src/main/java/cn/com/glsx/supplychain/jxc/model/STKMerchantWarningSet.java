package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "stk_merchant_warning_set")
public class STKMerchantWarningSet implements Serializable{
	@Id
    private Integer id;

    private String warningCode;

    private String warningType;

    private String channelType;

    private String merchantChannelId;
    
    private String merchantChannelName;
    
    private String merchantCode;
    
    private String merchantName;

    private Integer deviceType;

    private Double thresholdLow;

    private Double thresholdHigh;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarningCode() {
        return warningCode;
    }

    public void setWarningCode(String warningCode) {
        this.warningCode = warningCode == null ? null : warningCode.trim();
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType == null ? null : warningType.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }
    
    public String getMerchantChannelId() {
		return merchantChannelId;
	}

	public void setMerchantChannelId(String merchantChannelId) {
		this.merchantChannelId = merchantChannelId;
	}

	public String getMerchantChannelName() {
		return merchantChannelName;
	}

	public void setMerchantChannelName(String merchantChannelName) {
		this.merchantChannelName = merchantChannelName;
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

	public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Double getThresholdLow() {
        return thresholdLow;
    }

    public void setThresholdLow(Double thresholdLow) {
        this.thresholdLow = thresholdLow;
    }

    public Double getThresholdHigh() {
        return thresholdHigh;
    }

    public void setThresholdHigh(Double thresholdHigh) {
        this.thresholdHigh = thresholdHigh;
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
}