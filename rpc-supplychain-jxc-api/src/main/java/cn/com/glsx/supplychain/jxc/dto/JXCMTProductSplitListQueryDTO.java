package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTProductSplitListQueryDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "productCode", notes = "产品编码", dataType = "string", required = true, example = "202007241346310752")
	private String productCode;
	@ApiModelProperty(name = "productTypeId", notes = "产品类型Id", dataType = "int", required = false, example = "1")
	private Integer productTypeId;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "虎哥大屏导航")
	private String productName;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "GLXS41085")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "嘀嘀虎自主大屏导航-虎哥大屏（MTK）10寸  A200")
	private String materialName;
	@ApiModelProperty(name = "merchantCode", notes = "产品所属商户编码", dataType = "string", required = true, example = "44193004")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "产品所属商户名称", dataType = "string", required = true, example = "呼和浩特市弘路贸易有限公司")
    private String merchantName;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
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
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@Override
	public String toString() {
		return "JXCMTProductSplitListQueryDTO [productCode=" + productCode
				+ ", productTypeId=" + productTypeId + ", productName="
				+ productName + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName + "]";
	}
	
	
}
