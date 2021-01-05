package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTSpOrderDispatchDirectVO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = true, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "warehouseId", notes = "仓库id", dataType = "int", required = true, example = "")
	private Integer warehouseId;
	@ApiModelProperty(name = "sendNums", notes = "发货数量", dataType = "int", required = true, example = "")
	private Integer sendNums;
	@ApiModelProperty(name = "sendTime", notes = "发货时间", dataType = "string", required = true, example = "")
	private String sendTime;
	@ApiModelProperty(name = "logisticsNum", notes = "物流编码", dataType = "string", required = true, example = "")
	private String logisticsNum;
	@ApiModelProperty(name = "logisticsCompany", notes = "物流公司", dataType = "string", required = true, example = "")
    private String logisticsCompany;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getSendNums() {
		return sendNums;
	}
	public void setSendNums(Integer sendNums) {
		this.sendNums = sendNums;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getLogisticsNum() {
		return logisticsNum;
	}
	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}
	public String getLogisticsCompany() {
		return logisticsCompany;
	}
	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	@Override
	public String toString() {
		return "JXCMTSpOrderDispatchDirectVO [merchantOrder=" + merchantOrder
				+ ", warehouseId=" + warehouseId + ", sendNums=" + sendNums
				+ ", sendTime=" + sendTime + ", logisticsNum=" + logisticsNum
				+ ", logisticsCompany=" + logisticsCompany + "]";
	}
	
	
}
