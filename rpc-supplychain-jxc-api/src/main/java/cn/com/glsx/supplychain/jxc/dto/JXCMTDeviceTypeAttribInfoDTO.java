package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTDeviceTypeAttribInfoDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类id", dataType = "int", required = true, example = "")
	private Integer deviceTypeId;
	@ApiModelProperty(name = "attribName", notes = "配置名称", dataType = "string", required = true, example = "")
    private String attribName;
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getAttribName() {
		return attribName;
	}
	public void setAttribName(String attribName) {
		this.attribName = attribName;
	}
	@Override
	public String toString() {
		return "JXCMTDeviceTypeAttribInfoDTO [deviceTypeId=" + deviceTypeId
				+ ", attribName=" + attribName + "]";
	}
	
}
