package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMdbTransferOrderCheckVO implements Serializable{

	@ApiModelProperty(name = "tranOrderCode", notes = "调拨子订单编号", dataType = "string", required = true, example = "")
	private String tranOrderCode;
	@ApiModelProperty(name = "totalCheck", notes = "审核数量", dataType = "int", required = true, example = "")
	private Integer totalCheck;

	public String getTranOrderCode() {
		return tranOrderCode;
	}

	public void setTranOrderCode(String tranOrderCode) {
		this.tranOrderCode = tranOrderCode;
	}

	public Integer getTotalCheck() {
		return totalCheck;
	}

	public void setTotalCheck(Integer totalCheck) {
		this.totalCheck = totalCheck;
	}

	@Override
	public String toString() {
		return "JXCMdbTransferOrderCheckVO{" +
				"tranOrderCode='" + tranOrderCode + '\'' +
				", totalCheck=" + totalCheck +
				'}';
	}
}
