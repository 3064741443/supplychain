package cn.com.glsx.supplychain.jst.enums;

/**
 * @author: luoqiang
 * @description: 广汇订单的转换状态
 * @date: 2020/8/31 18:13
 */
public enum GhMerchantOrderStatuEnum {

	/**
	 * 已取消
	 */
	ORDER_FB((byte)9,"已取消"),

	/**
	 * 待发货
	 */
	ORDER_WS((byte)1,"待发货"),

	/**
	 * 已发货
	 */
	ORDER_SP((byte)2,"已发货"),

	/**
	 * 已完成
	 */
	ORDER_FI((byte)3,"已完成");

	private byte code;
    private String name;
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private GhMerchantOrderStatuEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
}
