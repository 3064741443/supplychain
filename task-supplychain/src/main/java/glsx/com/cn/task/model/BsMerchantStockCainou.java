package glsx.com.cn.task.model;

import java.util.Date;

public class BsMerchantStockCainou {
    private Integer id;

    private String materialCode;

    private String materialName;

    private String fromMerchantCode;

    private String fromMerchantName;

    private String toMerchantCode;

    private String toMerchantName;

    private Integer deviceType;

    private String deviceTypeName;

    private Date deliTime;

    private Date signTime;

    private Integer deliNum;

    private Integer signNum;

    private Integer status;

    private String logisticscpy;

    private String logisticsno;

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

    public String getFromMerchantCode() {
        return fromMerchantCode;
    }

    public void setFromMerchantCode(String fromMerchantCode) {
        this.fromMerchantCode = fromMerchantCode == null ? null : fromMerchantCode.trim();
    }

    public String getFromMerchantName() {
        return fromMerchantName;
    }

    public void setFromMerchantName(String fromMerchantName) {
        this.fromMerchantName = fromMerchantName == null ? null : fromMerchantName.trim();
    }

    public String getToMerchantCode() {
        return toMerchantCode;
    }

    public void setToMerchantCode(String toMerchantCode) {
        this.toMerchantCode = toMerchantCode == null ? null : toMerchantCode.trim();
    }

    public String getToMerchantName() {
        return toMerchantName;
    }

    public void setToMerchantName(String toMerchantName) {
        this.toMerchantName = toMerchantName == null ? null : toMerchantName.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public Date getDeliTime() {
        return deliTime;
    }

    public void setDeliTime(Date deliTime) {
        this.deliTime = deliTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getDeliNum() {
        return deliNum;
    }

    public void setDeliNum(Integer deliNum) {
        this.deliNum = deliNum;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLogisticscpy() {
        return logisticscpy;
    }

    public void setLogisticscpy(String logisticscpy) {
        this.logisticscpy = logisticscpy == null ? null : logisticscpy.trim();
    }

    public String getLogisticsno() {
        return logisticsno;
    }

    public void setLogisticsno(String logisticsno) {
        this.logisticsno = logisticsno == null ? null : logisticsno.trim();
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