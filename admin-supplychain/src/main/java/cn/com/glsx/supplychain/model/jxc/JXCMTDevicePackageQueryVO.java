package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTDevicePackageQueryVO implements Serializable{

	@ApiModelProperty(name = "deviceCode", notes = "设备小类编码", dataType = "int", required = true, example = "")
	private Integer deviceCode;

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	@Override
	public String toString() {
		return "JXCMTDevicePackageQueryVO [deviceCode=" + deviceCode + "]";
	}
	
}
