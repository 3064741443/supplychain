package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCAmKcCustomerRelationDTO implements Serializable{

	@ApiModelProperty(name = "merchantCode", notes = "服务商户编码", dataType = "string", required = false, example = "")
	private String merchantCode;
	@ApiModelProperty(name = "customerCode", notes = "K3客户编码", dataType = "string", required = false, example = "")
    private String customerCode;
	@ApiModelProperty(name = "customerName", notes = "K3客户名称", dataType = "string", required = false, example = "")
    private String customerName;
	@ApiModelProperty(name = "customerName", notes = "服务商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@Override
	public String toString() {
		return "JXCAmKcCustomerRelationDTO [merchantCode=" + merchantCode
				+ ", customerCode=" + customerCode + ", customerName="
				+ customerName + ", merchantName=" + merchantName + "]";
	}
	
	
}
