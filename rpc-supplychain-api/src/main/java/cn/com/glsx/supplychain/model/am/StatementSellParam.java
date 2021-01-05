package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class StatementSellParam implements Serializable{

	private String workOrder;
	
	private List<String> listSn;

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public List<String> getListSn() {
		return listSn;
	}

	public void setListSn(List<String> listSn) {
		this.listSn = listSn;
	}
	
	
}
