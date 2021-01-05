package cn.com.glsx.supplychain.model.stk;

import java.io.Serializable;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class MerchantStockDeductDetailVO implements Serializable{

	private String tradeDate;
	private String materialCode;
    private String materialName;
	private String warehouseCode;	
    private Integer tradeTotal;
    private String remark;
    private String result;
    
    @ExcelResources(title = "业务日期",order = 0)
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	@ExcelResources(title = "物料编码",order = 1)
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@ExcelResources(title = "物料名称",order = 2)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelResources(title = "仓库编码",order = 3)
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	@ExcelResources(title = "数量",order = 4)
	public Integer getTradeTotal() {
		return tradeTotal;
	}
	public void setTradeTotal(Integer tradeTotal) {
		this.tradeTotal = tradeTotal;
	}
	@ExcelResources(title = "备注",order = 5)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ExcelResources(title = "失败原因",order = 6)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "MerchantStockDeductDetailVO [tradeDate=" + tradeDate
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", warehouseCode=" + warehouseCode
				+ ", tradeTotal=" + tradeTotal + ", remark=" + remark
				+ ", result=" + result + "]";
	}
	
      
}
