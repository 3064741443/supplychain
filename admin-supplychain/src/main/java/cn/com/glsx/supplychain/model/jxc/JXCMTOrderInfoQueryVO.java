package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTOrderInfoQueryVO implements Serializable{

	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类id", dataType = "int", required = false, example = "")
	private Integer deviceTypeId;
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = false, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "status", notes = "发货状态 UF:未完成 OV:已完成 CL:已取消", dataType = "string", required = false, example = "")
    private String status;
	@ApiModelProperty(name = "merchantOrder", notes = "子订单号", dataType = "string", required = false, example = "JXC202007241346310752")
	private String merchantOrder;
	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号", dataType = "string", required = false, example = "")
	private String sendMerchantNo;
	@ApiModelProperty(name = "warehouseId", notes = "发货仓库id", dataType = "int", required = false, example = "")
	private Integer warehouseId;
	@ApiModelProperty(name = "startSendTime", notes = "发货单管理发货开始时间(搜索条件)", dataType = "string", required = false, example = "")
    private String startSendTime;
	@ApiModelProperty(name = "endSendTime", notes = "发货单管理发货结束时间(搜索条件)", dataType = "string", required = false, example = "")
    private String endSendTime;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getStartSendTime() {
		return startSendTime;
	}

	public void setStartSendTime(String startSendTime) {
		this.startSendTime = startSendTime;
	}

	public String getEndSendTime() {
		return endSendTime;
	}

	public void setEndSendTime(String endSendTime) {
		this.endSendTime = endSendTime;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "JXCMTOrderInfoQueryVO{" +
				"deviceTypeId=" + deviceTypeId +
				", dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", status='" + status + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", sendMerchantNo='" + sendMerchantNo + '\'' +
				", warehouseId=" + warehouseId +
				", startSendTime='" + startSendTime + '\'' +
				", endSendTime='" + endSendTime + '\'' +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				'}';
	}
}
