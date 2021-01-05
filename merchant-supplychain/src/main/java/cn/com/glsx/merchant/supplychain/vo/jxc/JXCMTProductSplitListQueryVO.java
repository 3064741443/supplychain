package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTProductSplitListQueryVO implements Serializable{	
	@ApiModelProperty(name = "productTypeId", notes = "产品类型Id", dataType = "int", required = false, example = "1")
	private Integer productTypeId;
	@ApiModelProperty(name = "productCode", notes = "产品编码", dataType = "string", required = false, example = "202006221042050709")
	private String productCode;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "虎哥大屏导航")
	private String productName;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "GLXS41085")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "嘀嘀虎自主大屏导航-虎哥大屏（MTK）10寸  A200")
	private String materialName;
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public String toString() {
		return "JXCMTProductSplitListQueryVO{" +
				"productTypeId=" + productTypeId +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				'}';
	}


}
