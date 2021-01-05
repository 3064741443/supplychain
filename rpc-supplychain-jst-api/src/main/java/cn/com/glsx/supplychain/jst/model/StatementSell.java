package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "am_statement_sell")
public class StatementSell implements Serializable{
	@Id
    private Integer id;

    private String sn;

    private String merchantCode;

    private String merchantName;

    private String merchantOrderCode;

    private String dispatchOrderCode;

    private String productCode;

    private String productName;

    private Date time;

    private String materialCode;

    private String materialName;

    private String logisticsNo;

    private String logisticsCmp;

    private String vtSn;

    private Date sendTime;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String productType;
    
    private String workOrder;
    
    @Transient
    private Date reconTimeStart;

    @Transient
    private Date reconTimeEnd;
    
    public Date getReconTimeStart() {
		return reconTimeStart;
	}

	public void setReconTimeStart(Date reconTimeStart) {
		this.reconTimeStart = reconTimeStart;
	}

	public Date getReconTimeEnd() {
		return reconTimeEnd;
	}

	public void setReconTimeEnd(Date reconTimeEnd) {
		this.reconTimeEnd = reconTimeEnd;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantOrderCode() {
        return merchantOrderCode;
    }

    public void setMerchantOrderCode(String merchantOrderCode) {
        this.merchantOrderCode = merchantOrderCode == null ? null : merchantOrderCode.trim();
    }

    public String getDispatchOrderCode() {
        return dispatchOrderCode;
    }

    public void setDispatchOrderCode(String dispatchOrderCode) {
        this.dispatchOrderCode = dispatchOrderCode == null ? null : dispatchOrderCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getLogisticsCmp() {
        return logisticsCmp;
    }

    public void setLogisticsCmp(String logisticsCmp) {
        this.logisticsCmp = logisticsCmp == null ? null : logisticsCmp.trim();
    }

    public String getVtSn() {
        return vtSn;
    }

    public void setVtSn(String vtSn) {
        this.vtSn = vtSn == null ? null : vtSn.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	@Override
	public String toString() {
		return "StatementSell [id=" + id + ", sn=" + sn + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", merchantOrderCode=" + merchantOrderCode
				+ ", dispatchOrderCode=" + dispatchOrderCode + ", productCode="
				+ productCode + ", productName=" + productName + ", time="
				+ time + ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", logisticsNo=" + logisticsNo
				+ ", logisticsCmp=" + logisticsCmp + ", vtSn=" + vtSn
				+ ", sendTime=" + sendTime + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + ", productType=" + productType + ", workOrder="
				+ workOrder + ", reconTimeStart=" + reconTimeStart
				+ ", reconTimeEnd=" + reconTimeEnd + "]";
	}
	
	
}