package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderSignSuplyDTO implements Serializable{

	@Id
    private Integer id;

    private String dispatchOrderCode;

    private String logisticsNo;

    private String billSignNumber;

    private String signUrl;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String billType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}

	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getBillSignNumber() {
		return billSignNumber;
	}

	public void setBillSignNumber(String billSignNumber) {
		this.billSignNumber = billSignNumber;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
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
		this.deletedFlag = deletedFlag;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderSignSuplyDTO [id=" + id
				+ ", dispatchOrderCode=" + dispatchOrderCode + ", logisticsNo="
				+ logisticsNo + ", billSignNumber=" + billSignNumber
				+ ", signUrl=" + signUrl + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + ", billType=" + billType + "]";
	}
    
    
}
