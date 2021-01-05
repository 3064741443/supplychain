package cn.com.glsx.supplychain.model.bs;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "bs_after_sale_order")
public class AfterSaleOrder implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     *  订单号
     */
    private String orderNumber;
    /**
     * 售后类型（1：退货，2：返修）
     */
    private Byte type;
    /**
     * 订单时间
     */
    private Date orderTime;
    /**
     * 商户CODE
     */
    private String merchantCode;
    /**
     * 产品CODE
     */
    private String productCode;
    /**
     * 售后状态（1：待审核，2：待寄回，3：待发货，4：待签收，5:已完成，6：已驳回，7：已取消，8：已寄回, 9:部分发货,10:部分签收）
     */
    private Byte status;
    /**
     * 售后原因
     */
    private String reason;
    /**
     * 驳回理由
     */
    private String reject;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 商户名称
     */
    @Transient
    private String merchantName;
    /**
     * 售后订单详情
     */
    @Transient
    private List<AfterSaleOrderDetail> afterSaleOrderDetailList;

    //物流
    @Transient
    private Logistics logistics;

    @Transient
    private List<Logistics>logisticsList;

    /**
     * 产品信息
     * @return
     */
    @Transient
    private Product productInfo;

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
     * 提交开始时间
     */
    @Transient
    private Date orderStartDate;

    /**
     * 提交结束时间
     */
    @Transient
    private Date orderEndDate;

    /**
     * 设备数量
     */
    @Transient
    private Integer number;
    /**
     * 签收实际数量
     */
    @Transient
    private Integer signQuantity;

    /**
     * 维修管理SN切换记录信息
     */
    @Transient
    private List<MaintainSnChange>maintainSnChangeList;
    /**
     * 维修总费用
     */
    @Transient
    private Double repairCost;

    public Double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Double repairCost) {
        this.repairCost = repairCost;
    }

    public Integer getSignQuantity() {
        return signQuantity;
    }

    public void setSignQuantity(Integer signQuantity) {
        this.signQuantity = signQuantity;
    }

    public List<MaintainSnChange> getMaintainSnChangeList() {
        return maintainSnChangeList;
    }

    public void setMaintainSnChangeList(List<MaintainSnChange> maintainSnChangeList) {
        this.maintainSnChangeList = maintainSnChangeList;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<AfterSaleOrderDetail> getAfterSaleOrderDetailList() {
        return afterSaleOrderDetailList;
    }

    public void setAfterSaleOrderDetailList(List<AfterSaleOrderDetail> afterSaleOrderDetailList) {
        this.afterSaleOrderDetailList = afterSaleOrderDetailList;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
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

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }
}