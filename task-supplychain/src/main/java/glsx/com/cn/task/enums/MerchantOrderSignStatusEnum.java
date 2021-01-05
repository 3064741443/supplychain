package glsx.com.cn.task.enums;

public enum MerchantOrderSignStatusEnum {
	
	ORDER_SIGN_STATUS_UNSIGN((byte)1,"未签收"),
	ORDER_SIGN_STATUS_PTSIGN((byte)2,"部分签收"),
	ORDER_SIGN_STATUS_ALSIGN((byte)3,"全部签收"),
	ORDER_SIGN_STATUS_UNSEND((byte)4,"未发货");
	
	private byte code;
    private String name;
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private MerchantOrderSignStatusEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
    
    
}
