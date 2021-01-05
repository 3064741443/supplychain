package cn.com.glsx.supplychain.jxc.kafka;

public class OperatorExportKafkaMessage {

	private Integer taskCfgId;
	
	private String userName;
	
	private ExportMerchantOrder merchantOrderExport;

	private ExportBsMdbTransferOrder exportMdbTransferOrder;

	private ExportJxcMdbTransferOrder exportJxcMdbTransferOrder;

	private  ExportSTKMerchantStock exportSTKMerchantStock;

	private ExportMerchantWarningWaresale exportMerchantWarningWaresale;

	private ExportWarningDeviceSn exportWarningDeviceSn;

	private ExportMerchantStockSnStat exportMerchantStockSnStat;

	private ExportMerchantStockSn exportMerchantStockSn;

	private ExportJXCMTOrderInfo exportJXCMTOrderInfo;

	private ExportDeviceFile exportDeviceFile;

	private GenBills genBills;

	public Integer getTaskCfgId() {
		return taskCfgId;
	}

	public void setTaskCfgId(Integer taskCfgId) {
		this.taskCfgId = taskCfgId;
	}

	public ExportDeviceFile getExportDeviceFile() {
		return exportDeviceFile;
	}

	public void setExportDeviceFile(ExportDeviceFile exportDeviceFile) {
		this.exportDeviceFile = exportDeviceFile;
	}

	public ExportJXCMTOrderInfo getExportJXCMTOrderInfo() {
		return exportJXCMTOrderInfo;
	}

	public void setExportJXCMTOrderInfo(ExportJXCMTOrderInfo exportJXCMTOrderInfo) {
		this.exportJXCMTOrderInfo = exportJXCMTOrderInfo;
	}

	public ExportMerchantStockSn getExportMerchantStockSn() {
		return exportMerchantStockSn;
	}

	public void setExportMerchantStockSn(ExportMerchantStockSn exportMerchantStockSn) {
		this.exportMerchantStockSn = exportMerchantStockSn;
	}

	public ExportMerchantStockSnStat getExportMerchantStockSnStat() {
		return exportMerchantStockSnStat;
	}

	public void setExportMerchantStockSnStat(ExportMerchantStockSnStat exportMerchantStockSnStat) {
		this.exportMerchantStockSnStat = exportMerchantStockSnStat;
	}

	public ExportWarningDeviceSn getExportWarningDeviceSn() {
		return exportWarningDeviceSn;
	}

	public void setExportWarningDeviceSn(ExportWarningDeviceSn exportWarningDeviceSn) {
		this.exportWarningDeviceSn = exportWarningDeviceSn;
	}

	public ExportMerchantWarningWaresale getExportMerchantWarningWaresale() {
		return exportMerchantWarningWaresale;
	}

	public void setExportMerchantWarningWaresale(ExportMerchantWarningWaresale exportMerchantWarningWaresale) {
		this.exportMerchantWarningWaresale = exportMerchantWarningWaresale;
	}

	public ExportSTKMerchantStock getExportSTKMerchantStock() {
		return exportSTKMerchantStock;
	}

	public void setExportSTKMerchantStock(ExportSTKMerchantStock exportSTKMerchantStock) {
		this.exportSTKMerchantStock = exportSTKMerchantStock;
	}

	public ExportMerchantOrder getMerchantOrderExport() {
		return merchantOrderExport;
	}

	public void setMerchantOrderExport(ExportMerchantOrder merchantOrderExport) {
		this.merchantOrderExport = merchantOrderExport;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public GenBills getGenBills() {
		return genBills;
	}

	public void setGenBills(GenBills genBills) {
		this.genBills = genBills;
	}

	public ExportBsMdbTransferOrder getExportMdbTransferOrder() {
		return exportMdbTransferOrder;
	}

	public void setExportMdbTransferOrder(ExportBsMdbTransferOrder exportMdbTransferOrder) {
		this.exportMdbTransferOrder = exportMdbTransferOrder;
	}

	public ExportJxcMdbTransferOrder getExportJxcMdbTransferOrder() {
		return exportJxcMdbTransferOrder;
	}

	public void setExportJxcMdbTransferOrder(ExportJxcMdbTransferOrder exportJxcMdbTransferOrder) {
		this.exportJxcMdbTransferOrder = exportJxcMdbTransferOrder;
	}
}