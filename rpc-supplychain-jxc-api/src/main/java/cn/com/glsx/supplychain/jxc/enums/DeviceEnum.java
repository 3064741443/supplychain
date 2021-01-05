package cn.com.glsx.supplychain.jxc.enums;

import java.util.HashMap;
import java.util.Map;

public enum DeviceEnum {	
	/**
	 * 入库
	 */
	STATUS_IN("IN"),
	
	/**
	 * 出库
	 */
	STATUS_OUT("OUT");

	
	private String value;
	
	DeviceEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public static DeviceEnum findByCode(String code) {
		DeviceEnum[] objs = DeviceEnum.values();
        for (DeviceEnum obj : objs) {
            if (code.equals(obj.getValue())) {
                return obj;
            }
        }
        return null;
    }
	
	public static Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(DeviceEnum.STATUS_IN.getValue(), "入库");
		map.put(DeviceEnum.STATUS_OUT.getValue(), "出库");
		return map;
	}
}
