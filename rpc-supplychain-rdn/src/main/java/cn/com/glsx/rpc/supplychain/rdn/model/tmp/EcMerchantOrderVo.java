package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import java.util.Date;

import javax.persistence.Transient;

public class EcMerchantOrderVo {
	/**
     * 商户渠道
     */
    @Transient
    private String channel;
    /**
     * 下单商户
     */
    @Transient
    private String merchantName;
    /**
     * 商户CODE
     */
    @Transient
    private String merchantCode;
    /**
     * 订单号
     */
    @Transient
    private String orderNumber;
    /**
     * 下单产品
     */
    @Transient
    private String productName;
    /**
     * 产品编号
     */
    @Transient
    private String productCode;
    /**
     * 产品类型
     */
    @Transient
    private String deviceType;
    /**
     * 单价
     */
    @Transient
    private Double price;
    /**
     * 下单数
     */
    @Transient
    private Integer orderQuantity;
    /**
     * 审核数量
     */
    @Transient
    private Integer checkQuantity;
    /**
     * 发货单
     */
    @Transient
    private String dispatchOrderNumber;
    /**
     * 已发数量
     */
    @Transient
    private Integer alreadyShipmentQuantity;
    /**
     * 发货时间
     */
    @Transient
    private String shipmentTime;
    /**
     * 发货数量
     */
    @Transient
    private String shipmentQuantity;
    /**
     * 签收数量
     */
    @Transient
    private Integer signQuantity;
    /**
     * 欠数
     */
    @Transient
    private Integer oweQuantity;
    /**
     * 订单总额
     */
    @Transient
    private Double totalAmount;
    /**
     * 下单日期
     */
    @Transient
    private String orderTime;
    /**
     * 产品备注
     */
    @Transient
    private String productRemarks;
    
    @Transient
    private String orderRemark;
    
    @Transient
    private String checkRemark;
    /**
     * 审核人
     */
    @Transient
    private String checkBy;
    /**
     * 审核时间
     */
    @Transient
    private String checkTime;
    /**
     * 状态
     */
    @Transient
    private String status;

    /**
     * 收件人
     */
    @Transient
    private String Addressee;
    /**
     * 联系方式
     */
    @Transient
    private String mobile;
    /**
     * 地址
     */
    @Transient
    private String addressDetail;

    @Transient
    private String createdBy;

    @Transient
    private Date createdDate;

    @Transient
    private String updatedBy;

    @Transient
    private Date updatedDate;
    
    @Transient
    private String materialCode;
    
    @Transient
    private String materialName;
    
    @Transient
    private String logisticsDesc;
    
    @Transient
    private String dealerUserName;
    
    

	public String getDealerUserName() {
		return dealerUserName;
	}

	public void setDealerUserName(String dealerUserName) {
		this.dealerUserName = dealerUserName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getDispatchOrderNumber() {
		return dispatchOrderNumber;
	}

	public void setDispatchOrderNumber(String dispatchOrderNumber) {
		this.dispatchOrderNumber = dispatchOrderNumber;
	}

	public Integer getAlreadyShipmentQuantity() {
		return alreadyShipmentQuantity;
	}

	public void setAlreadyShipmentQuantity(Integer alreadyShipmentQuantity) {
		this.alreadyShipmentQuantity = alreadyShipmentQuantity;
	}

	public String getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public String getShipmentQuantity() {
		return shipmentQuantity;
	}

	public void setShipmentQuantity(String shipmentQuantity) {
		this.shipmentQuantity = shipmentQuantity;
	}

	public Integer getSignQuantity() {
		return signQuantity;
	}

	public void setSignQuantity(Integer signQuantity) {
		this.signQuantity = signQuantity;
	}

	public Integer getOweQuantity() {
		return oweQuantity;
	}

	public void setOweQuantity(Integer oweQuantity) {
		this.oweQuantity = oweQuantity;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getProductRemarks() {
		return productRemarks;
	}

	public void setProductRemarks(String productRemarks) {
		this.productRemarks = productRemarks;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressee() {
		return Addressee;
	}

	public void setAddressee(String addressee) {
		Addressee = addressee;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
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

	public String getLogisticsDesc() {
		return logisticsDesc;
	}

	public void setLogisticsDesc(String logisticsDesc) {
		this.logisticsDesc = logisticsDesc;
	}
	
	

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	@Override
	public String toString() {
		return "EcMerchantOrderVo [channel=" + channel + ", merchantName="
				+ merchantName + ", merchantCode=" + merchantCode
				+ ", orderNumber=" + orderNumber + ", productName="
				+ productName + ", productCode=" + productCode
				+ ", deviceType=" + deviceType + ", price=" + price
				+ ", orderQuantity=" + orderQuantity + ", checkQuantity="
				+ checkQuantity + ", dispatchOrderNumber="
				+ dispatchOrderNumber + ", alreadyShipmentQuantity="
				+ alreadyShipmentQuantity + ", shipmentTime=" + shipmentTime
				+ ", shipmentQuantity=" + shipmentQuantity + ", signQuantity="
				+ signQuantity + ", oweQuantity=" + oweQuantity
				+ ", totalAmount=" + totalAmount + ", orderTime=" + orderTime
				+ ", productRemarks=" + productRemarks + ", checkBy=" + checkBy
				+ ", checkTime=" + checkTime + ", status=" + status
				+ ", Addressee=" + Addressee + ", mobile=" + mobile
				+ ", addressDetail=" + addressDetail + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", logisticsDesc=" + logisticsDesc + "]";
	}
    
    
}
