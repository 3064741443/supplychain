package cn.com.glsx.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("dataformat")
public class DataFormatProperty {
	
	public Integer maxPhoneLen;
	
	public Integer maxIccidLen;
	
	public Integer maxImeiLen;
	
	public Integer maxImsiLen;
	
	public Integer maxBatchLen;
	
	public Integer maxVcodeLen;
	
	public Integer maxCommonLen;

	public Integer getMaxPhoneLen() {
		return maxPhoneLen;
	}

	public void setMaxPhoneLen(Integer maxPhoneLen) {
		this.maxPhoneLen = maxPhoneLen;
	}

	public Integer getMaxIccidLen() {
		return maxIccidLen;
	}

	public void setMaxIccidLen(Integer maxIccidLen) {
		this.maxIccidLen = maxIccidLen;
	}

	public Integer getMaxImeiLen() {
		return maxImeiLen;
	}

	public void setMaxImeiLen(Integer maxImeiLen) {
		this.maxImeiLen = maxImeiLen;
	}

	public Integer getMaxImsiLen() {
		return maxImsiLen;
	}

	public void setMaxImsiLen(Integer maxImsiLen) {
		this.maxImsiLen = maxImsiLen;
	}

	public Integer getMaxBatchLen() {
		return maxBatchLen;
	}

	public void setMaxBatchLen(Integer maxBatchLen) {
		this.maxBatchLen = maxBatchLen;
	}

	public Integer getMaxVcodeLen() {
		return maxVcodeLen;
	}

	public void setMaxVcodeLen(Integer maxVcodeLen) {
		this.maxVcodeLen = maxVcodeLen;
	}

	public Integer getMaxCommonLen() {
		return maxCommonLen;
	}

	public void setMaxCommonLen(Integer maxCommonLen) {
		this.maxCommonLen = maxCommonLen;
	}
	
	
	
	
}
