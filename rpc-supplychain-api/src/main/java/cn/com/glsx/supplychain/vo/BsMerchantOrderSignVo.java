package cn.com.glsx.supplychain.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BsMerchantOrderSignVo implements Serializable{

	private String merchantSignNumber;
	
	//private List<String> merchantOrderNumbers;
	
	private String signUrl;

	public String getMerchantSignNumber() {
		return merchantSignNumber;
	}

	public void setMerchantSignNumber(String merchantSignNumber) {
		this.merchantSignNumber = merchantSignNumber;
	}

	/*public List<String> getMerchantOrderNumbers() {
		return merchantOrderNumbers;
	}

	public void setMerchantOrderNumbers(List<String> merchantOrderNumbers) {
		this.merchantOrderNumbers = merchantOrderNumbers;
	}*/

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	
	
}
