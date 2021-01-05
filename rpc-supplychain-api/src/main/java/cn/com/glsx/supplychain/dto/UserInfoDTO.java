package cn.com.glsx.supplychain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: DemoDTO.java
 * @Description:
 * @author ${userName}
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@SuppressWarnings("serial")
public class UserInfoDTO implements Serializable{
	
private Integer id;
	
	private String userId;
	
	private String password;
	
	private Integer role;
	
	private Integer warehouse_id;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Integer warehouse_id) {
		this.warehouse_id = warehouse_id;
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
