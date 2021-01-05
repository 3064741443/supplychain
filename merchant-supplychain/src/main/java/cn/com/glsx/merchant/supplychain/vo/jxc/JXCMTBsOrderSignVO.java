package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsOrderSignVO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号集合", dataType = "list", required = true, example = "")
	private List<String> listMerchantOrders;

	public List<String> getListMerchantOrders() {
		return listMerchantOrders;
	}

	public void setListMerchantOrders(List<String> listMerchantOrders) {
		this.listMerchantOrders = listMerchantOrders;
	}

	@Override
	public String toString() {
		return "JXCMTBsOrderSignVO [listMerchantOrders=" + listMerchantOrders
				+ "]";
	}
	
	
}
