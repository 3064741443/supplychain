package cn.com.glsx.supplychain.jxc.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Table(name = "bs_merchant_order")
public class JXCMTBsMerchantOrder implements Serializable{
	@Id
    private Long id;

    private String orderNumber;

    private Date orderTime;

    private Date hopeTime;

    private String merchantCode;
    
    private Integer productTotal;

    private Integer totalOrder;

    private Integer totalCheck;

    private Double totalAmount;

    private Byte status;

    private String remarks;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String checkRemark;

    private Byte signStatus;
    
    private String rebackReason;
    
    private String finishReason;
    
    private String recallReason;
    
    private String modelDevice;
    
    private String urlDispatchBills;
    @Transient
    private Date orderTimeStart;
    @Transient
    private Date orderTimeEnd;
    @Transient
    private String moOrderCode;
    @Transient
    private String productName;
    @Transient
    private String productCode;
    @Transient
    private String materialCode;
    @Transient
    private String materialName;
    @Transient
    private String merchantName;
    @Transient
    private Byte merchantChannelId;
    @Transient
    private Byte productTypeId;
    @Transient
    private Integer pageStart;
    @Transient
    private Integer pageSize;
    @Transient
    private List<String> listOrderNums;
    @Transient
    private Date checkTimeStart;
    @Transient
    private Date checkTimeEnd;

    public Date getCheckTimeStart() {
        return checkTimeStart;
    }

    public void setCheckTimeStart(Date checkTimeStart) {
        this.checkTimeStart = checkTimeStart;
    }

    public Date getCheckTimeEnd() {
        return checkTimeEnd;
    }

    public void setCheckTimeEnd(Date checkTimeEnd) {
        this.checkTimeEnd = checkTimeEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getHopeTime() {
        return hopeTime;
    }

    public void setHopeTime(Date hopeTime) {
        this.hopeTime = hopeTime;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(Integer totalCheck) {
        this.totalCheck = totalCheck;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }

    public Byte getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Byte signStatus) {
        this.signStatus = signStatus;
    }

	public String getRebackReason() {
		return rebackReason;
	}

	public void setRebackReason(String rebackReason) {
		this.rebackReason = rebackReason;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	public Integer getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(Integer productTotal) {
		this.productTotal = productTotal;
	}

	public String getModelDevice() {
		return modelDevice;
	}

	public void setModelDevice(String modelDevice) {
		this.modelDevice = modelDevice;
	}

	public String getUrlDispatchBills() {
		return urlDispatchBills;
	}

	public void setUrlDispatchBills(String urlDispatchBills) {
		this.urlDispatchBills = urlDispatchBills;
	}

	public String getRecallReason() {
		return recallReason;
	}

	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
	}

	public Date getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Date orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public String getMoOrderCode() {
		return moOrderCode;
	}

	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Byte getMerchantChannelId() {
		return merchantChannelId;
	}

	public void setMerchantChannelId(Byte merchantChannelId) {
		this.merchantChannelId = merchantChannelId;
	}

	public Byte getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Byte productTypeId) {
		this.productTypeId = productTypeId;
	}

	public List<String> getListOrderNums() {
		return listOrderNums;
	}

	public void setListOrderNums(List<String> listOrderNums) {
		this.listOrderNums = listOrderNums;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
    
	
    
}