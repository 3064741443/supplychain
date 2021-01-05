package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMdbTransferOrderRebackVO implements Serializable{

	@ApiModelProperty(name = "tranOrderCode", notes = "调拨子订单编号", dataType = "string", required = true, example = "")
	private String tranOrderCode;
	@ApiModelProperty(name = "backRemark", notes = "驳回原因", dataType = "string", required = true, example = "")
	private String  backRemark;

	public String getTranOrderCode() {
		return tranOrderCode;
	}

	public void setTranOrderCode(String tranOrderCode) {
		this.tranOrderCode = tranOrderCode;
	}

	public String getBackRemark() {
		return backRemark;
	}

	public void setBackRemark(String backRemark) {
		this.backRemark = backRemark;
	}

	@Override
	public String toString() {
		return "JXCMdbTransferOrderRebackVO{" +
				"tranOrderCode='" + tranOrderCode + '\'' +
				", backRemark=" + backRemark +
				'}';
	}
}
