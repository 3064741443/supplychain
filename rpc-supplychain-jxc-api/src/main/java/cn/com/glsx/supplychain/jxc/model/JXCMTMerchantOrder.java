package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "mt_merchant_order")
public class JXCMTMerchantOrder implements Serializable{
	@Id
    private Integer id;

    private String moOrderCode;

    private String moMerchantCode;

    private String moMerchantName;

    private String moProductCode;

    private String moProductName;

    private String moProductPackage;

    private String moProductServiceTime;

    private Integer moTotal;

    private Double moPrice;

    private String moRemark;
    
    private Date moHopeTime;

    private Integer moAddressId;

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

    public String getMoMerchantCode() {
        return moMerchantCode;
    }

    public void setMoMerchantCode(String moMerchantCode) {
        this.moMerchantCode = moMerchantCode == null ? null : moMerchantCode.trim();
    }

    public String getMoMerchantName() {
        return moMerchantName;
    }

    public void setMoMerchantName(String moMerchantName) {
        this.moMerchantName = moMerchantName == null ? null : moMerchantName.trim();
    }

    public String getMoProductCode() {
        return moProductCode;
    }

    public void setMoProductCode(String moProductCode) {
        this.moProductCode = moProductCode == null ? null : moProductCode.trim();
    }

    public String getMoProductName() {
        return moProductName;
    }

    public void setMoProductName(String moProductName) {
        this.moProductName = moProductName == null ? null : moProductName.trim();
    }

    public String getMoProductPackage() {
        return moProductPackage;
    }

    public void setMoProductPackage(String moProductPackage) {
        this.moProductPackage = moProductPackage == null ? null : moProductPackage.trim();
    }

    public String getMoProductServiceTime() {
        return moProductServiceTime;
    }

    public void setMoProductServiceTime(String moProductServiceTime) {
        this.moProductServiceTime = moProductServiceTime == null ? null : moProductServiceTime.trim();
    }

    public Integer getMoTotal() {
        return moTotal;
    }

    public void setMoTotal(Integer moTotal) {
        this.moTotal = moTotal;
    }

    public Double getMoPrice() {
        return moPrice;
    }

    public void setMoPrice(Double moPrice) {
        this.moPrice = moPrice;
    }

    public String getMoRemark() {
        return moRemark;
    }

    public void setMoRemark(String moRemark) {
        this.moRemark = moRemark == null ? null : moRemark.trim();
    }

    public Integer getMoAddressId() {
        return moAddressId;
    }

    public void setMoAddressId(Integer moAddressId) {
        this.moAddressId = moAddressId;
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

	public Date getMoHopeTime() {
		return moHopeTime;
	}

	public void setMoHopeTime(Date moHopeTime) {
		this.moHopeTime = moHopeTime;
	}
}