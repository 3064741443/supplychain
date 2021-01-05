package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockDeductionDetailDTO implements Serializable{

	@ApiModelProperty(name = "tradeDate", notes = "业务日期(年月日 2020-11-04)", dataType = "string", required = false, example = "")
	private String tradeDate;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "warehouseCode", notes = "物料名称", dataType = "string", required = false, example = "")
	private String warehouseCode;
	@ApiModelProperty(name = "tradeTotal", notes = "数量", dataType = "int", required = false, example = "")
    private Integer tradeTotal;
	@ApiModelProperty(name = "remark", notes = "备注", dataType = "string", required = false, example = "")
    private String remark;
	@ApiModelProperty(name = "result", notes = "失败原因", dataType = "string", required = false, example = "")
	private String result;
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
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
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public Integer getTradeTotal() {
		return tradeTotal;
	}
	public void setTradeTotal(Integer tradeTotal) {
		this.tradeTotal = tradeTotal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "STKMerchantStockDeductionDetailDTO [tradeDate=" + tradeDate
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", warehouseCode=" + warehouseCode
				+ ", tradeTotal=" + tradeTotal + ", remark=" + remark
				+ ", result=" + result + "]";
	}
	
	
	
}
