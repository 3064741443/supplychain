package glsx.com.cn.task.model;

import java.util.Date;


/**
 * @Title: OrderInfo
 * @Description: 订单表
 * @author liuquan 
 * @date 2018年4月21日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
public class OrderInfo {

	private Integer id;

	private String orderCode;

	private Integer total;

	private String status;

	private Integer deviceId;

	private String deviceName;

	private String attribCode;

	private String operatorMerchantNo;

	private String batch;

	private Integer warehouseId;

	private String sendMerchantNo;

	private String sendMerchantName;

	private String address;

	private String contacts;

	private String mobile;

	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;

	private String deletedFlag;

	private String remark;

	private String packageOne;

	private String packageTwo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	public String getOperatorMerchantNo() {
		return operatorMerchantNo;
	}

	public void setOperatorMerchantNo(String operatorMerchantNo) {
		this.operatorMerchantNo = operatorMerchantNo;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSendMerchantNo() {
		return sendMerchantNo;
	}

	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}

	public String getSendMerchantName() {
		return sendMerchantName;
	}

	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPackageOne() {
		return packageOne;
	}

	public void setPackageOne(String packageOne) {
		this.packageOne = packageOne;
	}

	public String getPackageTwo() {
		return packageTwo;
	}

	public void setPackageTwo(String packageTwo) {
		this.packageTwo = packageTwo;
	}

	@Override
	public String toString() {
		return "OrderInfo{" +
				"id=" + id +
				", orderCode='" + orderCode + '\'' +
				", total=" + total +
				", status='" + status + '\'' +
				", deviceId=" + deviceId +
				", deviceName='" + deviceName + '\'' +
				", attribCode='" + attribCode + '\'' +
				", operatorMerchantNo='" + operatorMerchantNo + '\'' +
				", batch='" + batch + '\'' +
				", warehouseId=" + warehouseId +
				", sendMerchantNo='" + sendMerchantNo + '\'' +
				", sendMerchantName='" + sendMerchantName + '\'' +
				", address='" + address + '\'' +
				", contacts='" + contacts + '\'' +
				", mobile='" + mobile + '\'' +
				", createdDate=" + createdDate +
				", createdBy='" + createdBy + '\'' +
				", updatedDate=" + updatedDate +
				", updatedBy='" + updatedBy + '\'' +
				", deletedFlag='" + deletedFlag + '\'' +
				", remark='" + remark + '\'' +
				", packageOne='" + packageOne + '\'' +
				", packageTwo='" + packageTwo + '\'' +
				'}';
	}
}
