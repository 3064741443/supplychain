package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gh_shopping_cart")
public class GhShoppingCart {
	@Id
    private Integer id;

    private String cartCode;

    private String merchantCode;

    private String productConfigCode;

    private Integer parentBrandId;

    private Integer subBrandId;

    private Integer audiId;

    private String motorcycle;

    private Integer categoryId;

    private String spaProductCode;

    private String spaProductName;

    private String glsxProductCode;

    private String glsxProductName;

    private Integer total;

    private String remark;

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

    public String getCartCode() {
        return cartCode;
    }

    public void setCartCode(String cartCode) {
        this.cartCode = cartCode == null ? null : cartCode.trim();
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
		this.productConfigCode = productConfigCode;
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

	@Override
	public String toString() {
		return "GhShoppingCart [id=" + id + ", cartCode=" + cartCode
				+ ", merchantCode=" + merchantCode + ", productConfigCode="
				+ productConfigCode + ", parentBrandId=" + parentBrandId
				+ ", subBrandId=" + subBrandId + ", audiId=" + audiId
				+ ", motorcycle=" + motorcycle + ", categoryId=" + categoryId
				+ ", spaProductCode=" + spaProductCode + ", spaProductName="
				+ spaProductName + ", glsxProductCode=" + glsxProductCode
				+ ", glsxProductName=" + glsxProductName + ", total=" + total
				+ ", remark=" + remark + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}