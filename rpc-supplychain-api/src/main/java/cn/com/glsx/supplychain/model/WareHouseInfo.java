package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

/**
 * 
 * @Title: WareHouseInfo
 * @Description: 仓库管理实体
 * @author Leiyj  
 * @date 2018年1月15日 下午4:30:49
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class WareHouseInfo  implements Serializable{
    private Integer id;
    
    private String belong;   //工厂或者仓库类型(FA:工厂      WA:仓库)

    private String name;

    private String address;

    private String mobile;

    private String contacts;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String deletedFlag;
    
    @Transient
    private String wareHouseName;
    
    @Transient
    private String wareHouseUpName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? "" : address.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? "" : mobile.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? "" : contacts.trim();
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

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WareHouseInfo [id=");
		builder.append(id);
		builder.append(", belong=");
		builder.append(belong);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", contacts=");
		builder.append(contacts);
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
		builder.append(", wareHouseName=");
		builder.append(wareHouseName);
		builder.append(", wareHouseUpName=");
		builder.append(wareHouseUpName);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getAddress()=");
		builder.append(getAddress());
		builder.append(", getMobile()=");
		builder.append(getMobile());
		builder.append(", getContacts()=");
		builder.append(getContacts());
		builder.append(", getCreatedDate()=");
		builder.append(getCreatedDate());
		builder.append(", getCreatedBy()=");
		builder.append(getCreatedBy());
		builder.append(", getUpdatedDate()=");
		builder.append(getUpdatedDate());
		builder.append(", getUpdatedBy()=");
		builder.append(getUpdatedBy());
		builder.append(", getDeletedFlag()=");
		builder.append(getDeletedFlag());
		builder.append(", getBelong()=");
		builder.append(getBelong());
		builder.append(", getWareHouseName()=");
		builder.append(getWareHouseName());
		builder.append(", getWareHouseUpName()=");
		builder.append(getWareHouseUpName());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	} 
}