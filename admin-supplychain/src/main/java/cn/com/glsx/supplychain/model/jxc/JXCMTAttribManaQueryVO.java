package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTAttribManaQueryVO implements Serializable{

	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类id", dataType = "int", required = true, example = "")
	private Integer deviceTypeId;
	@ApiModelProperty(name = "attribCode", notes = "硬件配置编码等同物料编码", dataType = "string", required = false, example = "")
	private String attribCode;
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	@Override
	public String toString() {
		return "JXCMTAttribManaQueryVO [deviceTypeId=" + deviceTypeId
				+ ", attribCode=" + attribCode + "]";
	}
	
	
}
