package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MerchantVO implements Serializable{

	//代理商户
	private String agentMerchantCode;

    private String agentMerchantName;

	public String getAgentMerchantCode() {
		return agentMerchantCode;
	}

	public void setAgentMerchantCode(String agentMerchantCode) {
		this.agentMerchantCode = agentMerchantCode;
	}

	public String getAgentMerchantName() {
		return agentMerchantName;
	}

	public void setAgentMerchantName(String agentMerchantName) {
		this.agentMerchantName = agentMerchantName;
	}

	@Override
	public String toString() {
		return "MerchantVo [agentMerchantCode=" + agentMerchantCode
				+ ", agentMerchantName=" + agentMerchantName + "]";
	}
    
    
}
