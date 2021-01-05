package cn.com.glsx.supplychain.jst.model;

public class GhMerchantOrderReflectDispatch {

	private String ghMerchantOrderCode;
	private String merchantOrderNumber;
	private String logisticsDesc;
	
	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}
	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}
	
	public String getMerchantOrderNumber() {
		return merchantOrderNumber;
	}
	public void setMerchantOrderNumber(String merchantOrderNumber) {
		this.merchantOrderNumber = merchantOrderNumber;
	}
	public String getLogisticsDesc() {
		return logisticsDesc;
	}
	public void setLogisticsDesc(String logisticsDesc) {
		this.logisticsDesc = logisticsDesc;
	}
	@Override
	public String toString() {
		return "GhMerchantOrderReflectDispatch [ghMerchantOrderCode="
				+ ghMerchantOrderCode + ", merchantOrderNumber="
				+ merchantOrderNumber + ", logisticsDesc=" + logisticsDesc
				+ "]";
	}
	
	
	
}
