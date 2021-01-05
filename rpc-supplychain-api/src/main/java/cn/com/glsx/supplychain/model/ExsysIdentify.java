package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ExsysIdentify implements Serializable{
    private Integer id;

    private String systemFlag;

    private String type;

    private String isSign;

    private String pubSign;

    private String methodUrl;

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

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag == null ? null : systemFlag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign == null ? null : isSign.trim();
    }

    public String getPubSign() {
        return pubSign;
    }

    public void setPubSign(String pubSign) {
        this.pubSign = pubSign == null ? null : pubSign.trim();
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public void setMethodUrl(String methodUrl) {
        this.methodUrl = methodUrl == null ? null : methodUrl.trim();
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
		return "ExsysIdentify [id=" + id + ", systemFlag=" + systemFlag
				+ ", type=" + type + ", isSign=" + isSign + ", pubSign="
				+ pubSign + ", methodUrl=" + methodUrl + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
   
    
    
}