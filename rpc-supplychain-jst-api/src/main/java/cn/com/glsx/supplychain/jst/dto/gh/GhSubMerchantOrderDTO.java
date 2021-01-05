package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;

public class GhSubMerchantOrderDTO implements Serializable{

	private String merchantCode;
	private String merchantName;
	private Integer addressId;
	private String spaPurchaseCode;
	private List<GhShoppingCartDTO> listShoppingCartCode;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getSpaPurchaseCode() {
		return spaPurchaseCode;
	}
	public void setSpaPurchaseCode(String spaPurchaseCode) {
		this.spaPurchaseCode = spaPurchaseCode;
	}
	
	public List<GhShoppingCartDTO> getListShoppingCartCode() {
		return listShoppingCartCode;
	}
	public void setListShoppingCartCode(List<GhShoppingCartDTO> listShoppingCartCode) {
		this.listShoppingCartCode = listShoppingCartCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@Override
	public String toString() {
		return "GhSubMerchantOrderDTO [merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", addressId=" + addressId
				+ ", spaPurchaseCode=" + spaPurchaseCode
				+ ", listShoppingCartCode=" + listShoppingCartCode + "]";
	}
	
	
}
