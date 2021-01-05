package cn.com.glsx.supplychain.enums;

public enum OrderStatusEnum {
	/**
	 * 未完成
	 */
	STATUS_UF("UF"),
	
	/**
	 * 已完成
	 */
	STATUS_OV("OV"),
	
	/**
	 * 已取消
	 */
	STATUS_CL("CL");

	
	private String value;
	
	OrderStatusEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
