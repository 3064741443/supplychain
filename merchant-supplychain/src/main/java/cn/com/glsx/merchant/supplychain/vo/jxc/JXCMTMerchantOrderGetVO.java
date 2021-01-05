package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderGetVO implements Serializable{

	@ApiModelProperty(name = "moOrderCode", notes = "订单编号", dataType = "string", required = true, example = "202007241346310752")
	private String moOrderCode;

	public String getMoOrderCode() {
		return moOrderCode;
	}

	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}

	@Override
	public String toString() {
		return "JXCMTMerchantOrderGetVO [moOrderCode=" + moOrderCode + "]";
	}
	
	
}
