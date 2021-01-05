package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsMerchantMaterialCheckVO implements Serializable{

	@ApiModelProperty(name = "orderMaterialCode", notes = "订购物料编码", dataType = "string", required = true, example = "")
	private String orderMaterialCode;

	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}

	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantMaterialCheckVO [orderMaterialCode="
				+ orderMaterialCode + "]";
	}
	
}
