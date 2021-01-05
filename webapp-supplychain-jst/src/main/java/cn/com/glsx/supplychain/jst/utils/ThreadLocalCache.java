package cn.com.glsx.supplychain.jst.utils;

import cn.com.glsx.supplychain.jst.vo.WeappUserInfoVO;
import cn.com.glsx.supplychain.jst.web.session.Session;

public class ThreadLocalCache {

private static ThreadLocal<Session> sessionLocal = new ThreadLocal<Session>();
	
	private static ThreadLocal<String> openIdLocal = new ThreadLocal<String>();
	
	private static ThreadLocal<WeappUserInfoVO> weappUserInfoLocal = new ThreadLocal<WeappUserInfoVO>();
	
	public static void setSession(Session session){
		sessionLocal.set(session);
	}
	
	public static Session getSession(){
		return sessionLocal.get();
	}
	
	public static void removeSession(){
		sessionLocal.remove();
	}
	
	public static void setOpenId(String openId){
		openIdLocal.set(openId);
	}
	
	public static String getOpenId(){
		return openIdLocal.get();
	}
	
	public static void removeOpenId(){
		openIdLocal.remove();
	}

	public static void setWeappUserInfo(WeappUserInfoVO weappUserInfo){
		weappUserInfoLocal.set(weappUserInfo);
	}
	
	public static WeappUserInfoVO getWeappUserInfo(){
		return weappUserInfoLocal.get();
	}
	
	public static void removeWeappUserInfo(){
		weappUserInfoLocal.remove();
	}
}
