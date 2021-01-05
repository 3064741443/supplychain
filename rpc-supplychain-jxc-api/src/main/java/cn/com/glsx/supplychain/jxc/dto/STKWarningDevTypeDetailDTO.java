package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKWarningDevTypeDetailDTO implements Serializable{

	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "waringCount", notes = "预警数量", dataType = "int", required = false, example = "")
	private Integer waringCount;
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public Integer getWaringCount() {
		return waringCount;
	}
	public void setWaringCount(Integer waringCount) {
		this.waringCount = waringCount;
	}
	@Override
	public String toString() {
		return "STKWarningDevTypeDetailDTO [deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", waringCount="
				+ waringCount + "]";
	}
	
	
}
