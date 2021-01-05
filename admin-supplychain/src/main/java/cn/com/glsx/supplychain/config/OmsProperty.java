package cn.com.glsx.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: OmsProperty.java
 * @Description: 
 * @author zhoudan  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Configuration
@ConfigurationProperties("oms")
public class OmsProperty {

	// 对应配置文件项：oms.static-path
	private String staticPath;
    
    public String getStaticPath()
    {
        return staticPath;
    }
    
    public void setStaticPath(String staticPath)
    {
        this.staticPath = staticPath;
    }
}
