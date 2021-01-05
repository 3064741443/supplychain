package cn.com.glsx.supplychain.enums;

public enum TaskReportExportStatuEnum {

	TASK_EXPORT_STATU_WA("WA"),//流量卡
	TASK_EXPORT_STATU_ST("ST"),//绑定用户
	TASK_EXPORT_STATU_FI("FI");//激活用户
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private TaskReportExportStatuEnum(String value) {
		this.value = value;
	}
	
	
	
	
}
