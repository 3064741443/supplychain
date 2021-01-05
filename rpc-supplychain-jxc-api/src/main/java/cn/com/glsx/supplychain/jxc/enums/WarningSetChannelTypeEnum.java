package cn.com.glsx.supplychain.jxc.enums;

public enum WarningSetChannelTypeEnum {
	WARNNING_CHANNEL_TYPE_CH("CH","按照渠道"),
	WARNNING_CHANNEL_TYPE_ME("ME","按服务商");
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
	private WarningSetChannelTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
