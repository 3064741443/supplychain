package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "bs_merchant_material_check")
public class JXCMTBsMerchantMaterialCheck implements Serializable{
    private Integer id;

    private String orderMaterialCode;

    private String orderMaterialName;

    private String checkMaterialCode;

    private String checkMaterialName;

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

    public String getOrderMaterialCode() {
        return orderMaterialCode;
    }

    public void setOrderMaterialCode(String orderMaterialCode) {
        this.orderMaterialCode = orderMaterialCode == null ? null : orderMaterialCode.trim();
    }

    public String getOrderMaterialName() {
        return orderMaterialName;
    }

    public void setOrderMaterialName(String orderMaterialName) {
        this.orderMaterialName = orderMaterialName == null ? null : orderMaterialName.trim();
    }

    public String getCheckMaterialCode() {
        return checkMaterialCode;
    }

    public void setCheckMaterialCode(String checkMaterialCode) {
        this.checkMaterialCode = checkMaterialCode == null ? null : checkMaterialCode.trim();
    }

    public String getCheckMaterialName() {
        return checkMaterialName;
    }

    public void setCheckMaterialName(String checkMaterialName) {
        this.checkMaterialName = checkMaterialName == null ? null : checkMaterialName.trim();
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