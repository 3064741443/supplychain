package cn.com.glsx.supplychain.enums;

/**
 * 物料类型
 */
public enum MaterialTypeEnum {

    /**
	 * 硬件
	 */
	MATERIAL_TYPE_HARD((byte)0,"硬件"),
	
	/**
	 * 网联软件
	 */
	MATERIAL_TYPE_SOFT((byte)1,"网联软件"),
	
	/**
	 * 风险评估
	 */
	MATERIAL_TYPE_RISK((byte)2,"风险评估"),
	
	/**
	 * 风控服务
	 */
	MATERIAL_TYPE_CTRL((byte)3,"风控服务"),
	
	/**
	 * 安装服务
	 */
	MATERIAL_TYPE_INST((byte)4,"安装服务"),
	
	/**
	 * 智慧门店服务
	 */
	MATERIAL_TYPE_AIST((byte)5,"智慧门店服务"),
	
	/**
	 * AI车联网服务
	 */
	MATERIAL_TYPE_AINE((byte)6,"AI车联网服务"),
	
	/**
	 * 配件
	 */
	MATERIAL_TYPE_PART((byte)7,"配件");

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
	private MaterialTypeEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}

    
}
