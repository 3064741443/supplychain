package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTProductSplitDetailDTO implements Serializable{
	
	@ApiModelProperty(name = "productCode", notes = "产品编码", dataType = "string", required = true, example = "202007241346310752")
	private String productCode;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = true, example = "GLXS41085")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = true, example = "嘀嘀虎自主大屏导航-虎哥大屏（MTK）10寸  A200")
    private String materialName;
	@ApiModelProperty(name = "propQuantity", notes = "单套数量", dataType = "int", required = true, example = "1")
    private Integer propQuantity;
	@ApiModelProperty(name = "orderTotal", notes = "订购数量", dataType = "int", required = true, example = "10")
    private Integer orderTotal;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Integer getPropQuantity() {
		return propQuantity;
	}
	public void setPropQuantity(Integer propQuantity) {
		this.propQuantity = propQuantity;
	}
	public Integer getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}
	@Override
	public String toString() {
		return "JXCMTProductSplitDetailDTO [productCode=" + productCode
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", propQuantity=" + propQuantity
				+ ", orderTotal=" + orderTotal + "]";
	}
	
}
