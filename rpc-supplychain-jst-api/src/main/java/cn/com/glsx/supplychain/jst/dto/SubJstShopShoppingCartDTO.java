package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubJstShopShoppingCartDTO extends BaseDTO implements Serializable{

	//购物车
	private List<JstShopShoppingCartDTO> listCartDto;
	
	//门店编码
	private String shopCode;
	//总数
	private Integer totalCount;

	public List<JstShopShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<JstShopShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}


	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@Override
	public String toString() {
		return "SubJstShopShoppingCartDTO [listCartDto=" + listCartDto
				+ ", shopCode=" + shopCode + ", totalCount=" + totalCount + "]";
	} 
	
	
}
