package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JstShopAgentRelationDTO extends BaseDTO implements Serializable{

	private Integer id;

	private String shopCode;

	private String agentMerchantCode;

	private String agentMerchantName;

	private String status;

	private String checkFailResult;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckFailResult() {
		return checkFailResult;
	}

	public void setCheckFailResult(String checkFailResult) {
		this.checkFailResult = checkFailResult;
	}

	@Override
	public String toString() {
		return "JstShopAgentRelationDTO{" +
				"id=" + id +
				", shopCode='" + shopCode + '\'' +
				", agentMerchantCode='" + agentMerchantCode + '\'' +
				", agentMerchantName='" + agentMerchantName + '\'' +
				", status='" + status + '\'' +
				", checkFailResult='" + checkFailResult + '\'' +
				'}';
	}

}
