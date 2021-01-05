package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTDeviceFileDTO extends JXCMTBaseDTO implements Serializable{
    private Integer id;

    private Integer devTypeId;

    private String sn;

    private String iccid;

    private String imsi;

    private String userFlag;
    
    private String imei;

    private Integer deviceCode;

    private String deviceName;

    private Integer packageId;

    private String sendMerchantNo;

    private String inStorageTime;

    private String outStorageTime;

    private String deviceTypeName; //设备类型名称
    
    private Integer searchType;
    
    private String packageStatu;

    /**
     * 出库开始时间
     */
    private String outStorageStartDate;

    /**
     * 出库结束时间
     */
    private String outStorageEndDate;

    /**
     * 激活开始时间
     */
    private String packageUserStartDate;

    /**
     * 激活结束时间
     */
    private String packageUserEndDate;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Integer devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getSendMerchantNo() {
        return sendMerchantNo;
    }

    public void setSendMerchantNo(String sendMerchantNo) {
        this.sendMerchantNo = sendMerchantNo;
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public String getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(String outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getPackageStatu() {
        return packageStatu;
    }

    public void setPackageStatu(String packageStatu) {
        this.packageStatu = packageStatu;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JXCMTDeviceFileDTO{" +
                "id=" + id +
                ", devTypeId=" + devTypeId +
                ", sn='" + sn + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", userFlag='" + userFlag + '\'' +
                ", imei='" + imei + '\'' +
                ", deviceCode=" + deviceCode +
                ", deviceName='" + deviceName + '\'' +
                ", packageId=" + packageId +
                ", sendMerchantNo='" + sendMerchantNo + '\'' +
                ", inStorageTime='" + inStorageTime + '\'' +
                ", outStorageTime='" + outStorageTime + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", searchType=" + searchType +
                ", packageStatu='" + packageStatu + '\'' +
                ", outStorageStartDate='" + outStorageStartDate + '\'' +
                ", outStorageEndDate='" + outStorageEndDate + '\'' +
                ", packageUserStartDate='" + packageUserStartDate + '\'' +
                ", packageUserEndDate='" + packageUserEndDate + '\'' +
                ", userId=" + userId +
                '}';
    }
}