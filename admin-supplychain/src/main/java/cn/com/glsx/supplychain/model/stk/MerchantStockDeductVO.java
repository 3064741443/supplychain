package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MerchantStockDeductVO implements Serializable{
	
	@ApiModelProperty(name = "tradeType", notes = "类型 LS:其他盘亏 RT:退货", dataType = "string", required = false, example = "")
	private String tradeType;
	@ApiModelProperty(name = "MerchantStockDeductDetailVO", notes = "扣减数据信息", dataType = "object", required = false, example = "")
	private List<MerchantStockDeductDetailVO> listDetailVo;
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public List<MerchantStockDeductDetailVO> getListDetailVo() {
		return listDetailVo;
	}
	public void setListDetailVo(List<MerchantStockDeductDetailVO> listDetailVo) {
		this.listDetailVo = listDetailVo;
	}
	@Override
	public String toString() {
		return "MerchantStockDeductVO [tradeType=" + tradeType
				+ ", listDetailVo=" + listDetailVo + "]";
	}
	
	
}
