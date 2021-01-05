package cn.com.glsx.supplychain.model;

import java.util.List;

import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;
import cn.com.glsx.supplychain.model.am.StatementSellRzfbImport;

public class StatementSellRzfbImportVo {

	private String saleCode;
	private String saleName;
	private String settleCustomerCode;
	private String settleCustomerName;
	private List<StatementSellRzfbImport> listStatementSellRzfbImport;
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
	public List<StatementSellRzfbImport> getListStatementSellRzfbImport() {
		return listStatementSellRzfbImport;
	}
	public void setListStatementSellRzfbImport(
			List<StatementSellRzfbImport> listStatementSellRzfbImport) {
		this.listStatementSellRzfbImport = listStatementSellRzfbImport;
	}
	
	
}
