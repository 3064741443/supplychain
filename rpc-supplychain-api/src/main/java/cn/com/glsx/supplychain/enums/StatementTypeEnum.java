package cn.com.glsx.supplychain.enums;

public enum StatementTypeEnum {

	STATEMENT_TYPE_SELL("SELL","经销结算"),
	STATEMENT_TYPE_COLL("COLL","广汇代销"),
	STATEMENT_TYPE_FINA("FINA","金融风控代销");
	
	String code;
	String name;
	private StatementTypeEnum(String code, String name) {
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
