package cn.com.glsx.merchant.supplychain.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: NeedLogin.java
 * @Description: 登录拦截注解
 * @author Alvin.zengqi  
 * @date 2015年9月26日 下午12:12:01
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin
{
	
}
