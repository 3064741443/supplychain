package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderVehicleGetVO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;

	public String getMerchantOrder() {
		return merchantOrder;
	}

	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderVehicleGetVO [merchantOrder="
				+ merchantOrder + "]";
	}
	
}
