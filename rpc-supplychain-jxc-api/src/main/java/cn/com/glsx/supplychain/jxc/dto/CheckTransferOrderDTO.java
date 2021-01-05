package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckTransferOrderDTO implements Serializable{

	public String tranOrderCode;
	
	public String sn;

	public String getTranOrderCode() {
		return tranOrderCode;
	}

	public void setTranOrderCode(String tranOrderCode) {
		this.tranOrderCode = tranOrderCode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public String toString() {
		return "CheckTransferOrderDTO{" +
				"tranOrderCode='" + tranOrderCode + '\'' +
				", sn='" + sn + '\'' +
				'}';
	}
}
