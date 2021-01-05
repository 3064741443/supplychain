package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Table(name = "stk_merchant_warning_waresale")
public class STKMerchantWarningWaresale implements Serializable{
	@Id
    private Integer id;

    private String warningCode;

    private String merchantCode;

    private String merchantName;

    private String warningMonth;

    private Integer deviceType;

    private Double waresaleRate;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    @Transient
    private Byte merchantChannelId;
    
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getWarningMonth() {
        return warningMonth;
    }

    public void setWarningMonth(String warningMonth) {
        this.warningMonth = warningMonth == null ? null : warningMonth.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Double getWaresaleRate() {
        return waresaleRate;
    }

    public void setWaresaleRate(Double waresaleRate) {
        this.waresaleRate = waresaleRate;
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

	public Byte getMerchantChannelId() {
		return merchantChannelId;
	}

	public void setMerchantChannelId(Byte merchantChannelId) {
		this.merchantChannelId = merchantChannelId;
	}
    
    
}