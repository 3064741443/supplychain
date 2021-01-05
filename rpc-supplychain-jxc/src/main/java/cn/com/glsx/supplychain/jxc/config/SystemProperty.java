package cn.com.glsx.supplychain.jxc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: SystemProperty.java
 * @Description: 
 * @author deployer name  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Configuration
@ConfigurationProperties("system")
public class SystemProperty {
	
	// 对应配置项：system.name
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
