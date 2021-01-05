package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "gh_merchant_order")
public class JXCMTGhMerchantOrder implements Serializable{
	@Id
    private Integer id;

    private String ghMerchantOrderCode;

    private Date orderTime;

    private String merchantCode;

    private String productConfigCode;

    private Integer parentBrandId;

    private Integer subBrandId;

    private Integer audiId;

    private String motorcycle;

    private Integer categoryId;

    private String spaPurchaseCode;

    private String spaProductCode;

    private String spaProductName;

    private String glsxProductCode;

    private String glsxProductName;

    private Integer total;

    private String remark;

    private Byte status;

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

    public String getGhMerchantOrderCode() {
        return ghMerchantOrderCode;
    }

    public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
        this.ghMerchantOrderCode = ghMerchantOrderCode == null ? null : ghMerchantOrderCode.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getProductConfigCode() {
        return productConfigCode;
    }

    public void setProductConfigCode(String productConfigCode) {
        this.productConfigCode = productConfigCode == null ? null : productConfigCode.trim();
    }

    public Integer getParentBrandId() {
        return parentBrandId;
    }

    public void setParentBrandId(Integer parentBrandId) {
        this.parentBrandId = parentBrandId;
    }

    public Integer getSubBrandId() {
        return subBrandId;
    }

    public void setSubBrandId(Integer subBrandId) {
        this.subBrandId = subBrandId;
    }

    public Integer getAudiId() {
        return audiId;
    }

    public void setAudiId(Integer audiId) {
        this.audiId = audiId;
    }

    public String getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(String motorcycle) {
        this.motorcycle = motorcycle == null ? null : motorcycle.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpaPurchaseCode() {
        return spaPurchaseCode;
    }

    public void setSpaPurchaseCode(String spaPurchaseCode) {
        this.spaPurchaseCode = spaPurchaseCode == null ? null : spaPurchaseCode.trim();
    }

    public String getSpaProductCode() {
        return spaProductCode;
    }

    public void setSpaProductCode(String spaProductCode) {
        this.spaProductCode = spaProductCode == null ? null : spaProductCode.trim();
    }

    public String getSpaProductName() {
        return spaProductName;
    }

    public void setSpaProductName(String spaProductName) {
        this.spaProductName = spaProductName == null ? null : spaProductName.trim();
    }

    public String getGlsxProductCode() {
        return glsxProductCode;
    }

    public void setGlsxProductCode(String glsxProductCode) {
        this.glsxProductCode = glsxProductCode == null ? null : glsxProductCode.trim();
    }

    public String getGlsxProductName() {
        return glsxProductName;
    }

    public void setGlsxProductName(String glsxProductName) {
        this.glsxProductName = glsxProductName == null ? null : glsxProductName.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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