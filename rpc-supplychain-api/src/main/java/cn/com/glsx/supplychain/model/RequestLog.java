package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: DeviceInfo
 * @Description: 设备管理实体
 * @author Leiyj  
 * @date 2018年1月15日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class RequestLog extends BaseInfo implements Serializable {
    private Integer id;

    private String iccid;

    private String imei;

    private Integer deviceId;

    private Integer result;

    private Integer errorCode;

    private String errorMsg;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String deletedFlag;

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

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
}