package cn.com.glsx.supplychain.jst.enums;

public enum ServiceTypeEnum {
	
	/**
	 * 驾宝无忧
	 */
	SERVICE_TYPE_JB((byte)1,"驾保无忧"),
	
	/**
	 * 金融风控
	 */
	SERVICE_TYPE_JR((byte)2,"金融风控"),
	
	/**
	 * 车载导航
	 */
	SERVICE_TYPE_CZ((byte)3,"车载导航"),
	
	/**
	 * 后视镜
	 */
	SERVICE_TYPE_HS((byte)4,"后视镜");
	
	

	private byte code;
    private String name;
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private ServiceTypeEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
	
}
