package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsCityVO implements Serializable{

	@ApiModelProperty(name = "cityId", notes = "城市id", dataType = "int", required = true, example = "3")
    private Integer cityId;
	@ApiModelProperty(name = "cityName", notes = "城市名称", dataType = "string", required = true, example = "合肥市")
    private String cityName;
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
	@Override
	public String toString() {
		return "JXCMTBsCityVO [cityId=" + cityId + ", cityName=" + cityName
				+ "]";
	}
	
	
}
