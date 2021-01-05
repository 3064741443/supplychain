package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WatermarkVO implements Serializable{

	private String appid;
	
	private Long timestamp;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "WatermarkVO [appid=" + appid + ", timestamp=" + timestamp + "]";
	}
	
	
}
