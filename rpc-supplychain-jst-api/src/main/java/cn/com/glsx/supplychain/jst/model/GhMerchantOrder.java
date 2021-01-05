package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "gh_merchant_order")
public class GhMerchantOrder {
	@Id
    private Integer id;

    private String ghMerchantOrderCode;

    private Date orderTime;

    private String merchantCode;
    
    private String merchantName;

    private String productConfigCode;

    private Integer parentBrandId;

    private Integer subBrandId;

    private Integer audiId;

    private String motorcycle;

    private Integer categoryId;

    private String spaPurchaseCode;

    private String spaProductCode;

    private String spaProductName;

    private String glsxProductCode;

    private String glsxProductName;

    private Integer total;

    private String remark;

    private Byte status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private Integer addressId;
    
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

    @Transient
    private String dtoStatus;

    @Transient
    private  BsAddress bsAddress;

    /**
     * 发货数量
     */
    @Transient
    private Integer shipmentsQuantity;

    /**
     * 催单次数
     */
    @Transient
    private Integer reminderTotal;

    public Integer getReminderTotal() {
        return reminderTotal;
    }

    public void setReminderTotal(Integer reminderTotal) {
        this.reminderTotal = reminderTotal;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
    }

    public String getDtoStatus() {
		return dtoStatus;
	}

	public void setDtoStatus(String dtoStatus) {
		this.dtoStatus = dtoStatus;
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGhMerchantOrderCode() {
        return ghMerchantOrderCode;
    }

    public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
        this.ghMerchantOrderCode = ghMerchantOrderCode == null ? null : ghMerchantOrderCode.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getProductConfigCode() {
		return productConfigCode;
	}

	public void setProductConfigCode(String productConfigCode) {
		this.productConfigCode = productConfigCode;
	}

	public Integer getParentBrandId() {
        return parentBrandId;
    }

    public void setParentBrandId(Integer parentBrandId) {
        this.parentBrandId = parentBrandId;
    }

    public Integer getSubBrandId() {
        return subBrandId;
    }

    public void setSubBrandId(Integer subBrandId) {
        this.subBrandId = subBrandId;
    }

    public Integer getAudiId() {
        return audiId;
    }

    public void setAudiId(Integer audiId) {
        this.audiId = audiId;
    }

    public String getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(String motorcycle) {
        this.motorcycle = motorcycle == null ? null : motorcycle.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpaPurchaseCode() {
        return spaPurchaseCode;
    }

    public void setSpaPurchaseCode(String spaPurchaseCode) {
        this.spaPurchaseCode = spaPurchaseCode == null ? null : spaPurchaseCode.trim();
    }

    public String getSpaProductCode() {
        return spaProductCode;
    }

    public void setSpaProductCode(String spaProductCode) {
        this.spaProductCode = spaProductCode == null ? null : spaProductCode.trim();
    }

    public String getSpaProductName() {
        return spaProductName;
    }

    public void setSpaProductName(String spaProductName) {
        this.spaProductName = spaProductName == null ? null : spaProductName.trim();
    }

    public String getGlsxProductCode() {
        return glsxProductCode;
    }

    public void setGlsxProductCode(String glsxProductCode) {
        this.glsxProductCode = glsxProductCode == null ? null : glsxProductCode.trim();
    }

    public String getGlsxProductName() {
        return glsxProductName;
    }

    public void setGlsxProductName(String glsxProductName) {
        this.glsxProductName = glsxProductName == null ? null : glsxProductName.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

    public BsAddress getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(BsAddress bsAddress) {
        this.bsAddress = bsAddress;
    }

    @Override
    public String toString() {
        return "GhMerchantOrder{" +
                "id=" + id +
                ", ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
                ", orderTime=" + orderTime +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", productConfigCode='" + productConfigCode + '\'' +
                ", parentBrandId=" + parentBrandId +
                ", subBrandId=" + subBrandId +
                ", audiId=" + audiId +
                ", motorcycle='" + motorcycle + '\'' +
                ", categoryId=" + categoryId +
                ", spaPurchaseCode='" + spaPurchaseCode + '\'' +
                ", spaProductCode='" + spaProductCode + '\'' +
                ", spaProductName='" + spaProductName + '\'' +
                ", glsxProductCode='" + glsxProductCode + '\'' +
                ", glsxProductName='" + glsxProductName + '\'' +
                ", total=" + total +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", addressId=" + addressId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", dtoStatus='" + dtoStatus + '\'' +
                ", bsAddress=" + bsAddress +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", reminderTotal=" + reminderTotal +
                '}';
    }
}