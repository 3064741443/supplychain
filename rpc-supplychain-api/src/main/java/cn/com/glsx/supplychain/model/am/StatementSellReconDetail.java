package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_recon_detail")
public class StatementSellReconDetail implements Serializable{
	@Id
    private Integer id;

    private String workOrder;
    
    private String merchantOrderCode;
    
    private String productCode;

    private String productName;

    private String packageOne;

    private String serviceTime;

    private String unitType;

    private Integer sendCount;

    private Double hardwareUintPrice;

    private Double serviceUintPrice;

    private Double hardwareTotalPrice;

    private Double serviceTotalPrice;

    private Double uintTotalPrice;

    private Double totalPrice;

    private String remark;

    private String reconCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String materialCodes;
    
    private String materialNames;
    
    private String logisticsInfo;
    
    private String sendGoodsTime;
    
    @Transient
    private Date reconTimeStart;
    
    @Transient
    private String merchantCode;
    
    @Transient
    private List<StatementSn> listStatementSn;
    
    @Transient
    private List<String> listSn;
    
    @Transient
    private List<StatementSnTemp> listStatementSnTemp;
    
    
    public Date getReconTimeStart() {
		return reconTimeStart;
	}

	public void setReconTimeStart(Date reconTimeStart) {
		this.reconTimeStart = reconTimeStart;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMaterialCodes() {
		return materialCodes;
	}

	public void setMaterialCodes(String materialCodes) {
		this.materialCodes = materialCodes;
	}

	public String getMaterialNames() {
		return materialNames;
	}

	public void setMaterialNames(String materialNames) {
		this.materialNames = materialNames;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne == null ? null : packageOne.trim();
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime == null ? null : serviceTime.trim();
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType == null ? null : unitType.trim();
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Double getHardwareUintPrice() {
        return hardwareUintPrice;
    }

    public void setHardwareUintPrice(Double hardwareUintPrice) {
        this.hardwareUintPrice = hardwareUintPrice;
    }

    public Double getServiceUintPrice() {
        return serviceUintPrice;
    }

    public void setServiceUintPrice(Double serviceUintPrice) {
        this.serviceUintPrice = serviceUintPrice;
    }

    public Double getHardwareTotalPrice() {
        return hardwareTotalPrice;
    }

    public void setHardwareTotalPrice(Double hardwareTotalPrice) {
        this.hardwareTotalPrice = hardwareTotalPrice;
    }

    public Double getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(Double serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    public Double getUintTotalPrice() {
        return uintTotalPrice;
    }

    public void setUintTotalPrice(Double uintTotalPrice) {
        this.uintTotalPrice = uintTotalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public String getLogisticsInfo() {
		return logisticsInfo;
	}

	public void setLogisticsInfo(String logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}

	public List<StatementSn> getListStatementSn() {
		return listStatementSn;
	}

	public void setListStatementSn(List<StatementSn> listStatementSn) {
		this.listStatementSn = listStatementSn;
	}

	public List<String> getListSn() {
		return listSn;
	}

	public void setListSn(List<String> listSn) {
		this.listSn = listSn;
	}

	public String getMerchantOrderCode() {
		return merchantOrderCode;
	}

	public void setMerchantOrderCode(String merchantOrderCode) {
		this.merchantOrderCode = merchantOrderCode;
	}

	public List<StatementSnTemp> getListStatementSnTemp() {
		return listStatementSnTemp;
	}

	public void setListStatementSnTemp(List<StatementSnTemp> listStatementSnTemp) {
		this.listStatementSnTemp = listStatementSnTemp;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}
	
	
}