package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTBsAddressDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "id", notes = "地址id", dataType = "int", required = false, example = "3")
	private Integer id;
	@ApiModelProperty(name = "name", notes = "联系人", dataType = "string", required = true, example = "张小姐")
    private String name;
	@ApiModelProperty(name = "mobile", notes = "联系电话", dataType = "string", required = true, example = "13412341234")
    private String mobile;
	@ApiModelProperty(name = "provinceId", notes = "省份id", dataType = "int", required = true, example = "32")
    private Integer provinceId;
	@ApiModelProperty(name = "provinceName", notes = "省份名称", dataType = "string", required = true, example = "安徽省")
    private String provinceName;
	@ApiModelProperty(name = "cityId", notes = "城市id", dataType = "int", required = true, example = "3")
    private Integer cityId;
	@ApiModelProperty(name = "cityName", notes = "城市名称", dataType = "string", required = true, example = "合肥市")
    private String cityName;
	@ApiModelProperty(name = "areaId", notes = "区域id", dataType = "int", required = true, example = "3")
    private Integer areaId;
	@ApiModelProperty(name = "areaName", notes = "区域名称", dataType = "string", required = true, example = "江海区")
    private String areaName;
	@ApiModelProperty(name = "address", notes = "详细地址", dataType = "string", required = true, example = "人民路3号大院")
    private String address;
	@ApiModelProperty(name = "merchantCode", notes = "商户编码", dataType = "string", required = false, example = "商户编号")
    private String merchantCode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
		return "JXCMTBsAddressDTO [id=" + id + ", name=" + name + ", mobile="
				+ mobile + ", provinceId=" + provinceId + ", provinceName="
				+ provinceName + ", cityId=" + cityId + ", cityName="
				+ cityName + ", areaId=" + areaId + ", areaName=" + areaName
				+ ", address=" + address + ", merchantCode=" + merchantCode
				+ "]";
	}
	
	
}
