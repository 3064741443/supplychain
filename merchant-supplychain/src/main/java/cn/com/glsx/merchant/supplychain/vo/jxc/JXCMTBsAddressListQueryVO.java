package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsAddressListQueryVO implements Serializable{

	@ApiModelProperty(name = "merchantCode", notes = "指定商户地址,如不指定 返回代理商本身地址", dataType = "String", required = false, example = "32")
    private String merchantCode;
	@ApiModelProperty(name = "provinceId", notes = "省份id", dataType = "int", required = false, example = "32")
    private Integer provinceId;
	@ApiModelProperty(name = "cityId", notes = "城市id", dataType = "int", required = false, example = "3")
    private Integer cityId;
	@ApiModelProperty(name = "areaId", notes = "区域id", dataType = "int", required = false, example = "3")
    private Integer areaId;
	
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	@Override
	public String toString() {
		return "JXCMTBsAddressListQueryVO [merchantCode=" + merchantCode
				+ ", provinceId=" + provinceId + ", cityId=" + cityId
				+ ", areaId=" + areaId + "]";
	}
	
	
	
}
