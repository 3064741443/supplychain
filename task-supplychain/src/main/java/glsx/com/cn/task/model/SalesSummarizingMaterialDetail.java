package glsx.com.cn.task.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SalesSummarizingMaterialDetail
 * @Author admin
 * @Param
 * @Date 2019/5/17 12:18
 * @Version
 **/
@Table(name = "bs_sales_summarizing_material_detail")
public class SalesSummarizingMaterialDetail implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 销售汇总ID
     */
    private Long salesId;

    /**
     *  产品编号
     */
    private String productCode;

    /**
     * 物料编号
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料价格
     */
    private Double materialPrice;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     *  不含税单价
     */
    @Transient
    private Double freeTaxPrice;

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

    public Double getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(Double materialPrice) {
        this.materialPrice = materialPrice;
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

    public Double getFreeTaxPrice() {
        return freeTaxPrice;
    }

    public void setFreeTaxPrice(Double freeTaxPrice) {
        this.freeTaxPrice = freeTaxPrice;
    }

    @Override
    public String toString() {
        return "SalesSummarizingMaterialDetail{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", productCode='" + productCode + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialPrice=" + materialPrice +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}
