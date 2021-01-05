package cn.com.glsx.supplychain.model;

import java.util.List;

import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;

public class StatementSellRenewImportVo {

	private String saleCode;
	private String saleName;
	private String settleCustomerCode;
	private String settleCustomerName;
	private List<StatementSellRenewImport> listStatementSellRenewImport;
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
	public List<StatementSellRenewImport> getListStatementSellRenewImport() {
		return listStatementSellRenewImport;
	}
	public void setListStatementSellRenewImport(
			List<StatementSellRenewImport> listStatementSellRenewImport) {
		this.listStatementSellRenewImport = listStatementSellRenewImport;
	}
	
	
	
}
