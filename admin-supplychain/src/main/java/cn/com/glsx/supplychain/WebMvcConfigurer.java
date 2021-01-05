package cn.com.glsx.supplychain;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title: Main.java
 * @Description:
 * @author zhoudan 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter
{
    @Override  
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**")  
                .allowedOrigins("*")
                .allowCredentials(true)  
                .allowedMethods("HEAD","GET", "POST", "DELETE", "PUT")  
                .maxAge(3600);  
    }  
}
