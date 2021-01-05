package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum PackageStatuEnum {

	PACKAGE_STATU_UNACTIVE("UA"), //未激活
	PACKAGE_STATU_ALACTIVE("AL"), //已激活
	PACKAGE_STATU_INITIAL("IN");  //初始化
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private PackageStatuEnum(String value) {
		this.value = value;
	}
	
	public static PackageStatuEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,PackageStatuEnum> stringToEnum = new HashMap<String,PackageStatuEnum>();
	static
	{
		for(PackageStatuEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
}
