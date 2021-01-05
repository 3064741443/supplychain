package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubMerchantOrderDTO extends BaseDTO implements Serializable {

	private String merchantCode;
	// 地址
	private BsAddressDTO bsAddressDto;

	// 下单物品
	private List<BsMerchantShoppingCartDTO> listCartDto;

	public BsAddressDTO getBsAddressDto() {
		return bsAddressDto;
	}

	public void setBsAddressDto(BsAddressDTO bsAddressDto) {
		this.bsAddressDto = bsAddressDto;
	}

	public List<BsMerchantShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<BsMerchantShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Override
	public String toString() {
		return "SubMerchantOrderDTO [merchantCode=" + merchantCode
				+ ", bsAddressDto=" + bsAddressDto + ", listCartDto="
				+ listCartDto + "]";
	}

}
