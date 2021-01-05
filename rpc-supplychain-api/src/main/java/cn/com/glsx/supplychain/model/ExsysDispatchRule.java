package cn.com.glsx.supplychain.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ExsysDispatchRule implements Serializable{
    private Integer id;

    private Integer deviceType;

    private String mnumName;

    private String snHead;

    private Integer subject;

    private String issure;

    private String systemFlag;

    private String moduleFlag;

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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getMnumName() {
        return mnumName;
    }

    public void setMnumName(String mnumName) {
        this.mnumName = mnumName == null ? null : mnumName.trim();
    }

    public String getSnHead() {
        return snHead;
    }

    public void setSnHead(String snHead) {
        this.snHead = snHead == null ? null : snHead.trim();
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public String getIssure() {
        return issure;
    }

    public void setIssure(String issure) {
        this.issure = issure == null ? null : issure.trim();
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag == null ? null : systemFlag.trim();
    }

    public String getModuleFlag() {
        return moduleFlag;
    }

    public void setModuleFlag(String moduleFlag) {
        this.moduleFlag = moduleFlag == null ? null : moduleFlag.trim();
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