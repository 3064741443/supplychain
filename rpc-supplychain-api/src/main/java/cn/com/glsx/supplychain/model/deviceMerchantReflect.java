package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "device_merchant_reflect")
public class deviceMerchantReflect{
	@Id
    private Integer id;

    private String sendToMerchantCode;

    private String sendToMerchantName;

    private String serverMerchantCode;

    private String serverMerchantName;

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

    public String getSendToMerchantCode() {
        return sendToMerchantCode;
    }

    public void setSendToMerchantCode(String sendToMerchantCode) {
        this.sendToMerchantCode = sendToMerchantCode == null ? null : sendToMerchantCode.trim();
    }

    public String getSendToMerchantName() {
        return sendToMerchantName;
    }

    public void setSendToMerchantName(String sendToMerchantName) {
        this.sendToMerchantName = sendToMerchantName == null ? null : sendToMerchantName.trim();
    }

    public String getServerMerchantCode() {
        return serverMerchantCode;
    }

    public void setServerMerchantCode(String serverMerchantCode) {
        this.serverMerchantCode = serverMerchantCode == null ? null : serverMerchantCode.trim();
    }

    public String getServerMerchantName() {
        return serverMerchantName;
    }

    public void setServerMerchantName(String serverMerchantName) {
        this.serverMerchantName = serverMerchantName == null ? null : serverMerchantName.trim();
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