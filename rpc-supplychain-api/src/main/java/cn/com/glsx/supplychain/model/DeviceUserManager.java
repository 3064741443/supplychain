package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DeviceUserManager implements Serializable{
    private Integer id;

    private String userFlag;

    private String flagType;

    private Integer companyId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag == null ? null : userFlag.trim();
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType == null ? null : flagType.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

	@Override
	public String toString() {
		return "DeviceUserManager ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (userFlag != null ? "userFlag=" + userFlag + ", " : "")
				+ (flagType != null ? "flagType=" + flagType + ", " : "")
				+ (companyId != null ? "companyId=" + companyId + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", "
						: "")
				+ (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "")
				+ (updatedDate != null ? "updatedDate=" + updatedDate + ", "
						: "")
				+ (deletedFlag != null ? "deletedFlag=" + deletedFlag + ", "
						: "") + (remark != null ? "remark=" + remark : "")
				+ "]";
	}

    
    
    
}