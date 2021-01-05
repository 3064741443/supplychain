package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKProductConfigDetailDTO implements Serializable{

	@ApiModelProperty(name = "configCode", notes = "配置编码", dataType = "string", required = false, example = "")
	private String configCode;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "total", notes = "数量", dataType = "int", required = false, example = "")
    private Integer total;
	@ApiModelProperty(name = "typeFlag", notes = "SA:销售物料 ST:实发物料", dataType = "int", required = false, example = "")
    private String typeFlag;
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	@Override
	public String toString() {
		return "STKProductConfigDetailDTO [configCode=" + configCode
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", total=" + total
				+ ", typeFlag=" + typeFlag + "]";
	}
	
}
