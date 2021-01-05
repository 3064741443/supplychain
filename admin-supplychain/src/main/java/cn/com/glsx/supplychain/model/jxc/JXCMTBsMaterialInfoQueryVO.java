package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsMaterialInfoQueryVO implements Serializable{

	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类id", dataType = "string", required = false, example = "")
    private Integer deviceTypeId;
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	@Override
	public String toString() {
		return "JXCMTBsMaterialInfoQueryVO [materialName=" + materialName
				+ ", deviceTypeId=" + deviceTypeId + "]";
	}
	
	
	
}
