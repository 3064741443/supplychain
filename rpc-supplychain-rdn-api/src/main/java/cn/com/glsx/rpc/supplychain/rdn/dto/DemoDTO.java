package cn.com.glsx.rpc.supplychain.rdn.dto;

import java.io.Serializable;

/**
 * @Title: DemoDTO.java
 * @Description:
 * @author ${userName}
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@SuppressWarnings("serial")
public class DemoDTO implements Serializable{
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
