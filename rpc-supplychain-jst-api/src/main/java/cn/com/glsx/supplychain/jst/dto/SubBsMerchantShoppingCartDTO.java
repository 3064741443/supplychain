package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubBsMerchantShoppingCartDTO extends BaseDTO implements Serializable{

	public String merchantCode;
	
	public List<BsMerchantShoppingCartDTO> listCartDto;
	
	public Integer totalCount;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public List<BsMerchantShoppingCartDTO> getListCartDto() {
		return listCartDto;
	}

	public void setListCartDto(List<BsMerchantShoppingCartDTO> listCartDto) {
		this.listCartDto = listCartDto;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "SubBsMerchantShoppingCartDTO [merchantCode=" + merchantCode
				+ ", listCartDto=" + listCartDto + ", totalCount=" + totalCount
				+ "]";
	}
	
	
}
