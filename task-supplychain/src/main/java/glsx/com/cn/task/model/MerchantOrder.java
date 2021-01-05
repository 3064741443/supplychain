package glsx.com.cn.task.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@SuppressWarnings("serial")
@Table(name = "bs_merchant_order")
public class MerchantOrder implements Serializable{
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 期望到货时间
     */
    private Date hopeTime;
    /**
     * 商户CODE
     */
    private String merchantCode;
    /**
     * 订购总数
     */
    private Integer totalOrder;
    /**
     * 审核总数
     */
    private Integer totalCheck;
    /**
     * 订单总额
     */
    private Double totalAmount;
    /**
     * 状态（0：已驳回，1：待审核，2：待发货，3：待签收，4：部分签收，5：已完成，6：已开票,7:已作废）
     */
    private Byte status;

    private String remarks;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private Byte signStatus;

    /**
     * 订单详情
     */
    @Transient
    private List<MerchantOrderDetail> merchantOrderDetailList;

    /**
     * 商户名称
     */
    @Transient
    private String merchantName;

    /**
     * 物流
     */
    @Transient
    private Logistics logistics;

    /**
     * 物流List
     */
    @Transient
    private List<Logistics> logisticsList;

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
     * 期望到货开始时间
     */
    @Transient
    private Date hopeStartDate;

    /**
     * 期望到货结束时间
     */
    @Transient
    private Date hopeEndDate;

    /**
     *  商户渠道
     */
    @Transient
    private Byte channel;
    /**
     *  产品信息
     */
    @Transient
    private Product productInfo;
    /**
     * 含税单价
     */
    @Transient
    private Double amount;
    /**
     * 产品编号
     */
    @Transient
    private String code;
    /**
     * 产品名称
     */
    @Transient
    private String productName;
    /**
     * 产品类型
     */
    @Transient
    private  String type;
    /**
     *  商户订单详情信息
     */
    @Transient
    private MerchantOrderDetail merchantOrderDetailInfo;


    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MerchantOrderDetail getMerchantOrderDetailInfo() {
        return merchantOrderDetailInfo;
    }

    public void setMerchantOrderDetailInfo(MerchantOrderDetail merchantOrderDetailInfo) {
        this.merchantOrderDetailInfo = merchantOrderDetailInfo;
    }

    public Date getHopeStartDate() {
        return hopeStartDate;
    }

    public void setHopeStartDate(Date hopeStartDate) {
        this.hopeStartDate = hopeStartDate;
    }

    public Date getHopeEndDate() {
        return hopeEndDate;
    }

    public void setHopeEndDate(Date hopeEndDate) {
        this.hopeEndDate = hopeEndDate;
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

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public List<Logistics> getLogisticsList() {
        return logisticsList;
    }

    public void setLogisticsList(List<Logistics> logisticsList) {
        this.logisticsList = logisticsList;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<MerchantOrderDetail> getMerchantOrderDetailList() {
        return merchantOrderDetailList;
    }

    public void setMerchantOrderDetailList(List<MerchantOrderDetail> merchantOrderDetailList) {
        this.merchantOrderDetailList = merchantOrderDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getHopeTime() {
        return hopeTime;
    }

    public void setHopeTime(Date hopeTime) {
        this.hopeTime = hopeTime;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(Integer totalCheck) {
        this.totalCheck = totalCheck;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "MerchantOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderTime=" + orderTime +
                ", hopeTime=" + hopeTime +
                ", merchantCode='" + merchantCode + '\'' +
                ", totalOrder=" + totalOrder +
                ", totalCheck=" + totalCheck +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", remarks='" + remarks + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }

	public Byte getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Byte signStatus) {
		this.signStatus = signStatus;
	}
}