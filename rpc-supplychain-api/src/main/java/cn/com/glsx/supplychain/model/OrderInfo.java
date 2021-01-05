package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

/**
 * 
 * @Title: OrderInfo
 * @Description: 订单管理实体
 * @author Leiyj
 * @date 2018年1月15日 下午4:29:47
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class OrderInfo extends BaseInfo implements Serializable {
	private Integer id;

	private String orderCode;

	private Integer total;

	private Integer alreadyShipped;

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
	
	private Integer attribManaId;
	
	private Integer sendQuanlity;
	
	@Transient
    private WareHouseInfo wareHouseInfo; //仓库信息
    
    @Transient
    private AttribMana attribMana;  //属性配置管理

	@Transient
	private Long merchantOrderDetailId; //商户订单明细ID

	@Transient
	private Integer	devTypeId;//设备类型ID
	
	@Transient
	private String warehouseName;
	
	@Transient
	private Integer countLogistiss;//物流发货数量
	
	@Transient
	private String LogistisNo;//物流单号
	
	@Transient
	private String LogistisCpy;//物流公司
	
	@Transient
	private String merchantOrderCode; //商户订单号 
	

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getCountLogistiss() {
		return countLogistiss;
	}

	public void setCountLogistiss(Integer countLogistiss) {
		this.countLogistiss = countLogistiss;
	}

	public String getLogistisNo() {
		return LogistisNo;
	}

	public void setLogistisNo(String logistisNo) {
		LogistisNo = logistisNo;
	}

	public String getLogistisCpy() {
		return LogistisCpy;
	}

	public void setLogistisCpy(String logistisCpy) {
		LogistisCpy = logistisCpy;
	}

	public Long getMerchantOrderDetailId() {
		return merchantOrderDetailId;
	}

	public void setMerchantOrderDetailId(Long merchantOrderDetailId) {
		this.merchantOrderDetailId = merchantOrderDetailId;
	}

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
		this.orderCode = orderCode == null ? "" : orderCode.trim();
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAlreadyShipped() {
		return alreadyShipped;
	}

	public void setAlreadyShipped(Integer alreadyShipped) {
		this.alreadyShipped = alreadyShipped;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? "" : status.trim();
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

	public String getOperatorMerchantNo() {
		return operatorMerchantNo;
	}

	public void setOperatorMerchantNo(String operatorMerchantNo) {
		this.operatorMerchantNo = operatorMerchantNo == null ? ""
				: operatorMerchantNo.trim();
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
		this.sendMerchantNo = sendMerchantNo == null ? "" : sendMerchantNo
				.trim();
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
		this.address = address == null ? "" : address.trim();
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts == null ? "" : contacts.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? "" : mobile.trim();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate == null ? new Date() : createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? "" : createdBy.trim();
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate == null ? new Date() : updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? "" : updatedBy.trim();
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag == null ? "" : deletedFlag.trim();
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

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	public WareHouseInfo getWareHouseInfo() {
		return wareHouseInfo;
	}

	public void setWareHouseInfo(WareHouseInfo wareHouseInfo) {
		this.wareHouseInfo = wareHouseInfo;
	}

	public AttribMana getAttribMana() {
		return attribMana;
	}

	public void setAttribMana(AttribMana attribMana) {
		this.attribMana = attribMana;
	}

	public Integer getDevTypeId() {
		return devTypeId;
	}

	public void setDevTypeId(Integer devTypeId) {
		this.devTypeId = devTypeId;
	}

	public Integer getAttribManaId() {
		return attribManaId;
	}

	public void setAttribManaId(Integer attribManaId) {
		this.attribManaId = attribManaId;
	}

	public String getMerchantOrderCode() {
		return merchantOrderCode;
	}

	public void setMerchantOrderCode(String merchantOrderCode) {
		this.merchantOrderCode = merchantOrderCode;
	}

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", orderCode=" + orderCode + ", total="
				+ total + ", alreadyShipped=" + alreadyShipped + ", status="
				+ status + ", deviceId=" + deviceId + ", deviceName="
				+ deviceName + ", attribCode=" + attribCode
				+ ", operatorMerchantNo=" + operatorMerchantNo + ", batch="
				+ batch + ", warehouseId=" + warehouseId + ", sendMerchantNo="
				+ sendMerchantNo + ", sendMerchantName=" + sendMerchantName
				+ ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", updatedDate=" + updatedDate
				+ ", updatedBy=" + updatedBy + ", deletedFlag=" + deletedFlag
				+ ", remark=" + remark + ", packageOne=" + packageOne
				+ ", packageTwo=" + packageTwo + ", attribManaId="
				+ attribManaId + ", wareHouseInfo=" + wareHouseInfo
				+ ", attribMana=" + attribMana + ", merchantOrderDetailId="
				+ merchantOrderDetailId + ", devTypeId=" + devTypeId
				+ ", warehouseName=" + warehouseName + ", countLogistiss="
				+ countLogistiss + ", LogistisNo=" + LogistisNo
				+ ", LogistisCpy=" + LogistisCpy + ", merchantOrderCode="
				+ merchantOrderCode + "]";
	}

	public Integer getSendQuanlity() {
		return sendQuanlity;
	}

	public void setSendQuanlity(Integer sendQuanlity) {
		this.sendQuanlity = sendQuanlity;
	}
	
	

}
