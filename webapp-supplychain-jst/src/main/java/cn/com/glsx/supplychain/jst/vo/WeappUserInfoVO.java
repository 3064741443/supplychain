package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

public class WeappUserInfoVO implements Serializable{

	private static final long serialVersionUID = -5609081987754083629L;

	/**
     * 账户ID(用户中心账号ID)
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别(1：男性，2：女性，0：未知 )
     */
    private Integer sex;

    /**
     * 头像
     */
    private String logo;
    
    /**
     * 微信openid
     */
    private String openId;
    
    /**
     * 微信用户唯一id
     */
    private String unionId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Override
	public String toString() {
		return "WeappUserInfoVO [userId=" + userId + ", nickName=" + nickName
				+ ", phone=" + phone + ", sex=" + sex + ", logo=" + logo
				+ ", openId=" + openId + ", unionId=" + unionId + "]";
	}
}
