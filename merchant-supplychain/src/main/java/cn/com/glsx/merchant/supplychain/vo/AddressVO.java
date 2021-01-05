package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddressVO implements Serializable{

	//数据id
	private Long id;

	//联系人
    private String name;

    //联系电话
    private String mobile;

    //省份id
    private Integer provinceId;

    //省份名称
    private String provinceName;
    
    //城市id
    private Integer cityId;
    
    //城市名称
    private String cityName;

    //地区id
    private Integer areaId;

    //地区名称
    private String areaName;

    //地址
    private String address;

    //商户编码
    private String merchantCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Override
	public String toString() {
		return "AddressVo [id=" + id + ", name=" + name + ", mobile=" + mobile
				+ ", provinceId=" + provinceId + ", provinceName="
				+ provinceName + ", cityId=" + cityId + ", cityName="
				+ cityName + ", areaId=" + areaId + ", areaName=" + areaName
				+ ", address=" + address + ", merchantCode=" + merchantCode
				+ "]";
	}
    
    

   
}
