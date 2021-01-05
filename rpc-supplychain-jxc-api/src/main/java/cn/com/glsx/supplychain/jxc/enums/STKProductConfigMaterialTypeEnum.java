package cn.com.glsx.supplychain.jxc.enums;

public enum STKProductConfigMaterialTypeEnum {
	
	STK_PRODUCT_CONFIG_MATERIAL_SALES("SA","销售物料"),
	STK_PRODUCT_CONFIG_MATERIAL_SEND("ST","实发物料");
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private STKProductConfigMaterialTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
}
