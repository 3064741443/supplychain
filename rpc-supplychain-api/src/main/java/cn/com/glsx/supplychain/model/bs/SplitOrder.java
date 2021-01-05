package cn.com.glsx.supplychain.model.bs;

import java.util.Date;

/**
 * @ClassName SplitOrder
 * @Description TODO
 * @Author yangbin
 * @Date 2019/8/13 10:33
 * @Version 1.0
 */
public class SplitOrder {
	
	private Long orderId;
	
	private String orderNumber;
	
	private Date orderTime;
	
	private Date hopeTime;
	
	private String merchantCode;
	
	private Integer totalOrder;
	
	private Integer totalCheck;
	
	private Double totalAmount;
	
	private Integer status;
	
	private String remarks;
	
	private String orderCreatedBy;
	
	private Date orderCreatedDate;
	
	private String orderUpdatedBy;
	
	private Date orderUpdatedDate;
	
	private String orderDeletedFlag;
	
	
	
	private Long detailId;
	
	private String merchantOrderNumber;
	
	private String productCode;
	
	private Integer orderQuantity;
	
	private Integer checkQuantity;
	
	private Integer acceptQuantity;
	
	private String dispatchOrderNumber;
	
	private String detailCreatedBy;
	
	private Date detailCreatedDate;
	
	private String detailUpdatedBy;
	
	private Date detailUpdatedDate;
	
	private String detailDeletedFlag;
	
	private String productRemarks;
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getOrderCreatedBy() {
		return orderCreatedBy;
	}
	
	public void setOrderCreatedBy(String orderCreatedBy) {
		this.orderCreatedBy = orderCreatedBy;
	}
	
	public Date getOrderCreatedDate() {
		return orderCreatedDate;
	}
	
	public void setOrderCreatedDate(Date orderCreatedDate) {
		this.orderCreatedDate = orderCreatedDate;
	}
	
	public String getOrderUpdatedBy() {
		return orderUpdatedBy;
	}
	
	public void setOrderUpdatedBy(String orderUpdatedBy) {
		this.orderUpdatedBy = orderUpdatedBy;
	}
	
	public Date getOrderUpdatedDate() {
		return orderUpdatedDate;
	}
	
	public void setOrderUpdatedDate(Date orderUpdatedDate) {
		this.orderUpdatedDate = orderUpdatedDate;
	}
	
	public String getOrderDeletedFlag() {
		return orderDeletedFlag;
	}
	
	public void setOrderDeletedFlag(String orderDeletedFlag) {
		this.orderDeletedFlag = orderDeletedFlag;
	}
	
	public Long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	
	public String getMerchantOrderNumber() {
		return merchantOrderNumber;
	}
	
	public void setMerchantOrderNumber(String merchantOrderNumber) {
		this.merchantOrderNumber = merchantOrderNumber;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	public Integer getCheckQuantity() {
		return checkQuantity;
	}
	
	public void setCheckQuantity(Integer checkQuantity) {
		this.checkQuantity = checkQuantity;
	}
	
	public Integer getAcceptQuantity() {
		return acceptQuantity;
	}
	
	public void setAcceptQuantity(Integer acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}
	
	public String getDispatchOrderNumber() {
		return dispatchOrderNumber;
	}
	
	public void setDispatchOrderNumber(String dispatchOrderNumber) {
		this.dispatchOrderNumber = dispatchOrderNumber;
	}
	
	public String getDetailCreatedBy() {
		return detailCreatedBy;
	}
	
	public void setDetailCreatedBy(String detailCreatedBy) {
		this.detailCreatedBy = detailCreatedBy;
	}
	
	public Date getDetailCreatedDate() {
		return detailCreatedDate;
	}
	
	public void setDetailCreatedDate(Date detailCreatedDate) {
		this.detailCreatedDate = detailCreatedDate;
	}
	
	public String getDetailUpdatedBy() {
		return detailUpdatedBy;
	}
	
	public void setDetailUpdatedBy(String detailUpdatedBy) {
		this.detailUpdatedBy = detailUpdatedBy;
	}
	
	public Date getDetailUpdatedDate() {
		return detailUpdatedDate;
	}
	
	public void setDetailUpdatedDate(Date detailUpdatedDate) {
		this.detailUpdatedDate = detailUpdatedDate;
	}
	
	public String getDetailDeletedFlag() {
		return detailDeletedFlag;
	}
	
	public void setDetailDeletedFlag(String detailDeletedFlag) {
		this.detailDeletedFlag = detailDeletedFlag;
	}
	
	public String getProductRemarks() {
		return productRemarks;
	}
	
	public void setProductRemarks(String productRemarks) {
		this.productRemarks = productRemarks;
	}
	
	@Override
	public String toString() {
		return "SplitOrder{" +
				"orderId=" + orderId +
				", orderNumber='" + orderNumber + '\'' +
				", orderTime=" + orderTime +
				", hopeTime=" + hopeTime +
				", merchantCode='" + merchantCode + '\'' +
				", totalOrder=" + totalOrder +
				", totalCheck=" + totalCheck +
				", totalAmount=" + totalAmount +
				", status=" + status +
				", remarks='" + remarks + '\'' +
				", orderCreatedBy='" + orderCreatedBy + '\'' +
				", orderCreatedDate=" + orderCreatedDate +
				", orderUpdatedBy='" + orderUpdatedBy + '\'' +
				", orderUpdatedDate=" + orderUpdatedDate +
				", orderDeletedFlag='" + orderDeletedFlag + '\'' +
				", detailId=" + detailId +
				", merchantOrderNumber='" + merchantOrderNumber + '\'' +
				", productCode='" + productCode + '\'' +
				", orderQuantity=" + orderQuantity +
				", checkQuantity=" + checkQuantity +
				", acceptQuantity=" + acceptQuantity +
				", dispatchOrderNumber='" + dispatchOrderNumber + '\'' +
				", detailCreatedBy='" + detailCreatedBy + '\'' +
				", detailCreatedDate=" + detailCreatedDate +
				", detailUpdatedBy='" + detailUpdatedBy + '\'' +
				", detailUpdatedDate=" + detailUpdatedDate +
				", detailDeletedFlag='" + detailDeletedFlag + '\'' +
				", productRemarks='" + productRemarks + '\'' +
				'}';
	}
}
