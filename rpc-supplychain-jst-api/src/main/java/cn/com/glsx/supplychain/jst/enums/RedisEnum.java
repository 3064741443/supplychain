package cn.com.glsx.supplychain.jst.enums;

public enum RedisEnum {

	SUPPLYCHAIN_JST_RPCREQUEST_VERIFY_CONSUMER("SUPPLYCHAIN_JST_RPCREQUEST_VERIFY_CONSUMER_"),//RPC请求验证
	JST_REDIS_ATTRI_INFO("JST_REDIS_ATTRI_INFO_");    //attribInfo缓存
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private RedisEnum(String value) {
		this.value = value;
	}
	
	
}
