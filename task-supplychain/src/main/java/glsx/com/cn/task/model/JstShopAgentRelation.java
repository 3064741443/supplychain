package glsx.com.cn.task.model;

import java.util.Date;

public class JstShopAgentRelation {
    private Integer id;

    private String shopCode;

    private String agentMerchantCode;

    private String agentMerchantName;

    private String status;

    private String checkFailResult;

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

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getAgentMerchantCode() {
        return agentMerchantCode;
    }

    public void setAgentMerchantCode(String agentMerchantCode) {
        this.agentMerchantCode = agentMerchantCode == null ? null : agentMerchantCode.trim();
    }

    public String getAgentMerchantName() {
        return agentMerchantName;
    }

    public void setAgentMerchantName(String agentMerchantName) {
        this.agentMerchantName = agentMerchantName == null ? null : agentMerchantName.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckFailResult() {
        return checkFailResult;
    }

    public void setCheckFailResult(String checkFailResult) {
        this.checkFailResult = checkFailResult;
    }

	@Override
	public String toString() {
		return "JstShopAgentRelation [id=" + id + ", shopCode=" + shopCode
				+ ", agentMerchantCode=" + agentMerchantCode
				+ ", agentMerchantName=" + agentMerchantName + ", status="
				+ status + ", checkFailResult=" + checkFailResult
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + "]";
	}
    
    
}