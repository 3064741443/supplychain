package cn.com.glsx.supplychain.jst.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wechat")
public class WechatProperty {

	private String weAppId;
	
	private String weAppSecret;

	public String getWeAppId() {
		return weAppId;
	}

	public void setWeAppId(String weAppId) {
		this.weAppId = weAppId;
	}

	public String getWeAppSecret() {
		return weAppSecret;
	}

	public void setWeAppSecret(String weAppSecret) {
		this.weAppSecret = weAppSecret;
	}

	@Override
	public String toString() {
		return "WechatProperty [weAppId=" + weAppId + ", weAppSecret="
				+ weAppSecret + "]";
	}
	
	
}
