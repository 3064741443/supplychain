package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockSnStatDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "toMerchantCode", notes = "发往商户编码", dataType = "string", required = false, example = "")
    private String toMerchantCode;
	@ApiModelProperty(name = "toMerchantName", notes = "发往商户名称", dataType = "string", required = false, example = "")
    private String toMerchantName;
	@ApiModelProperty(name = "tradeTotal", notes = "数量", dataType = "int", required = false, example = "")
    private Integer tradeTotal;
	@ApiModelProperty(name = "unActiveDays", notes = "未激活天数(查询条件)", dataType = "int", required = false, example = "")
    private Integer unActiveDays;
	@ApiModelProperty(name = "activeOrNot", notes = "激活与否 Y:激活 N:未激活(查询条件)", dataType = "string", required = false, example = "")
    private String activeOrNot;
	@ApiModelProperty(name = "statTime", notes = "截至统计时间", dataType = "string", required = false, example = "")
	private String statTime;
	@ApiModelProperty(name = "unActiveDayFlag", notes = "TH:大于3个月未激活 SI:大于6个月未激活 NI:大于9个月未激活", dataType = "string", required = false, example = "")
    private String unActiveDayFlag;
	
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
	public String getToMerchantCode() {
		return toMerchantCode;
	}
	public void setToMerchantCode(String toMerchantCode) {
		this.toMerchantCode = toMerchantCode;
	}
	public String getToMerchantName() {
		return toMerchantName;
	}
	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
	}
	public Integer getTradeTotal() {
		return tradeTotal;
	}
	public void setTradeTotal(Integer tradeTotal) {
		this.tradeTotal = tradeTotal;
	}
	public Integer getUnActiveDays() {
		return unActiveDays;
	}
	public void setUnActiveDays(Integer unActiveDays) {
		this.unActiveDays = unActiveDays;
	}
	public String getActiveOrNot() {
		return activeOrNot;
	}
	public void setActiveOrNot(String activeOrNot) {
		this.activeOrNot = activeOrNot;
	}
	public String getStatTime() {
		return statTime;
	}
	public void setStatTime(String statTime) {
		this.statTime = statTime;
	}
	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}
	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
	}
	@Override
	public String toString() {
		return "STKMerchantStockSnStatDTO [deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", toMerchantCode="
				+ toMerchantCode + ", toMerchantName=" + toMerchantName
				+ ", tradeTotal=" + tradeTotal + ", unActiveDays="
				+ unActiveDays + ", activeOrNot=" + activeOrNot + ", statTime="
				+ statTime + ", unActiveDayFlag=" + unActiveDayFlag + "]";
	}
	
	
	
	
	
}
