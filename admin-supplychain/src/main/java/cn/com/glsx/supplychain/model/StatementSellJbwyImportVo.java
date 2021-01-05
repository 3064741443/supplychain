package cn.com.glsx.supplychain.model;

import java.util.List;

import cn.com.glsx.supplychain.model.am.StatementSellJbwyImport;

public class StatementSellJbwyImportVo {

	private String saleCode;
	private String saleName;
	private String settleCustomerCode;
	private String settleCustomerName;
	private List<StatementSellJbwyImport> listStatementSellJbwyImport;
	public String getSaleCode() {
		return saleCode;
	}
	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSettleCustomerCode() {
		return settleCustomerCode;
	}
	public void setSettleCustomerCode(String settleCustomerCode) {
		this.settleCustomerCode = settleCustomerCode;
	}
	public String getSettleCustomerName() {
		return settleCustomerName;
	}
	public void setSettleCustomerName(String settleCustomerName) {
		this.settleCustomerName = settleCustomerName;
	}
	public List<StatementSellJbwyImport> getListStatementSellJbwyImport() {
		return listStatementSellJbwyImport;
	}
	public void setListStatementSellJbwyImport(
			List<StatementSellJbwyImport> listStatementSellJbwyImport) {
		this.listStatementSellJbwyImport = listStatementSellJbwyImport;
	}
	
	
}
