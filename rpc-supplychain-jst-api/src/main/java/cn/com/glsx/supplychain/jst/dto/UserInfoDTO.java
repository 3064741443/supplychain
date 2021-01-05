package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfoDTO extends BaseDTO implements Serializable{

	private String merchantCode;
	
	private String role;
	
	private String merchantName;
		
	private String shopCode;
		
	private String shopName;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

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

	@Override
	public String toString() {
		return "UserInfoDTO [merchantCode=" + merchantCode + ", role=" + role
				+ ", merchantName=" + merchantName + ", shopCode=" + shopCode
				+ ", shopName=" + shopName + "]";
	}

	
	
	
}
