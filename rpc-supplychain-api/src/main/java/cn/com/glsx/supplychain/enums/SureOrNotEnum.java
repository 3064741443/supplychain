package cn.com.glsx.supplychain.enums;

public enum SureOrNotEnum {

	SURE_YES("Y"),
	SURE_NO("N");
    private String code;

	private SureOrNotEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
