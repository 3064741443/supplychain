package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsOrderQueryVO implements Serializable{
	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "status", notes = "订单状态", dataType = "string", required = false, example = "")
    private String status;
    @ApiModelProperty(name = "signStatus", notes = "签收状态", dataType = "string", required = false, example = "")
    private String signStatus;
    @ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId; 
    @ApiModelProperty(name = "orderTimeStart", notes = "下单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeStart;
	@ApiModelProperty(name = "orderTimeEnd", notes = "下单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeEnd;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "productTypeId", notes = "产品类型id", dataType = "int", required = false, example = "")
	private Integer productTypeId;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "")
    private String productName;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
	private String materialName;
    @ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	@ApiModelProperty(name = "checkTimeStart", notes = "审核时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeStart;
	@ApiModelProperty(name = "checkTimeEnd", notes = "审核时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeEnd;

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

	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getOrderTimeStart() {
		return orderTimeStart;
	}
	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}
	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}
	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
		return "JXCMTBsOrderQueryVO{" +
				"merchantOrder='" + merchantOrder + '\'' +
				", status='" + status + '\'' +
				", signStatus='" + signStatus + '\'' +
				", channelId=" + channelId +
				", orderTimeStart='" + orderTimeStart + '\'' +
				", orderTimeEnd='" + orderTimeEnd + '\'' +
				", merchantName='" + merchantName + '\'' +
				", productTypeId=" + productTypeId +
				", productName='" + productName + '\'' +
				", materialName='" + materialName + '\'' +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", checkTimeStart='" + checkTimeStart + '\'' +
				", checkTimeEnd='" + checkTimeEnd + '\'' +
				'}';
	}


}
