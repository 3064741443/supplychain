package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDTO implements Serializable{

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


}
