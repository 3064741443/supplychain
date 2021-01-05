package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

import cn.com.glsx.supplychain.model.bs.Logistics;

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
public class DeviceInfo extends BaseInfo implements Serializable {
	@Id
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
    
    private String remark;
    
    private String vcode;
    
    public String imsi;
    
    public String simCardNo;
    
    @Transient
    private String materialName;
    
    @Transient
    private WareHouseInfo wareHouseInfo; //仓库信息
    
    @Transient
    private AttribMana attribMana;  //属性配置管理
    
    @Transient
    private Integer attribCount;
    
    @Transient
    private Integer pn;  //当前页
    
    @Transient
    private Integer ps;  //每页大小
    
    @Transient
    private Logistics logistics;

    @Transient
    private Integer	devTypeId;//设备类型ID
    
    
    public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	public Integer getAttribCount() {
		return attribCount;
	}

	public void setAttribCount(Integer attribCount) {
		this.attribCount = attribCount;
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
        this.iccid = iccid == null ? "" : iccid.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? "" : imei.trim();
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
        this.orderCode = orderCode == null ? "" : orderCode.trim();
    }

    public Date getCreatedDate() {
        return createdDate == null ? new Date() : createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getDevTypeId() {
		return devTypeId;
	}

	public void setDevTypeId(Integer devTypeId) {
		this.devTypeId = devTypeId;
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
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceInfo [id=");
		builder.append(id);
		builder.append(", iccid=");
		builder.append(iccid);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", attribCode=");
		builder.append(attribCode);
		builder.append(", batch=");
		builder.append(batch);
		builder.append(", status=");
		builder.append(status);
		builder.append(", orderCode=");
		builder.append(orderCode);
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
		builder.append(", wareHouseId=");
		builder.append(wareHouseId);
		builder.append(", wareHouseIdUp=");
		builder.append(wareHouseIdUp);
		builder.append(", wareHouseInfo=");
		builder.append(wareHouseInfo);
		builder.append(", attribMana=");
		builder.append(attribMana);
		builder.append(", getWareHouseId()=");
		builder.append(getWareHouseId());
		builder.append(", getWareHouseIdUp()=");
		builder.append(getWareHouseIdUp());
		builder.append(", getWareHouseInfo()=");
		builder.append(getWareHouseInfo());
		builder.append(", getAttribMana()=");
		builder.append(getAttribMana());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getIccid()=");
		builder.append(getIccid());
		builder.append(", getImei()=");
		builder.append(getImei());
		builder.append(", getBatch()=");
		builder.append(getBatch());
		builder.append(", getStatus()=");
		builder.append(getStatus());
		builder.append(", getOrderCode()=");
		builder.append(getOrderCode());
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
		builder.append(", getAttribCode()=");
		builder.append(getAttribCode());
		builder.append(", getModelName()=");
		builder.append(getModelName());
		builder.append(", getTypeName()=");
		builder.append(getTypeName());
		builder.append(", getConfigureName()=");
		builder.append(getConfigureName());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	
}