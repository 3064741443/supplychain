package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_glwy")
public class StatementSellGlwy implements Serializable{
    private Integer id;

    private String saleGroupCode;
    
    private String saleGroupName;
    
    private String belongCompany;

    private String area;

    private String shopCode;
    
    private String shopName;

    private String applyNo;

    private String contractPaymentNo;

    private String customerCode;

    private String customerName;

    private String rentAttrible;

    private String financialDes;

    private String vinNo;

    private String engineNo;

    private Date contractDate;

    private String insureYear;

    private Double glwyInsureMoney;

    private Double glwySettleMoney;

    private Double rentProfitMoney;

    private Double insureAssureMoney;

    private String contractStatu;

    private String financingMaturity;

    private String shopAttrib;

    private String settleStatu;

    private String billNo;

    private Date applyDate;

    private String workOrder;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String settleCustomerCode;
    
    private String settleCustomerName;
    
    /**
     *  状态(1:未拆分,2:拆分成功,3:拆分失败)
     */
    private Byte status;

    /**
     * 失败原因
     */
    private String reasons;
    /**
     * 开始时间
     */
    @Transient
    private Date startDate;

    /**
     * 结束时间
     */
    @Transient
    private Date endDate;
    
    /**
     * 导入时间检索
     */
    @Transient
    private Date iptStartDate;
    
    /**
     * 导入时间检索
     */
    @Transient
    private Date iptEndDate;
    
    private Date contractDateSplit;
    
    private String financingMaturitySplit;
    
    
    public Date getIptStartDate() {
		return iptStartDate;
	}

	public void setIptStartDate(Date iptStartDate) {
		this.iptStartDate = iptStartDate;
	}

	public Date getIptEndDate() {
		return iptEndDate;
	}

	public void setIptEndDate(Date iptEndDate) {
		this.iptEndDate = iptEndDate;
	}

	public String getSaleGroupCode() {
		return saleGroupCode;
	}

	public void setSaleGroupCode(String saleGroupCode) {
		this.saleGroupCode = saleGroupCode;
	}

	public String getSaleGroupName() {
		return saleGroupName;
	}

	public void setSaleGroupName(String saleGroupName) {
		this.saleGroupName = saleGroupName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(String belongCompany) {
        this.belongCompany = belongCompany == null ? null : belongCompany.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getContractPaymentNo() {
        return contractPaymentNo;
    }

    public void setContractPaymentNo(String contractPaymentNo) {
        this.contractPaymentNo = contractPaymentNo == null ? null : contractPaymentNo.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getRentAttrible() {
        return rentAttrible;
    }

    public void setRentAttrible(String rentAttrible) {
        this.rentAttrible = rentAttrible == null ? null : rentAttrible.trim();
    }

    public String getFinancialDes() {
        return financialDes;
    }

    public void setFinancialDes(String financialDes) {
        this.financialDes = financialDes == null ? null : financialDes.trim();
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo == null ? null : vinNo.trim();
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo == null ? null : engineNo.trim();
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getInsureYear() {
        return insureYear;
    }

    public void setInsureYear(String insureYear) {
        this.insureYear = insureYear == null ? null : insureYear.trim();
    }

    public Double getGlwyInsureMoney() {
        return glwyInsureMoney;
    }

    public void setGlwyInsureMoney(Double glwyInsureMoney) {
        this.glwyInsureMoney = glwyInsureMoney;
    }

    public Double getGlwySettleMoney() {
        return glwySettleMoney;
    }

    public void setGlwySettleMoney(Double glwySettleMoney) {
        this.glwySettleMoney = glwySettleMoney;
    }

    public Double getRentProfitMoney() {
        return rentProfitMoney;
    }

    public void setRentProfitMoney(Double rentProfitMoney) {
        this.rentProfitMoney = rentProfitMoney;
    }

    public Double getInsureAssureMoney() {
        return insureAssureMoney;
    }

    public void setInsureAssureMoney(Double insureAssureMoney) {
        this.insureAssureMoney = insureAssureMoney;
    }

    public String getContractStatu() {
        return contractStatu;
    }

    public void setContractStatu(String contractStatu) {
        this.contractStatu = contractStatu == null ? null : contractStatu.trim();
    }

    public String getFinancingMaturity() {
        return financingMaturity;
    }

    public void setFinancingMaturity(String financingMaturity) {
        this.financingMaturity = financingMaturity;
    }

    public String getShopAttrib() {
        return shopAttrib;
    }

    public void setShopAttrib(String shopAttrib) {
        this.shopAttrib = shopAttrib == null ? null : shopAttrib.trim();
    }

    public String getSettleStatu() {
        return settleStatu;
    }

    public void setSettleStatu(String settleStatu) {
        this.settleStatu = settleStatu == null ? null : settleStatu.trim();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder == null ? null : workOrder.trim();
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
    
    

	public String getSettleCustomerCode() {
		return settleCustomerCode;
	}

	public void setSettleCustomerCode(String settleCustomerCode) {
		this.settleCustomerCode = settleCustomerCode;
	}

	public String getSettleCustomerName() {
		return settleCustomerName;
	}

	public void setSettleCustomerName(String settleCustomerName) {
		this.settleCustomerName = settleCustomerName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "StatementSellGlwy [id=" + id + ", belongCompany="
				+ belongCompany + ", area=" + area + ", shopCode=" + shopCode
				+ ", applyNo=" + applyNo + ", contractPaymentNo="
				+ contractPaymentNo + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", rentAttrible="
				+ rentAttrible + ", financialDes=" + financialDes + ", vinNo="
				+ vinNo + ", engineNo=" + engineNo + ", contractDate="
				+ contractDate + ", insureYear=" + insureYear
				+ ", glwyInsureMoney=" + glwyInsureMoney + ", glwySettleMoney="
				+ glwySettleMoney + ", rentProfitMoney=" + rentProfitMoney
				+ ", insureAssureMoney=" + insureAssureMoney
				+ ", contractStatu=" + contractStatu + ", financingMaturity="
				+ financingMaturity + ", shopAttrib=" + shopAttrib
				+ ", settleStatu=" + settleStatu + ", billNo=" + billNo
				+ ", applyDate=" + applyDate + ", workOrder=" + workOrder
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + "]";
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getContractDateSplit() {
		return contractDateSplit;
	}

	public void setContractDateSplit(Date contractDateSplit) {
		this.contractDateSplit = contractDateSplit;
	}

	public String getFinancingMaturitySplit() {
		return financingMaturitySplit;
	}

	public void setFinancingMaturitySplit(String financingMaturitySplit) {
		this.financingMaturitySplit = financingMaturitySplit;
	}
    
    
}