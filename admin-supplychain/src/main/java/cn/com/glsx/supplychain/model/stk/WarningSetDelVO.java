package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WarningSetDelVO implements Serializable{
	@ApiModelProperty(name = "warningCode", notes = "预警编码", dataType = "string", required = true, example = "")
	private String warningCode;

	public String getWarningCode() {
		return warningCode;
	}

	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}

	@Override
	public String toString() {
		return "WarningSetDelVO [warningCode=" + warningCode + "]";
	}
	
}
