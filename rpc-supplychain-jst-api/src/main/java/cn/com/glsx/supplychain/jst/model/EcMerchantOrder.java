package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName EcMerchantOrder
 * @Author admin
 * @Param
 * @Date 2019/11/8 15:58
 * @Version
 **/
@Table(name = "ec_merchant_order")
public class EcMerchantOrder implements Serializable {
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 商户渠道
     */
    private String channel;
    /**
     * 商户编号
     */
    private String merchantCode;
    /**
     * 下单商户
     */
    private String merchantName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 下单产品
     */
    private String productName;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品类型
     */
    private String deviceType;
    /**
     * 单价
     */
    private Double price;
    /**
     * 下单数
     */
    private Integer orderQuantity;
    /**
     * 审核数量
     */
    private Integer checkQuantity;
    /**
     * 发货单
     */
    private String dispatchOrderNumber;
    /**
     * 已发数量
     */
    private Integer alreadyShipmentQuantity;
    /**
     * 发货时间
     */
    private String shipmentTime;
    /**
     * 发货数量
     */
    private String shipmentQuantity;
    /**
     * 签收数量
     */
    private Integer signQuantity;
    /**
     * 欠数
     */
    private Integer oweQuantity;
    /**
     * 订单总额
     */
    private Double totalAmount;
    /**
     * 下单日期
     */
    private String orderTime;
    /**
     * 产品备注
     */
    private String productRemarks;
    /**
     * 审核人
     */
    private String checkBy;
    /**
     * 审核时间
     */
    private String checkTime;
    /**
     * 状态
     */
    private String status;

    private String orderRemark;

    /**
     * 收件人
     */
    private String Addressee;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 地址
     */
    private String addressDetail;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String materialCode;
    
    private String materialName;

    private String logisticsDesc;

    private Integer productTotal;

    private String signStatus;
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
     * 审核开始时间
     */
    @Transient
    private Date checkStartDate;

    /**
     * 审核结束时间
     */
    @Transient
    private Date checkEndDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
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

    public Integer getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    @Override
    public String toString() {
        return "EcMerchantOrder{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", price=" + price +
                ", orderQuantity=" + orderQuantity +
                ", checkQuantity=" + checkQuantity +
                ", dispatchOrderNumber='" + dispatchOrderNumber + '\'' +
                ", alreadyShipmentQuantity=" + alreadyShipmentQuantity +
                ", shipmentTime='" + shipmentTime + '\'' +
                ", shipmentQuantity='" + shipmentQuantity + '\'' +
                ", signQuantity=" + signQuantity +
                ", oweQuantity=" + oweQuantity +
                ", totalAmount=" + totalAmount +
                ", orderTime='" + orderTime + '\'' +
                ", productRemarks='" + productRemarks + '\'' +
                ", checkBy='" + checkBy + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", status='" + status + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", Addressee='" + Addressee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", logisticsDesc='" + logisticsDesc + '\'' +
                ", productTotal=" + productTotal +
                ", signStatus='" + signStatus + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", checkStartDate=" + checkStartDate +
                ", checkEndDate=" + checkEndDate +
                '}';
    }
}
