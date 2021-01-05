package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * 运营平台设备明细导出搜索条件
 */
public class ExportDeviceFile implements Serializable{
    private Integer devTypeId;

    private String packageStatu;

    private String sn;

    private String iccid;

    private String imsi;

    private String userFlag;

    private Integer deviceCode;

    private Integer packageId;

    private String sendMerchantNo;

    private String outStorageStartDate;

    private String outStorageEndDate;

    private String packageUserStartDate;

    private String packageUserEndDate;

    public Integer getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Integer devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getPackageStatu() {
        return packageStatu;
    }

    public void setPackageStatu(String packageStatu) {
        this.packageStatu = packageStatu;
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

    public Integer getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        this.deviceCode = deviceCode;
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

    @Override
    public String toString() {
        return "ExportDeviceFile{" +
                "devTypeId=" + devTypeId +
                ", packageStatu='" + packageStatu + '\'' +
                ", sn='" + sn + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", userFlag='" + userFlag + '\'' +
                ", deviceCode=" + deviceCode +
                ", packageId=" + packageId +
                ", sendMerchantNo='" + sendMerchantNo + '\'' +
                ", outStorageStartDate='" + outStorageStartDate + '\'' +
                ", outStorageEndDate='" + outStorageEndDate + '\'' +
                ", packageUserStartDate='" + packageUserStartDate + '\'' +
                ", packageUserEndDate='" + packageUserEndDate + '\'' +
                '}';
    }
}