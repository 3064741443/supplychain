package cn.com.glsx.merchant.supplychain.web.session;

import java.io.Serializable;
import java.util.HashMap;


/**
 * @Title: Session.java
 * @Description:
 * @author Alvin.zengqi  
 * @date 2015年12月22日 下午9:57:35
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class Session extends HashMap<String, Serializable> {
	
	private String id;
	
	private boolean sync = false;
	
	private boolean logout = false;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
	
	public <T> T get(String key, Class<T> clazz){
		Object value = get(key);
		if(value != null){
			return clazz.cast(value);
		}
		return null;
	}

	public boolean isLogout() {
		return logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
		if(logout){
			setSync(true);
		}
	}
	
	@Override
	public Serializable put(String key, Serializable value) {
		this.sync = true;
		return super.put(key, value);
	}
	
	@Override
	public String toString() {
		return "[id:" + id + ", sync:" + sync + ", logout:" + logout + ", Map:" + super.toString() + "]";
	}
}
