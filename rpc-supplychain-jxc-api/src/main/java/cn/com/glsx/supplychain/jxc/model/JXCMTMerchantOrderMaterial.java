package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "mt_merchant_order_material")
public class JXCMTMerchantOrderMaterial implements Serializable{
    private Integer id;

    private String moOrderCode;

    private String moProductCode;

    private String moMaterialCode;

    private String moMaterialName;

    private Integer moPropQuantity;

    private Integer moTotal;

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

    public String getMoOrderCode() {
        return moOrderCode;
    }

    public void setMoOrderCode(String moOrderCode) {
        this.moOrderCode = moOrderCode == null ? null : moOrderCode.trim();
    }

    public String getMoProductCode() {
        return moProductCode;
    }

    public void setMoProductCode(String moProductCode) {
        this.moProductCode = moProductCode == null ? null : moProductCode.trim();
    }

    public String getMoMaterialCode() {
        return moMaterialCode;
    }

    public void setMoMaterialCode(String moMaterialCode) {
        this.moMaterialCode = moMaterialCode == null ? null : moMaterialCode.trim();
    }

    public String getMoMaterialName() {
        return moMaterialName;
    }

    public void setMoMaterialName(String moMaterialName) {
        this.moMaterialName = moMaterialName == null ? null : moMaterialName.trim();
    }

    public Integer getMoPropQuantity() {
        return moPropQuantity;
    }

    public void setMoPropQuantity(Integer moPropQuantity) {
        this.moPropQuantity = moPropQuantity;
    }

    public Integer getMoTotal() {
        return moTotal;
    }

    public void setMoTotal(Integer moTotal) {
        this.moTotal = moTotal;
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