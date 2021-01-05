package cn.com.glsx.supplychain.jst.enums;

public enum MerchantRoleEnum {
	
	/**
	 * 门店
	 */
	ROLE_SHOP("SP"),
	
	/**
	 * 代理商
	 */
	ROLE_AGENT("AG"),
	
	
	/**
	 * 既是门店又是代理商
	 */
	ROLE_ALL("AL");

	String value;

	private MerchantRoleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
