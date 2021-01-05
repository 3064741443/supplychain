package cn.com.glsx.merchant.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("supplyadmin")
public class SupplyadminProperty
{
    private String appid;
    
    private String accesskey;
    
    /**
     * 批量导入启用停用数据最大值
     */
    private String opAllowNum;
    
    /**
     * 上传路径
     */
    private String uploadPath;
    
    /**
     * 下载路径
     */
    private String downloadPath;
    
    /**
     * 文件服务器域名
     */
    private String domain;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}


	public String getOpAllowNum() {
		return opAllowNum;
	}

	public void setOpAllowNum(String opAllowNum) {
		this.opAllowNum = opAllowNum;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
