package cn.com.glsx.supplychain.jst.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "gh_merchant_order_config")
public class GhMerchantOrderConfig {
	@Id
    private Integer id;

    private String ghMerchantOrderCode;

    private Integer attribInfoId;

    private String option;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    @Transient
    private List<String> listGhMerchantCodes;
    
   
    public List<String> getListGhMerchantCodes() {
		return listGhMerchantCodes;
	}

	public void setListGhMerchantCodes(List<String> listGhMerchantCodes) {
		this.listGhMerchantCodes = listGhMerchantCodes;
	}

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

    public Integer getAttribInfoId() {
        return attribInfoId;
    }

    public void setAttribInfoId(Integer attribInfoId) {
        this.attribInfoId = attribInfoId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option == null ? null : option.trim();
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
		return "GhMerchantOrderConfig [id=" + id + ", ghMerchantOrderCode="
				+ ghMerchantOrderCode + ", attribInfoId=" + attribInfoId
				+ ", option=" + option + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}