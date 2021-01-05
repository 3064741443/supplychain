package cn.com.glsx.supplychain.jxc.enums;

public enum WarnningSetTypeEnum {

	 WARNNING_TYPE_KUXIAO("KX","库销比预警"),
	 WARNNING_TYPE_DAIZHI("DZ","呆滞品预警");
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
	private WarnningSetTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	 
	 
}
