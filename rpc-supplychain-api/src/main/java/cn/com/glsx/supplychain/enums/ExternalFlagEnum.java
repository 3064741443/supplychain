package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum ExternalFlagEnum {

	//内部卡内部设备
	EXTERNALFLAG_IN("IN"),
	//内部卡外部设备
	EXTERNALFLAG_EX("EX"),
	//外部卡外部设备
	EXTERNALFLAG_AX("AX");
	
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
	private ExternalFlagEnum(String value) {
		this.value = value;
	}
	
	public static ExternalFlagEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,ExternalFlagEnum> stringToEnum = new HashMap<String,ExternalFlagEnum>();
	static
	{
		for(ExternalFlagEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
}
