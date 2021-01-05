package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockSnDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "sn", notes = "设备sn", dataType = "string", required = false, example = "")
    private String sn;
	@ApiModelProperty(name = "toMerchantCode", notes = "发往商户编码", dataType = "string", required = false, example = "")
    private String toMerchantCode;
	@ApiModelProperty(name = "toMerchantName", notes = "发往商户名称", dataType = "string", required = false, example = "")
    private String toMerchantName;
	@ApiModelProperty(name = "curMerchantCode", notes = "当前所在商户编码", dataType = "string", required = false, example = "")
    private String curMerchantCode;
	@ApiModelProperty(name = "curMerchantCode", notes = "当前所在商户名称", dataType = "string", required = false, example = "")
    private String curMerchantName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "activeTime", notes = "激活时间", dataType = "string", required = false, example = "")
    private String activeTime;
    @ApiModelProperty(name = "materialName", notes = "发货时间", dataType = "string", required = false, example = "")
    private String sendTime;
    @ApiModelProperty(name = "materialName", notes = "滞留仓位入库时间", dataType = "string", required = false, example = "")
    private String transferTime;
    @ApiModelProperty(name = "unActiveDays", notes = "总呆滞时长（天）", dataType = "int", required = false, example = "")
    private Integer unActiveDays;
    @ApiModelProperty(name = "stayCurMerchantDays", notes = "仓位滞留时长", dataType = "int", required = false, example = "")
    private Integer stayCurMerchantDays;
    @ApiModelProperty(name = "activeOrNot", notes = "激活与否 Y:激活 N:未激活(查询条件)", dataType = "string", required = false, example = "")
    private String activeOrNot;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public String getCurMerchantCode() {
		return curMerchantCode;
	}
	public void setCurMerchantCode(String curMerchantCode) {
		this.curMerchantCode = curMerchantCode;
	}
	public String getCurMerchantName() {
		return curMerchantName;
	}
	public void setCurMerchantName(String curMerchantName) {
		this.curMerchantName = curMerchantName;
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
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public Integer getUnActiveDays() {
		return unActiveDays;
	}
	public void setUnActiveDays(Integer unActiveDays) {
		this.unActiveDays = unActiveDays;
	}
	public Integer getStayCurMerchantDays() {
		return stayCurMerchantDays;
	}
	public void setStayCurMerchantDays(Integer stayCurMerchantDays) {
		this.stayCurMerchantDays = stayCurMerchantDays;
	}
	public String getActiveOrNot() {
		return activeOrNot;
	}
	public void setActiveOrNot(String activeOrNot) {
		this.activeOrNot = activeOrNot;
	}
	@Override
	public String toString() {
		return "STKMerchantStockSnDTO [sn=" + sn + ", toMerchantCode="
				+ toMerchantCode + ", toMerchantName=" + toMerchantName
				+ ", curMerchantCode=" + curMerchantCode + ", curMerchantName="
				+ curMerchantName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", activeTime=" + activeTime + ", sendTime=" + sendTime
				+ ", transferTime=" + transferTime + ", unActiveDays="
				+ unActiveDays + ", stayCurMerchantDays=" + stayCurMerchantDays
				+ ", activeOrNot=" + activeOrNot + "]";
	}
	
    
}
