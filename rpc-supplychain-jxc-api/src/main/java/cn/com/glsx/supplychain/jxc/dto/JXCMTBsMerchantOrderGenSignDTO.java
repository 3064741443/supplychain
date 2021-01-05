package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderGenSignDTO implements Serializable{

	//单据号
	private String documentNo;
	//商户子订单列表
	private List<String> listMerchantOrders;
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public List<String> getListMerchantOrders() {
		return listMerchantOrders;
	}
	public void setListMerchantOrders(List<String> listMerchantOrders) {
		this.listMerchantOrders = listMerchantOrders;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderGenSignDTO [documentNo=" + documentNo
				+ ", listMerchantOrders=" + listMerchantOrders + "]";
	}
	
	
	
	
}
