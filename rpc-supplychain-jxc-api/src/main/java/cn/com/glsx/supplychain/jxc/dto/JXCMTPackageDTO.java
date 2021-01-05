package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTPackageDTO implements Serializable{

	@ApiModelProperty(name = "packageCode", notes = "商品套餐(激活)Code", dataType = "string", required = false, example = "")
    private String packageCode;
	@ApiModelProperty(name = "packageName", notes = "商品套餐(激活)名称", dataType = "string", required = false, example = "")
    private String packageName;
	@ApiModelProperty(name = "deviceCode", notes = "设备小类编码", dataType = "int", required = true, example = "")
	private Integer deviceCode;
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}
	@Override
	public String toString() {
		return "JXCMTPackageDTO [packageCode=" + packageCode + ", packageName="
				+ packageName + ", deviceCode=" + deviceCode + "]";
	}
	
	
}
