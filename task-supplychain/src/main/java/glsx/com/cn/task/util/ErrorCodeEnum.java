package glsx.com.cn.task.util;

public enum ErrorCodeEnum {
	
	E_OK("0","正确"),
	ERRCODE_FAILED_REQUEST_FLOWCARD("11100", "流量卡平台接口报错"),
	ERRCODE_ABNORMAL_REPONSE_FLOWCARD("11101","流量卡平台接口返回异常数据"),
	ERRCODE_FAILED_CARD_INSERT("11102","卡管理插入数据失败"),
	ERRCODE_CARD_BAND_DEVICE("11103","卡已被绑定在设备上"),
	ERRCODE_FAILED_DEVICEFILE_INSERT("11104","设备插入失败"),
	ERRCODE_DEVICE_ALREADY_EXISTS("11105","设备已经在库中"),
	ERRCODE_IMEI_STOCK_INSERT("11106","IMEI库存插入失败"),
	ERRCODE_DEVICE_STOCK_FAILED_REQUEST_FLOWCARD("11107","设备库存同步失败流量卡平台接口报错");
	
	private String describle;
	private String code;
	
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	ErrorCodeEnum(String code, String describle){
		
		this.describle	= describle;
		this.code		= code;	
	}
	
	
}
