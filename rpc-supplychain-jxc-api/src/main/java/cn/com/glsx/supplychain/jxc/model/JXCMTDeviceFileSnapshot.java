package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "device_file_snapshot")
public class JXCMTDeviceFileSnapshot implements Serializable{
    private String sn;

    @Id
    private Integer id;

    private String packageStatu;

    private Integer packageId;

    private Integer androidPackageId;

    private Integer packageUserId;

    private String packageUserTime;

    private Integer userId;

    private String userTime;

    private Integer cardId;

    private String cardTime;

    private Integer firmwareId;

    private Integer vehicleId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageStatu() {
        return packageStatu;
    }

    public void setPackageStatu(String packageStatu) {
        this.packageStatu = packageStatu == null ? null : packageStatu.trim();
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

    public Integer getPackageUserId() {
        return packageUserId;
    }

    public void setPackageUserId(Integer packageUserId) {
        this.packageUserId = packageUserId;
    }

    public String getPackageUserTime() {
        return packageUserTime;
    }

    public void setPackageUserTime(String packageUserTime) {
        this.packageUserTime = packageUserTime == null ? null : packageUserTime.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime == null ? null : userTime.trim();
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime == null ? null : cardTime.trim();
    }

    public Integer getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Integer firmwareId) {
        this.firmwareId = firmwareId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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