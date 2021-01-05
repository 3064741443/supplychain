package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.model.am.StatementFinanceImport;

@SuppressWarnings("serial")
public class StatementFinanceImportVo implements Serializable{

	private String saleCode;
	private String saleName;
	private String customerCode;
	private String customerName;
	private String hardwareCustomerCode;
	private String hardwareCustomerName;
	private String serviceCustomerCode;
	private String serviceCustomerName;
	private List<StatementFinanceImport> listStatementFinanceImport;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getHardwareCustomerCode() {
		return hardwareCustomerCode;
	}
	public void setHardwareCustomerCode(String hardwareCustomerCode) {
		this.hardwareCustomerCode = hardwareCustomerCode;
	}
	public String getHardwareCustomerName() {
		return hardwareCustomerName;
	}
	public void setHardwareCustomerName(String hardwareCustomerName) {
		this.hardwareCustomerName = hardwareCustomerName;
	}
	public String getServiceCustomerCode() {
		return serviceCustomerCode;
	}
	public void setServiceCustomerCode(String serviceCustomerCode) {
		this.serviceCustomerCode = serviceCustomerCode;
	}
	public String getServiceCustomerName() {
		return serviceCustomerName;
	}
	public void setServiceCustomerName(String serviceCustomerName) {
		this.serviceCustomerName = serviceCustomerName;
	}
	public List<StatementFinanceImport> getListStatementFinanceImport() {
		return listStatementFinanceImport;
	}
	public void setListStatementFinanceImport(
			List<StatementFinanceImport> listStatementFinanceImport) {
		this.listStatementFinanceImport = listStatementFinanceImport;
	}
	
	
}
