package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_rzfb")
public class StatementSellRzfb implements Serializable{
    private Integer id;

    private String settleCustomerCode;

    private String settleCustomerName;
    
    private String saleGroupCode;
    
    private String saleGroupName;

    private String workOrder;

    private String alipayTransactionNumber;

    private String alipaySerialNumber;

    private String merchantOrderCode;

    private String accountType;

    private Double income;

    private Double expenditure;

    private Double accountBalance;

    private Double serviceFee;

    private String paymentChannel;

    private String signedProducts;

    private String counterAccount;

    private String counterName;

    private String bankOrderNumber;

    private String productName;

    private Date recordedData;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSettleCustomerCode() {
        return settleCustomerCode;
    }

    public void setSettleCustomerCode(String settleCustomerCode) {
        this.settleCustomerCode = settleCustomerCode == null ? null : settleCustomerCode.trim();
    }

    public String getSettleCustomerName() {
        return settleCustomerName;
    }

    public void setSettleCustomerName(String settleCustomerName) {
        this.settleCustomerName = settleCustomerName == null ? null : settleCustomerName.trim();
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder == null ? null : workOrder.trim();
    }

    public String getAlipayTransactionNumber() {
        return alipayTransactionNumber;
    }

    public void setAlipayTransactionNumber(String alipayTransactionNumber) {
        this.alipayTransactionNumber = alipayTransactionNumber == null ? null : alipayTransactionNumber.trim();
    }

    public String getAlipaySerialNumber() {
        return alipaySerialNumber;
    }

    public void setAlipaySerialNumber(String alipaySerialNumber) {
        this.alipaySerialNumber = alipaySerialNumber == null ? null : alipaySerialNumber.trim();
    }

    public String getMerchantOrderCode() {
        return merchantOrderCode;
    }

    public void setMerchantOrderCode(String merchantOrderCode) {
        this.merchantOrderCode = merchantOrderCode == null ? null : merchantOrderCode.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel == null ? null : paymentChannel.trim();
    }

    public String getSignedProducts() {
        return signedProducts;
    }

    public void setSignedProducts(String signedProducts) {
        this.signedProducts = signedProducts == null ? null : signedProducts.trim();
    }

    public String getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(String counterAccount) {
        this.counterAccount = counterAccount == null ? null : counterAccount.trim();
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName == null ? null : counterName.trim();
    }

    public String getBankOrderNumber() {
        return bankOrderNumber;
    }

    public void setBankOrderNumber(String bankOrderNumber) {
        this.bankOrderNumber = bankOrderNumber == null ? null : bankOrderNumber.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Date getRecordedData() {
        return recordedData;
    }

    public void setRecordedData(Date recordedData) {
        this.recordedData = recordedData;
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
		return "StatementSellRzfb [id=" + id + ", settleCustomerCode="
				+ settleCustomerCode + ", settleCustomerName="
				+ settleCustomerName + ", workOrder=" + workOrder
				+ ", alipayTransactionNumber=" + alipayTransactionNumber
				+ ", alipaySerialNumber=" + alipaySerialNumber
				+ ", merchantOrderCode=" + merchantOrderCode + ", accountType="
				+ accountType + ", income=" + income + ", expenditure="
				+ expenditure + ", accountBalance=" + accountBalance
				+ ", serviceFee=" + serviceFee + ", paymentChannel="
				+ paymentChannel + ", signedProducts=" + signedProducts
				+ ", counterAccount=" + counterAccount + ", counterName="
				+ counterName + ", bankOrderNumber=" + bankOrderNumber
				+ ", productName=" + productName + ", recordedData="
				+ recordedData + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", deletedFlag=" + deletedFlag + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
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
    
    
}