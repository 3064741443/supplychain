package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

public class OrderInfo {
    private Integer id;

    private String orderCode;

    private Integer total;

    private String status;

    private Integer deviceId;

    private String deviceName;

    private String packageOne;

    private String packageTwo;

    private String attribCode;

    private String operatorMerchantNo;

    private String batch;

    private Integer warehouseId;

    private String sendMerchantNo;

    private String sendMerchantName;

    private String address;

    private String contacts;

    private String mobile;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne == null ? null : packageOne.trim();
    }

    public String getPackageTwo() {
        return packageTwo;
    }

    public void setPackageTwo(String packageTwo) {
        this.packageTwo = packageTwo == null ? null : packageTwo.trim();
    }

    public String getAttribCode() {
        return attribCode;
    }

    public void setAttribCode(String attribCode) {
        this.attribCode = attribCode == null ? null : attribCode.trim();
    }

    public String getOperatorMerchantNo() {
        return operatorMerchantNo;
    }

    public void setOperatorMerchantNo(String operatorMerchantNo) {
        this.operatorMerchantNo = operatorMerchantNo == null ? null : operatorMerchantNo.trim();
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSendMerchantNo() {
        return sendMerchantNo;
    }

    public void setSendMerchantNo(String sendMerchantNo) {
        this.sendMerchantNo = sendMerchantNo == null ? null : sendMerchantNo.trim();
    }

    public String getSendMerchantName() {
        return sendMerchantName;
    }

    public void setSendMerchantName(String sendMerchantName) {
        this.sendMerchantName = sendMerchantName == null ? null : sendMerchantName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
}