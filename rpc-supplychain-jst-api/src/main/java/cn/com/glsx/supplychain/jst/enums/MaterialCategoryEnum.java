package cn.com.glsx.supplychain.jst.enums;

public enum MaterialCategoryEnum {

	/**
	 * 主物料
	 */
	MATERIAL_MAIN((byte)0,"主物料"),
	
	/**
	 * 物料大类
	 */
	MATERIAL_BIG((byte)1,"物料大类"),
	
	/**
	 * 物料小类
	 */
	MATERIAL_SMALL((byte)2,"物料小类");
	
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
	private MaterialCategoryEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
	
	
}
