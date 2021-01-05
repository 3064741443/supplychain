package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gh_merchant_brand")
public class GhMerchantBrand {
	@Id
    private Integer id;

    private String merchantCode;

    private Integer parentBrandId;

    private Integer subBrandId;

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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
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
		return "GhMerchantBrand [id=" + id + ", merchantCode=" + merchantCode
				+ ", parentBrandId=" + parentBrandId + ", subBrandId="
				+ subBrandId + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", deletedFlag=" + deletedFlag + "]";
	}
    
    
}