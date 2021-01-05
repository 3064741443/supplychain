package cn.com.glsx.supplychain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: OrderInfoDeatil
 * @Description: 订单详情导出实体类
 * @author Leiyj  
 * @date 2018年1月15日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class OrderInfoDetailExcelVo implements Serializable {
	private Integer id;

	private String orderCode;

	private String iccid;

	private String imei;

	private String sn;

	private String attribCode;

	private String batch;

	private Integer warehouseId;

	private Integer warehouseIdUp;

	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private String updatedDate;

	private String deletedFlag;

	private Integer logisticsId;

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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getWarehouseIdUp() {
		return warehouseIdUp;
	}

	public void setWarehouseIdUp(Integer warehouseIdUp) {
		this.warehouseIdUp = warehouseIdUp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	@Override
	public String toString() {
		return "OrderInfoDetailExcelVo{" +
				"id=" + id +
				", orderCode='" + orderCode + '\'' +
				", iccid='" + iccid + '\'' +
				", imei='" + imei + '\'' +
				", sn='" + sn + '\'' +
				", attribCode='" + attribCode + '\'' +
				", batch='" + batch + '\'' +
				", warehouseId=" + warehouseId +
				", warehouseIdUp=" + warehouseIdUp +
				", createdBy='" + createdBy + '\'' +
				", createdDate=" + createdDate +
				", updatedBy='" + updatedBy + '\'' +
				", updatedDate=" + updatedDate +
				", deletedFlag='" + deletedFlag + '\'' +
				", logisticsId=" + logisticsId +
				'}';
	}
}