package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Table(name = "order_info")
public class OrderInfo implements Serializable {
    @Id
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

    private String ndScan;

    private String materialCode;

    private String materialName;

    private String remark;

    private Integer sendQuanlity;
    @Transient
    private Integer productTypeId;
    @Transient
    private String merchantOrder;
    @Transient
    private Date orderTimeStart;
    @Transient
    private Date orderTimeEnd;
    @Transient
    private Integer deviceTypeId;
    @Transient
    private String queryType;
    @Transient
    private String factMaterialCode;
    @Transient
    private String factMaterialName;
    @Transient
    private Date orderDistribTimeStart;
    @Transient
    private Date orderDistribTimeEnd;
    @Transient
    private String dispatchOrderCode;

    public String getDispatchOrderCode() {
        return dispatchOrderCode;
    }

    public void setDispatchOrderCode(String dispatchOrderCode) {
        this.dispatchOrderCode = dispatchOrderCode;
    }

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

    public String getNdScan() {
        return ndScan;
    }

    public void setNdScan(String ndScan) {
        this.ndScan = ndScan == null ? null : ndScan.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getMerchantOrder() {
        return merchantOrder;
    }

    public void setMerchantOrder(String merchantOrder) {
        this.merchantOrder = merchantOrder;
    }

    public Date getOrderTimeStart() {
        return orderTimeStart;
    }

    public void setOrderTimeStart(Date orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public Integer getSendQuanlity() {
        return sendQuanlity;
    }

    public void setSendQuanlity(Integer sendQuanlity) {
        this.sendQuanlity = sendQuanlity;
    }

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getFactMaterialCode() {
        return factMaterialCode;
    }

    public void setFactMaterialCode(String factMaterialCode) {
        this.factMaterialCode = factMaterialCode;
    }

    public String getFactMaterialName() {
        return factMaterialName;
    }

    public void setFactMaterialName(String factMaterialName) {
        this.factMaterialName = factMaterialName;
    }

    public Date getOrderDistribTimeStart() {
        return orderDistribTimeStart;
    }

    public void setOrderDistribTimeStart(Date orderDistribTimeStart) {
        this.orderDistribTimeStart = orderDistribTimeStart;
    }

    public Date getOrderDistribTimeEnd() {
        return orderDistribTimeEnd;
    }

    public void setOrderDistribTimeEnd(Date orderDistribTimeEnd) {
        this.orderDistribTimeEnd = orderDistribTimeEnd;
    }


}