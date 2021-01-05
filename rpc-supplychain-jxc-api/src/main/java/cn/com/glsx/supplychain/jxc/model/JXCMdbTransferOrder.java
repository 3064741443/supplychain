package cn.com.glsx.supplychain.jxc.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
@Table(name = "mdb_transfer_order")
public class JXCMdbTransferOrder implements Serializable {
    @Id
    private Integer id;
    /**
     * 调拨订单编号
     */
    private String tranOrderCode;

    private String outMerchantCode;

    private String outMerchantName;

    private String inMerchantCode;

    private String inMerchantName;

    private String orderSource;

    private String materialCode;

    private String materialName;

    private String orderStatus;

    private String checkBy;

    private Integer orderTotal;

    private Integer checkTotal;

    private Integer sendTotal;

    private String backRemark;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    @Transient
    private  String merchantName;
    @Transient
    private String transferType;
    @Transient
    private Date startTime;
    @Transient
    private Date endTime;
    @Transient
    private String  inServiceProviderName;

    public String getInServiceProviderName() {
        return inServiceProviderName;
    }

    public void setInServiceProviderName(String inServiceProviderName) {
        this.inServiceProviderName = inServiceProviderName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    public String getOutMerchantCode() {
        return outMerchantCode;
    }

    public void setOutMerchantCode(String outMerchantCode) {
        this.outMerchantCode = outMerchantCode;
    }

    public String getOutMerchantName() {
        return outMerchantName;
    }

    public void setOutMerchantName(String outMerchantName) {
        this.outMerchantName = outMerchantName;
    }

    public String getInMerchantCode() {
        return inMerchantCode;
    }

    public void setInMerchantCode(String inMerchantCode) {
        this.inMerchantCode = inMerchantCode;
    }

    public String getInMerchantName() {
        return inMerchantName;
    }

    public void setInMerchantName(String inMerchantName) {
        this.inMerchantName = inMerchantName;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    public Integer getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(Integer sendTotal) {
        this.sendTotal = sendTotal;
    }

    public String getBackRemark() {
        return backRemark;
    }

    public void setBackRemark(String backRemark) {
        this.backRemark = backRemark;
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
        return "JXCMdbTransferOrder{" +
                "id=" + id +
                ", tranOrderCode='" + tranOrderCode + '\'' +
                ", outMerchantCode='" + outMerchantCode + '\'' +
                ", outMerchantName='" + outMerchantName + '\'' +
                ", inMerchantCode='" + inMerchantCode + '\'' +
                ", inMerchantName='" + inMerchantName + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", checkBy='" + checkBy + '\'' +
                ", orderTotal=" + orderTotal +
                ", checkTotal=" + checkTotal +
                ", sendTotal=" + sendTotal +
                ", backRemark='" + backRemark + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", transferType='" + transferType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                '}';
    }
}
