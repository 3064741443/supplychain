package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTGhMerchantOrderConfigDTO implements Serializable{

	private String dispatchOrderCode;
	private String fastenConfigDesc;
	private String optionConfigDesc;
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}
	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}
	public String getOptionConfigDesc() {
		return optionConfigDesc;
	}
	public void setOptionConfigDesc(String optionConfigDesc) {
		this.optionConfigDesc = optionConfigDesc;
	}
	@Override
	public String toString() {
		return "JXCMTGhMerchantOrderConfigDTO [dispatchOrderCode="
				+ dispatchOrderCode + ", fastenConfigDesc=" + fastenConfigDesc
				+ ", optionConfigDesc=" + optionConfigDesc + "]";
	}
	
	
	
}
