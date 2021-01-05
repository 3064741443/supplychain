package cn.com.glsx.supplychain.model.bs;



import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "bs_sales")
public class Sales implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 商户CODE
     */
    private String merchantCode;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 物流ID
     */
    private Long logisticsId;
    /**
     * 对账状态(1:未对账,2:已对账,3:已完成,4:已开票)
     */
    private Byte status;
    /**
     * 发货日期
     */
    private Date dispatchTime;
    /**
     * 数量
     */
    private Integer quantity;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    //经销方式（1：经销，2：代销）
    @Transient
    private Byte saleMode;

    /**
     * 商户名称
     */
    @Transient
    private String merchantName;

    /**
     *  产品信息
     */
    @Transient
    private Product productInfo;

    /**
     *  产品名称
     */
    @Transient
    private String productName;

    /**
     *  产品类型
     */
    @Transient
    private String type;

    /**
     * 开始时间
     */
    @Transient
    private Date startDate;

    /**
     * 结束时间
     */
    @Transient
    private Date endDate;

    /**
     * w物流信息List
     */
    @Transient
    private List<Logistics> logisticsInfoList;

    public Long getLogisticsId() {
        return logisticsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Logistics> getLogisticsInfoList() {
        return logisticsInfoList;
    }

    public void setLogisticsInfoList(List<Logistics> logisticsInfoList) {
        this.logisticsInfoList = logisticsInfoList;
    }

    public Byte getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(Byte saleMode) {
        this.saleMode = saleMode;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", merchantCode='" + merchantCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", Status=" + status +
                ", dispatchTime=" + dispatchTime +
                ", quantity=" + quantity +
                ", merchantName='" + merchantName + '\'' +
                ", productInfo=" + productInfo +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
