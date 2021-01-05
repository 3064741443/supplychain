package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTSpMerchantOrderDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "merchantCode", notes = "下单商户号", dataType = "string", required = false, example = "")
	private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "merchantLoginName", notes = "下单商户登陆名", dataType = "string", required = false, example = "")
	private String merchantLoginName;
	@ApiModelProperty(name = "materialCode", notes = "审核物料编号", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "审核物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "deviceTypeId", notes = "物料设备大类id", dataType = "int", required = false, example = "")
    private Integer deviceTypeId;
	@ApiModelProperty(name = "deviceTypeName", notes = "物料设备大类名称", dataType = "string", required = false, example = "")
    private String deviceTypeName;
	@ApiModelProperty(name = "status", notes = "订单状态", dataType = "string", required = false, example = "")
    private String status;
	@ApiModelProperty(name = "totalCheck", notes = "审核数量", dataType = "int", required = false, example = "")
    private Integer totalCheck;
	@ApiModelProperty(name = "totalSends", notes = "已发货数量", dataType = "int", required = false, example = "")
    private Integer totalSends;
	@ApiModelProperty(name = "totalOwes", notes = "欠数", dataType = "int", required = false, example = "")
    private Integer totalOwes;
	@ApiModelProperty(name = "checkBy", notes = "审核人", dataType = "string", required = false, example = "")
	private String checkBy;
	@ApiModelProperty(name = "checkBy", notes = "审核时间", dataType = "string", required = false, example = "")
    private String checkTime;
	@ApiModelProperty(name = "checkRemark", notes = "审核备注", dataType = "string", required = false, example = "")
    private String checkRemark;
	@ApiModelProperty(name = "subjectId", notes = "项目id", dataType = "Integer", required = false, example = "")
	private Integer subjectId;
	@ApiModelProperty(name = "subjectName", notes = "项目名称", dataType = "string", required = false, example = "")
	private String subjectName;
	@ApiModelProperty(name = "deviceScanType", notes = "设备是否扫码出库 Y:扫码出库  N:不扫码出库", dataType = "int", required = false, example = "Y")
	private String deviceScanType;
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单号", dataType = "string", required = false, example = "")
	private String dispatchOrderCode;
	@ApiModelProperty(name = "orderTimeStart", notes = "下单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeStart;
	@ApiModelProperty(name = "orderTimeEnd", notes = "下单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeEnd;
	@ApiModelProperty(name = "urlDispatchBills", notes = "发货单下载地址", dataType = "string", required = false, example = "")
	private String urlDispatchBills;
	@ApiModelProperty(name = "bsAddressDto", notes = "收获地址信息", dataType = "object", required = false, example = "")
    private JXCMTBsAddressDTO bsAddressDto;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalCheck() {
		return totalCheck;
	}
	public void setTotalCheck(Integer totalCheck) {
		this.totalCheck = totalCheck;
	}
	public Integer getTotalSends() {
		return totalSends;
	}
	public void setTotalSends(Integer totalSends) {
		this.totalSends = totalSends;
	}
	public Integer getTotalOwes() {
		return totalOwes;
	}
	public void setTotalOwes(Integer totalOwes) {
		this.totalOwes = totalOwes;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDeviceScanType() {
		return deviceScanType;
	}
	public void setDeviceScanType(String deviceScanType) {
		this.deviceScanType = deviceScanType;
	}
	public JXCMTBsAddressDTO getBsAddressDto() {
		return bsAddressDto;
	}
	public void setBsAddressDto(JXCMTBsAddressDTO bsAddressDto) {
		this.bsAddressDto = bsAddressDto;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
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
	
	public String getMerchantLoginName() {
		return merchantLoginName;
	}
	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	public String getUrlDispatchBills() {
		return urlDispatchBills;
	}
	public void setUrlDispatchBills(String urlDispatchBills) {
		this.urlDispatchBills = urlDispatchBills;
	}
	@Override
	public String toString() {
		return "JXCMTSpMerchantOrderDTO [merchantOrder=" + merchantOrder
				+ ", channelId=" + channelId + ", channelName=" + channelName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", merchantLoginName=" + merchantLoginName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", deviceTypeId=" + deviceTypeId
				+ ", deviceTypeName=" + deviceTypeName + ", status=" + status
				+ ", totalCheck=" + totalCheck + ", totalSends=" + totalSends
				+ ", totalOwes=" + totalOwes + ", checkBy=" + checkBy
				+ ", checkTime=" + checkTime + ", checkRemark=" + checkRemark
				+ ", subjectId=" + subjectId + ", subjectName=" + subjectName
				+ ", deviceScanType=" + deviceScanType + ", dispatchOrderCode="
				+ dispatchOrderCode + ", orderTimeStart=" + orderTimeStart
				+ ", orderTimeEnd=" + orderTimeEnd + ", urlDispatchBills="
				+ urlDispatchBills + ", bsAddressDto=" + bsAddressDto + "]";
	}
	
	
	
	
	
}
