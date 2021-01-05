package glsx.com.cn.task.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Table(name = "bs_product")
public class Product implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 产品编号
     */
    private String code;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品规格
     */
    private String specification;
    /**
     * 产品类型
     */
    private String type;
    /**
     * 渠道(0:通用,1:广汇,2:非广汇)
     */
    private Byte channel;
    /**
     * 含税单价
     */
    @Transient
    private Double amount;
    /**
     * 产品状态（1：上架，2：下架）
     */
    private Byte status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 产品详情
     */
    @Transient
    private List<ProductDetail> productDetailList;

    /**
     * 产品历史价格类型
     */
    @Transient
    private String productHistoryType;

    public String getProductHistoryType() {
        return productHistoryType;
    }

    public void setProductHistoryType(String productHistoryType) {
        this.productHistoryType = productHistoryType;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", specification='" + specification + '\'' +
                ", type='" + type + '\'' +
                ", channel=" + channel +
                ", amount=" + amount +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", productDetailList=" + productDetailList +
                '}';
    }
}