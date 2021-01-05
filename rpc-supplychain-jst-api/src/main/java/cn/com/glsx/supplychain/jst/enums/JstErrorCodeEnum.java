package cn.com.glsx.supplychain.jst.enums;

import cn.com.glsx.framework.core.enums.ErrorEnum;

public enum JstErrorCodeEnum implements ErrorEnum{
	
	ERRCODE_INVALID_REQ("10100","无效请求"),
	ERRCODE_INVALID_PARAM("10101","无效参数"),
	ERRCODE_INVALID_SIGN("10102","无效签名"),
	ERRCODE_FAILED_PASSWORD("10103","帐号或密码错误"),
	ERRCODE_FAILED_ROLE_SEL("10104","角色选择错误"),
	ERRCODE_RPC_FORBIDDEN("10105","无权限访问RPC服务"),
	ERRCODE_SESSION_TIME_OUT("10106","未登陆或者session失效,请重新登陆"),
	ERRCODE_SYSTEM_CACHE_WRITE_FAILED("10107","系统缓存写错误"),
	ERRCODE_SYSTEM_CACHE_READ_FAILED("10108","系统缓存读错误"),
	ERRCODE_INVALID_PHONE("10109","手机号关联的商户号不存在"),
	ERRCODE_INVALID_JS_CODE("10110","js code无效"),
	ERRCODE_SYSTEM_DATABASE_FAILED("10117","数据库读写错误"),
	ERRCODE_ACCOUNT_NOT_EXIST("20201","帐号不存在,请联系广联客户"),
	ERRCODE_NULL_MERCHANTCODE("20202","未获取到绑定的商户信息，请联系广联运营"),
	ERRCODE_NULL_DEALERUSER("20203","商户用户表未被创建"),
	ERRCODE_NULL_SHOP("20204","门店不存在或被删除"),
	ERRCODE_NULL_SHOPAGENTMERCHANT("20205","门店和供应商关系被解除"),
	ERRCODE_NULL_PRODUCT("20206","产品已经下架"),
	ERRCODE_INVALID_ADDRESS("20207","无效地址"),
	ERRCODE_INVALID_PRODUCT_PRICE("20208","商品价格发生改变"),
	ERRCODE_INVALID_MATERIAL("20209","物料已被删除"),
	ERRCODE_NULL_DEALERUSER_MERCHANT("20210","商户用户表不存在"),
	ERRCODE_INVALID_CART("20211","无效购物车物品"),
	ERRCODE_NULL_SHOP_ORDER("20212","订单不存在"),
	ERRCODE_ORDER_FINISHED("20212","订单已完成"),
	ERRCODE_ORDER_CANCEL("20213","订单被取消"),
	ERRCODE_OVER_SHOP_ORDER_TOTAL("20214","查出当前发货数量加已发货数量大于订单数量"),
	ERRCODE_DEVICESN_ISNOT_IN_STOCK("20215","设备未进库存"),
	ERRCODE_SEFE_MESSAGE("20218","sn未入库"),
	ERRCODE_TOO_LONG_REENTER("20219","停留时间过长,请重新进入"),
	ERRCODE_MORE_THAN_CATDEF("20220","单个产品超过购物车数量"),
	ERRCODE_NULL_ADDRESS("20221","无效地址"),
	ERRCODE_FAILED_GEN_SHOPPING_CARD("20221","生成购物车失败"),
	ERRCODE_INVALID_PRODUCT_CONFIG_CODE("20222","无效产品配置编码"),
	ERRCODE_INVALID_PRODUCT_CONFIG_OTHERS("20223","产品配置中无效其他配置"),
	ERRCODE_INVALID_GH_CART_CODE("20224","无效购物车编码"),
	ERRCODE_ORDER_SHIPPED("20215","订单已发货");
	
	
	private String describle;
	private String code;

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

	private JstErrorCodeEnum(String code,String describle) {
		this.describle = describle;
		this.code = code;
	}

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
	
	

}
