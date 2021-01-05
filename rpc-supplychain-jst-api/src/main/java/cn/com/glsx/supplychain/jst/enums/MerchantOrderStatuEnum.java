package cn.com.glsx.supplychain.jst.enums;

public enum MerchantOrderStatuEnum {

	/**
	 * 已驳回
	 */
	ORDER_FB((byte)0,"已驳回"),
	
	/**
	 * 待审核
	 */
	ORDER_WC((byte)1,"待审核"),
	/**
	 * 待发货
	 */
	ORDER_WS((byte)2,"待发货"),
	/**
	 * 待签收
	 */
	ORDER_WR((byte)3,"待签收"),
	/**
	 * 部分签收
	 */
	ORDER_PR((byte)4,"部分签收"),
	/**
	 * 已完成
	 */
	ORDER_FI((byte)5,"已完成"),
	/**
	 * 已开票
	 */
	ORDER_RT((byte)6,"已开票"),
	/**
	 * 已作废
	 */
	ORDER_RB((byte)7,"已作废"),

	ORDER_WAIT_DISPATCH((byte)8,"待分配"),

	ORDER_ALREADY_CANCEL((byte)9,"已取消");
	
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
	private MerchantOrderStatuEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		MerchantOrderStatuEnum[] statuEnums = values();
		for (MerchantOrderStatuEnum statuEnum : statuEnums) {
			if (statuEnum.code == code) {
				return statuEnum.name;
			}
		}
		return null;
	}
}
