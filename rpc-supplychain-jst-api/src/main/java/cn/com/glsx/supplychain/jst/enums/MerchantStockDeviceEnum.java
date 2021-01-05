package cn.com.glsx.supplychain.jst.enums;

public enum MerchantStockDeviceEnum {

	/**
	 * 在库
	 */
	STATU_IN("IN"),
	
	/**
	 * 调出
	 */
	STATU_OD("OD"),
	
	
	/**
	 * 发货
	 */
	STATU_OS("OS"),
	
	/**
	 * 退货
	 */
	STATU_RT("RT");
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private MerchantStockDeviceEnum(String value) {
		this.value = value;
	}
	
	
}
