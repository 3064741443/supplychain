package cn.com.glsx.supplychain.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SupplyRequest implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消费者
	 */
	private String consumer;

	/**
	 * 消费时间
	 */
	private String time;

	/**
	 * 服务的版本号
	 */
	private String version;

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SupplyRequest ["
				+ (consumer != null ? "consumer=" + consumer + ", " : "")
				+ (time != null ? "time=" + time + ", " : "")
				+ (version != null ? "version=" + version : "") + "]";
	}
	
	
	
	
}
