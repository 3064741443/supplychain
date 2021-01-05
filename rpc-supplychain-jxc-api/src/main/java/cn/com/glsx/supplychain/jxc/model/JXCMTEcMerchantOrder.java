package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "ec_merchant_order")
public class JXCMTEcMerchantOrder implements Serializable{
	@Id
    private Long id;

    private String channel;

    private String merchantCode;

    private String merchantName;

    private String orderNumber;

    private String productName;

    private String productCode;

    private String deviceType;

    private Double price;

    private Integer orderQuantity;

    private Integer checkQuantity;

    private String dispatchOrderNumber;

    private Integer alreadyShipmentQuantity;

    private String shipmentTime;

    private String shipmentQuantity;

    private Integer signQuantity;

    private Integer oweQuantity;

    private Double totalAmount;

    private String orderTime;

    private String productRemarks;

    private String checkBy;

    private String checkTime;

    private String status;

    private String addressee;

    private String mobile;

    private String addressDetail;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String materialCode;

    private String materialName;

    private String logisticsDesc;

    private String orderRemark;

    private String checkRemark;
    
    private String signStatus;
    
    private String orderMaterialCode;
    
    private String orderMaterialName;
    
    private String rebackReason;
    
    private String recallReason;
    
    private String finishReason;
    
    private Integer productTotal; 

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
        this.channel = channel == null ? null : channel.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
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
        this.dispatchOrderNumber = dispatchOrderNumber == null ? null : dispatchOrderNumber.trim();
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
        this.shipmentTime = shipmentTime == null ? null : shipmentTime.trim();
    }

    public String getShipmentQuantity() {
        return shipmentQuantity;
    }

    public void setShipmentQuantity(String shipmentQuantity) {
        this.shipmentQuantity = shipmentQuantity == null ? null : shipmentQuantity.trim();
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
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public String getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        this.productRemarks = productRemarks == null ? null : productRemarks.trim();
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy == null ? null : checkBy.trim();
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime == null ? null : checkTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee == null ? null : addressee.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
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

    public String getLogisticsDesc() {
        return logisticsDesc;
    }

    public void setLogisticsDesc(String logisticsDesc) {
        this.logisticsDesc = logisticsDesc == null ? null : logisticsDesc.trim();
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}

	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}

	public String getOrderMaterialName() {
		return orderMaterialName;
	}

	public void setOrderMaterialName(String orderMaterialName) {
		this.orderMaterialName = orderMaterialName;
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

	public String getRecallReason() {
		return recallReason;
	}

	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
	}
    
    
}