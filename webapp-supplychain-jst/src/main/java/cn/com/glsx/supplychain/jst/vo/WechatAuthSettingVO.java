package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WechatAuthSettingVO implements Serializable{
	
	private String consumer;
	
	private String version;

	/**
	 * 包括敏感数据在内的完整用户信息的加密数据
	 */
	private String encryptedData;
	
	/**
	 * 加密算法初始向量
	 */
	private String iv;
	
	/**
	 * 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 */
	private String signature;
	
	/**
	 * 不包括敏感信息的原始数据字符串，用于计算签名
	 */
	private String rawData;
	
	/**
	 * 用户信息对象，不包含 openid 等敏感信息
	 */
	private WechatUserInfoVO userInfo;
	
	/**
	 * 登录CODE
	 */
	private String code;
	
	private String unionId;
	
	private String openId;
	
	private String appId;
	
	private String wxId;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 场景路径
	 */
	private String sceneUrl;
	
	/**
	 * 场景参数
	 */
	private String sceneParam;
	
	
	
	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public WechatUserInfoVO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(WechatUserInfoVO userInfo) {
		this.userInfo = userInfo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSceneUrl() {
		return sceneUrl;
	}

	public void setSceneUrl(String sceneUrl) {
		this.sceneUrl = sceneUrl;
	}

	public String getSceneParam() {
		return sceneParam;
	}

	public void setSceneParam(String sceneParam) {
		this.sceneParam = sceneParam;
	}

	@Override
	public String toString() {
		return "WechatAuthSettingVO [encryptedData=" + encryptedData + ", iv="
				+ iv + ", signature=" + signature + ", rawData=" + rawData
				+ ", userInfo=" + userInfo + ", code=" + code + ", unionId="
				+ unionId + ", openId=" + openId + ", appId=" + appId
				+ ", wxId=" + wxId + ", gender=" + gender + ", sceneUrl="
				+ sceneUrl + ", sceneParam=" + sceneParam + ", toString()="
				+ super.toString() + "]";
	}
}
