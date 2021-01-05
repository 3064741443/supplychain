package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum SystemPlatEnum {
	
	SYSTEM_PLAT_PRIM_PERIOD("PRIM"), //老运营平台流程
	SYSTEM_PLAT_MIDD_PERIOD("MIDD"); //新运营平台流程
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private SystemPlatEnum(String value) {
		this.value = value;
	}
	
	public static SystemPlatEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,SystemPlatEnum> stringToEnum = new HashMap<String,SystemPlatEnum>();
	static
	{
		for(SystemPlatEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
}
