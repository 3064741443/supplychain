package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubShopOrderDTO extends BaseDTO implements Serializable{
	
	private String shopCode;

	//地址
	private BsAddressDTO bsAddressDto;
			
	//下单物品
	private List<JstShopShoppingCartDTO> listCartDto;

	public BsAddressDTO getBsAddressDto() {
		return bsAddressDto;
	}

	public void setBsAddressDto(BsAddressDTO bsAddressDto) {
		this.bsAddressDto = bsAddressDto;
	}

	

	public List<JstShopShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<JstShopShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@Override
	public String toString() {
		return "SubShopOrderDTO [shopCode=" + shopCode + ", bsAddressDto="
				+ bsAddressDto + ", listCartDto=" + listCartDto + "]";
	}

	
	
}
