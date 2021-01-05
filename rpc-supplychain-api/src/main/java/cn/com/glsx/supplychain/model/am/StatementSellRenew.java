package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_renew")
public class StatementSellRenew implements Serializable{
    private Integer id;
    
    private String saleGroupCode;
    
    private String saleGroupName;

    private Date tradeTime;

    private String pubaccountId;

    private String merchantCode;

    private String specialMerchantCode;

    private String deviceSn;

    private String weixinOrderNo;

    private String merchantOrderNo;

    private String userFlag;

    private String tradeType;

    private String tradeStatu;

    private String payBank;

    private String currencyType;

    private Double shsettleOrderMoney;

    private Double vouchersMoney;

    private String weixinReturnNo;

    private String merchantReturnNo;

    private Double returnMoney;

    private Double erchangeReturnMoney;

    private String returnType;

    private String returnStatu;

    private String merchantName;

    private Double chargesMoney;

    private Double feeRate;

    private Double orderMoney;

    private Double applyReturnMoney;

    private String feeRateRemark;

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

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getPubaccountId() {
        return pubaccountId;
    }

    public void setPubaccountId(String pubaccountId) {
        this.pubaccountId = pubaccountId == null ? null : pubaccountId.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getSpecialMerchantCode() {
        return specialMerchantCode;
    }

    public void setSpecialMerchantCode(String specialMerchantCode) {
        this.specialMerchantCode = specialMerchantCode == null ? null : specialMerchantCode.trim();
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn == null ? null : deviceSn.trim();
    }

    public String getWeixinOrderNo() {
        return weixinOrderNo;
    }

    public void setWeixinOrderNo(String weixinOrderNo) {
        this.weixinOrderNo = weixinOrderNo == null ? null : weixinOrderNo.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag == null ? null : userFlag.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getTradeStatu() {
        return tradeStatu;
    }

    public void setTradeStatu(String tradeStatu) {
        this.tradeStatu = tradeStatu == null ? null : tradeStatu.trim();
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank == null ? null : payBank.trim();
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public Double getShsettleOrderMoney() {
        return shsettleOrderMoney;
    }

    public void setShsettleOrderMoney(Double shsettleOrderMoney) {
        this.shsettleOrderMoney = shsettleOrderMoney;
    }

    public Double getVouchersMoney() {
        return vouchersMoney;
    }

    public void setVouchersMoney(Double vouchersMoney) {
        this.vouchersMoney = vouchersMoney;
    }

    public String getWeixinReturnNo() {
        return weixinReturnNo;
    }

    public void setWeixinReturnNo(String weixinReturnNo) {
        this.weixinReturnNo = weixinReturnNo == null ? null : weixinReturnNo.trim();
    }

    public String getMerchantReturnNo() {
        return merchantReturnNo;
    }

    public void setMerchantReturnNo(String merchantReturnNo) {
        this.merchantReturnNo = merchantReturnNo == null ? null : merchantReturnNo.trim();
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getErchangeReturnMoney() {
        return erchangeReturnMoney;
    }

    public void setErchangeReturnMoney(Double erchangeReturnMoney) {
        this.erchangeReturnMoney = erchangeReturnMoney;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getReturnStatu() {
        return returnStatu;
    }

    public void setReturnStatu(String returnStatu) {
        this.returnStatu = returnStatu == null ? null : returnStatu.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public Double getChargesMoney() {
        return chargesMoney;
    }

    public void setChargesMoney(Double chargesMoney) {
        this.chargesMoney = chargesMoney;
    }

    public Double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(Double feeRate) {
        this.feeRate = feeRate;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getApplyReturnMoney() {
        return applyReturnMoney;
    }

    public void setApplyReturnMoney(Double applyReturnMoney) {
        this.applyReturnMoney = applyReturnMoney;
    }

    public String getFeeRateRemark() {
        return feeRateRemark;
    }

    public void setFeeRateRemark(String feeRateRemark) {
        this.feeRateRemark = feeRateRemark == null ? null : feeRateRemark.trim();
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
		return "StatementSellRenew [id=" + id + ", tradeTime=" + tradeTime
				+ ", pubaccountId=" + pubaccountId + ", merchantCode="
				+ merchantCode + ", specialMerchantCode=" + specialMerchantCode
				+ ", deviceSn=" + deviceSn + ", weixinOrderNo=" + weixinOrderNo
				+ ", merchantOrderNo=" + merchantOrderNo + ", userFlag="
				+ userFlag + ", tradeType=" + tradeType + ", tradeStatu="
				+ tradeStatu + ", payBank=" + payBank + ", currencyType="
				+ currencyType + ", shsettleOrderMoney=" + shsettleOrderMoney
				+ ", vouchersMoney=" + vouchersMoney + ", weixinReturnNo="
				+ weixinReturnNo + ", merchantReturnNo=" + merchantReturnNo
				+ ", returnMoney=" + returnMoney + ", erchangeReturnMoney="
				+ erchangeReturnMoney + ", returnType=" + returnType
				+ ", returnStatu=" + returnStatu + ", merchantName="
				+ merchantName + ", chargesMoney=" + chargesMoney
				+ ", feeRate=" + feeRate + ", orderMoney=" + orderMoney
				+ ", applyReturnMoney=" + applyReturnMoney + ", feeRateRemark="
				+ feeRateRemark + ", workOrder=" + workOrder + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
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