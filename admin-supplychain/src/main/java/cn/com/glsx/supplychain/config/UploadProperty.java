package cn.com.glsx.supplychain.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: UploadProperty.java
 * @Description:
 * @author Alvin.zengqi  
 * @date 2018年5月11日 上午11:02:04
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Configuration
@ConfigurationProperties("upload")
public class UploadProperty {
	
	/**
	 * 文件访问路径
	 */
	private Map<String, String> urls;
	/**
	 * 文件存储路径
	 */
	private Map<String, String> dirs;
	/**
	 * 文件大小限制（默认最大5MB）
	 */
	private Integer maxSize = 5;

	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	public Map<String, String> getDirs() {
		return dirs;
	}

	public void setDirs(Map<String, String> dirs) {
		this.dirs = dirs;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
}
