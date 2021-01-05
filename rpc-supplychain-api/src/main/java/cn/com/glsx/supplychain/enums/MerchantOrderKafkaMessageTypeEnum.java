package cn.com.glsx.supplychain.enums;

public enum MerchantOrderKafkaMessageTypeEnum {

	MERCHANT_ORDER_SIGN((byte)0,"订单签收同步设备"),
	
	MERCHANT_ORDER_UPEC((byte)1,"出库同步订单导出表出库信息更新");
	
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
	private MerchantOrderKafkaMessageTypeEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
}
