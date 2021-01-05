package cn.com.glsx.rpc.supplychain.rdn.model.kafka;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Bill implements Serializable{

	private String billSignNumber;
	private String sendMerchantNo;
	private String sendMerchantName;
	private String address;
	private String contacts;
	private String mobile;
	private List<BillDetail> listBillDetail;
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public String getSendMerchantName() {
		return sendMerchantName;
	}
	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<BillDetail> getListBillDetail() {
		return listBillDetail;
	}
	public void setListBillDetail(List<BillDetail> listBillDetail) {
		this.listBillDetail = listBillDetail;
	}
	public String getBillSignNumber() {
		return billSignNumber;
	}
	public void setBillSignNumber(String billSignNumber) {
		this.billSignNumber = billSignNumber;
	}
	@Override
	public String toString() {
		return "Bill [billSignNumber=" + billSignNumber + ", sendMerchantNo="
				+ sendMerchantNo + ", sendMerchantName=" + sendMerchantName
				+ ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", listBillDetail=" + listBillDetail
				+ "]";
	}
	
	
	
}
