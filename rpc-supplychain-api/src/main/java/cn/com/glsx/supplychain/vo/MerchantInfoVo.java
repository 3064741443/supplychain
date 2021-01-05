package cn.com.glsx.supplychain.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MerchantInfoVo implements Serializable{

	private Integer merchantId;//商户号
    private String passWord;//登录密码
    private String merchantName;//商户名称
    private String merchantAlias;//商户别名
    private Integer merchantChannel;//渠道
    private String contactor;//联系人名称
    private String contactPhone;//联系人电话
    private Integer parentId;//上级商户id
    private String merchantSource;//商户注册来源
    private Integer merchantStatus;//商户状态
    private String lng;//经度
    private String lat;//纬度
    private String gpsAddress;//经纬度地址
    private Integer thirdMerchantId;//第三方商户id
    private String storePhoto;//门店照片
    
    private String merchantPortrait;//商户头像
    private Integer provinceId;//省份id
    private String provinceName;//省份名称
    private Integer cityId;//市id
    private String cityName;//市名称
    private Integer areaId;//区id
    private String areaName;//区名称
    private String address;//地址
    private String phone;//电话
    private String fax;//传真
    private String email;//邮箱
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    
    private Integer groupId;
    private String groupName;
    private String groupCode;
    private Integer businessCode;
    
    
    
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Integer getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(Integer businessCode) {
		this.businessCode = businessCode;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantAlias() {
		return merchantAlias;
	}
	public void setMerchantAlias(String merchantAlias) {
		this.merchantAlias = merchantAlias;
	}
	public Integer getMerchantChannel() {
		return merchantChannel;
	}
	public void setMerchantChannel(Integer merchantChannel) {
		this.merchantChannel = merchantChannel;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getMerchantSource() {
		return merchantSource;
	}
	public void setMerchantSource(String merchantSource) {
		this.merchantSource = merchantSource;
	}
	public Integer getMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(Integer merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getGpsAddress() {
		return gpsAddress;
	}
	public void setGpsAddress(String gpsAddress) {
		this.gpsAddress = gpsAddress;
	}
	public Integer getThirdMerchantId() {
		return thirdMerchantId;
	}
	public void setThirdMerchantId(Integer thirdMerchantId) {
		this.thirdMerchantId = thirdMerchantId;
	}
	public String getStorePhoto() {
		return storePhoto;
	}
	public void setStorePhoto(String storePhoto) {
		this.storePhoto = storePhoto;
	}
	public String getMerchantPortrait() {
		return merchantPortrait;
	}
	public void setMerchantPortrait(String merchantPortrait) {
		this.merchantPortrait = merchantPortrait;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
