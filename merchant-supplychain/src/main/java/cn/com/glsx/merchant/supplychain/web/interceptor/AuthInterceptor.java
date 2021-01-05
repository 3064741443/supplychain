package cn.com.glsx.merchant.supplychain.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.vo.WeappUserInfoVO;
import cn.com.glsx.merchant.supplychain.web.annotation.NeedLogin;
import cn.com.glsx.merchant.supplychain.web.session.Session;


/**
 * 授权拦截
 * <P>Description： </P>
 *
 * @author Alvin
 * @version 1.0
 */
@Component
@Order(2)
public class AuthInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mav) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		Session session = ThreadLocalCache.getSession();
		Map<String, String> params = getParameterMap(request);
		logger.info("[ip:{}, method:{}, uri:{}, params:{}, user-agent:{}, session:{}"
				, request.getRemoteAddr()
				, request.getMethod()
				, request.getRequestURI()
				, params
				, request.getHeader("User-Agent")
				, session);
		WeappUserInfoVO weappUserInfo = session.get(Constants.SessionKey.SESSION_USERINFO_KEY.getValue(), WeappUserInfoVO.class);
		ThreadLocalCache.setWeappUserInfo(weappUserInfo);
		HandlerMethod method = null;
		if(handler instanceof HandlerMethod){
			method = (HandlerMethod) handler;
		}
		boolean isAuthorized = (weappUserInfo == null) && method != null && (method.getMethodAnnotation(NeedLogin.class) != null || method.getBeanType().getDeclaredAnnotation(NeedLogin.class) != null); 
		if(isAuthorized){
			response.sendError(401, "Unauthorized");
			return false;
		}
		return true;
	}

	private HashMap<String, String> getParameterMap(HttpServletRequest request){
		HashMap<String, String> parameterMap = new HashMap<String, String>(16);
		for(String name:request.getParameterMap().keySet()){
			parameterMap.put(name, request.getParameter(name));
		}
		return parameterMap;
	}
	
}
