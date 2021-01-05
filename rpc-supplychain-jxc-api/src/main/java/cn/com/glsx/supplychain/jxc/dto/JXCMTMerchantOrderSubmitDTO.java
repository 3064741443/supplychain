package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderSubmitDTO extends JXCMTBaseDTO implements Serializable{
	
	@ApiModelProperty(name = "merchantCode", notes = "商户号", dataType = "string", required = false, example = "1")
	private String merchantCode;
	@ApiModelProperty(name = "addressDto", notes = "有id的 填id", dataType = "int", required = true, example = "1011")
	private JXCMTBsAddressDTO addressDto;
	@ApiModelProperty(name = "hopeTime", notes = "期望到货时间", dataType = "string", required = true, example = "2020-08-03")
	private String hopeTime;
	@ApiModelProperty(name = "listProductSplitDto", notes = "下单产品列表", dataType = "list", required = true, example = "")
	private List<JXCMTProductSplitDTO> listProductSplitDto;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}	
	public String getHopeTime() {
		return hopeTime;
	}
	public void setHopeTime(String hopeTime) {
		this.hopeTime = hopeTime;
	}
	public List<JXCMTProductSplitDTO> getListProductSplitDto() {
		return listProductSplitDto;
	}
	public void setListProductSplitDto(List<JXCMTProductSplitDTO> listProductSplitDto) {
		this.listProductSplitDto = listProductSplitDto;
	}
	public JXCMTBsAddressDTO getAddressDto() {
		return addressDto;
	}
	public void setAddressDto(JXCMTBsAddressDTO addressDto) {
		this.addressDto = addressDto;
	}
	
	
}
