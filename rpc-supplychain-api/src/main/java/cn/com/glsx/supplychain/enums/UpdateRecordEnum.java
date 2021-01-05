package cn.com.glsx.supplychain.enums;

import java.util.HashMap;
import java.util.Map;

public enum UpdateRecordEnum {

	UPDATE_RECORD_CARD("CA"),//流量卡
	UPDATE_RECORD_USER("US"),//绑定用户
	UPDATE_RECORD_ACTI("AC"),//激活用户
	UPDATE_RECORD_VEHI("VE"),//车辆
	UPDATE_RECORD_FIRM("FA"),//firmware
	UPDATE_RECORD_PACK("PA");//套餐
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private UpdateRecordEnum(String value) {
		this.value = value;
	}
	
	public static UpdateRecordEnum fromString(String symbol)
	{
		return stringToEnum.get(symbol);
	}
	
	private static final Map<String,UpdateRecordEnum> stringToEnum = new HashMap<String,UpdateRecordEnum>();
	static
	{
		for(UpdateRecordEnum r:values())
		{
			stringToEnum.put(r.getValue(), r);
		}
	}
	
	
	
}
