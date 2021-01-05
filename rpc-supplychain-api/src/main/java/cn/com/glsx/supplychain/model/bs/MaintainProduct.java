package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "bs_maintain_product")
public class MaintainProduct implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 售后订单
     */
    private String afterSaleOrderNumber;
    /**
     * 商户编号
     */
    private String merchantCode;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 状态（0:待维修,1:维修中,2:取消维修,3:已完成）
     */
    private Byte status;
    /**
     * 维修费用
     */
    private Double repairCost;

    /**
     * 维修管理详情List
     */
    @Transient
    private List<MaintainProductDetail> maintainProductDetailList;

    /**
     * 维修管理详情
     */
    @Transient
    private MaintainProductDetail maintainProductDetail;

    /**
     * 维修管理SN切换记录
     */
    @Transient
    private  MaintainSnChange maintainSnChange;

    /**
     * 商户名称
     */
    @Transient
    private String merchantName;
    /**
     * 产品名称
     */
    @Transient
    private String productName;
    /**
     * 售后类型（1：退货，2：返修）
     */
    @Transient
    private Byte type;
    /**
     * 订单时间
     */
    @Transient
    private Date orderTime;
    /**
     * 设备数量
     */
    @Transient
    private Integer number;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfterSaleOrderNumber() {
        return afterSaleOrderNumber;
    }

    public void setAfterSaleOrderNumber(String afterSaleOrderNumber) {
        this.afterSaleOrderNumber = afterSaleOrderNumber;
    }

    public List<MaintainProductDetail> getMaintainProductDetailList() {
        return maintainProductDetailList;
    }

    public MaintainProductDetail getMaintainProductDetail() {
        return maintainProductDetail;
    }

    public void setMaintainProductDetail(MaintainProductDetail maintainProductDetail) {
        this.maintainProductDetail = maintainProductDetail;
    }

    public void setMaintainProductDetailList(List<MaintainProductDetail> maintainProductDetailList) {
        this.maintainProductDetailList = maintainProductDetailList;
    }

    public MaintainSnChange getMaintainSnChange() {
        return maintainSnChange;
    }

    public void setMaintainSnChange(MaintainSnChange maintainSnChange) {
        this.maintainSnChange = maintainSnChange;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Double repairCost) {
        this.repairCost = repairCost;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}