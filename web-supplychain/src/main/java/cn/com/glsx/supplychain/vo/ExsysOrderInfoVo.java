package cn.com.glsx.supplychain.vo;

/**
 * @Title: 业务系统上报的订单以及订单状态 主要来自保活干系统
 * @Description: 
 * @author 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
public class ExsysOrderInfoVo {

	private String orderno;
	private Long ordertimestamp;
	private String tomerchantno;
	private String productno;
	private String productname;
	private Integer productquantity;
	private String contactname;
	private String contactphone;
	private String contactaddress;
	private Long expectedtimestamp;
	private Long updatetime;
	private	String orderstatu;
	private String remark;
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Long getOrdertimestamp() {
		return ordertimestamp;
	}
	public void setOrdertimestamp(Long ordertimestamp) {
		this.ordertimestamp = ordertimestamp;
	}
	public String getTomerchantno() {
		return tomerchantno;
	}
	public void setTomerchantno(String tomerchantno) {
		this.tomerchantno = tomerchantno;
	}
	public String getProductno() {
		return productno;
	}
	public void setProductno(String productno) {
		this.productno = productno;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getProductquantity() {
		return productquantity;
	}
	public void setProductquantity(Integer productquantity) {
		this.productquantity = productquantity;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getContactphone() {
		return contactphone;
	}
	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	public String getContactaddress() {
		return contactaddress;
	}
	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}
	public Long getExpectedtimestamp() {
		return expectedtimestamp;
	}
	public void setExpectedtimestamp(Long expectedtimestamp) {
		this.expectedtimestamp = expectedtimestamp;
	}
	public Long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}
	public String getOrderstatu() {
		return orderstatu;
	}
	public void setOrderstatu(String orderstatu) {
		this.orderstatu = orderstatu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ExsysOrderInfoVo [orderno=" + orderno + ", ordertimestamp="
				+ ordertimestamp + ", tomerchantno=" + tomerchantno
				+ ", productno=" + productno + ", productname=" + productname
				+ ", productquantity=" + productquantity + ", contactname="
				+ contactname + ", contactphone=" + contactphone
				+ ", contactaddress=" + contactaddress + ", expectedtimestamp="
				+ expectedtimestamp + ", updatetime=" + updatetime
				+ ", orderstatu=" + orderstatu + ", remark=" + remark + "]";
	}
	
	
}
