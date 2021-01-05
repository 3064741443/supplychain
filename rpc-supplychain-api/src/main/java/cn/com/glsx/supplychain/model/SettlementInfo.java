package cn.com.glsx.supplychain.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Settlement
 * @Author admin
 * @Param
 * @Date 2019/7/1 15:28
 * @Version
 **/
@Table(name = "bs_settlement_info")
public class SettlementInfo implements Serializable {

    private Integer id;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 客户订单号
     */
    private String customerOrderNum;
    /**
     * 发货单
     */
    private String deliveryOrderNum;
    /**
     * 汇总ID
     */
    private Long salesId;

    /**
     * 物流ID
     */
    private Long logisticsId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerOrderNum() {
        return customerOrderNum;
    }

    public void setCustomerOrderNum(String customerOrderNum) {
        this.customerOrderNum = customerOrderNum;
    }

    public String getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(String deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
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

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    @Override
    public String toString() {
        return "SettlementInfo{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", customerOrderNum='" + customerOrderNum + '\'' +
                ", deliveryOrderNum='" + deliveryOrderNum + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}
