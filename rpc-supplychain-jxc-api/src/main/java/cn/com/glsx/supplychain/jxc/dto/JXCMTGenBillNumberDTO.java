package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTGenBillNumberDTO implements Serializable{

	private String userName;
	
	private String billType;
	
	private List<JXCMTOrderInfoSignDTO> listOrderInfoSigns;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<JXCMTOrderInfoSignDTO> getListOrderInfoSigns() {
		return listOrderInfoSigns;
	}

	public void setListOrderInfoSigns(List<JXCMTOrderInfoSignDTO> listOrderInfoSigns) {
		this.listOrderInfoSigns = listOrderInfoSigns;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String toString() {
		return "JXCMTGenBillNumberDTO [userName=" + userName + ", billType="
				+ billType + ", listOrderInfoSigns=" + listOrderInfoSigns + "]";
	}
	
}
