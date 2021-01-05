package cn.com.glsx.supplychain.jxc.enums;

public enum MerchantStockDeductTypeEnum {
	
	/**
	 * 盘亏
	 */
	TRADE_TYPE_LOSS("LS"),
	
	/**
	 * 退货
	 */
	TRADE_TYPE_RETURN("RT");

	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private MerchantStockDeductTypeEnum(String value) {
		this.value = value;
	}
	
	
}
