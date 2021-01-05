package cn.com.glsx.supplychain.enums;

import cn.com.glsx.framework.core.enums.ErrorEnum;

public enum ErrorCodeEnum implements ErrorEnum{
	
	ERRCODE_INVALID_REQ("10100","无效请求"),
	ERRCODE_INVALID_PARAM("10101","无效参数"),
	ERRCODE_INVALID_SIGN("10102","无效签名"),
	ERRCODE_ACCOUNT_USERNAME_ALREADY_USED("10103","用户名已存在"),
	ERRCODE_ACCOUNT_USERNAME_NOT_EXIST("10104","用户名不存在"),
	ERRCODE_ACCOUNT_PASSWORD_WRONG("10105","密码错误"),
	ERRCODE_RECORD_REPEATED("10106","记录重复"),
	ERRCODE_SYSTEM_CACHE_WRITE_FAILED("10107","系统缓存写错误"),
	ERRCODE_SYSTEM_CACHE_READ_FAILED("10108","系统缓存读错误"),
	ERRCODE_ORDER_NOT_EXIST("10108","订单不存在"),
	ERRCODE_ORDER_CANCELED("10109","订单取消"),
	ERRCODE_ORDER_COMPLETE("10110","订单完成"),
	ERRCODE_RECORD_CONFIGURE_NOT_MATCH("10111","记录配置不匹配"),
	ERRCODE_RECORD_IMEI_NOT_BELONG_ORDER("10112","记录imei不属于该订单"),
	ERRCODE_RECORD_IMEI_READY_OUT("10113","记录设备已出库"),
	ERRCODE_RECORD_IMEI_ICCID_NOT_MATCH("10114","记录imei iccid不匹配"),
	ERRCODE_SYSTEM_ORDER_CONFIG_FAILED("10115","系统订单配置错误"),
	ERRCODE_RECORD_IMEI_NOT_EXIST("10116","记录设备不存在"),
	ERRCODE_SYSTEM_DATABASE_FAILED("10117","数据库读写错误"),
	ERRCODE_SYSTEM_ATTRIB_READ_FAILED("10118","系统属性配置表读取失败"),
	ERRCODE_RECORD_INVALID_ATTRIB_ID("10119","记录配置id错误"),
	ERRCODE_ACCUNT_INVALID_USERNAME("10120","无效用户或者用户无权限"),
	ERRCODE_SYSTEM_RECORD_UNUSUAL("10121","数据库数据异常"),
	ERRCODE_EXTERNAL_INTERFACE("10122","外部接口异常"),
	ERRCODE_ORDER_ALREADY_OUTGOING("10123","该订单已出货，不能取消"),
	ERRCODE_ORDER_INVALID_OPERATOR("10124","修改的订单数少于原有订单数"),
	ERRCODE_WAREHOUSE_NAME_ALREADY_USED("10125","仓库名已存在"),
	ERRCODE_ATTRIB_MANA_CODE("10126","配置编码已经存在"),
	ERRCODE_DEVICE_DEVICENAME_ALREADY_USED("10127","设备名称已存在"),
	ERRCODE_DEVICE_DEVICECODE_DELETE("10128","设备名称无效删除,已有设备"),
	ERRCODE_DEVICE_NOT_EXIST("10129","无此设备"),
	ERRCODE_DEVICE_INVALID_JSON_FORMAT("10130","json解析错误"),
	ERRCODE_DEVICE_ATYPISM("10131","解绑车辆ID与设备绑定车辆不一致"),
	ERRCODE_DEVICE_USER_BINDING("10132","激活设备ID与绑定用户设备不一致"),
	ERRCODE_DATA_DIFFER("10133","数据不一致"),
	ERRCODE_MAX_IMPORT_SIZE("10134","超过最大导入数"),
	ERRCODE_ATTRIB_MANA_CODE_NOT_EXIST("10135","配置编码不存在"),
	ERRCODE_WAREHOUSE_NO_EXISTS("10136","仓库没有建立"),
	ERRCODE_WAREHOUSE_TYPE_WRONG("10137","仓库类型错误"),
	ERRCODE_PACKAGE_NOT_EXIST("10138","根据id未查到对应套餐"),
	ERRCODE_DEVICEFILE_EXIST("10139","设备清单已经存在此设备"),
	ERRCODE_DEVICEFILE_SNAPSHOT_EXIST("10140","设备关系已经存在"),
	ERRCODE_CARD_ALREAD_BAND("10141","流量卡已被绑定"),
	ERRCODE_DEVICEVIRTUAL_EXIST("10145","流量卡已经存在虚拟表中"),
	ERRCODE_VEHICLE_ALREAD_BAND("10146","车辆已经绑定其他设备"),
	ERRCODE_DEVICE_ALREAD_INITIAL("10147","设备已被初始化"),
	ERRCODE_DEVICE_ALREAD_ACTIVE("10148","设备已被激活"),
	ERRCODE_DEVICE_ALREAD_UNACTIVE("10149","设备未被激活"),
	ERRCODE_DEVICE_IMEI_INVALID("10150","设备存在IMEI"),
	ERRCODE_DEVICECODE_NOT_EXIST("10152","设备编号不存在"),
	ERRCODE_DEVICE_IMEI_NOT_EXIST("10153","IMEI为空"),
	ERRCODE_INVALID_DEVICE_MERCHANT_NO("10154","商户号不存在"),
	ERRCODE_INVALID_DATA_LENGTH("10155","输入值的长度不合法"),
	EERRCODE_DEVICE_DEVICE_INVALID("10156","设备编号已存在"),
	ERRCODE_DEVICE_ALREAD_BAND_PACKAGE("10157","设备已绑定套餐"),
	ERRCODE_DEVICECODE_ALREADY_USED("10158","设备编号已被使用"),
	ERRCODE_DEALER_USER_INFO_NAME_ALREADY_USED("10159","经销商用户名已经被使用"),
	ERRCODE_PRODUCT_NAME_REPEATED("10160","产品名称已经被使用"),
	ERRCODE_DEALER_USER_INFO_NAME_MERCHANT_USED("10161","经销商商户已经被使用"),
	ERRCODE_DEVICE_SN_IS_EMPTY("10162","SN为空"),
	ERRCODE_DEVICE_ICCID_IS_EMPTY("10163","ICCID为空"),
	ERRCODE_INVALIDE_IMEI_FORMAT("10164","IMEI格式错误"),
	ERRCODE_INVALIDE_ICCID_FORMAT("10165","ICCID格式错误"),
	ERRCODE_INVALIDE_SN_FORMAT("10166","SN格式错误"),
	ERRCODE_REPEATED_REOCRD_SELF_MESSAGE("10167","重复记录"),
	ERRCODE_ICCID_IS_NOT_GLSX("10168","流量卡未在广联流量卡平台录入"),
	ERRCODE_EXCEL_DATA_NUM_NOT_EQUEL("10169","excel读取数量与实际上传数量不符"),
	ERRCODE_DEVICE_IS_NOT_OUT("10170","设备未出库"),
	ERRCODE_BEYOND_ORDER_TOTAL("10171","导入总数超出订单需求数"),
	ERRCODE_ATTRIB_CODE_NOT_MATCH("10172","硬件编码不匹配"),
	ERRCODE_LOGISTICS_NULL_CPY("10173","物流公司为空"),
	ERRCODE_LOGISTICS_NULL_NO("10174","物流编号为空"),
	ERRCODE_INVALID_SYSFLAG("10175","未知系统标识"),
	ERRCODE_SYSTEM_LOGISTISC_ADD_FAIL("10176","物流信息添加出错 系统问题"),
	ERRCODE_MATERIIAL_SHORTAGE_IN_NUMBER("10177","库存数小于调出数 无法调出"),
	ERRCODE_FORBID_UPDATE_DELINUM("10178","已经完成的调拨单不能修改发货数量"),
	ERRCODE_FORBID_UPDATE_SIGNNUM("10179","未完成的调拨单不能修改签收数量"),
	ERRCODE_FILE_OUT_OF_RANGE("10180","文件超出大小"),
	ERRCODE_INVALID_DEV_MNUM("10181","根据设备型号找不到合适的设备类型编码"),
	ERRCODE_INVALID_ADDRESS("10182","无效地址"),
	ERRCODE_INVALID_PRODUCT("10183","产品未上架"),
	ERRCODE_INVALID_MATERIAL("10184","物料已被删除"),
	ERRCODE_NULL_STATEMENT_SELL_DATA("10185","待生成对账的发货的设备数据为空"),
	ERRCODE_NULL_MERCHANTCODE_IN_MERCHANTPLAT("10186","商户号在商户平台不存在"),
	ERRCODE_NULL_WAREHOUSE_OF_MERCHANTCODE("10187","未匹配到K3系统的仓库"),
	ERRCODE_NULL_CUSTOMER_OF_MERCHANTCODE("10188","未匹配到K3系统的客户"),
	ERRCODE_RECON_TIME_ISNOT_PROBABLE("10189","选择的对账时间范围内已经存在对账单");
	

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String describle;
	private String code;
	
	private ErrorCodeEnum(String code, String describle){
		
		this.describle	= describle;
		this.code		= code;	
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return describle;
	}		
}
