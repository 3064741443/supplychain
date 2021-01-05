package cn.com.glsx.supplychain.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.CasFilter;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import cn.com.glsx.framework.core.beans.ResultEntity;

@ApiIgnore
@Controller
public class BaseController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserInfoHolder userInfo;
	
	protected User getUser(){
		String jsessionid = getCookie("JSESSIONID");
		if(jsessionid == null){
			return null;
		}
		User user = userInfo.getUserInfo(jsessionid); 
		if(user != null){
			return user;
		}
		Object userSessoin = request.getSession().getAttribute(CasFilter.SESSION_USER_KEY);
		if(userSessoin != null && userSessoin instanceof User){
			return (User) userSessoin;
		}
		return null;
	}
	
	protected String getCookie(String name){
		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length == 0){
			return null;
		}
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(name)){
				return cookie.getValue();
			}
		}
		return null;
	}

	@RequestMapping("/")
	@ResponseBody
	@ApiIgnore
	public ResultEntity<String> index(){
		
		return ResultEntity.error("-200", "数据接口登录成功，请重新获取数据");
	}
}
