package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;
@Table(name = "bs_merchant_order")
public class MerchantOrder{
	@Id
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
    private String checkRemark;
    private Byte signStatus;
    private String rebackReason;
    private String finishReason;  
    private String recallReason;
    private String modelDevice;
    private String urlDispatchBills;
    @Transient
    private Date startDate;
    @Transient
    private Date endDate;
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
    private Byte productTypeId;
    /**
     *  商户渠道
     */
    @Transient
    private Byte channel;
    /**
     * 产品类型
     */
    @Transient
    private  String type;
    @Transient
    private List<String> listOrderNums;
    @Transient
    private String dispatchOrderNumber;
    @Transient
    private Date checkStartDate;
    @Transient
    private Date checkEndDate;
    @Transient
    private Date hopeStartDate;
    @Transient
    private Date hopeEndDate;
    @Transient
    private Byte metrialType;
    //签收单图片
    @Transient
    private String jsonSignUrl;  
    @Transient
    private String signNumberCode;
    /**
     * 含税单价
     */
    @Transient
    private Double amount;
    /**
     * 产品编号
     */
    @Transient
    private String code;
    
    /**
     * 订单详情
     */
    @Transient
    private List<MerchantOrderDetail> merchantOrderDetailList;

    /**
     * 物流
     */
    @Transient
    private Logistics logistics;

    /**
     * 物流List
     */
    @Transient
    private List<Logistics> logisticsList;
   
    /**
     *  产品信息
     */
    @Transient
    private Product productInfo;
    /**
     *  商户订单详情信息
     */
    @Transient
    private MerchantOrderDetail merchantOrderDetailInfo;
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
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
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
	public String getRecallReason() {
		return recallReason;
	}
	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
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
	public Byte getChannel() {
		return channel;
	}
	public void setChannel(Byte channel) {
		this.channel = channel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getListOrderNums() {
		return listOrderNums;
	}
	public void setListOrderNums(List<String> listOrderNums) {
		this.listOrderNums = listOrderNums;
	}
	public String getDispatchOrderNumber() {
		return dispatchOrderNumber;
	}
	public void setDispatchOrderNumber(String dispatchOrderNumber) {
		this.dispatchOrderNumber = dispatchOrderNumber;
	}
	public Date getCheckStartDate() {
		return checkStartDate;
	}
	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}
	public Date getCheckEndDate() {
		return checkEndDate;
	}
	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}
	public Date getHopeStartDate() {
		return hopeStartDate;
	}
	public void setHopeStartDate(Date hopeStartDate) {
		this.hopeStartDate = hopeStartDate;
	}
	public Date getHopeEndDate() {
		return hopeEndDate;
	}
	public void setHopeEndDate(Date hopeEndDate) {
		this.hopeEndDate = hopeEndDate;
	}
	public Byte getMetrialType() {
		return metrialType;
	}
	public void setMetrialType(Byte metrialType) {
		this.metrialType = metrialType;
	}
	public String getJsonSignUrl() {
		return jsonSignUrl;
	}
	public void setJsonSignUrl(String jsonSignUrl) {
		this.jsonSignUrl = jsonSignUrl;
	}
	public String getSignNumberCode() {
		return signNumberCode;
	}
	public void setSignNumberCode(String signNumberCode) {
		this.signNumberCode = signNumberCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<MerchantOrderDetail> getMerchantOrderDetailList() {
		return merchantOrderDetailList;
	}
	public void setMerchantOrderDetailList(
			List<MerchantOrderDetail> merchantOrderDetailList) {
		this.merchantOrderDetailList = merchantOrderDetailList;
	}
	public Logistics getLogistics() {
		return logistics;
	}
	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}
	public List<Logistics> getLogisticsList() {
		return logisticsList;
	}
	public void setLogisticsList(List<Logistics> logisticsList) {
		this.logisticsList = logisticsList;
	}
	public Product getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(Product productInfo) {
		this.productInfo = productInfo;
	}
	public MerchantOrderDetail getMerchantOrderDetailInfo() {
		return merchantOrderDetailInfo;
	}
	public void setMerchantOrderDetailInfo(
			MerchantOrderDetail merchantOrderDetailInfo) {
		this.merchantOrderDetailInfo = merchantOrderDetailInfo;
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
	public Byte getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Byte productTypeId) {
		this.productTypeId = productTypeId;
	}
	@Override
	public String toString() {
		return "MerchantOrder [id=" + id + ", orderNumber=" + orderNumber
				+ ", orderTime=" + orderTime + ", hopeTime=" + hopeTime
				+ ", merchantCode=" + merchantCode + ", totalOrder="
				+ totalOrder + ", totalCheck=" + totalCheck + ", totalAmount="
				+ totalAmount + ", status=" + status + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + ", checkRemark="
				+ checkRemark + ", signStatus=" + signStatus
				+ ", rebackReason=" + rebackReason + ", finishReason="
				+ finishReason + ", recallReason=" + recallReason
				+ ", modelDevice=" + modelDevice + ", urlDispatchBills="
				+ urlDispatchBills + ", startDate=" + startDate + ", endDate="
				+ endDate + ", orderTimeStart=" + orderTimeStart
				+ ", orderTimeEnd=" + orderTimeEnd + ", moOrderCode="
				+ moOrderCode + ", productName=" + productName
				+ ", productCode=" + productCode + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", merchantName=" + merchantName + ", channel=" + channel
				+ ", type=" + type + ", listOrderNums=" + listOrderNums
				+ ", dispatchOrderNumber=" + dispatchOrderNumber
				+ ", checkStartDate=" + checkStartDate + ", checkEndDate="
				+ checkEndDate + ", hopeStartDate=" + hopeStartDate
				+ ", hopeEndDate=" + hopeEndDate + ", metrialType="
				+ metrialType + ", jsonSignUrl=" + jsonSignUrl
				+ ", signNumberCode=" + signNumberCode + ", amount=" + amount
				+ ", code=" + code + ", merchantOrderDetailList="
				+ merchantOrderDetailList + ", logistics=" + logistics
				+ ", logisticsList=" + logisticsList + ", productInfo="
				+ productInfo + ", merchantOrderDetailInfo="
				+ merchantOrderDetailInfo + "]";
	}
	
	
	
}