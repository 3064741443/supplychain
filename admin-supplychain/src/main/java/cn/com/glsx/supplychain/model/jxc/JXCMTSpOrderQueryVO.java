package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTSpOrderQueryVO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单号", dataType = "string", required = false, example = "")
	private String dispatchOrderCode;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
	private String materialName;
	@ApiModelProperty(name = "status", notes = "订单状态", dataType = "string", required = false, example = "")
    private String status;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId; 
	@ApiModelProperty(name = "checkTimeStart", notes = "审核时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeStart;
	@ApiModelProperty(name = "checkTimeEnd", notes = "审核时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeEnd;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getCheckTimeStart() {
		return checkTimeStart;
	}
	public void setCheckTimeStart(String checkTimeStart) {
		this.checkTimeStart = checkTimeStart;
	}
	public String getCheckTimeEnd() {
		return checkTimeEnd;
	}
	public void setCheckTimeEnd(String checkTimeEnd) {
		this.checkTimeEnd = checkTimeEnd;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "JXCMTSpOrderQueryVO [merchantOrder=" + merchantOrder
				+ ", dispatchOrderCode=" + dispatchOrderCode
				+ ", merchantName=" + merchantName + ", materialName="
				+ materialName + ", status=" + status + ", channelId="
				+ channelId + ", checkTimeStart=" + checkTimeStart
				+ ", checkTimeEnd=" + checkTimeEnd + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + "]";
	}
	
    
	
}
