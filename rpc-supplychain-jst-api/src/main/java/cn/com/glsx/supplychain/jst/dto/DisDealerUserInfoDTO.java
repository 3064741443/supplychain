package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisDealerUserInfoDTO extends BaseDTO implements Serializable{

	List<String> listMerchantCode;
	
	List<BsDealerUserInfoDTO> listDealerUserInfoDTO;

	public List<String> getListMerchantCode() {
		return listMerchantCode;
	}

	public void setListMerchantCode(List<String> listMerchantCode) {
		this.listMerchantCode = listMerchantCode;
	}

	public List<BsDealerUserInfoDTO> getListDealerUserInfoDTO() {
		return listDealerUserInfoDTO;
	}

	public void setListDealerUserInfoDTO(
			List<BsDealerUserInfoDTO> listDealerUserInfoDTO) {
		this.listDealerUserInfoDTO = listDealerUserInfoDTO;
	}

	@Override
	public String toString() {
		return "DisDealerUserInfoDTO [listMerchantCode=" + listMerchantCode
				+ ", listDealerUserInfoDTO=" + listDealerUserInfoDTO + "]";
	}
	
	
}
