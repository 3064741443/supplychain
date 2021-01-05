package cn.com.glsx.supplychain.model;

import java.io.Serializable;

//商户信息（临时变量）
public class SupplyChainMerchantInfo implements Serializable {

	private Integer id;   //商户id
	
	private String name;  //商户名称
	
	private String type;  //商户类型

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
