package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class GenBills implements Serializable{

	private String userName;
	private String billType;
	private List<Bill> listBills;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Bill> getListBills() {
		return listBills;
	}
	public void setListBills(List<Bill> listBills) {
		this.listBills = listBills;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	@Override
	public String toString() {
		return "GenBills [userName=" + userName + ", billType=" + billType
				+ ", listBills=" + listBills + "]";
	}
	
	
}
