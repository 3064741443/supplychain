package glsx.com.cn.task.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class ExsysDispatchLog implements Serializable{
    private Integer id;

    private String sn;

    private String iccid;

    private String imei;
    
    private String imsi;

    private String simPhone;

    private String cardNo;

    private String packageNo;

    private String toMerchantNo;

    private String operatormerchantNo;

    private String factoryNo;
    
    private String factoryName;

    private String verifCode;

    private String dispatchNo;

    private String productNo;

    private String productName;

    private String orderNo;

    private String thirdOrderNo;

    private Long inTimestamp;

    private Long outTimestamp;

    private String mnumName;
    
    private Integer deviceCode;
    
    private String deviceCodeName;

    private Integer deviceType;

    private String deviceTypeName;
    
    private String softversion;

    private String systemFlag;
    
    private String moduleFlag;

    private Integer resentCount;

    private Integer resentMax;
    
    private String retCode;
    
    private String retDesc;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    @Transient
    private Integer subjectId;
    
    @Transient
    private String isSure;
    
    
    
    public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getIsSure() {
		return isSure;
	}

	public void setIsSure(String isSure) {
		this.isSure = isSure;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSoftversion() {
		return softversion;
	}

	public void setSoftversion(String softversion) {
		this.softversion = softversion;
	}

	public String getModuleFlag() {
		return moduleFlag;
	}

	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
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

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getSimPhone() {
        return simPhone;
    }

    public void setSimPhone(String simPhone) {
        this.simPhone = simPhone == null ? null : simPhone.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo == null ? null : packageNo.trim();
    }

    public String getToMerchantNo() {
        return toMerchantNo;
    }

    public void setToMerchantNo(String toMerchantNo) {
        this.toMerchantNo = toMerchantNo == null ? null : toMerchantNo.trim();
    }

    public String getOperatormerchantNo() {
        return operatormerchantNo;
    }

    public void setOperatormerchantNo(String operatormerchantNo) {
        this.operatormerchantNo = operatormerchantNo == null ? null : operatormerchantNo.trim();
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo == null ? null : factoryNo.trim();
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode == null ? null : verifCode.trim();
    }

    public String getDispatchNo() {
        return dispatchNo;
    }

    public void setDispatchNo(String dispatchNo) {
        this.dispatchNo = dispatchNo == null ? null : dispatchNo.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo == null ? null : thirdOrderNo.trim();
    }

    public Long getInTimestamp() {
        return inTimestamp;
    }

    public void setInTimestamp(Long inTimestamp) {
        this.inTimestamp = inTimestamp;
    }

    public Long getOutTimestamp() {
        return outTimestamp;
    }

    public void setOutTimestamp(Long outTimestamp) {
        this.outTimestamp = outTimestamp;
    }

    public String getMnumName() {
        return mnumName;
    }

    public void setMnumName(String mnumName) {
        this.mnumName = mnumName == null ? null : mnumName.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag == null ? null : systemFlag.trim();
    }

    public Integer getResentCount() {
        return resentCount;
    }

    public void setResentCount(Integer resentCount) {
        this.resentCount = resentCount;
    }

    public Integer getResentMax() {
        return resentMax;
    }

    public void setResentMax(Integer resentMax) {
        this.resentMax = resentMax;
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

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceCodeName() {
		return deviceCodeName;
	}

	public void setDeviceCodeName(String deviceCodeName) {
		this.deviceCodeName = deviceCodeName;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	@Override
	public String toString() {
		return "ExsysDispatchLog [id=" + id + ", sn=" + sn + ", iccid=" + iccid
				+ ", imei=" + imei + ", simPhone=" + simPhone + ", cardNo="
				+ cardNo + ", packageNo=" + packageNo + ", toMerchantNo="
				+ toMerchantNo + ", operatormerchantNo=" + operatormerchantNo
				+ ", factoryNo=" + factoryNo + ", verifCode=" + verifCode
				+ ", dispatchNo=" + dispatchNo + ", productNo=" + productNo
				+ ", productName=" + productName + ", orderNo=" + orderNo
				+ ", thirdOrderNo=" + thirdOrderNo + ", inTimestamp="
				+ inTimestamp + ", outTimestamp=" + outTimestamp
				+ ", mnumName=" + mnumName + ", deviceCode=" + deviceCode
				+ ", deviceCodeName=" + deviceCodeName + ", deviceType="
				+ deviceType + ", deviceTypeName=" + deviceTypeName
				+ ", softversion=" + softversion + ", systemFlag=" + systemFlag
				+ ", moduleFlag=" + moduleFlag + ", resentCount=" + resentCount
				+ ", resentMax=" + resentMax + ", retCode=" + retCode
				+ ", retDesc=" + retDesc + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
	
	
    
    
}