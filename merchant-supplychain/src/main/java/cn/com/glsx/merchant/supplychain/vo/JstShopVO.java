package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JstShopVO implements Serializable {
    private Integer id;

    /**
     * 门店编号
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 门店地址
     */
    private String addr;
    /**
     *联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 门店状态 WC:待审核 PS:审核通过 FI:审核失败
     */
    private String status;
    /**
     * 门店本身的商户code
     */
    private String shopMerchantCode;
    /**
     * 门店本事的商户名称
     */
    private String shopMerchantName;
    /**
     * 审核失败原因
     */
    private String checkFailResult;
    /**
     * 备注
     */
    private String remark;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 代理商商户COde
     */
    private String agentMerchantCode;

    /**
     * 代理商商户NAME
     */
    private String agentMerchantName;

    /**
     * 门店本身的商户code(接收商户平台)
     */
    private String merchantId;
    /**
     * 门店本事的商户名称(接收商户平台)
     */
    private String merchantName;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

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

    @Override
    public String toString() {
        return "JstShopVO{" +
                "id=" + id +
                ", shopCode='" + shopCode + '\'' +
                ", shopName='" + shopName + '\'' +
                ", addr='" + addr + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", shopMerchantCode='" + shopMerchantCode + '\'' +
                ", shopMerchantName='" + shopMerchantName + '\'' +
                ", checkFailResult='" + checkFailResult + '\'' +
                ", remark='" + remark + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", agentMerchantCode='" + agentMerchantCode + '\'' +
                ", agentMerchantName='" + agentMerchantName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}