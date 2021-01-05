package cn.com.glsx.supplychain.jst.config;

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
	
	// 对应配置项：system.name
	private String name;

	private String address;
	private String sendName;
	private String sendPwd;
	private String sendSmtp;
	private String supplyReceiver;
	private String mailHead;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendPwd() {
		return sendPwd;
	}

	public void setSendPwd(String sendPwd) {
		this.sendPwd = sendPwd;
	}

	public String getSendSmtp() {
		return sendSmtp;
	}

	public void setSendSmtp(String sendSmtp) {
		this.sendSmtp = sendSmtp;
	}

	public String getSupplyReceiver() {
		return supplyReceiver;
	}

	public void setSupplyReceiver(String supplyReceiver) {
		this.supplyReceiver = supplyReceiver;
	}

	public String getMailHead() {
		return mailHead;
	}

	public void setMailHead(String mailHead) {
		this.mailHead = mailHead;
	}
}
