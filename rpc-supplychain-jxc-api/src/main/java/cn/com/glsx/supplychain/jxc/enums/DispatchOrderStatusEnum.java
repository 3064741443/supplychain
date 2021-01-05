package cn.com.glsx.supplychain.jxc.enums;

public enum DispatchOrderStatusEnum {
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
	
	DispatchOrderStatusEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
