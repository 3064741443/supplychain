package cn.com.glsx.supplychain.jst.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "gh_product_config")
public class GhProductConfig {
	@Id
    private Integer id;

    private String productConfigCode;

    private Integer parentBrandId;

    private Integer subBrandId;

    private Integer audiId;

    private String motorcycle;

    private Integer categoryId;
    
    private Integer modelYearId;

    private String spaProductCode;

    private String spaProductName;

    private String glsxProductCode;

    private String glsxProductName;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    @Transient
    private String merchantCode;
    
    @Transient
    List<Integer> listParentBrandIds;
    @Transient
	List<Integer> listSubBrandIds;
    
    
    
    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public List<Integer> getListParentBrandIds() {
		return listParentBrandIds;
	}

	public void setListParentBrandIds(List<Integer> listParentBrandIds) {
		this.listParentBrandIds = listParentBrandIds;
	}

	public List<Integer> getListSubBrandIds() {
		return listSubBrandIds;
	}

	public void setListSubBrandIds(List<Integer> listSubBrandIds) {
		this.listSubBrandIds = listSubBrandIds;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

	public Integer getModelYearId() {
		return modelYearId;
	}

	public void setModelYearId(Integer modelYearId) {
		this.modelYearId = modelYearId;
	}

	@Override
	public String toString() {
		return "GhProductConfig [id=" + id + ", productConfigCode="
				+ productConfigCode + ", parentBrandId=" + parentBrandId
				+ ", subBrandId=" + subBrandId + ", audiId=" + audiId
				+ ", motorcycle=" + motorcycle + ", categoryId=" + categoryId
				+ ", modelYearId=" + modelYearId + ", spaProductCode="
				+ spaProductCode + ", spaProductName=" + spaProductName
				+ ", glsxProductCode=" + glsxProductCode + ", glsxProductName="
				+ glsxProductName + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
	
	
}