package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

public class CartConditionVO implements Serializable{

	String merchantCode;
	List<String> cartCodes;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public List<String> getCartCodes() {
		return cartCodes;
	}
	public void setCartCodes(List<String> cartCodes) {
		this.cartCodes = cartCodes;
	}
	@Override
	public String toString() {
		return "CartConditionVO [merchantCode=" + merchantCode + ", cartCodes="
				+ cartCodes + ", getMerchantCode()=" + getMerchantCode()
				+ ", getCartCodes()=" + getCartCodes() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
