package cn.com.glsx.supplychain.jst.dto;

import cn.com.glsx.supplychain.jst.model.BsAddress;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BsLogisticsDTO extends BaseDTO implements Serializable {

    private Long id;

    private String code;

    //物流单号
    @ApiModelProperty(value = "物流单号")
    private String orderNumber;

    //物流公司
    @ApiModelProperty(value = "物流公司")
    private String company;

    private Byte type;

    private String serviceCode;

    private Long sendId;

    private Long receiveId;

    private String accept;
    //发货数量
    @ApiModelProperty(value = "发货数量")
    private Integer shipmentsQuantity;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;
    //发货时间
    private Date updatedDate;

    private String deletedFlag;

    /**
     * 配送方式：O：线上配送  F:线下配送
     */
    private String shipType;

    /**
     * 广汇订单
     */
    private String ghMerchantOrderCode;
    
    /**
     * 地址信息
     */ 
    private BsAddress bsAddress;

    /**
     * sap产品名称
     */
    @ApiModelProperty(value = "sap产品名称")
    private String spaProductName;

    @ApiModelProperty(value = "发货时间")
    private String sendTime;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSpaProductName() {
        return spaProductName;
    }

    public void setSpaProductName(String spaProductName) {
        this.spaProductName = spaProductName;
    }

    public BsAddress getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(BsAddress bsAddress) {
        this.bsAddress = bsAddress;
    }

    public String getGhMerchantOrderCode() {
        return ghMerchantOrderCode;
    }

    public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
        this.ghMerchantOrderCode = ghMerchantOrderCode;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
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

    @Override
    public String toString() {
        return "BsLogisticsDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", company='" + company + '\'' +
                ", type=" + type +
                ", serviceCode='" + serviceCode + '\'' +
                ", sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", accept='" + accept + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", shipType='" + shipType + '\'' +
                ", ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
                ", bsAddress=" + bsAddress +
                ", spaProductName='" + spaProductName + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
