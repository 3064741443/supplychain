package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "bs_after_sale_order_detail")
public class AfterSaleOrderDetail implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 售后订单
     */
    private String afterSaleOrderNumber;
    /**
     * SN
     */
    private String sn;
    /**
     * 售后SN状态(1:申请售后SN,2:实际签收SN)
     */
    private Byte status;
    /**
     * 物流ID
     */
    private Long logisticsId;
    /**
     * 设备售后原因
     */
    private String  deviceAfterReason;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfterSaleOrderNumber() {
        return afterSaleOrderNumber;
    }

    public void setAfterSaleOrderNumber(String afterSaleOrderNumber) {
        this.afterSaleOrderNumber = afterSaleOrderNumber;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getDeviceAfterReason() {
        return deviceAfterReason;
    }

    public void setDeviceAfterReason(String deviceAfterReason) {
        this.deviceAfterReason = deviceAfterReason;
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
}