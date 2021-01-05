package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BsAddressDTO extends BaseDTO implements Serializable{

	private Long id;

    private String name;

    private String mobile;

    private Integer provinceId;

    private String provinceName;

    private Integer cityId;

    private String cityName;

    private Integer areaId;

    private String areaName;

    private String address;

    private String merchantCode;
    
    

    private String deletedFlag;

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

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "BsAddressDTO [id=" + id + ", name=" + name + ", mobile="
				+ mobile + ", provinceId=" + provinceId + ", provinceName="
				+ provinceName + ", cityId=" + cityId + ", cityName="
				+ cityName + ", areaId=" + areaId + ", areaName=" + areaName
				+ ", address=" + address + ", merchantCode=" + merchantCode
				+ ", deletedFlag=" + deletedFlag + "]";
	}

	
    
    

}
