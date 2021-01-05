package cn.com.glsx.merchant.supplychain.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.com.glsx.merchant.supplychain.web.interceptor.AuthInterceptor;
import cn.com.glsx.merchant.supplychain.web.interceptor.SessionInterceptor;


/**
 * @Title: SpringMvcConfig.java
 * @Description:
 * @author Alvin  
 * @date 2019年5月30日 下午3:53:13
 * @version V1.0  
 * @Company: didihu.com.cn
 * @Copyright Copyright (c) 2018
 */
@SpringBootConfiguration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Autowired
	private SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authInterceptor).addPathPatterns("/**");
	}

}
