package cn.com.glsx.supplychain.enums;

public enum SettlementSellWorkType {

	WORK_TYPE_S("S","经销对账"),
	WORK_TYPE_W("W","经销续费-微信对账"),
	WORK_TYPE_Z("Z","经销续费-支付宝对账"),
	WORK_TYPE_G("G","经销广联无忧"),
	WORK_TYPE_J("J","经销驾保无忧");
	
	String code;
	String describle;
	private SettlementSellWorkType(String code, String describle) {
		this.code = code;
		this.describle = describle;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	
	
}
