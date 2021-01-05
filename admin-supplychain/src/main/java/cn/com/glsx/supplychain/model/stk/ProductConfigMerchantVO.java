package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductConfigMerchantVO implements Serializable{
	@ApiModelProperty(name = "configCode", notes = "配置编码", dataType = "string", required = false, example = "")
    private String configCode;
	@ApiModelProperty(name = "merchantCode", notes = "商户编码", dataType = "string", required = true, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "商户名称", dataType = "string", required = true, example = "")
    private String merchantName;
	@ApiModelProperty(name = "warehouseCode", notes = "k3仓库编码", dataType = "string", required = true, example = "")
    private String warehouseCode;
	@ApiModelProperty(name = "warehouseName", notes = "k3仓库名称", dataType = "string", required = true, example = "")
    private String warehouseName;
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
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	@Override
	public String toString() {
		return "ProductConfigMerchantVO [configCode=" + configCode
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", warehouseCode=" + warehouseCode
				+ ", warehouseName=" + warehouseName + "]";
	}
	
	
}
