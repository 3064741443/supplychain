package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKWarningWaresaleDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "merchantCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "warningMonth", notes = "预警月份（年月 2020-11）", dataType = "string", required = false, example = "")
    private String warningMonth;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "stockSaleRatio", notes = "结存库销比", dataType = "int", required = false, example = "")
    private Double waresaleRate;
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
	public String getWarningMonth() {
		return warningMonth;
	}
	public void setWarningMonth(String warningMonth) {
		this.warningMonth = warningMonth;
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
	public Double getWaresaleRate() {
		return waresaleRate;
	}
	public void setWaresaleRate(Double waresaleRate) {
		this.waresaleRate = waresaleRate;
	}
	@Override
	public String toString() {
		return "STKMerchantWarningWaresaleDTO [channelId=" + channelId
				+ ", channelName=" + channelName + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", warningMonth=" + warningMonth + ", deviceType="
				+ deviceType + ", deviceTypeName=" + deviceTypeName
				+ ", waresaleRate=" + waresaleRate + "]";
	}
	

}
