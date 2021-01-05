package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "device_file")
public class JXCMTDeviceFile implements Serializable{
	@Id
    private Integer id;

    private String sn;

    private Integer deviceCode;

    private String orderCode;

    private String verifCode;

    private String batchNo;

    private Integer packageId;

    private Integer androidPackageId;

    private String operatorMerchantNo;

    private String sendMerchantNo;

    private String orderMerchantNo;

    private String inStorageTime;

    private String outStorageTime;

    private String outStorageType;

    private String terminalDiscode;

    private String externalFlag;

    private String manufacturerCode;

    private Integer firmwareId;

    private Integer cardId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String imei;

    private String remark;

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

    public String getOrderMerchantNo() {
        return orderMerchantNo;
    }

    public void setOrderMerchantNo(String orderMerchantNo) {
        this.orderMerchantNo = orderMerchantNo == null ? null : orderMerchantNo.trim();
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

    public Integer getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Integer firmwareId) {
        this.firmwareId = firmwareId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}