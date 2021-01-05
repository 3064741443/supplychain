package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

//第三方数据类型标识
public enum UserFlagTypeEnum {

	/**
	 * 用户手机号
	 */
	USERTYPE_PH("PH"),
	
	/**
	 * 用户平台ID
	 */
	USERTYPE_UD("UD"),
	
	/**
	 * 其他标识
	 */
	USERTYPE_OT("OT");
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param value
	 */
	private UserFlagTypeEnum(String value) {
		this.value = value;
	}
	
	public static UserFlagTypeEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,UserFlagTypeEnum> stringToEnum = new HashMap<String,UserFlagTypeEnum>();
	static
	{
		for(UserFlagTypeEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
	
}
