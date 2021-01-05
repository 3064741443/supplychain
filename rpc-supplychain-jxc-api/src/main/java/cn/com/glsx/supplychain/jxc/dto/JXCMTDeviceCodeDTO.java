package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTDeviceCodeDTO implements Serializable{

	@ApiModelProperty(name = "deviceCode", notes = "设备小类编码", dataType = "int", required = false, example = "")
	private Integer deviceCode;
	@ApiModelProperty(name = "deviceName", notes = "设备小类名称", dataType = "int", required = false, example = "")
    private String deviceName;
	@ApiModelProperty(name = "deviceName", notes = "设备大类id", dataType = "int", required = false, example = "")
	private Integer typeId;
	
	public Integer getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	@Override
	public String toString() {
		return "JXCMTDeviceCodeDTO [deviceCode=" + deviceCode + ", deviceName="
				+ deviceName + ", typeId=" + typeId + "]";
	}
	

}
