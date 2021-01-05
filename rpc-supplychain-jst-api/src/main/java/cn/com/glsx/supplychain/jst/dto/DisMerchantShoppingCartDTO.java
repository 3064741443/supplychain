package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisMerchantShoppingCartDTO extends BaseDTO implements Serializable{

	// 页面大小
	private Integer pageSize;

	// 页号
	private Integer pageNo;
	
	private String merchantCode;

	private List<BsMerchantShoppingCartDTO> listCartDto;

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

	public List<BsMerchantShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<BsMerchantShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	@Override
	public String toString() {
		return "DisMerchantShoppingCartDTO [pageSize=" + pageSize + ", pageNo="
				+ pageNo + ", listCartDto=" + listCartDto + "]";
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

}
