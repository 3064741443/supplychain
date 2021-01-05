package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMaterialInfoDTO implements Serializable{
	
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "materialTypeId", notes = "物料类型id", dataType = "int", required = false, example = "")
    private Integer materialTypeId;
	@ApiModelProperty(name = "materialTypeName", notes = "物料类型名称", dataType = "string", required = false, example = "")
    private String materialTypeName;
	@ApiModelProperty(name = "deviceTypeId", notes = "物料设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceTypeId;
	@ApiModelProperty(name = "deviceTypeName", notes = "物料设备类型名称", dataType = "string", required = false, example = "")
    private String deviceTypeName;
	@ApiModelProperty(name = "propQuantity", notes = "单套数量", dataType = "int", required = false, example = "")
    private Integer propQuantity;
	@ApiModelProperty(name = "materialTotal", notes = "订购总数", dataType = "int", required = false, example = "")
    private Integer materialTotal;
	@ApiModelProperty(name = "deviceTypeDispatch", notes = "设备是否支持分发 Y:支持分发 N:不支持分发", dataType = "int", required = false, example = "Y")
	private String deviceTypeDispatch;
	@ApiModelProperty(name = "deviceScanType", notes = "设备是否扫码出库 Y:扫码出库  N:不扫码出库", dataType = "int", required = false, example = "Y")
	private String deviceScanType;
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
	public Integer getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(Integer materialTypeId) {
		this.materialTypeId = materialTypeId;
	}
	public String getMaterialTypeName() {
		return materialTypeName;
	}
	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public Integer getPropQuantity() {
		return propQuantity;
	}
	public void setPropQuantity(Integer propQuantity) {
		this.propQuantity = propQuantity;
	}
	public Integer getMaterialTotal() {
		return materialTotal;
	}
	public void setMaterialTotal(Integer materialTotal) {
		this.materialTotal = materialTotal;
	}
	public String getDeviceTypeDispatch() {
		return deviceTypeDispatch;
	}
	public void setDeviceTypeDispatch(String deviceTypeDispatch) {
		this.deviceTypeDispatch = deviceTypeDispatch;
	}
	public String getDeviceScanType() {
		return deviceScanType;
	}
	public void setDeviceScanType(String deviceScanType) {
		this.deviceScanType = deviceScanType;
	}
	@Override
	public String toString() {
		return "JXCMTBsMaterialInfoDTO [materialCode=" + materialCode
				+ ", materialName=" + materialName + ", materialTypeId="
				+ materialTypeId + ", materialTypeName=" + materialTypeName
				+ ", deviceTypeId=" + deviceTypeId + ", deviceTypeName="
				+ deviceTypeName + ", propQuantity=" + propQuantity
				+ ", materialTotal=" + materialTotal + ", deviceTypeDispatch="
				+ deviceTypeDispatch + ", deviceScanType=" + deviceScanType
				+ "]";
	}
	
	
	
}
