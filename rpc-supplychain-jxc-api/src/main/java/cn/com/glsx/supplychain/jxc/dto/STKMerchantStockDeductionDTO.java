package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class STKMerchantStockDeductionDTO extends JXCMTBaseDTO implements Serializable{
	
	@ApiModelProperty(name = "tradeType", notes = "类型 LS:其他盘亏 RT:退货", dataType = "string", required = false, example = "")
	private String tradeType;
	@ApiModelProperty(name = "listDetailDto", notes = "扣减数据信息", dataType = "object", required = false, example = "")
	private List<STKMerchantStockDeductionDetailDTO> listDetailDto;
	@ApiModelProperty(name = "listSuccessDto", notes = "扣减校验返回成功列表", dataType = "object", required = false, example = "")
	private List<STKMerchantStockDeductionDetailDTO> listSuccessDto;
	@ApiModelProperty(name = "listFailedDto", notes = "扣减校验返回失败列表", dataType = "object", required = false, example = "")
	private List<STKMerchantStockDeductionDetailDTO> listFailedDto;
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public List<STKMerchantStockDeductionDetailDTO> getListDetailDto() {
		return listDetailDto;
	}
	public void setListDetailDto(
			List<STKMerchantStockDeductionDetailDTO> listDetailDto) {
		this.listDetailDto = listDetailDto;
	}
	public List<STKMerchantStockDeductionDetailDTO> getListSuccessDto() {
		return listSuccessDto;
	}
	public void setListSuccessDto(
			List<STKMerchantStockDeductionDetailDTO> listSuccessDto) {
		this.listSuccessDto = listSuccessDto;
	}
	public List<STKMerchantStockDeductionDetailDTO> getListFailedDto() {
		return listFailedDto;
	}
	public void setListFailedDto(
			List<STKMerchantStockDeductionDetailDTO> listFailedDto) {
		this.listFailedDto = listFailedDto;
	}
	@Override
	public String toString() {
		return "STKMerchantStockDeductionDTO [tradeType=" + tradeType
				+ ", listDetailDto=" + listDetailDto + ", listSuccessDto="
				+ listSuccessDto + ", listFailedDto=" + listFailedDto + "]";
	}
	
	
}
