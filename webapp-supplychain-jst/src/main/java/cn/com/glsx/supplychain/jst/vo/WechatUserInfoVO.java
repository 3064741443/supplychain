package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WechatUserInfoVO implements Serializable{

	private String nickName;
	
	private Integer gender;
	
	private String language;
	
	private String province;
	
	private String city;
	
	private String country;
	
	private String avatarUrl;
	
	private String unionId;
	
	private String openId;
	
	private String appId;
	
	private String wxId;
	
	private WatermarkVO watermark;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public WatermarkVO getWatermark() {
		return watermark;
	}

	public void setWatermark(WatermarkVO watermark) {
		this.watermark = watermark;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	@Override
	public String toString() {
		return "WechatUserInfoVO [nickName=" + nickName + ", gender=" + gender
				+ ", language=" + language + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", avatarUrl="
				+ avatarUrl + ", unionId=" + unionId + ", openId=" + openId
				+ ", appId=" + appId + ", wxId=" + wxId + ", watermark="
				+ watermark + "]";
	}
}
