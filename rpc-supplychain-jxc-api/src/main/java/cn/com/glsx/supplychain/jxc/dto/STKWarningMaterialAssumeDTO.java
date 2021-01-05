package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class STKWarningMaterialAssumeDTO implements Serializable{
	@ApiModelProperty(name = "warningCode", notes = "预警编码", dataType = "string", required = false, example = "")
	private String warningCode;
	@ApiModelProperty(name = "thresholdLow", notes = "低阀值", dataType = "double", required = false, example = "")
    private Double thresholdLow;
	@ApiModelProperty(name = "thresholdHigh", notes = "高阀值", dataType = "double", required = false, example = "")
    private Double thresholdHigh;
	@ApiModelProperty(name = "merchantCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "waringTotal", notes = "预警数量", dataType = "int", required = false, example = "")
    private Integer waringTotal;
	public String getWarningCode() {
		return warningCode;
	}
	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}
	public Double getThresholdLow() {
		return thresholdLow;
	}
	public void setThresholdLow(Double thresholdLow) {
		this.thresholdLow = thresholdLow;
	}
	public Double getThresholdHigh() {
		return thresholdHigh;
	}
	public void setThresholdHigh(Double thresholdHigh) {
		this.thresholdHigh = thresholdHigh;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	public Integer getWaringTotal() {
		return waringTotal;
	}
	public void setWaringTotal(Integer waringTotal) {
		this.waringTotal = waringTotal;
	}
	@Override
	public String toString() {
		return "STKWarningMaterialAssumeDTO [warningCode=" + warningCode
				+ ", thresholdLow=" + thresholdLow + ", thresholdHigh="
				+ thresholdHigh + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", deviceType=" + deviceType + ", deviceTypeName="
				+ deviceTypeName + ", waringTotal=" + waringTotal + "]";
	}
	
	
}
