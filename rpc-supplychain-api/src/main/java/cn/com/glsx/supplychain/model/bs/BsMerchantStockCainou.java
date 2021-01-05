package cn.com.glsx.supplychain.model.bs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
* 商户库存调入调出明细表
*/
@SuppressWarnings("serial")
public class BsMerchantStockCainou implements Serializable{
	/**
	* ID
	*/
    private Integer id;

    /**
	* 物料编号
	*/
    private String materialCode;

    /**
	* 物料名称
	*/
    private String materialName;

    /**
	* 调出商户编号
	*/
    private String fromMerchantCode;

    /**
	* 调出商户名称
	*/
    private String fromMerchantName;

    /**
	* 调入商户编号
	*/
    private String toMerchantCode;

    /**
	* 调入商户名称
	*/
    private String toMerchantName;

    /**
	* 物料关联设备类型
	*/
    private Integer deviceType;

    /**
	* 物料关联设备类型名称
	*/
    private String deviceTypeName;

    /**
	* 调入发起时间
	*/
    private Date deliTime;

    /**
	* 签收时间
	*/
    private Date signTime;

    /**
	* 调入数量
	*/
    private Integer deliNum;

    /**
	* 签收数量
	*/
    private Integer signNum;

    /**
	* 1:待签收 2:已完成
	*/
    private Integer status;

    /**
	* 物流公司
	*/
    private String logisticscpy;

    /**
	* 物流编号
	*/
    private String logisticsno;

    /**
	* 创建人
	*/
    private String createdBy;

    /**
	* 创建时间
	*/
    private Date createdDate;

    /**
	* 修改人
	*/
    private String updatedBy;

    /**
	* 修改时间
	*/
    private Date updatedDate;

    /**
	* 删除标记
	*/
    private String deletedFlag;
    
    /**
     * 开始时间
     */
    @Transient
    private String startDate;

    /**
     * 结束时间
     */
    @Transient
    private String endDate;
    
    /**
     * 签收开始时间
     */
    @Transient
    private String startSignDate;

    /**
     * 签收结束时间
     */
    @Transient
    private String endSignDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFromMerchantCode() {
        return fromMerchantCode;
    }

    public void setFromMerchantCode(String fromMerchantCode) {
        this.fromMerchantCode = fromMerchantCode == null ? null : fromMerchantCode.trim();
    }

    public String getFromMerchantName() {
        return fromMerchantName;
    }

    public void setFromMerchantName(String fromMerchantName) {
        this.fromMerchantName = fromMerchantName == null ? null : fromMerchantName.trim();
    }

    public String getToMerchantCode() {
        return toMerchantCode;
    }

    public void setToMerchantCode(String toMerchantCode) {
        this.toMerchantCode = toMerchantCode == null ? null : toMerchantCode.trim();
    }

    public String getToMerchantName() {
        return toMerchantName;
    }

    public void setToMerchantName(String toMerchantName) {
        this.toMerchantName = toMerchantName == null ? null : toMerchantName.trim();
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

    public Date getDeliTime() {
        return deliTime;
    }

    public void setDeliTime(Date deliTime) {
        this.deliTime = deliTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getDeliNum() {
        return deliNum;
    }

    public void setDeliNum(Integer deliNum) {
        this.deliNum = deliNum;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLogisticscpy() {
        return logisticscpy;
    }

    public void setLogisticscpy(String logisticscpy) {
        this.logisticscpy = logisticscpy == null ? null : logisticscpy.trim();
    }

    public String getLogisticsno() {
        return logisticsno;
    }

    public void setLogisticsno(String logisticsno) {
        this.logisticsno = logisticsno == null ? null : logisticsno.trim();
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartSignDate() {
		return startSignDate;
	}

	public void setStartSignDate(String startSignDate) {
		this.startSignDate = startSignDate;
	}

	public String getEndSignDate() {
		return endSignDate;
	}

	public void setEndSignDate(String endSignDate) {
		this.endSignDate = endSignDate;
	}

	@Override
	public String toString() {
		return "BsMerchantStockCainou [id=" + id + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", fromMerchantCode=" + fromMerchantCode
				+ ", fromMerchantName=" + fromMerchantName
				+ ", toMerchantCode=" + toMerchantCode + ", toMerchantName="
				+ toMerchantName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", deliTime="
				+ deliTime + ", signTime=" + signTime + ", deliNum=" + deliNum
				+ ", signNum=" + signNum + ", status=" + status
				+ ", logisticscpy=" + logisticscpy + ", logisticsno="
				+ logisticsno + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", deletedFlag=" + deletedFlag + "]";
	}
    
    
}