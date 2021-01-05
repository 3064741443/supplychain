package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;


/**
 * 
 * @Title: UserInfo
 * @Description: 用户管理实体
 * @author Leiyj  
 * @date 2018年1月15日 下午4:29:59
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class UserInfo extends SupplyRequest implements Serializable{
	private Integer id;
	
	private String userName;
	
	private String password;
	
	private Integer role;
	
	private Integer warehouseId;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date updatedDate;
	
	private String updatedBy;
	
	private String deletedFlag;
	
	private Integer isSup;
	
	@Transient
	private WareHouseInfo wareHouseInfo;
	
	public Integer getIsSup() {
		return isSup;
	}

	public void setIsSup(Integer isSup) {
		this.isSup = isSup;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
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
		this.createdBy = createdBy == null ? "" : createdBy;
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
		
		this.updatedBy = updatedBy == null ? "" : updatedBy;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public WareHouseInfo getWareHouseInfo() {
		return wareHouseInfo;
	}

	public void setWareHouseInfo(WareHouseInfo wareHouseInfo) {
		this.wareHouseInfo = wareHouseInfo;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userName=" + userName + ", password="
				+ password + ", role=" + role + ", warehouseId=" + warehouseId
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ ", deletedFlag=" + deletedFlag + ", isSup=" + isSup
				+ ", wareHouseInfo=" + wareHouseInfo + "]";
	}

	
}
