package cn.com.glsx.supplychain.enums;

/**
 * 
 * @Title: BelongEnum
 * @Description: 工厂或仓库类型枚举
 * @author Leiyj  
 * @date 2018年4月13日 上午10:23:20
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
public enum BelongEnum {
	BELONG_FA("FA"),   //工厂
	BELONG_WA("WA");   //仓库
	
	private String value;
	
	BelongEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
