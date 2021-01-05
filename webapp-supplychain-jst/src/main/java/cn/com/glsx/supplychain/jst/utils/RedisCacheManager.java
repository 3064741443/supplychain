package cn.com.glsx.supplychain.jst.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.com.glsx.supplychain.jst.config.SystemProperty;

@Component
public class RedisCacheManager {

	@Autowired
	private SystemProperty property;
	
	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	public Object get(String key) {
		return redisTemplate.opsForValue().get(property.getCachePrefix().concat(key));
	}
	
	public <T> T get(String key, Class<T> classType) {
		Object value = get(key);
		if (value != null) { 
			return classType.cast(value); 
		}
		return null;
	}

	public void set(String key, Serializable value) {
		redisTemplate.opsForValue().set(property.getCachePrefix().concat(key), value);
	}

	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 * @param expireTime 失效时间（单位：秒）
	 * @author Alvin
	 * @date 2019年4月19日 上午11:19:22
	 */
	public void set(String key, Serializable value, Long expireTime) {
		if(expireTime == null){
			expireTime = property.getCacheExpire();
		}
		redisTemplate.opsForValue().set(property.getCachePrefix().concat(key), value, expireTime, TimeUnit.SECONDS);
	}
	
	public void del(String key) {
		this.redisTemplate.delete(property.getCachePrefix().concat(key));
	}
}
