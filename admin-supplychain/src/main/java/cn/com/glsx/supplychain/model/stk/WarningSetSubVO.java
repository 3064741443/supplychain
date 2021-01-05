package cn.com.glsx.supplychain.model.stk;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class WarningSetSubVO implements Serializable{
	@ApiModelProperty(name = "warningType", notes = "预警类型 KX:库销比预警 DZ:呆滞品预警", dataType = "string", required = true, example = "")
    private String warningType;
	@ApiModelProperty(name = "channelType", notes = "渠道商户 CH:按渠道 ME:按服务商", dataType = "string", required = true, example = "")
    private String channelType;
	@ApiModelProperty(name = "merchantChannelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private String merchantChannelId;
	@ApiModelProperty(name = "merchantChannelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
    private String merchantChannelName;
	@ApiModelProperty(name = "merchantCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = true, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "thresholdLow", notes = "低阀值", dataType = "double", required = false, example = "")
    private Double thresholdLow;
	@ApiModelProperty(name = "thresholdHigh", notes = "高阀值", dataType = "double", required = false, example = "")
    private Double thresholdHigh;
	public String getWarningType() {
		return warningType;
	}
	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getMerchantChannelId() {
		return merchantChannelId;
	}
	public void setMerchantChannelId(String merchantChannelId) {
		this.merchantChannelId = merchantChannelId;
	}
	public String getMerchantChannelName() {
		return merchantChannelName;
	}
	public void setMerchantChannelName(String merchantChannelName) {
		this.merchantChannelName = merchantChannelName;
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
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
	@Override
	public String toString() {
		return "WarningSetSubVO [warningType=" + warningType + ", channelType="
				+ channelType + ", merchantChannelId=" + merchantChannelId
				+ ", merchantChannelName=" + merchantChannelName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", deviceType=" + deviceType
				+ ", thresholdLow=" + thresholdLow + ", thresholdHigh="
				+ thresholdHigh + "]";
	}
	

}
