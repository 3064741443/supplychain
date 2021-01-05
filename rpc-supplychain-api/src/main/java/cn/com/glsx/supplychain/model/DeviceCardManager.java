package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class DeviceCardManager implements Serializable{
    private Integer id;

    private String iccid;

    private String imsi;

    private Integer companyId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String remark;
    
    @Transient
    private String cardNo;

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
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "DeviceCardManager ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (iccid != null ? "iccid=" + iccid + ", " : "")
				+ (imsi != null ? "imsi=" + imsi + ", " : "")
				+ (companyId != null ? "companyId=" + companyId + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", "
						: "")
				+ (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "")
				+ (updatedDate != null ? "updatedDate=" + updatedDate + ", "
						: "")
				+ (deletedFlag != null ? "deletedFlag=" + deletedFlag + ", "
						: "")
				+ (remark != null ? "remark=" + remark + ", " : "")
				+ (cardNo != null ? "cardNo=" + cardNo : "") + "]";
	}

	

	
    
    
}