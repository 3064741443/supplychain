package cn.com.glsx.supplychain.enums;

public enum MerchantStockCainouStatuEnum {
	
	MSTOCK_CAINOU_WAIT_SIGN((byte)1,"待签收"),
	MSTOCK_CAINOU_WAIT_FINI((byte)2,"已完成");
		
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
	private MerchantStockCainouStatuEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
    
    
}
