package cn.com.glsx.merchant.supplychain.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.merchant.supplychain.web.session.SessionManager;

/**
 * @Title: SessionInterceptor.java
 * @Description:
 * @author Alvin.zengqi  
 * @date 2015年12月23日 上午11:39:38
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Component
@Order(1)
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String sessionId = request.getParameter("sessionId");
		if(!StringUtils.isEmpty(sessionId)){
			Session session = sessionManager.getSession(sessionId);
			if(session != null){
				sessionManager.cacheSession(session, response);
			}else{
				sessionManager.createSession(request, response);
			}
		}else{
			sessionManager.createSession(request, response);
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object response, ModelAndView arg3) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object response, Exception arg3) throws Exception {
		// 同步集群环境session
		sessionManager.sync();

		removeThreadLocal();
	}
	
	/**
	 * @Description: 清除线程变量，防止OOM
	 * @author Alvin.zengqi  
	 * @date 2017年10月19日 上午10:52:57
	 */
	private void removeThreadLocal(){
		ThreadLocalCache.removeSession();
		ThreadLocalCache.removeOpenId();
		ThreadLocalCache.removeWeappUserInfo();
	}
}
