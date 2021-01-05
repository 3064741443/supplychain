package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderWarehouseDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = true, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "warehouseId", notes = "仓库id", dataType = "int", required = true, example = "")
	private Integer warehouseId;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	@Override
	public String toString() {
		return "JXCMTMerchantOrderWarehouseDTO [merchantOrder=" + merchantOrder
				+ ", warehouseId=" + warehouseId + "]";
	}
	
}
