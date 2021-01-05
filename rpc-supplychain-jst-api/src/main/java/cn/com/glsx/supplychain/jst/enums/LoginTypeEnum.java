package cn.com.glsx.supplychain.jst.enums;

public enum LoginTypeEnum {

	/**
	 * 手机号授权
	 */
	LOGIN_TYPE_PH("PH"),
	
	/**
	 * 商户号密码
	 */
	LOGIN_TYPE_PS("PW");
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private LoginTypeEnum(String value) {
		this.value = value;
	}
	
	
	
}
