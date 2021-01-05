package cn.com.glsx.supplychain.enums;

public enum FinanceSettleTypeEnum {

	//N:新装结算
	FINANCE_SETTLE_TYPE_N("N","新装结算"),
	FINANCE_SETTLE_TYPE_C("C","续费结算"),
	FINANCE_SETTLE_TYPE_B("B","补充费结算"),
	FINANCE_SETTLE_TYPE_D("D","扣除费结算"),
	FINANCE_SETTLE_TYPE_I("I","安装费结算"),
	FINANCE_SETTLE_TYPE_S("S","拆装费结算"),
	FINANCE_SETTLE_TYPE_H("H","硬件费结算");
	
	public String code;
	public String name;
	private FinanceSettleTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
