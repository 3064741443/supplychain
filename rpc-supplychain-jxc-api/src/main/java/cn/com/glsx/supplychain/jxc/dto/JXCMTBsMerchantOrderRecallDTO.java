package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderRecallDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "recallReason", notes = "驳回原因", dataType = "string", required = false, example = "")
	private String recallReason;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getRecallReason() {
		return recallReason;
	}
	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderRecallDTO [merchantOrder=" + merchantOrder
				+ ", recallReason=" + recallReason + "]";
	}
	
	
}
