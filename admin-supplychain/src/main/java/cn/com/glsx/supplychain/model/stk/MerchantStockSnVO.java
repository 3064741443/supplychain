package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MerchantStockSnVO implements Serializable{
	@ApiModelProperty(name = "deviceType", notes = "设备大类", dataType = "integer", required = true, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "unActiveDayFlag", notes = "TH:大于3个月未激活", dataType = "string", required = true, example = "")
	private String unActiveDayFlag;

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}

	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
	}

	@Override
	public String toString() {
		return "MerchantStockSnVO{" +
				"deviceType=" + deviceType +
				", unActiveDayFlag='" + unActiveDayFlag + '\'' +
				'}';
	}
}
