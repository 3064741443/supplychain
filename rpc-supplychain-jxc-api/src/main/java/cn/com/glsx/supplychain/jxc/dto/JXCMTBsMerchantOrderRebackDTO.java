package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderRebackDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "rebackReason", notes = "驳回原因", dataType = "string", required = false, example = "")
	private String rebackReason;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getRebackReason() {
		return rebackReason;
	}
	public void setRebackReason(String rebackReason) {
		this.rebackReason = rebackReason;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderRebackDTO [merchantOrder=" + merchantOrder
				+ ", rebackReason=" + rebackReason + "]";
	}
	
	
}
