package cn.com.glsx.supplychain.jst.enums;

public enum ShProductConfigOptionEnum {

	OPTION_FASTEN("F"),
	OPTION_ELECTI("O");
	
	String value;

	private ShProductConfigOptionEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
