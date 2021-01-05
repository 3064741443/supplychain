package cn.com.glsx.supplychain.jst.enums;

import cn.com.glsx.framework.core.enums.ErrorEnum;

public enum ErrorCodeEnum implements ErrorEnum{
	
	ERRCODE_INVALID_REQ("10100","无效请求"),
	ERRCODE_INVALID_PARAM("10101","无效参数"),
	ERRCODE_MAX_IMPORT_SIZE("10102","超过最大导入数"),
	INVALID_DEVICE_DATA_FORMAT("10103","必填字段为空"),
	ERRCODE_NO_SN("10105","SN不在库存中"),
	ERRCODE_SN("10106","SN在库存出现多个"),
	ERRCODE_NAME_REPEAT("10107","门店名称重复"),
	ERRCODE_MERCHANT_NAME_REPEAT("10115","商户号不存在"),
	ERRCODE_NAME_PHONE("10108","联系电话重复"),
	ERRCODE_SHOP_NAME("10109","店面名称存在相同"),
	ERRCODE_ADDR_TOO_LONG("10108","门店地址过长"),
	ERRCODE_PHONE_TOO_LONG("10109","手机号过长"),
	ERRCODE_SHOP_NAME_TOO_LONG("10110","门店名称过长"),
	ERRCODE_CONTACT_TOO_LONG("10111","联系人过长"),
	ERRCODE_AGENT_MERCHANT_CODE_TOO_LONG("10112","供货商编号过长"),
	ERRCODE_AGENT_MERCHANT_NAME_TOO_LONG("10113","供货商名称过长"),
	ERRCODE_SN_TOO_LONG("10114","SN长度过长"),
	ERRCODE_FILE_OUT_OF_RANGE("10104","文件超出大小");
	

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
