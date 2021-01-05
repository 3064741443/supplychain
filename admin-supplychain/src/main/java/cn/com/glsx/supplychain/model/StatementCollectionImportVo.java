package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.model.am.StatementCollectionImport;

@SuppressWarnings("serial")
public class StatementCollectionImportVo implements Serializable{

	private String saleCode;
	private String saleName;
	private Byte serviceType;
	private List<StatementCollectionImport> listImportStatementCollection;
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
	public Byte getServiceType() {
		return serviceType;
	}
	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}
	public List<StatementCollectionImport> getListImportStatementCollection() {
		return listImportStatementCollection;
	}
	public void setListImportStatementCollection(
			List<StatementCollectionImport> listImportStatementCollection) {
		this.listImportStatementCollection = listImportStatementCollection;
	}
	
	
	
}
