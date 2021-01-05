package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsOrderQueryVO implements Serializable{
	@ApiModelProperty(name = "status", notes = "订单状态", dataType = "string", required = false, example = "")
    private String status;
    @ApiModelProperty(name = "signStatus", notes = "签收状态", dataType = "string", required = false, example = "")
    private String signStatus;
    @ApiModelProperty(name = "orderTimeStart", notes = "下单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeStart;
	@ApiModelProperty(name = "orderTimeEnd", notes = "下单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeEnd;
	@ApiModelProperty(name = "moOrderCode", notes = "总订单编号", dataType = "string", required = false, example = "")
	private String moOrderCode;
	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "")
    private String productName;
    @ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
    @ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String materialCode;
    @ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
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
	public String getMoOrderCode() {
		return moOrderCode;
	}
	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}
	
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
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
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@Override
	public String toString() {
		return "JXCMTBsOrderQueryVO [status=" + status + ", signStatus="
				+ signStatus + ", orderTimeStart=" + orderTimeStart
				+ ", orderTimeEnd=" + orderTimeEnd + ", moOrderCode="
				+ moOrderCode + ", merchantOrder=" + merchantOrder
				+ ", productName=" + productName + ", materialName="
				+ materialName + ", materialCode=" + materialCode
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
    
}
