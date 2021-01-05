package glsx.com.cn.task.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@Table(name = "am_statement_sell_jbwy")
public class StatementSellJbwy implements Serializable{
    private Integer id;
    
    private String saleGroupCode;
    
    private String saleGroupName;

    private String no;

    private String insureNo;

    private String vechoPrice;

    private String vinNo;

    private String vechoUserName;

    private String deviceSn;

    private String engineNo;

    private String deviceType;

    private String version;

    private String insureDueTime;

    private Double money;

    private String insureReportPractice;

    private Date insureStartDate;

    private Date insureEndDate;

    private String princeAgent;

    private String cityAgent;

    private String handInMerchant;

    private String shopName;

    private String preMerchant;

    private String area;

    private String certifiNo;

    private String mobile;

    private String vechoBrand;

    private String vechoType;

    private String vechoSet;

    private String vechoColor;

    private String firstMan;

    private String insureMaturity;

    private String sellServerMane;

    private Double jbwyServerMoney;

    private String mileage;

    private String insureCompany;

    private String operator;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getInsureNo() {
        return insureNo;
    }

    public void setInsureNo(String insureNo) {
        this.insureNo = insureNo == null ? null : insureNo.trim();
    }

    public String getVechoPrice() {
        return vechoPrice;
    }

    public void setVechoPrice(String vechoPrice) {
        this.vechoPrice = vechoPrice == null ? null : vechoPrice.trim();
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo == null ? null : vinNo.trim();
    }

    public String getVechoUserName() {
        return vechoUserName;
    }

    public void setVechoUserName(String vechoUserName) {
        this.vechoUserName = vechoUserName == null ? null : vechoUserName.trim();
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn == null ? null : deviceSn.trim();
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo == null ? null : engineNo.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getInsureDueTime() {
        return insureDueTime;
    }

    public void setInsureDueTime(String insureDueTime) {
        this.insureDueTime = insureDueTime == null ? null : insureDueTime.trim();
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getInsureReportPractice() {
        return insureReportPractice;
    }

    public void setInsureReportPractice(String insureReportPractice) {
        this.insureReportPractice = insureReportPractice == null ? null : insureReportPractice.trim();
    }

    public Date getInsureStartDate() {
        return insureStartDate;
    }

    public void setInsureStartDate(Date insureStartDate) {
        this.insureStartDate = insureStartDate;
    }

    public Date getInsureEndDate() {
        return insureEndDate;
    }

    public void setInsureEndDate(Date insureEndDate) {
        this.insureEndDate = insureEndDate;
    }

    public String getPrinceAgent() {
        return princeAgent;
    }

    public void setPrinceAgent(String princeAgent) {
        this.princeAgent = princeAgent == null ? null : princeAgent.trim();
    }

    public String getCityAgent() {
        return cityAgent;
    }

    public void setCityAgent(String cityAgent) {
        this.cityAgent = cityAgent == null ? null : cityAgent.trim();
    }

    public String getHandInMerchant() {
        return handInMerchant;
    }

    public void setHandInMerchant(String handInMerchant) {
        this.handInMerchant = handInMerchant == null ? null : handInMerchant.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getPreMerchant() {
        return preMerchant;
    }

    public void setPreMerchant(String preMerchant) {
        this.preMerchant = preMerchant == null ? null : preMerchant.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCertifiNo() {
        return certifiNo;
    }

    public void setCertifiNo(String certifiNo) {
        this.certifiNo = certifiNo == null ? null : certifiNo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getVechoBrand() {
        return vechoBrand;
    }

    public void setVechoBrand(String vechoBrand) {
        this.vechoBrand = vechoBrand == null ? null : vechoBrand.trim();
    }

    public String getVechoType() {
        return vechoType;
    }

    public void setVechoType(String vechoType) {
        this.vechoType = vechoType == null ? null : vechoType.trim();
    }

    public String getVechoSet() {
        return vechoSet;
    }

    public void setVechoSet(String vechoSet) {
        this.vechoSet = vechoSet == null ? null : vechoSet.trim();
    }

    public String getVechoColor() {
        return vechoColor;
    }

    public void setVechoColor(String vechoColor) {
        this.vechoColor = vechoColor == null ? null : vechoColor.trim();
    }

    public String getFirstMan() {
        return firstMan;
    }

    public void setFirstMan(String firstMan) {
        this.firstMan = firstMan == null ? null : firstMan.trim();
    }

    public String getInsureMaturity() {
        return insureMaturity;
    }

    public void setInsureMaturity(String insureMaturity) {
        this.insureMaturity = insureMaturity == null ? null : insureMaturity.trim();
    }

    public String getSellServerMane() {
        return sellServerMane;
    }

    public void setSellServerMane(String sellServerMane) {
        this.sellServerMane = sellServerMane == null ? null : sellServerMane.trim();
    }

    public Double getJbwyServerMoney() {
        return jbwyServerMoney;
    }

    public void setJbwyServerMoney(Double jbwyServerMoney) {
        this.jbwyServerMoney = jbwyServerMoney;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage == null ? null : mileage.trim();
    }

    public String getInsureCompany() {
        return insureCompany;
    }

    public void setInsureCompany(String insureCompany) {
        this.insureCompany = insureCompany == null ? null : insureCompany.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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
	
	

	@Override
	public String toString() {
		return "StatementSellJbwy [id=" + id + ", no=" + no + ", insureNo="
				+ insureNo + ", vechoPrice=" + vechoPrice + ", vinNo=" + vinNo
				+ ", vechoUserName=" + vechoUserName + ", deviceSn=" + deviceSn
				+ ", engineNo=" + engineNo + ", deviceType=" + deviceType
				+ ", version=" + version + ", insureDueTime=" + insureDueTime
				+ ", money=" + money + ", insureReportPractice="
				+ insureReportPractice + ", insureStartDate=" + insureStartDate
				+ ", insureEndDate=" + insureEndDate + ", princeAgent="
				+ princeAgent + ", cityAgent=" + cityAgent
				+ ", handInMerchant=" + handInMerchant + ", shopName="
				+ shopName + ", preMerchant=" + preMerchant + ", area=" + area
				+ ", certifiNo=" + certifiNo + ", mobile=" + mobile
				+ ", vechoBrand=" + vechoBrand + ", vechoType=" + vechoType
				+ ", vechoSet=" + vechoSet + ", vechoColor=" + vechoColor
				+ ", firstMan=" + firstMan + ", insureMaturity="
				+ insureMaturity + ", sellServerMane=" + sellServerMane
				+ ", jbwyServerMoney=" + jbwyServerMoney + ", mileage="
				+ mileage + ", insureCompany=" + insureCompany + ", operator="
				+ operator + ", workOrder=" + workOrder + ", createdBy="
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