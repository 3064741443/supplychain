package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderFinishDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "finishReason", notes = "提前结束原因", dataType = "string", required = false, example = "")
	private String finishReason;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getFinishReason() {
		return finishReason;
	}
	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderFinishDTO [merchantOrder=" + merchantOrder
				+ ", finishReason=" + finishReason + "]";
	}
	
	
}
