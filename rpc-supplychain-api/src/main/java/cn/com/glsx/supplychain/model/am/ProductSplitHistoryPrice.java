package cn.com.glsx.supplychain.model.am;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "am_product_split_history_price")
public class ProductSplitHistoryPrice implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 时间
     */
    private Date time;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 业务类型(1:驾宝无忧,2:金融风控,3:车机,4:后视镜)
     */
    private Byte serviceType;
    /**
     * 物料编号
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 类型(0:当前价格,1:未来价格)
     */
    private Byte type;
    /**
     * 价格
     */
    private Double price;
    /**
     * 产品类型(0:硬件,1:网联软件,2:风险评估,3:风控服务,4:安装服务,5:智慧门店服务,6:AI车联网服务,7:配件)
     */
    private String productType;
    /**
     * 税率
     */
    private Double taxRate;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
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
        return "ProductSplitHistoryPrice{" +
                "id=" + id +
                ", time=" + time +
                ", productCode='" + productCode + '\'' +
                ", serviceType=" + serviceType +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", productType='" + productType + '\'' +
                ", taxRate=" + taxRate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}