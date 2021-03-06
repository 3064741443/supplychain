package cn.com.glsx.supplychain.enums;

public enum RedisEnum {

	REDIS_DEVICE_FILE("DEVICE_FILE"),  //设备基础信息
	REDIS_USER_INFO_NAME("REDIS_USER_INFO_NAME_"),//供应链用户管理根据用户名缓存
	REDIS_USER_INFO_ID("REDIS_USER_INFO_ID_"),//供应链用户管理根据用户id缓存
	REDIS_WAREHOUSE_INFO("WAREHOUSE_INFO_"),  //仓库缓存枚举值
	REDIS_ATTRI_INFO("REDIS_ATTRI_INFO_"),    //配置属性枚举值
	REDIS_ATTRI_MANA("REDIS_ATTRI_MANA_"),	//配置管理
	REDIS_DEVICE_TYPE_INFO("REDIS_DEVICE_TYPE_INFO_"),//设备类型
	REDIS_DEVICE_CODE_INFO("REDIS_DEVICE_CODE_INFO_"), //设备小类型
	REDIS_DEVICE_VEHICLE_INFO("REDIS_DEVICE_VEHICLE_INFO_"),//设备车辆管理信息
	REDIS_DEVICE_USER_INFO("REDIS_DEVICE_USER_INFO_"),//设备用户管理信息
	REDIS_DEVICE_CARD_INFO("REDIS_DEVICE_CARD_INFO_"),//设备卡管理信息
	REDIS_DEVICE_SOFT_VERSION("REDIS_DEVICE_SOFT_VERSION_"),//设备软件版本信息
	REDIS_DEVICE_COMPANY_INFO("REDIS_DEVICE_COMPANY_INFO_"),//合作公司记录表
	REDIS_DEVICE_PACKAGE_INFO("REDIS_DEVICE_PACKAGE_INFO_"),//套餐商品
	REDIS_DEVICE_MERCHANT_NAME("REDIS_DEVICE_MERCHANT_NAME_"),//商户名称
	REDIS_DEVICE_MERCHANT_TYPE("REDIS_DEVICE_MERCHANT_TYPE_"),//商户类型
	REDIS_REQUEST_VERIFY_CONSUMER("REDIS_REQUEST_VERIFY_COSUMER_"), //请求验证
	REDIS_EXSYSTEM_INFO("REDIS_EXSYSTEM_INFO_"); //外部系统标识
	
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
