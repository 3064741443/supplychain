package cn.com.glsx.supplychain.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class DeviceFile extends PageInfo implements Serializable{
    private Integer id;

    private String sn;
    
    private String imei;
    
    private Integer firmwareId;
    
    private Integer cardId;

    private Integer deviceCode;

    private String orderCode;

    private String verifCode;

    private String batchNo;

    private Integer packageId;

    private Integer androidPackageId;

    private String operatorMerchantNo;

    private String sendMerchantNo;

    private String inStorageTime;

    private String outStorageTime;

    private String outStorageType;

    private String terminalDiscode;

    private String externalFlag;  //IN:内部卡内部设备 EX:内部卡外部设备,AX:外部卡外部设备

    private String manufacturerCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    @Transient
    private DeviceFileSnapshot snapshot;//关系快照
    
    @Transient
    private DeviceCardManager deviceCardManager; //当前绑定卡
    
    @Transient
    private DeviceCardManager initialCardManager; //入库流量卡
    
    @Transient
    private DeviceUserManager deviceActiveUserManager; //激活用户
    
    @Transient
    private DeviceUserManager deviceUserManager; //绑定用户
    
    @Transient
    private DeviceCode deviceCodeTable; //设备类型表
    
    @Transient
    private String sendMerchantName; //发往商户名称
    
    @Transient
    private String sendMerchantType; //发往商户类型
    
    @Transient
    private String operatorMerchantName; //运营商户名称
    
    @Transient
    private String operatorMerchantType; //运营商户类型
    
    @Transient
    private String packageName; //商品名称 
    
    @Transient
    private String deviceTypeName; //设备类型名称
    
    @Transient
    private String modelName;	//模块类型
    
    @Transient
    private String softVersion; //入库版本号
    
    //查询条件
    @Transient
    private String searchKey;
    
    @Transient
    private String searchValue;
    
    @Transient
    private Integer searchType;
    
    @Transient
    private String packageStatu;
    
    @Transient
    private List<Integer> deviceCodes;

    @Transient
    private Integer totalSum;

    /**
     * 出库开始时间
     */
    @Transient
    private String outStorageStartDate;

    /**
     * 出库结束时间
     */
    @Transient
    private String outStorageEndDate;

    /**
     * 激活开始时间
     */
    @Transient
    private String packageUserStartDate;

    /**
     * 激活结束时间
     */
    @Transient
    private String packageUserEndDate;

    /**
     * 调拨订单ID
     */
    @Transient
    private String tranOrderCode;

    /**
     * 最后所在门店
     */
    @Transient
    private String lastSendStore;

    /**
     * 最后所在服务商
     */
    @Transient
    private String lastServiceProvider;

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    public String getLastSendStore() {
        return lastSendStore;
    }

    public void setLastSendStore(String lastSendStore) {
        this.lastSendStore = lastSendStore;
    }

    public String getLastServiceProvider() {
        return lastServiceProvider;
    }

    public void setLastServiceProvider(String lastServiceProvider) {
        this.lastServiceProvider = lastServiceProvider;
    }

    public String getPackageUserStartDate() {
        return packageUserStartDate;
    }

    public void setPackageUserStartDate(String packageUserStartDate) {
        this.packageUserStartDate = packageUserStartDate;
    }

    public String getPackageUserEndDate() {
        return packageUserEndDate;
    }

    public void setPackageUserEndDate(String packageUserEndDate) {
        this.packageUserEndDate = packageUserEndDate;
    }

    public String getOutStorageStartDate() {
        return outStorageStartDate;
    }

    public void setOutStorageStartDate(String outStorageStartDate) {
        this.outStorageStartDate = outStorageStartDate;
    }

    public String getOutStorageEndDate() {
        return outStorageEndDate;
    }

    public void setOutStorageEndDate(String outStorageEndDate) {
        this.outStorageEndDate = outStorageEndDate;
    }

    public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPackageStatu() {
		return packageStatu;
	}

	public void setPackageStatu(String packageStatu) {
		this.packageStatu = packageStatu;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

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

    public Integer getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode == null ? null : verifCode.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

    public String getOperatorMerchantNo() {
        return operatorMerchantNo;
    }

    public void setOperatorMerchantNo(String operatorMerchantNo) {
        this.operatorMerchantNo = operatorMerchantNo == null ? null : operatorMerchantNo.trim();
    }

    public String getSendMerchantNo() {
        return sendMerchantNo;
    }

    public void setSendMerchantNo(String sendMerchantNo) {
        this.sendMerchantNo = sendMerchantNo == null ? null : sendMerchantNo.trim();
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime == null ? null : inStorageTime.trim();
    }

    public String getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(String outStorageTime) {
        this.outStorageTime = outStorageTime == null ? null : outStorageTime.trim();
    }

    public String getOutStorageType() {
        return outStorageType;
    }

    public void setOutStorageType(String outStorageType) {
        this.outStorageType = outStorageType == null ? null : outStorageType.trim();
    }

    public String getTerminalDiscode() {
        return terminalDiscode;
    }

    public void setTerminalDiscode(String terminalDiscode) {
        this.terminalDiscode = terminalDiscode == null ? null : terminalDiscode.trim();
    }

    public String getExternalFlag() {
        return externalFlag;
    }

    public void setExternalFlag(String externalFlag) {
        this.externalFlag = externalFlag == null ? null : externalFlag.trim();
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
    
	public DeviceFileSnapshot getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(DeviceFileSnapshot snapshot) {
		this.snapshot = snapshot;
	}

	public DeviceCardManager getDeviceCardManager() {
		return deviceCardManager;
	}

	public void setDeviceCardManager(DeviceCardManager deviceCardManager) {
		this.deviceCardManager = deviceCardManager;
	}

	public DeviceCardManager getInitialCardManager() {
		return initialCardManager;
	}

	public void setInitialCardManager(DeviceCardManager initialCardManager) {
		this.initialCardManager = initialCardManager;
	}

	public DeviceUserManager getDeviceActiveUserManager() {
		return deviceActiveUserManager;
	}

	public void setDeviceActiveUserManager(DeviceUserManager deviceActiveUserManager) {
		this.deviceActiveUserManager = deviceActiveUserManager;
	}

	public DeviceUserManager getDeviceUserManager() {
		return deviceUserManager;
	}

	public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
		this.deviceUserManager = deviceUserManager;
	}

	public DeviceCode getDeviceCodeTable() {
		return deviceCodeTable;
	}

	public void setDeviceCodeTable(DeviceCode deviceCodeTable) {
		this.deviceCodeTable = deviceCodeTable;
	}

	public String getSendMerchantName() {
		return sendMerchantName;
	}

	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}

	public String getSendMerchantType() {
		return sendMerchantType;
	}

	public void setSendMerchantType(String sendMerchantType) {
		this.sendMerchantType = sendMerchantType;
	}

	public String getOperatorMerchantName() {
		return operatorMerchantName;
	}

	public void setOperatorMerchantName(String operatorMerchantName) {
		this.operatorMerchantName = operatorMerchantName;
	}

	public String getOperatorMerchantType() {
		return operatorMerchantType;
	}

	public void setOperatorMerchantType(String operatorMerchantType) {
		this.operatorMerchantType = operatorMerchantType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	
	public Integer getFirmwareId() {
		return firmwareId;
	}

	public void setFirmwareId(Integer firmwareId) {
		this.firmwareId = firmwareId;
	}
	
	public List<Integer> getDeviceCodes() {
		return deviceCodes;
	}

	public void setDeviceCodes(List<Integer> deviceCodes) {
		this.deviceCodes = deviceCodes;
	}

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "DeviceFile{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", imei='" + imei + '\'' +
                ", firmwareId=" + firmwareId +
                ", cardId=" + cardId +
                ", deviceCode=" + deviceCode +
                ", orderCode='" + orderCode + '\'' +
                ", verifCode='" + verifCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", packageId=" + packageId +
                ", androidPackageId=" + androidPackageId +
                ", operatorMerchantNo='" + operatorMerchantNo + '\'' +
                ", sendMerchantNo='" + sendMerchantNo + '\'' +
                ", inStorageTime='" + inStorageTime + '\'' +
                ", outStorageTime='" + outStorageTime + '\'' +
                ", outStorageType='" + outStorageType + '\'' +
                ", terminalDiscode='" + terminalDiscode + '\'' +
                ", externalFlag='" + externalFlag + '\'' +
                ", manufacturerCode='" + manufacturerCode + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", snapshot=" + snapshot +
                ", deviceCardManager=" + deviceCardManager +
                ", initialCardManager=" + initialCardManager +
                ", deviceActiveUserManager=" + deviceActiveUserManager +
                ", deviceUserManager=" + deviceUserManager +
                ", deviceCodeTable=" + deviceCodeTable +
                ", sendMerchantName='" + sendMerchantName + '\'' +
                ", sendMerchantType='" + sendMerchantType + '\'' +
                ", operatorMerchantName='" + operatorMerchantName + '\'' +
                ", operatorMerchantType='" + operatorMerchantType + '\'' +
                ", packageName='" + packageName + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", softVersion='" + softVersion + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", searchType=" + searchType +
                ", packageStatu='" + packageStatu + '\'' +
                ", deviceCodes=" + deviceCodes +
                ", totalSum=" + totalSum +
                ", outStorageStartDate='" + outStorageStartDate + '\'' +
                ", outStorageEndDate='" + outStorageEndDate + '\'' +
                ", packageUserStartDate='" + packageUserStartDate + '\'' +
                ", packageUserEndDate='" + packageUserEndDate + '\'' +
                ", tranOrderCode='" + tranOrderCode + '\'' +
                ", lastSendStore='" + lastSendStore + '\'' +
                ", lastServiceProvider='" + lastServiceProvider + '\'' +
                '}';
    }
}