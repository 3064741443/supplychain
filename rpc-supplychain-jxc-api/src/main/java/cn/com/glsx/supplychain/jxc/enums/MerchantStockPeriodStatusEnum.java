package cn.com.glsx.supplychain.jxc.enums;

public enum MerchantStockPeriodStatusEnum {

	STK_PRODUCT_CONFIG_PERIOD_PRE("PRE","未到有效期"),
	STK_PRODUCT_CONFIG_PERIOD_CUR("CUR","有效期内"),
	STK_PRODUCT_CONFIG_PERIOD_NEX("NEX","已过期");
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private MerchantStockPeriodStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
}
