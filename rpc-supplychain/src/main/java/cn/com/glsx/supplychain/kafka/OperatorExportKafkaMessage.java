package cn.com.glsx.supplychain.kafka;

public class OperatorExportKafkaMessage {

	private Integer taskCfgId;
	
	private String userName;
	
	private ExportMerchantOrder merchantOrderExport;

	public Integer getTaskCfgId() {
		return taskCfgId;
	}

	public void setTaskCfgId(Integer taskCfgId) {
		this.taskCfgId = taskCfgId;
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

}
