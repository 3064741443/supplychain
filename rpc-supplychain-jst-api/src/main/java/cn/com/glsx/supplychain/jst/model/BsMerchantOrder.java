package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

import javax.persistence.Transient;

public class BsMerchantOrder {
    private Long id;

    private String orderNumber;

    private Date orderTime;

    private Date hopeTime;

    private String merchantCode;

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
    
    //发货单
    @Transient
    private String dispatchOrderCode;
    //物料编码
    @Transient
    private String bsMaterialCode;
    //物料名称
    @Transient
    private String bsMaterialName;
    //下单时间
    @Transient
    private String questOrderTime;
    //已发货数
    @Transient
    private Integer sendCount;
    //未发货数
    @Transient
    private Integer waitCount;
    //am产品编码
    @Transient
    private String bsProductCode;
    //am产品名称
    @Transient
    private String bsProductName;
    //am服务类型
    @Transient
    private Byte bsServiceType;
    //am套餐
    @Transient
    private String bsPackageOne;
    //am服务期限
    @Transient
    private String bsServiceTime;
    //am产品单价
    @Transient
    private Double bsPrice;
    //地址信息
    @Transient
    private BsAddress bsAddress;
    //物流信息
    @Transient
    private BsLogistics bsLogistics;
    //起始页
    @Transient
    private Integer pageStart;
    //页面大小
    @Transient
    private Integer pageSize;

	private Byte signStatus;

	private Integer productTotal;

	private String rebackReason;
    
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
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
		this.orderNumber = orderNumber;
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
		this.merchantCode = merchantCode;
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
		this.remarks = remarks;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
		this.updatedBy = updatedBy;
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
		this.deletedFlag = deletedFlag;
	}
	public String getQuestOrderTime() {
		return questOrderTime;
	}
	public void setQuestOrderTime(String questOrderTime) {
		this.questOrderTime = questOrderTime;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}
	public String getBsProductCode() {
		return bsProductCode;
	}
	public void setBsProductCode(String bsProductCode) {
		this.bsProductCode = bsProductCode;
	}
	public String getBsProductName() {
		return bsProductName;
	}
	public void setBsProductName(String bsProductName) {
		this.bsProductName = bsProductName;
	}
	public Byte getBsServiceType() {
		return bsServiceType;
	}
	public void setBsServiceType(Byte bsServiceType) {
		this.bsServiceType = bsServiceType;
	}
	public String getBsPackageOne() {
		return bsPackageOne;
	}
	public void setBsPackageOne(String bsPackageOne) {
		this.bsPackageOne = bsPackageOne;
	}
	public String getBsServiceTime() {
		return bsServiceTime;
	}
	public void setBsServiceTime(String bsServiceTime) {
		this.bsServiceTime = bsServiceTime;
	}
	public Double getBsprice() {
		return bsPrice;
	}
	public void setBsprice(Double bsprice) {
		this.bsPrice = bsprice;
	}
	public BsAddress getBsAddress() {
		return bsAddress;
	}
	public void setBsAddress(BsAddress bsAddress) {
		this.bsAddress = bsAddress;
	}
	public BsLogistics getBsLogistics() {
		return bsLogistics;
	}
	public void setBsLogistics(BsLogistics bsLogistics) {
		this.bsLogistics = bsLogistics;
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
	public String getBsMaterialCode() {
		return bsMaterialCode;
	}
	public void setBsMaterialCode(String bsMaterialCode) {
		this.bsMaterialCode = bsMaterialCode;
	}
	public String getBsMaterialName() {
		return bsMaterialName;
	}
	public void setBsMaterialName(String bsMaterialName) {
		this.bsMaterialName = bsMaterialName;
	}

	public Double getBsPrice() {
		return bsPrice;
	}

	public void setBsPrice(Double bsPrice) {
		this.bsPrice = bsPrice;
	}

	public Byte getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Byte signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(Integer productTotal) {
		this.productTotal = productTotal;
	}

	public String getRebackReason() {
		return rebackReason;
	}

	public void setRebackReason(String rebackReason) {
		this.rebackReason = rebackReason;
	}
}