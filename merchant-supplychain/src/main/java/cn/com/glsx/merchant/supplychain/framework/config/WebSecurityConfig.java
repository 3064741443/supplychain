/*
package cn.com.glsx.merchant.supplychain.framework.config;
*/
/**
 * liuyf
 *//*


import cn.com.glsx.merchant.supplychain.config.OmsProperty;
import cn.com.glsx.merchant.supplychain.framework.common.Constants;
import cn.com.glsx.merchant.supplychain.framework.exception.BusinessException;
import cn.com.glsx.merchant.supplychain.framework.utils.StringUtils;
import org.oreframework.boot.autoconfigure.cas.config.CasProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * 登录配置
 *//*

@Configuration
//@EnableWebMvc
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private OmsProperty omsProperty;

    @Autowired
    private CasProperties casProperties;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        //addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/dync_captcha");

        addInterceptor.excludePathPatterns("/api/**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
//            String url = request.getRequestURL().toString();
            String method = request.getMethod();

            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
//            logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
            logger.info(String.format("请求参数：method: %s, uri: %s, params: %s", method, uri, queryString));
           // request.getSession().setMaxInactiveInterval(5);
            Object obj = request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
            if (null == obj) { //未登录
                String ajaxXRequestedWith = request.getHeader("x-requested-with");
                if (StringUtils.isNullOrEmpty(ajaxXRequestedWith))
                    ajaxXRequestedWith = request.getHeader("X-Requested-With");
*/
/*                if ("XMLHttpRequest".equalsIgnoreCase(ajaxXRequestedWith)) { //如果是ajax请求响应头会有，x-requested-with
                    //在响应头设置session状态
                    response.setHeader("sessionstatus", "timeout");
                } else {
                    //跳转登录
                    response.sendRedirect(request.getContextPath() + "/login");
                }*//*

                response.sendRedirect( request.getContextPath().concat("/login"));
                return false;
            }
            return super.preHandle(request, response, handler);
        }
    }

}*/
