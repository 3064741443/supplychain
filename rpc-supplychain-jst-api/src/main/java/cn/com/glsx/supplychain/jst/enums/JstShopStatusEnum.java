package cn.com.glsx.supplychain.jst.enums;

public enum JstShopStatusEnum {
	/**
	 * IN 导入
	 */
	IN("IN"),
	/**
	 * WC待审核
	 */
	WC("WC"),
	/**
	 * PS审核通过
	 */
    PS("PS"),
    /**
	 * FI审核失败
	 */
    FI("FI");
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private JstShopStatusEnum(String code) {
		this.code = code;
	}
	
	
}
