package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WarningDevicesnVO implements Serializable{

	@ApiModelProperty(name = "toMerchantName", notes = "发往商户名称/编码", dataType = "string", required = false, example = "")
    private String toMerchantName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "materialName", notes = "物料名称/编码", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "warningCode", notes = "预警编码", dataType = "string", required = false, example = "")
	private String warningCode;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public String getToMerchantName() {
		return toMerchantName;
	}
	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getWarningCode() {
		return warningCode;
	}
	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "WarningDevicesnVO [toMerchantName=" + toMerchantName
				+ ", deviceType=" + deviceType + ", materialName="
				+ materialName + ", warningCode=" + warningCode + ", pageNum="
				+ pageNum + ", pageSize=" + pageSize + "]";
	}
	
	
}
