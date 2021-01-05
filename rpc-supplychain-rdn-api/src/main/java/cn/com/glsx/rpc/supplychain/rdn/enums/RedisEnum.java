package cn.com.glsx.rpc.supplychain.rdn.enums;

public enum RedisEnum {

	RDNREDIS_WAREHOUSE_INFO("RDNWAREHOUSE_INFO_"),  //仓库缓存枚举值
	RDNREDIS_ATTRI_INFO("RDNREDIS_ATTRI_INFO_"),    //配置属性枚举值
	JXCREDIS_ATTRI_INFO("JXCREDIS_ATTRI_INFO_"),    //配置属性枚举值
	RDNREDIS_ATTRI_MANA("RDNREDIS_ATTRI_MANA_"),	//配置管理
	RDNREDIS_DEVICE_TYPE_INFO("RDNREDIS_DEVICE_TYPE_INFO_"),//设备类型
	RDNREDIS_DEVICE_CODE_INFO("RDNREDIS_DEVICE_CODE_INFO_"); //设备小类型
	
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
