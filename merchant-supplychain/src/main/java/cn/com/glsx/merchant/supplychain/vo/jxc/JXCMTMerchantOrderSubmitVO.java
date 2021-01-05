package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDTO;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderSubmitVO implements Serializable{

	@ApiModelProperty(name = "addressVo", notes = "有id的 填id", dataType = "int", required = true, example = "1011")
	private JXCMTBsAddressVO addressVo;
	@ApiModelProperty(name = "hopeTime", notes = "期望到货时间", dataType = "string", required = true, example = "2020-08-03")
	private String hopeTime;
	@ApiModelProperty(name = "listProductSplitDto", notes = "下单产品列表", dataType = "list", required = true, example = "")
	private List<JXCMTProductSplitDTO> listProductSplitDto;
	
	public JXCMTBsAddressVO getAddressVo() {
		return addressVo;
	}
	public void setAddressVo(JXCMTBsAddressVO addressVo) {
		this.addressVo = addressVo;
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
	@Override
	public String toString() {
		return "JXCMTMerchantOrderSubmitVO [addressVo=" + addressVo
				+ ", hopeTime=" + hopeTime + ", listProductSplitDto="
				+ listProductSplitDto + "]";
	}
	
	
	
}
