package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "bs_merchant_order_vehicle")
public class BsMerchantOrderVehicle implements Serializable{
	@Id
    private Integer id;

    private String merchantOrder;

    private String dispatchOrderCode;

    private Integer bsParentBrandId;

    private String bsParentBrandName;

    private Integer bsSubBrandId;

    private String bsSubBrandName;

    private Integer bsAudiId;

    private String bsAudiName;

    private String bsMotorcycle;

    private String bsRemark;

    private Integer bsTotal;

    private Integer bsCheckQuantity;

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

    public String getMerchantOrder() {
        return merchantOrder;
    }

    public void setMerchantOrder(String merchantOrder) {
        this.merchantOrder = merchantOrder == null ? null : merchantOrder.trim();
    }

    public String getDispatchOrderCode() {
        return dispatchOrderCode;
    }

    public void setDispatchOrderCode(String dispatchOrderCode) {
        this.dispatchOrderCode = dispatchOrderCode == null ? null : dispatchOrderCode.trim();
    }

    public Integer getBsParentBrandId() {
        return bsParentBrandId;
    }

    public void setBsParentBrandId(Integer bsParentBrandId) {
        this.bsParentBrandId = bsParentBrandId;
    }

    public String getBsParentBrandName() {
        return bsParentBrandName;
    }

    public void setBsParentBrandName(String bsParentBrandName) {
        this.bsParentBrandName = bsParentBrandName == null ? null : bsParentBrandName.trim();
    }

    public Integer getBsSubBrandId() {
        return bsSubBrandId;
    }

    public void setBsSubBrandId(Integer bsSubBrandId) {
        this.bsSubBrandId = bsSubBrandId;
    }

    public String getBsSubBrandName() {
        return bsSubBrandName;
    }

    public void setBsSubBrandName(String bsSubBrandName) {
        this.bsSubBrandName = bsSubBrandName == null ? null : bsSubBrandName.trim();
    }

    public Integer getBsAudiId() {
        return bsAudiId;
    }

    public void setBsAudiId(Integer bsAudiId) {
        this.bsAudiId = bsAudiId;
    }

    public String getBsAudiName() {
        return bsAudiName;
    }

    public void setBsAudiName(String bsAudiName) {
        this.bsAudiName = bsAudiName == null ? null : bsAudiName.trim();
    }

    public String getBsMotorcycle() {
        return bsMotorcycle;
    }

    public void setBsMotorcycle(String bsMotorcycle) {
        this.bsMotorcycle = bsMotorcycle == null ? null : bsMotorcycle.trim();
    }

    public String getBsRemark() {
        return bsRemark;
    }

    public void setBsRemark(String bsRemark) {
        this.bsRemark = bsRemark == null ? null : bsRemark.trim();
    }

    public Integer getBsTotal() {
        return bsTotal;
    }

    public void setBsTotal(Integer bsTotal) {
        this.bsTotal = bsTotal;
    }

    public Integer getBsCheckQuantity() {
        return bsCheckQuantity;
    }

    public void setBsCheckQuantity(Integer bsCheckQuantity) {
        this.bsCheckQuantity = bsCheckQuantity;
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