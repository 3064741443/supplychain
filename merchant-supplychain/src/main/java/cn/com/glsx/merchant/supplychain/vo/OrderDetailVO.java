package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderDetailVO implements Serializable{

	//sn/imei
	public String sn;
	//设备编号
	public String attribCode;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	@Override
	public String toString() {
		return "OrderDetailVO [sn=" + sn + ", attribCode=" + attribCode + "]";
	}
	
	
}
