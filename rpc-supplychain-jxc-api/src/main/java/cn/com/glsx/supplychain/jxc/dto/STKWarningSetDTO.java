package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class STKWarningSetDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "warningCode", notes = "预警编码", dataType = "string", required = false, example = "")
	private String warningCode;
	@ApiModelProperty(name = "warningType", notes = "预警类型 KX:库销比预警 DZ:呆滞品预警", dataType = "string", required = false, example = "")
    private String warningType;
	@ApiModelProperty(name = "warningTypeName", notes = "预警类型 名称", dataType = "string", required = false, example = "")
    private String warningTypeName;
	@ApiModelProperty(name = "channelType", notes = "渠道商户 CH:按渠道 ME:按服务商", dataType = "string", required = false, example = "")
    private String channelType;
	@ApiModelProperty(name = "channelTypeName", notes = "渠道商户名称", dataType = "string", required = false, example = "")
    private String channelTypeName;
	@ApiModelProperty(name = "merchantChannelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private String merchantChannelId;
	@ApiModelProperty(name = "merchantChannelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
    private String merchantChannelName;
	@ApiModelProperty(name = "merchantCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "thresholdLow", notes = "低阀值", dataType = "double", required = false, example = "")
    private Double thresholdLow;
	@ApiModelProperty(name = "thresholdHigh", notes = "高阀值", dataType = "double", required = false, example = "")
    private Double thresholdHigh;
	@ApiModelProperty(name = "deletedFlag", notes = "删除标记", dataType = "double", required = false, example = "")
	private String deletedFlag;
	public String getWarningCode() {
		return warningCode;
	}
	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}
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
	public String getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
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
	public String getWarningTypeName() {
		return warningTypeName;
	}
	public void setWarningTypeName(String warningTypeName) {
		this.warningTypeName = warningTypeName;
	}
	public String getChannelTypeName() {
		return channelTypeName;
	}
	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	@Override
	public String toString() {
		return "STKWarningSetDTO [warningCode=" + warningCode
				+ ", warningType=" + warningType + ", channelType="
				+ channelType + ", merchantChannelId=" + merchantChannelId
				+ ", merchantChannelName=" + merchantChannelName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", thresholdLow="
				+ thresholdLow + ", thresholdHigh=" + thresholdHigh
				+ ", deletedFlag=" + deletedFlag + "]";
	}
	
	
	
	
    
}
