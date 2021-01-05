package cn.com.glsx.supplychain.jst.enums;

public enum ShopOrderStatuEnum {

	/**
	 * 未完成
	 */
	ORDER_UN("UF"),
	
	/**
	 * 已完成
	 */
	ORDER_OV("OV"),
	
	
	/**
	 * 已取消
	 */
	ORDER_CL("CL");

	String value;

	private ShopOrderStatuEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
