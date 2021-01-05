package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

public class DisJstShopShoppingCartDTO extends BaseDTO implements Serializable {

	private String shopCode;
	// 页面大小
	private Integer pageSize;

	// 页号
	private Integer pageNo;

	private List<JstShopShoppingCartDTO> listCartDto;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public List<JstShopShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<JstShopShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	@Override
	public String toString() {
		return "DisJstShopShoppingCartDTO [pageSize=" + pageSize + ", pageNo="
				+ pageNo + ", listCartDto=" + listCartDto + "]";
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

}
