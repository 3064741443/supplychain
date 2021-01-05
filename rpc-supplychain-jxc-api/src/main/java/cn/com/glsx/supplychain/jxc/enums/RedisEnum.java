package cn.com.glsx.supplychain.jxc.enums;

public enum RedisEnum {

	JXCREDIS_USERINFO_NAME("JXCREDIS_USERINFO_NAME_"),
	JXCREDIS_USERINFO_ID("JXCREDIS_USERINFO_ID_"),
	JXCREDIS_WAREHOUSE_INFO("JXCWAREHOUSE_INFO_"),  //仓库缓存枚举值
	JXCREDIS_ATTRI_INFO("JXCREDIS_ATTRI_INFO_"),    //配置属性枚举值
	JXCREDIS_ATTRI_MANA("JXCREDIS_ATTRI_MANA_"),	//配置管理
	JXCREDIS_DEVICE_TYPE_INFO("JXCREDIS_DEVICE_TYPE_INFO_"),//设备类型
	JXCREDIS_DEVICE_CODE_INFO("JXCREDIS_DEVICE_CODE_INFO_"); //设备小类型
	
	
	private String value;
	
	RedisEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
