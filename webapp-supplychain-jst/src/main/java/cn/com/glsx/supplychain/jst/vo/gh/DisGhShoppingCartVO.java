package cn.com.glsx.supplychain.jst.vo.gh;

import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.GhShoppingCartDTO;

public class DisGhShoppingCartVO {

	private String merchantCode;
	private List<GhShoppingCartDTO> listGhShoppingCart;
	// 页面大小
	private Integer pageSize;
	// 页号
	private Integer pageNo;
	public List<GhShoppingCartDTO> getListGhShoppingCart() {
		return listGhShoppingCart;
	}
	public void setListGhShoppingCart(List<GhShoppingCartDTO> listGhShoppingCart) {
		this.listGhShoppingCart = listGhShoppingCart;
	}
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
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	@Override
	public String toString() {
		return "DisGhShoppingCartVO [merchantCode=" + merchantCode
				+ ", listGhShoppingCart=" + listGhShoppingCart + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + "]";
	}
	
}
