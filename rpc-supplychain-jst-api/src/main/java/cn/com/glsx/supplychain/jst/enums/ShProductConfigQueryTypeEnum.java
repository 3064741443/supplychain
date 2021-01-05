package cn.com.glsx.supplychain.jst.enums;

public enum ShProductConfigQueryTypeEnum {

	//车型车系查询
	QUERY_TYPE_M("M"),
	//不带条件全部查询
	QUERY_TYPE_A("A"),
	//spacode查询
	QUERY_TYPE_S("S");
	
	String value;

	private ShProductConfigQueryTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
