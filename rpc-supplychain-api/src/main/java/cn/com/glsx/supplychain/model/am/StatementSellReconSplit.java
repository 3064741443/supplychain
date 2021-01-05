package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_recon_split")
public class StatementSellReconSplit implements Serializable{
    private Integer id;

    private String splitCode;

    private String workOrder;

    private String productCode;

    private String productName;

    private String productType;

    private String materialCode;

    private String materialName;

    private Integer sendCount;

    private Double serviceUintPrice;

    private Double serviceTotalPrice;

    private String reconCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private Double taxRate;
    
    @Transient
    private String installCode; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSplitCode() {
        return splitCode;
    }

    public void setSplitCode(String splitCode) {
        this.splitCode = splitCode == null ? null : splitCode.trim();
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder == null ? null : workOrder.trim();
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
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

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Double getServiceUintPrice() {
        return serviceUintPrice;
    }

    public void setServiceUintPrice(Double serviceUintPrice) {
        this.serviceUintPrice = serviceUintPrice;
    }

    public Double getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(Double serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    public String getReconCode() {
        return reconCode;
    }

    public void setReconCode(String reconCode) {
        this.reconCode = reconCode == null ? null : reconCode.trim();
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

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public String getInstallCode() {
		return installCode;
	}

	public void setInstallCode(String installCode) {
		this.installCode = installCode;
	}
	
	
}