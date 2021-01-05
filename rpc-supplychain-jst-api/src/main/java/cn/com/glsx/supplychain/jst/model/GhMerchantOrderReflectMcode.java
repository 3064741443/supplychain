package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/8/21 16:01
 */
//@Table(name = "gh_merchant_order_reflect_mcode")
public class GhMerchantOrderReflectMcode implements Serializable {
//    @Id
    private Integer id;

    /**
     * 广汇订单编码
     */
    private String ghMerchantOrderCode;

    /**
     * 商户订单号
     */
    private String merchantOrder;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 修改时间
     */
    private Date updatedDate;

    /**
     * 删除标记
     */
    private String deletedFlag;

    @Transient
    private  Integer bsStatus;

    @Transient
    private  Integer ghStatus;

    public Integer getBsStatus() {
        return bsStatus;
    }

    public void setBsStatus(Integer bsStatus) {
        this.bsStatus = bsStatus;
    }

    public Integer getGhStatus() {
        return ghStatus;
    }

    public void setGhStatus(Integer ghStatus) {
        this.ghStatus = ghStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGhMerchantOrderCode() {
        return ghMerchantOrderCode;
    }

    public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
        this.ghMerchantOrderCode = ghMerchantOrderCode;
    }

    public String getMerchantOrder() {
        return merchantOrder;
    }

    public void setMerchantOrder(String merchantOrder) {
        this.merchantOrder = merchantOrder;
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
        return "GhMerchantOrddaieflectMcode{" +
                "id=" + id +
                ", ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
                ", merchantOrder='" + merchantOrder + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", bsStatus=" + bsStatus +
                ", ghStatus=" + ghStatus +
                '}';
    }
}
