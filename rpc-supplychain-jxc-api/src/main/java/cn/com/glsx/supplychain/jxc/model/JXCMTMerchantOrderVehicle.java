package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "mt_merchant_order_vehicle")
public class JXCMTMerchantOrderVehicle implements Serializable{
	@Id
    private Integer id;

    private String moOrderCode;

    private Integer moParentBrandId;

    private String moParentBrandName;

    private Integer moSubBrandId;

    private String moSubBrandName;

    private Integer moAudiId;

    private String moAudiName;

    private String moMotorcycle;

    private String moRemark;

    private Integer moTotal;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoOrderCode() {
        return moOrderCode;
    }

    public void setMoOrderCode(String moOrderCode) {
        this.moOrderCode = moOrderCode == null ? null : moOrderCode.trim();
    }

    public Integer getMoParentBrandId() {
        return moParentBrandId;
    }

    public void setMoParentBrandId(Integer moParentBrandId) {
        this.moParentBrandId = moParentBrandId;
    }

    public String getMoParentBrandName() {
        return moParentBrandName;
    }

    public void setMoParentBrandName(String moParentBrandName) {
        this.moParentBrandName = moParentBrandName == null ? null : moParentBrandName.trim();
    }

    public Integer getMoSubBrandId() {
        return moSubBrandId;
    }

    public void setMoSubBrandId(Integer moSubBrandId) {
        this.moSubBrandId = moSubBrandId;
    }

    public String getMoSubBrandName() {
        return moSubBrandName;
    }

    public void setMoSubBrandName(String moSubBrandName) {
        this.moSubBrandName = moSubBrandName == null ? null : moSubBrandName.trim();
    }

    public Integer getMoAudiId() {
        return moAudiId;
    }

    public void setMoAudiId(Integer moAudiId) {
        this.moAudiId = moAudiId;
    }

    public String getMoAudiName() {
        return moAudiName;
    }

    public void setMoAudiName(String moAudiName) {
        this.moAudiName = moAudiName == null ? null : moAudiName.trim();
    }

    public String getMoMotorcycle() {
        return moMotorcycle;
    }

    public void setMoMotorcycle(String moMotorcycle) {
        this.moMotorcycle = moMotorcycle == null ? null : moMotorcycle.trim();
    }

    public String getMoRemark() {
        return moRemark;
    }

    public void setMoRemark(String moRemark) {
        this.moRemark = moRemark == null ? null : moRemark.trim();
    }

    public Integer getMoTotal() {
        return moTotal;
    }

    public void setMoTotal(Integer moTotal) {
        this.moTotal = moTotal;
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

	@Override
	public String toString() {
		return "JXCMTMerchantOrderVehicle [id=" + id + ", moOrderCode="
				+ moOrderCode + ", moParentBrandId=" + moParentBrandId
				+ ", moParentBrandName=" + moParentBrandName
				+ ", moSubBrandId=" + moSubBrandId + ", moSubBrandName="
				+ moSubBrandName + ", moAudiId=" + moAudiId + ", moAudiName="
				+ moAudiName + ", moMotorcycle=" + moMotorcycle + ", moRemark="
				+ moRemark + ", moTotal=" + moTotal + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}