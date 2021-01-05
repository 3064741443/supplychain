package cn.com.glsx.supplychain.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.model.DeviceImeiStokeListImport;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.DeviceInfoGpsPreimport;
import cn.com.glsx.supplychain.model.DeviceListExport;
import cn.com.glsx.supplychain.model.DeviceListImport;
import cn.com.glsx.supplychain.model.am.StatementCollectionExport;
import cn.com.glsx.supplychain.model.am.StatementCollectionImport;
import cn.com.glsx.supplychain.model.am.StatementFinanceExport;
import cn.com.glsx.supplychain.model.am.StatementFinanceImport;
import cn.com.glsx.supplychain.model.am.StatementSellGlwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellJbwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;
import cn.com.glsx.supplychain.model.am.StatementSellRzfbImport;
import cn.com.glsx.supplychain.model.bs.*;

@SuppressWarnings("serial")
public class CheckImportDataVo implements Serializable{

	List<DeviceInfoGpsPreimport> gpsPreImportSuccessList;
	List<DeviceInfoGpsPreimport> gpsPreImportFailList;
	List<DeviceListImport> importList;
	List<DeviceListExport> invalidList;
	List<DeviceImeiStokeListImport> imeiStokeList;
	List<SnImport>snImportList;
	List<SnExport>sninvalidList;
	List<MerchantOrderImport>merchantOrderImportList;
	List<MerchantOrderExport>merchantOrderExportlist;
	List<DeviceInfo> deviceInfoSucessList;
	List<DeviceInfo> deviceInfoFailList;
	List<SnChangeImport>snChangeImports;
	List<SnChangeExport>snChangeExportList;

	List<StatementFinanceImport> statementFinanceSuccessList;
	List<StatementFinanceImport> statementFinanceFailedList;

	List<StatementCollectionImport> statementCollectionSuccessList;
	List<StatementCollectionImport> statementCollectionFailedList;
	
	List<StatementSellRenewImport> statementSellRenewSuccessList;
	List<StatementSellRenewImport> statementSellRenewFailedList;
	
	List<StatementSellGlwyImport> statementSellGlwySuccessList;
	List<StatementSellGlwyImport> statementSellGlwyFailedList;
	
	List<StatementSellJbwyImport> statementSellJbwySuccessList;
	List<StatementSellJbwyImport> statementSellJbwyFailedList;
	
	List<StatementSellRzfbImport> statementSellRzfbSuccessList;
	List<StatementSellRzfbImport> statementSellRzfbFailedList;

	public List<DeviceListImport> getImportList() {
		return importList;
	}
	public void setImportList(List<DeviceListImport> importList) {
		this.importList = importList;
	}
	public List<DeviceListExport> getInvalidList() {
		return invalidList;
	}
	public void setInvalidList(List<DeviceListExport> invalidList) {
		this.invalidList = invalidList;
	}
	public List<DeviceImeiStokeListImport> getImeiStokeList() {
		return imeiStokeList;
	}
	public void setImeiStokeList(List<DeviceImeiStokeListImport> imeiStokeList) {
		this.imeiStokeList = imeiStokeList;
	}
	public List<SnImport> getSnImportList() {
		return snImportList;
	}
	public void setSnImportList(List<SnImport> snImportList) {
		this.snImportList = snImportList;
	}

	public List<SnExport> getSninvalidList() {
		return sninvalidList;
	}

	public void setSninvalidList(List<SnExport> sninvalidList) {
		this.sninvalidList = sninvalidList;
	}

	public List<MerchantOrderImport> getMerchantOrderImportList() {
		return merchantOrderImportList;
	}

	public void setMerchantOrderImportList(List<MerchantOrderImport> merchantOrderImportList) {
		this.merchantOrderImportList = merchantOrderImportList;
	}

	public List<MerchantOrderExport> getMerchantOrderExportlist() {
		return merchantOrderExportlist;
	}

	public void setMerchantOrderExportlist(List<MerchantOrderExport> merchantOrderExportlist) {
		this.merchantOrderExportlist = merchantOrderExportlist;
	}
	public List<DeviceInfo> getDeviceInfoSucessList() {
		return deviceInfoSucessList;
	}
	public void setDeviceInfoSucessList(List<DeviceInfo> deviceInfoSucessList) {
		this.deviceInfoSucessList = deviceInfoSucessList;
	}
	public List<DeviceInfo> getDeviceInfoFailList() {
		return deviceInfoFailList;
	}
	public void setDeviceInfoFailList(List<DeviceInfo> deviceInfoFailList) {
		this.deviceInfoFailList = deviceInfoFailList;
	}

	public List<SnChangeImport> getSnChangeImports() {
		return snChangeImports;
	}

	public void setSnChangeImports(List<SnChangeImport> snChangeImports) {
		this.snChangeImports = snChangeImports;
	}

	public List<SnChangeExport> getSnChangeExportList() {
		return snChangeExportList;
	}

	public void setSnChangeExportList(List<SnChangeExport> snChangeExportList) {
		this.snChangeExportList = snChangeExportList;
	}

	public List<StatementFinanceImport> getStatementFinanceSuccessList() {
		return statementFinanceSuccessList;
	}
	public void setStatementFinanceSuccessList(
			List<StatementFinanceImport> statementFinanceSuccessList) {
		this.statementFinanceSuccessList = statementFinanceSuccessList;
	}
	public List<StatementFinanceImport> getStatementFinanceFailedList() {
		return statementFinanceFailedList;
	}
	public void setStatementFinanceFailedList(
			List<StatementFinanceImport> statementFinanceFailedList) {
		this.statementFinanceFailedList = statementFinanceFailedList;
	}
	public List<DeviceInfoGpsPreimport> getGpsPreImportSuccessList() {
		return gpsPreImportSuccessList;
	}
	public void setGpsPreImportSuccessList(
			List<DeviceInfoGpsPreimport> gpsPreImportSuccessList) {
		this.gpsPreImportSuccessList = gpsPreImportSuccessList;
	}
	public List<DeviceInfoGpsPreimport> getGpsPreImportFailList() {
		return gpsPreImportFailList;
	}
	public void setGpsPreImportFailList(
			List<DeviceInfoGpsPreimport> gpsPreImportFailList) {
		this.gpsPreImportFailList = gpsPreImportFailList;
	}
	public List<StatementCollectionImport> getStatementCollectionSuccessList() {
		return statementCollectionSuccessList;
	}
	public void setStatementCollectionSuccessList(
			List<StatementCollectionImport> statementCollectionSuccessList) {
		this.statementCollectionSuccessList = statementCollectionSuccessList;
	}
	public List<StatementCollectionImport> getStatementCollectionFailedList() {
		return statementCollectionFailedList;
	}
	public void setStatementCollectionFailedList(
			List<StatementCollectionImport> statementCollectionFailedList) {
		this.statementCollectionFailedList = statementCollectionFailedList;
	}
	public List<StatementSellRenewImport> getStatementSellRenewSuccessList() {
		return statementSellRenewSuccessList;
	}
	public void setStatementSellRenewSuccessList(
			List<StatementSellRenewImport> statementSellRenewSuccessList) {
		this.statementSellRenewSuccessList = statementSellRenewSuccessList;
	}
	public List<StatementSellRenewImport> getStatementSellRenewFailedList() {
		return statementSellRenewFailedList;
	}
	public void setStatementSellRenewFailedList(
			List<StatementSellRenewImport> statementSellRenewFailedList) {
		this.statementSellRenewFailedList = statementSellRenewFailedList;
	}
	public List<StatementSellGlwyImport> getStatementSellGlwySuccessList() {
		return statementSellGlwySuccessList;
	}
	public void setStatementSellGlwySuccessList(
			List<StatementSellGlwyImport> statementSellGlwySuccessList) {
		this.statementSellGlwySuccessList = statementSellGlwySuccessList;
	}
	public List<StatementSellGlwyImport> getStatementSellGlwyFailedList() {
		return statementSellGlwyFailedList;
	}
	public void setStatementSellGlwyFailedList(
			List<StatementSellGlwyImport> statementSellGlwyFailedList) {
		this.statementSellGlwyFailedList = statementSellGlwyFailedList;
	}
	public List<StatementSellJbwyImport> getStatementSellJbwySuccessList() {
		return statementSellJbwySuccessList;
	}
	public void setStatementSellJbwySuccessList(
			List<StatementSellJbwyImport> statementSellJbwySuccessList) {
		this.statementSellJbwySuccessList = statementSellJbwySuccessList;
	}
	public List<StatementSellJbwyImport> getStatementSellJbwyFailedList() {
		return statementSellJbwyFailedList;
	}
	public void setStatementSellJbwyFailedList(
			List<StatementSellJbwyImport> statementSellJbwyFailedList) {
		this.statementSellJbwyFailedList = statementSellJbwyFailedList;
	}
	public List<StatementSellRzfbImport> getStatementSellRzfbSuccessList() {
		return statementSellRzfbSuccessList;
	}
	public void setStatementSellRzfbSuccessList(
			List<StatementSellRzfbImport> statementSellRzfbSuccessList) {
		this.statementSellRzfbSuccessList = statementSellRzfbSuccessList;
	}
	public List<StatementSellRzfbImport> getStatementSellRzfbFailedList() {
		return statementSellRzfbFailedList;
	}
	public void setStatementSellRzfbFailedList(
			List<StatementSellRzfbImport> statementSellRzfbFailedList) {
		this.statementSellRzfbFailedList = statementSellRzfbFailedList;
	}
	
	
};
