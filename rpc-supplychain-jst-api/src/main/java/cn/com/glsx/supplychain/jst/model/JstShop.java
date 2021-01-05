package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Transient;

import java.util.Date;

public class JstShop {
    private Integer id;

    private String shopCode;

    private String shopName;

    private String addr;

    private String contact;

    private String phone;

    private String status;

    private String shopMerchantCode;

    private String shopMerchantName;

    private String checkFailResult;

    private String remark;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 代理商商户COde
     */
    @Transient
    private String agentMerchantCode;

    /**
     * 代理商商户NAME
     */
    @Transient
    public String concatKey;
    @Transient
    private String agentMerchantName;
    @Transient
    private Integer pageStart;
    
    @Transient
    private Integer pageSize;

    public String getAgentMerchantName() {
        return agentMerchantName;
    }

    public void setAgentMerchantName(String agentMerchantName) {
        this.agentMerchantName = agentMerchantName;
    }

    public String getAgentMerchantCode() {
        return agentMerchantCode;
    }

    public void setAgentMerchantCode(String agentMerchantCode) {
        this.agentMerchantCode = agentMerchantCode;
    }

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getShopMerchantCode() {
        return shopMerchantCode;
    }

    public void setShopMerchantCode(String shopMerchantCode) {
        this.shopMerchantCode = shopMerchantCode == null ? null : shopMerchantCode.trim();
    }

    public String getShopMerchantName() {
        return shopMerchantName;
    }

    public void setShopMerchantName(String shopMerchantName) {
        this.shopMerchantName = shopMerchantName == null ? null : shopMerchantName.trim();
    }

    public String getCheckFailResult() {
        return checkFailResult;
    }

    public void setCheckFailResult(String checkFailResult) {
        this.checkFailResult = checkFailResult == null ? null : checkFailResult.trim();
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

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getConcatKey() {
		return concatKey;
	}

	public void setConcatKey(String concatKey) {
		this.concatKey = concatKey;
	}
    
    
}