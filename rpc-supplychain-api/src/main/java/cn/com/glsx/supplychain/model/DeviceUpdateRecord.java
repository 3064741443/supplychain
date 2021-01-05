package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class DeviceUpdateRecord extends PageInfo implements Serializable{
    private Integer id;

    private String sn;

    private Integer flagId;
    
    @Transient
    private String flagName; 

    private Integer preFlagId;
    
    @Transient
    private String preFlagName;

    private String flagType;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }

    public Integer getPreFlagId() {
        return preFlagId;
    }

    public void setPreFlagId(Integer preFlagId) {
        this.preFlagId = preFlagId;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType == null ? null : flagType.trim();
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

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public String getPreFlagName() {
		return preFlagName;
	}

	public void setPreFlagName(String preFlagName) {
		this.preFlagName = preFlagName;
	}

	@Override
	public String toString() {
		return "DeviceUpdateRecord [id=" + id + ", sn=" + sn + ", flagId="
				+ flagId + ", flagName=" + flagName + ", preFlagId="
				+ preFlagId + ", preFlagName=" + preFlagName + ", flagType="
				+ flagType + ", deletedFlag=" + deletedFlag + "]";
	}

	
    
}