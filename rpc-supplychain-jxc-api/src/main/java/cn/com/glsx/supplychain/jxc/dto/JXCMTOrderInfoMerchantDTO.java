package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTOrderInfoMerchantDTO implements Serializable{

	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号", dataType = "string", required = false, example = "")
	private String sendMerchantNo;
	@ApiModelProperty(name = "sendMerchantName", notes = "发往商户名称", dataType = "string", required = false, example = "")
	private String sendMerchantName;
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
	@Override
	public String toString() {
		return "JXCMTOrderInfoMerchantDTO [sendMerchantNo=" + sendMerchantNo
				+ ", sendMerchantName=" + sendMerchantName + "]";
	}
	
}
