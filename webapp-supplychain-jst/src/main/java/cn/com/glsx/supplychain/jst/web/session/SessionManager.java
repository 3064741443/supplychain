package cn.com.glsx.supplychain.jst.web.session;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.config.SystemProperty;
import cn.com.glsx.supplychain.jst.utils.RedisCacheManager;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;

/**
 * @Title: SessionManager.java
 * @Description: 会话管理
 * @author Alvin.zengqi
 * @date 2015年12月22日 下午10:02:17
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Component
public class SessionManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisCacheManager cacheManager;
	
	@Autowired
	private SystemProperty property;
	
	public Session createSession(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = getSessionId(request);
		Session session = null;
		try{
			session = cacheManager.get(sessionId, Session.class);
		}catch(Exception e){
			try {
				cacheManager.del(sessionId);
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e);
			} 
			logger.error("get session error:" + e.getMessage(), e);
		}
		if(session == null){
			session = new Session();
			session.setId(sessionId);
			try{
				cacheManager.set(sessionId, session, null);
			}catch(Exception e){
				
			}
		}else{
			if(StringUtils.isEmpty(session.getId())){
				session.setId(sessionId);
			}
		}
		Cookie cookie = new Cookie(property.getSessionIdKey(), sessionId);
		cookie.setPath("/");
		// 有效期30分钟
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
		ThreadLocalCache.setSession(session);
		return ThreadLocalCache.getSession();
	}
	
	public String getSessionId(HttpServletRequest request){
		String sessionId = request.getParameter("sessionId");
		if(StringUtils.isEmpty(sessionId)){
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals(property.getSessionIdKey())){
						sessionId = cookie.getValue();
						continue;
					}
				}
			}
		}
		if (StringUtils.isEmpty(sessionId)) {
			sessionId = request.getRequestedSessionId();
		}
		if (StringUtils.isEmpty(sessionId)) {
			String uri = request.getRequestURI();
			
			if (property.getSessionIdKey() != null) {
				int s = uri.indexOf(property.getSessionIdKey());
				if (s >= 0) {
					s += property.getCachePrefix().length();
					int i = s;
					while (i < uri.length()) {
						char c = uri.charAt(i);
						if (c == ';' || c == '#' || c == '?' || c == '/') {
							break;
						}
						i++;
					}
					sessionId = uri.substring(s,i);
				}
			}
		}
		if (StringUtils.isEmpty(sessionId)) {
			sessionId = UUID.randomUUID().toString().replaceAll("-", "");
		}
		return sessionId;
	}
	
	public void cacheSession(Session session, HttpServletResponse response){
		Cookie cookie = new Cookie(property.getSessionIdKey(), session.getId());
		cookie.setPath("/");
		// 有效期30分钟
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
		ThreadLocalCache.setSession(session);
	}
	
	public Session getSession(String sessionId){
		try {
			return cacheManager.get(sessionId, Session.class);
		} catch (Exception e) {
			logger.debug("get session by Id:" + sessionId + " error:" + e.getMessage(), e);
		} 
		return null;
	}
	
	/**
	 * @Description: 会话集群环境同步
	 * @author Alvin.zengqi  
	 * @date 2017年10月19日 上午10:49:40
	 */
	public void sync(){
		Session session = ThreadLocalCache.getSession();
		if(session != null && session.isSync()){
			session.setSync(false);
			if(session.isLogout()){
				cacheManager.del(session.getId());
				session.clear();
			}else{
				cacheManager.set(session.getId(), session, null);
			}
		}
	}
	
}
