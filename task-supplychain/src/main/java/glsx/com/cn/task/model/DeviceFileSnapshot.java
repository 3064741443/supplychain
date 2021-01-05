package glsx.com.cn.task.model;

import java.util.Date;

import javax.persistence.Transient;

public class DeviceFileSnapshot {
    private String sn;

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
    
    @Transient
    private Integer statisCount;
    
    
    
	public Integer getStatisCount() {
		return statisCount;
	}

	public void setStatisCount(Integer statisCount) {
		this.statisCount = statisCount;
	}

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

	
	
	
	@Override
	public String toString() {
		return "DeviceFileSnapshot [sn=" + sn + ", id=" + id
				+ ", packageStatu=" + packageStatu + ", packageId=" + packageId
				+ ", androidPackageId=" + androidPackageId + ", packageUserId="
				+ packageUserId + ", packageUserTime=" + packageUserTime
				+ ", userId=" + userId + ", userTime=" + userTime + ", cardId="
				+ cardId + ", cardTime=" + cardTime + ", firmwareId="
				+ firmwareId + ", vehicleId=" + vehicleId + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}

	public void clear()
	{
		sn = null;
		id = null;
		packageStatu = null;
		packageId = null;
		androidPackageId = null;
		packageUserId = null;
		packageUserTime = null;
		userId = null;
		userTime = null;
		cardId = null;
		cardTime = null;
		firmwareId = null;
		vehicleId = null;
		deletedFlag = null;
		createdBy = null;
		createdDate = null;
		updatedBy = null;
		updatedDate = null;
	}
	

    
    
}