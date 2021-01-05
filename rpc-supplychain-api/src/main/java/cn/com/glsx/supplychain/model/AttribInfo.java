package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: AttribInfo
 * @Description: 属性实体
 * @author Leiyj  
 * @date 2018年1月15日 下午4:28:54
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class AttribInfo implements Serializable {
	private Integer id;

	private Integer type;

	private String name;

	private String comment;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? "" : comment.trim();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AttribInfo [id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", deletedFlag=");
		builder.append(deletedFlag);
		builder.append("]");
		return builder.toString();
	}
}