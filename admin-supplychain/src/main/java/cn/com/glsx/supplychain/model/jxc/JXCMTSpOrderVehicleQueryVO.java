package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTSpOrderVehicleQueryVO implements Serializable{

	@ApiModelProperty(name = "listMerchantOrder", notes = "商户子订单编号列表", dataType = "list", required = true, example = "")
	public List<String> listMerchantOrder;

	public List<String> getListMerchantOrder() {
		return listMerchantOrder;
	}

	public void setListMerchantOrder(List<String> listMerchantOrder) {
		this.listMerchantOrder = listMerchantOrder;
	}

	@Override
	public String toString() {
		return "JXCMTSpOrderVehicleQueryVO [listMerchantOrder="
				+ listMerchantOrder + "]";
	}
	
}
