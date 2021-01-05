package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JstShopVO implements Serializable{

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
    
    
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShopMerchantCode() {
		return shopMerchantCode;
	}
	public void setShopMerchantCode(String shopMerchantCode) {
		this.shopMerchantCode = shopMerchantCode;
	}
	public String getShopMerchantName() {
		return shopMerchantName;
	}
	public void setShopMerchantName(String shopMerchantName) {
		this.shopMerchantName = shopMerchantName;
	}
	public String getCheckFailResult() {
		return checkFailResult;
	}
	public void setCheckFailResult(String checkFailResult) {
		this.checkFailResult = checkFailResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "JstShopVO [shopCode=" + shopCode + ", shopName=" + shopName
				+ ", addr=" + addr + ", contact=" + contact + ", phone="
				+ phone + ", status=" + status + ", shopMerchantCode="
				+ shopMerchantCode + ", shopMerchantName=" + shopMerchantName
				+ ", checkFailResult=" + checkFailResult + ", remark=" + remark
				+ "]";
	}
    
    
}
