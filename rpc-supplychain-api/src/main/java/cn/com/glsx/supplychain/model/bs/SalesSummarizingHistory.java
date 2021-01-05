package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "bs_sales_summarizing_history")
public class SalesSummarizingHistory implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 销售汇总ID
     */
    private Long salesId;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 销售数量
     */
    private Integer salesQuantity;
    /**
     * 销售金额
     */
    private Double salesAmount;
    /**
     * 销售时间
     */
    private Date salesTime;

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

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Date getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(Date salesTime) {
        this.salesTime = salesTime;
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