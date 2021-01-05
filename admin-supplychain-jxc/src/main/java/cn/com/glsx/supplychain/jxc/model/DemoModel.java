package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;

/**
 * @Title: DemoModel.java
 * @Description:
 * @author deployer name
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@SuppressWarnings("serial")
public class DemoModel implements Serializable{
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
