package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SpMerchantOrderVo implements Serializable{

	private String merchantOrder;
	private Integer channelId;
	private String channelName;
	private String merchantCode;
	private String merchantName;
    private String materialCode;
    private String materialName;
    private Integer deviceTypeId;
    private String deviceTypeName;
    private String status;
    private Integer totalCheck;
    private Integer totalSends;
    private Integer totalOwes;
	private String checkBy;
    private String checkTime;
    private String checkRemark;
	private Integer subjectId;
	private String subjectName;
	private String deviceScanType;
	private String dispatchOrderCode;
	private String orderTimeStart;
	private String orderTimeEnd;
	private String urlDispatchBills;
    private BsAddressVo bsAddressVo;
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
	public String getUrlDispatchBills() {
		return urlDispatchBills;
	}
	public void setUrlDispatchBills(String urlDispatchBills) {
		this.urlDispatchBills = urlDispatchBills;
	}
	public BsAddressVo getBsAddressVo() {
		return bsAddressVo;
	}
	public void setBsAddressVo(BsAddressVo bsAddressVo) {
		this.bsAddressVo = bsAddressVo;
	}
	@Override
	public String toString() {
		return "SpMerchantOrderVo [merchantOrder=" + merchantOrder
				+ ", channelId=" + channelId + ", channelName=" + channelName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", deviceTypeId="
				+ deviceTypeId + ", deviceTypeName=" + deviceTypeName
				+ ", status=" + status + ", totalCheck=" + totalCheck
				+ ", totalSends=" + totalSends + ", totalOwes=" + totalOwes
				+ ", checkBy=" + checkBy + ", checkTime=" + checkTime
				+ ", checkRemark=" + checkRemark + ", subjectId=" + subjectId
				+ ", subjectName=" + subjectName + ", deviceScanType="
				+ deviceScanType + ", dispatchOrderCode=" + dispatchOrderCode
				+ ", orderTimeStart=" + orderTimeStart + ", orderTimeEnd="
				+ orderTimeEnd + ", urlDispatchBills=" + urlDispatchBills
				+ ", bsAddressVo=" + bsAddressVo + "]";
	}	
}
