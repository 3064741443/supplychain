package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum OutStorageTypeEnum {
	
	//扫码出库
	OUT_STORAGE_TYPE_SCANNER("SC"),
	//excel出库
	OUT_STORAGE_TYPE_EXCEL("EX"),
	//cyb库同步过来的数据
	OUT_STORAGE_TYPE_SYNC("SY"),
	//外部卡外部设备自动生成
	OUT_STORAGE_TYPE_AUTO("AU");

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private OutStorageTypeEnum(String value) {
		this.value = value;
	}
	
	public static OutStorageTypeEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,OutStorageTypeEnum> stringToEnum = new HashMap<String,OutStorageTypeEnum>();
	static
	{
		for(OutStorageTypeEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
}
