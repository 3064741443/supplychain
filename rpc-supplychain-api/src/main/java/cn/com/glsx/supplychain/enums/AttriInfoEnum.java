package cn.com.glsx.supplychain.enums;

public enum AttriInfoEnum {

	TYPE("2"),
	CONFIGURE("3"),
	MODEL("4"),
	SIZE("5"),
	MNUM("10");
	
	private String value;
	
	AttriInfoEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
