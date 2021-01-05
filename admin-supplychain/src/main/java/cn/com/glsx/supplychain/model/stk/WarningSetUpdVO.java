package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WarningSetUpdVO implements Serializable{
	@ApiModelProperty(name = "warningCode", notes = "预警编码", dataType = "string", required = true, example = "")
	private String warningCode;
	@ApiModelProperty(name = "thresholdLow", notes = "低阀值", dataType = "double", required = false, example = "")
    private Double thresholdLow;
	@ApiModelProperty(name = "thresholdLow", notes = "高阀值", dataType = "double", required = false, example = "")
    private Double thresholdHigh;
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
	@Override
	public String toString() {
		return "WarningSetUpdVO [warningCode=" + warningCode
				+ ", thresholdLow=" + thresholdLow + ", thresholdHigh="
				+ thresholdHigh + "]";
	}
}
