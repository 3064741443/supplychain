package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BatchSubProductConfigCartVO implements Serializable{

	String merchantCode;
	List<ProductShoppingCartVO> listShopCarts;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public List<ProductShoppingCartVO> getListShopCarts() {
		return listShopCarts;
	}
	public void setListShopCarts(List<ProductShoppingCartVO> listShopCarts) {
		this.listShopCarts = listShopCarts;
	}
	@Override
	public String toString() {
		return "BatchSubProductConfigCartVO [merchantCode=" + merchantCode
				+ ", listShopCarts=" + listShopCarts + "]";
	}
	
	
}
