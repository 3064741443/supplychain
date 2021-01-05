package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
@Table(name = "bs_product_history_price")
public class ProductHistoryPrice {
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 业务类型ID(1:驾宝无忧,2:金融风控,3:车机,4:后视镜,5:其它)
     */
    private Byte serviceType;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品类型产品类型(0:硬件,1:网联软件,2:风险评估,3:风控服务,4:安装服务,5:智慧门店服务,6:AI车联网服务,7:配件)
     */
    private String productType;
    /**
     * 物料编号
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 时间
     */
    private Date time;
    /**
     * 类型(0:当前价格,1:未来价格)
     */
    private Byte type;
    /**
     * 价格
     */
    private Double price;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        return "ProductHistoryPrice{" +
                "id=" + id +
                ", serviceType=" + serviceType +
                ", productCode='" + productCode + '\'' +
                ", productType='" + productType + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", time=" + time +
                ", type=" + type +
                ", price=" + price +
                ", taxRate=" + taxRate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}