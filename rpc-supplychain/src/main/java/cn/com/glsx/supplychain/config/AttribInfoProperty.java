package cn.com.glsx.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("attribinfo")
public class AttribInfoProperty {

	private Integer netUnknow;
	private Integer netNo;
	private Integer net2G;
	private Integer net3G;
	private Integer net4G;
	private Integer net5G;
	private Integer cardSelfUnknow;
	private Integer cardSelfMy;
	private Integer cardSelfExtain;
	public Integer getNetUnknow() {
		return netUnknow;
	}
	public void setNetUnknow(Integer netUnknow) {
		this.netUnknow = netUnknow;
	}
	public Integer getNetNo() {
		return netNo;
	}
	public void setNetNo(Integer netNo) {
		this.netNo = netNo;
	}
	public Integer getNet2G() {
		return net2G;
	}
	public void setNet2G(Integer net2g) {
		net2G = net2g;
	}
	public Integer getNet3G() {
		return net3G;
	}
	public void setNet3G(Integer net3g) {
		net3G = net3g;
	}
	public Integer getNet4G() {
		return net4G;
	}
	public void setNet4G(Integer net4g) {
		net4G = net4g;
	}
	public Integer getNet5G() {
		return net5G;
	}
	public void setNet5G(Integer net5g) {
		net5G = net5g;
	}
	public Integer getCardSelfUnknow() {
		return cardSelfUnknow;
	}
	public void setCardSelfUnknow(Integer cardSelfUnknow) {
		this.cardSelfUnknow = cardSelfUnknow;
	}
	public Integer getCardSelfMy() {
		return cardSelfMy;
	}
	public void setCardSelfMy(Integer cardSelfMy) {
		this.cardSelfMy = cardSelfMy;
	}
	public Integer getCardSelfExtain() {
		return cardSelfExtain;
	}
	public void setCardSelfExtain(Integer cardSelfExtain) {
		this.cardSelfExtain = cardSelfExtain;
	}
	
	
}
