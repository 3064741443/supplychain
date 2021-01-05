package glsx.com.cn.task.model;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "bs_product_history_price")
public class ProductHistoryPrice implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 产品编号
     */
    private String productCode;
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
        return "ProductHistoryPrice{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", time=" + time +
                ", type=" + type +
                ", price=" + price +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}