package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

public class BsProductDetail {
    private Long id;

    private Byte serviceType;

    private String productCode;

    private String materialCode;

    private String materialName;

    private Integer propQuantity;

    private Byte type;

    private String orderMaterialCode;

    private String orderMaterialName;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public Integer getPropQuantity() {
        return propQuantity;
    }

    public void setPropQuantity(Integer propQuantity) {
        this.propQuantity = propQuantity;
    }

    public String getOrderMaterialCode() {
        return orderMaterialCode;
    }

    public void setOrderMaterialCode(String orderMaterialCode) {
        this.orderMaterialCode = orderMaterialCode;
    }

    public String getOrderMaterialName() {
        return orderMaterialName;
    }

    public void setOrderMaterialName(String orderMaterialName) {
        this.orderMaterialName = orderMaterialName;
    }
}