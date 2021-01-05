package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfoVo implements Serializable{
	
	//wx
	private String consumer;
	
	//版本 V1.0
	private String version;

	//手机号
	private String phone;
	
	//商户号
	private String merchantCode;
	
	//密码
	private String password;
	
	//角色  SP:门店  AG:代理商 AL:既是门店又是代理商
	private String role;
	
	//登陆方式 PH:手机号授权  PW:商户号密码
	private String loginType;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
}
