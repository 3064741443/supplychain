package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

/**
 * 
 * @Title: DeviceInfo
 * @Description: 设备管理导出实体类
 * @author Leiyj  
 * @date 2018年1月15日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class DeviceExcelVo implements Serializable {
    private Integer id;

    private String iccid;

    private String imei;

    private String attribCode;
    
    private String modelName;
    
    private String typeName;
    
    private String configureName;
    
    private String boardVersion;
    
    private String softVersion;
    
    private String batch;
    
    private String status;
    
    private String wareHouseName;
    
    private String wareHouseUpName;
    
    private String createdTime;
    
    private String updatedTime;
    
    private String orderCode;
    
    

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getConfigureName() {
		return configureName;
	}

	public void setConfigureName(String configureName) {
		this.configureName = configureName;
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

	public String getBoardVersion() {
		return boardVersion;
	}

	public void setBoardVersion(String boardVersion) {
		this.boardVersion = boardVersion;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
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

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getWareHouseUpName() {
		return wareHouseUpName;
	}

	public void setWareHouseUpName(String wareHouseUpName) {
		this.wareHouseUpName = wareHouseUpName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
    
    
}