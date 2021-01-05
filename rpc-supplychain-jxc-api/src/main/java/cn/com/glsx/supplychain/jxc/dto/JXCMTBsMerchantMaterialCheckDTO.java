package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMerchantMaterialCheckDTO implements Serializable{
	@ApiModelProperty(name = "orderMaterialCode", notes = "订购物料编码", dataType = "string", required = false, example = "")
	private String orderMaterialCode;
	@ApiModelProperty(name = "orderMaterialName", notes = "订购物料名称", dataType = "string", required = false, example = "")
    private String orderMaterialName;
	@ApiModelProperty(name = "checkMaterialCode", notes = "审核物料编码", dataType = "string", required = false, example = "")
	private String checkMaterialCode;
	@ApiModelProperty(name = "checkMaterialCode", notes = "审核物料名称", dataType = "string", required = false, example = "")
    private String checkMaterialName;
	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}
	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}
	public String getOrderMaterialName() {
		return orderMaterialName;
	}
	public void setOrderMaterialName(String orderMaterialName) {
		this.orderMaterialName = orderMaterialName;
	}
	public String getCheckMaterialCode() {
		return checkMaterialCode;
	}
	public void setCheckMaterialCode(String checkMaterialCode) {
		this.checkMaterialCode = checkMaterialCode;
	}
	public String getCheckMaterialName() {
		return checkMaterialName;
	}
	public void setCheckMaterialName(String checkMaterialName) {
		this.checkMaterialName = checkMaterialName;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantMaterialCheckDTO [orderMaterialCode="
				+ orderMaterialCode + ", orderMaterialName="
				+ orderMaterialName + ", checkMaterialCode="
				+ checkMaterialCode + ", checkMaterialName="
				+ checkMaterialName + "]";
	}
	
	
}
