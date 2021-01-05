package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StockSnDevTypeStatVO implements Serializable{
	@ApiModelProperty(name = "unActiveDayFlag", notes = "TH:大于3个月未激活 SI:大于6个月未激活 NI:大于9个月未激活", dataType = "string", required = true, example = "")
    private String unActiveDayFlag;

	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}

	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
	}

	@Override
	public String toString() {
		return "StockSnDevTypeStatVO [unActiveDayFlag=" + unActiveDayFlag + "]";
	}
	
	
	
	
}
