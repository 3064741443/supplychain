package cn.com.glsx.supplychain.jst.enums;

public enum LogisticsEnum {

	LOGISTICS_TYPE_1((byte) 1, "商户订单地址信息"),

	LOGISTICS_TYPE_2((byte) 2, "售后订单物流信息"),

	LOGISTICS_TYPE_3((byte) 3, "售后订单维修物流信息"),

	LOGISTICS_TYPE_4((byte) 4, "扫码工具物流信息"),

	LOGISTICS_TYPE_5((byte) 5, "商户订单直接发货信息"),

	LOGISTICS_TYPE_6((byte) 6, "商户下门店订单"),

	LOGISTICS_TYPE_7((byte) 7, "门店订单发货地址");

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

	private LogisticsEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}

}
