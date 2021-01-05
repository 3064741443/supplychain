package cn.com.glsx.merchant.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: SystemProperty.java
 * @Description: 
 * @author deployer name  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Configuration
@ConfigurationProperties("system")
public class SystemProperty {
	
	private String name;
	
	/**
	 * 会话ID键值
	 */
	private String sessionIdKey;

	/**
	 * 缓存键值前缀
	 */
	private String cachePrefix;
	
	/**
	 * 缓存失效时间（单位：秒）
	 */
	private Long cacheExpire;

	/**
	 * 云商商户号(20：云商终端联盟；30：嘀加智慧门店)
	 */
	private String clubMerchantId;
	
	public String getSessionIdKey() {
		return sessionIdKey;
	}

	public void setSessionIdKey(String sessionIdKey) {
		this.sessionIdKey = sessionIdKey;
	}

	public String getCachePrefix() {
		return cachePrefix;
	}

	public void setCachePrefix(String cachePrefix) {
		this.cachePrefix = cachePrefix;
	}

	public Long getCacheExpire() {
		return cacheExpire;
	}

	public void setCacheExpire(Long cacheExpire) {
		this.cacheExpire = cacheExpire;
	}

	public String getClubMerchantId() {
		return clubMerchantId;
	}

	public void setClubMerchantId(String clubMerchantId) {
		this.clubMerchantId = clubMerchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
