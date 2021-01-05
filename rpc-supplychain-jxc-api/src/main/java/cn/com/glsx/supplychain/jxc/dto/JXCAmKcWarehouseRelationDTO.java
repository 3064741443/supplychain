package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class JXCAmKcWarehouseRelationDTO implements Serializable{

	@ApiModelProperty(name = "warehouseCode", notes = "K3仓库编码", dataType = "string", required = false, example = "")
    private String warehouseCode;
	@ApiModelProperty(name = "warehouseName", notes = "K3仓库名称", dataType = "string", required = false, example = "")
    private String warehouseName;
	@ApiModelProperty(name = "merchantCode", notes = "服务商户编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
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
		return "JXCAmKcWarehouseRelationDTO [warehouseCode=" + warehouseCode
				+ ", warehouseName=" + warehouseName + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName + "]";
	}
	
}
