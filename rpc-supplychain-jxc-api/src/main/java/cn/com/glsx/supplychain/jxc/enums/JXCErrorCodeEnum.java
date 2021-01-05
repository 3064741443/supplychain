package cn.com.glsx.supplychain.jxc.enums;

import cn.com.glsx.framework.core.enums.ErrorEnum;

public enum JXCErrorCodeEnum implements ErrorEnum{
	
	ERRCODE_INVALID_REQ("10100","无效请求"),
	ERRCODE_INVALID_PARAM("10101","无效参数"),
	ERRCODE_INVALID_SIGN("10102","无效签名"),
	ERRCODE_ACCOUNT_USERNAME_ALREADY_USED("10103","用户名已存在"),
	ERRCODE_ACCOUNT_USERNAME_NOT_EXIST("10104","用户名不存在"),
	ERRCODE_ACCOUNT_PASSWORD_WRONG("10105","密码错误"),
	ERRCODE_RECORD_REPEATED("10106","记录重复"),
	ERRCODE_SYSTEM_CACHE_WRITE_FAILED("10107","系统缓存写错误"),
	ERRCODE_SYSTEM_CACHE_READ_FAILED("10108","系统缓存读错误"),
	ERRCODE_SYSTEM_DATABASE_FAILED("10117","数据库读写错误"),
	ERRCODE_SYSTEM_ATTRIB_READ_FAILED("10118","系统属性配置表读取失败"),
	ERRCODE_RECORD_INVALID_ATTRIB_ID("10119","记录配置id错误"),
	ERRCODE_ACCUNT_INVALID_USERNAME("10120","无效用户或者用户无权限"),
	ERRCODE_SYSTEM_RECORD_UNUSUAL("10121","数据库数据异常"),
	ERRCODE_EXTERNAL_INTERFACE("10122","外部接口异常"),
	ERRCODE_REPEAT_ADDRESS("20100","商户地址重复"),
	ERRCODE_SYSTEM_ERROR("20101","后台服务出问题了"),
	ERRCODE_REPEATED_REOCRD_SELF_MESSAGE("10167","重复记录"),
	ERRCODE_SESSION_TIME_OUT("20102","未登陆或者session失效,请重新登陆"),
	ERRCODE_FILE_OUT_OF_RANGE("20103","文件超出大小"),
	ERRCODE_DISPATCH_NUMBER_BIGGER("20104","发货数超出需求数"),
	ERRCODE_MERCHANT_ORDER_NOT_WAIT_DISPATCH("30101","订单不是待分配状态"),
	ERRCODE_MERCHANT_ORDER_NOT_WAIT_SHIPMENTS("30102","订单不是待发货状态"),
	ERRCODE_MERCHANT_ORDER_NOT_WAIT_SHIPMENTSORPART("30103","订单不是待发货或者部分发货状态"),
	ERRCODE_ATTRIB_CODE_EXISTS("30104","硬件配置已经存在"),
	ERRCODE_ATTRIB_CODE_NOT_EXISTS("30105","硬件配置不存在"),
	ERRCODE_MERCHANT_ORDER_NOT_WAIT_CHECK("30106","订单不是待审核状态"),
	ERRCODE_MERCHANT_ORDER_NOT_JXC("30107","非进销存下单不支持取消"),
	ERRCODE_WARNING_SET_REPEATED("30108","预警重复，可到列表中找到该条预警进行阀值修改"),
	ERRCODE_PRODUCT_CONFIG_MATERIAL_REPEATED("30109","同一个物料编码不能被重复配置"),
	ERRCODE_EXPORT_EQUIPMENT_DETAILS("30110","请到日志管理,Excel导出记录中下载");
	
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
	
	private JXCErrorCodeEnum(String code, String describle){
		
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
