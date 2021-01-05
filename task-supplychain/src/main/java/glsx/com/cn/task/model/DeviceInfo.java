package glsx.com.cn.task.model;

import java.util.Date;

import javax.persistence.Transient;


/**
 * @Title: DeviceInfo
 * @Description: 设备库存管理表实体类
 * @author liuquan 
 * @date 2018年4月21日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
public class DeviceInfo {

	private Integer id;

    private String iccid;

    private String imei;
    
    private String sn;

    private String attribCode;

    private String batch;

    private String status;

    private String orderCode;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String deletedFlag;
    
    private Integer wareHouseId;
    
    private Integer wareHouseIdUp;
    
    private String vcode;
    
    private String imsi;
    
    private String simCardNo;
    
    @Transient
    private OrderInfo orderInfo;

    @Transient
    private String createdTime;
    
    

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public Integer getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public Integer getWareHouseIdUp() {
		return wareHouseIdUp;
	}

	public void setWareHouseIdUp(Integer wareHouseIdUp) {
		this.wareHouseIdUp = wareHouseIdUp;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	@Override
	public String toString() {
		return "DeviceInfo [id=" + id + ", iccid=" + iccid + ", imei=" + imei
				+ ", sn=" + sn + ", attribCode=" + attribCode + ", batch="
				+ batch + ", status=" + status + ", orderCode=" + orderCode
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ ", deletedFlag=" + deletedFlag + ", wareHouseId="
				+ wareHouseId + ", wareHouseIdUp=" + wareHouseIdUp
				+ ", orderInfo=" + orderInfo + ", createdTime=" + createdTime
				+ "]";
	}

	
	
}
