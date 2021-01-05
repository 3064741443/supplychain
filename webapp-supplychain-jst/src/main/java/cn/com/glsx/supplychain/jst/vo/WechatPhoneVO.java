package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

public class WechatPhoneVO implements Serializable{

	private static final long serialVersionUID = -5428982796737076830L;
	
	/**
	 * 用户绑定的手机号（国外手机号会有区号）
	 */
	private String phoneNumber;
	
	/**
	 * 没有区号的手机号
	 */
	private String purePhoneNumber;
	
	/**
	 * 区号
	 */
	private String countryCode;
	
	private WatermarkVO watermark;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}

	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public WatermarkVO getWatermark() {
		return watermark;
	}

	public void setWatermark(WatermarkVO watermark) {
		this.watermark = watermark;
	}

	@Override
	public String toString() {
		return "WechatPhoneVO [phoneNumber=" + phoneNumber
				+ ", purePhoneNumber=" + purePhoneNumber + ", countryCode="
				+ countryCode + ", watermark=" + watermark + "]";
	}
	
	
	
}
