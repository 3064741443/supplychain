package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMdbTransferOrderRebackDTO extends JXCMTBaseDTO  implements Serializable{

	@ApiModelProperty(name = "tranOrderCode", notes = "调拨子订单编号", dataType = "string", required = true, example = "")
	private String tranOrderCode;
	@ApiModelProperty(name = "rebackReason", notes = "审核驳回原因", dataType = "String", required = true, example = "")
	private String rebackReason;

	public String getTranOrderCode() {
		return tranOrderCode;
	}

	public void setTranOrderCode(String tranOrderCode) {
		this.tranOrderCode = tranOrderCode;
	}

	public String getRebackReason() {
		return rebackReason;
	}

	public void setRebackReason(String rebackReason) {
		this.rebackReason = rebackReason;
	}

	@Override
	public String toString() {
		return "JXCMdbTransferOrderRebackDTO{" +
				"tranOrderCode='" + tranOrderCode + '\'' +
				", rebackReason='" + rebackReason + '\'' +
				'}';
	}
}
