package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleFlagTypeEnum {

	/**
	 * 车牌标识
	 */
	VEHICLETYPE_VN("VN"),
	
	/**
	 * 用户平台ID
	 */
	VEHICLETYPE_UD("UD"),
	
	/**
	 * 其他标识
	 */
	VEHICLETYPE_OT("OT");
	
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
	private VehicleFlagTypeEnum(String value) {
		this.value = value;
	}
	
	public static VehicleFlagTypeEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,VehicleFlagTypeEnum> stringToEnum = new HashMap<String,VehicleFlagTypeEnum>();
	static
	{
		for(VehicleFlagTypeEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
	
}
