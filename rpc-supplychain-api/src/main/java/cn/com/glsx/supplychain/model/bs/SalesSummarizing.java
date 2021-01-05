package cn.com.glsx.supplychain.model.bs;

import cn.com.glsx.supplychain.model.PageInfo;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "bs_sales_summarizing")
public class SalesSummarizing extends PageInfo implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 商户CODE
     */
    private String merchantCode;
    /**
     * 状态(1:未对账,2:待审核,3:待修正,4:已完成,5:已导出)
     */
    private Byte status;

    /**
     * 对账日期
     */
    private Date salesTime;

    /**
     * 对账总额
     */
    @Transient
    private Double totalPrice;

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

    /**
     * 商户名称
     */
    @Transient
    private String merchantName;

    /**
     * 销售详情
     */
    @Transient
    private List<Sales> salesList;

    /**
     * 销售汇总详情
     */
    @Transient
    private List<SalesSummarizingDetail> salesSummarizingDetailList;

    /**
     *  物料详情List
     */
    @Transient
    private List<SalesSummarizingMaterialDetail> salesSummarizingMaterialDetailList;

    /** --------------导出字段
     * 单据类型编码
     */
    @Transient
    private String billTypeID;

    /**
     * 单据类型名称
     */
    @Transient
    private String billTypeName;

    /**
     * 单据编号
     */
    @Transient
    private String billNo;

    /**
     * 日期
     */
    @Transient
    private Date date;

    /**
     * 销售组织编码
     */
    @Transient
    private Integer saleOrgId;

    /**
     * 销售组织名称
     */
    @Transient
    private String saleOrgName;

    /**
     *  客户编码
     */
    @Transient
    private String custId;

    /**
     *  客户名称
     */
    @Transient
    private String custName;

    /**
     * 结算币别编码
     */
    @Transient
    private String settleCurrId;

    /**
     *  结算币别名称
     */
    @Transient
    private String settleCurrName;

    /**
     * 物料编号
     */
    @Transient
    private String materialId;

    /**
     * 物料名称
     */
    @Transient
    private String MaterialName;

    /**
     * 物料价格
     */
    @Transient
    private Double MaterialPrice;

    /**
     * 销售单位名称
     */
    @Transient
    private String UnitName;

    /**
     * 销售总数
     */
    @Transient
    private Integer salesQuantity;

    /**
     * 含税单价
     */
    @Transient
    private Double TaxPrice;

    /**
     *  是否赠品
     */
    @Transient
    private Boolean onlyFree;

    /**
     * 税率
     */
    @Transient
    private Integer entryTaxRate;

    /**
     * 要货日期
     */
    @Transient
    private Date deliveryDate;

    /**
     *  结算组织编码
     */
    @Transient
    private Integer settleOrgIds;

    /**
     *  结算组织名称
     */
    @Transient
    private String settleOrgIdsName;

    /**
     * 预留类型
     */
    @Transient
    private String reserveType;

    /**
     * 发货仓库编码
     */
    @Transient
    private String warehouseCode;

    /**
     * 发货仓库名称
     */
    @Transient
    private String warehouseName;

    /**
     * 交货数量
     */
    @Transient
    private Integer planQuantity;

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

    public List<SalesSummarizingDetail> getSalesSummarizingDetailList() {
        return salesSummarizingDetailList;
    }

    public void setSalesSummarizingDetailList(List<SalesSummarizingDetail> salesSummarizingDetailList) {
        this.salesSummarizingDetailList = salesSummarizingDetailList;
    }

    public List<SalesSummarizingMaterialDetail> getSalesSummarizingMaterialDetailList() {
        return salesSummarizingMaterialDetailList;
    }

    public void setSalesSummarizingMaterialDetailList(List<SalesSummarizingMaterialDetail> salesSummarizingMaterialDetailList) {
        this.salesSummarizingMaterialDetailList = salesSummarizingMaterialDetailList;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getBillTypeID() {
        return billTypeID;
    }

    public void setBillTypeID(String billTypeID) {
        this.billTypeID = billTypeID;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSaleOrgId() {
        return saleOrgId;
    }

    public void setSaleOrgId(Integer saleOrgId) {
        this.saleOrgId = saleOrgId;
    }

    public String getSaleOrgName() {
        return saleOrgName;
    }

    public void setSaleOrgName(String saleOrgName) {
        this.saleOrgName = saleOrgName;
    }


    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSettleCurrId() {
        return settleCurrId;
    }

    public void setSettleCurrId(String settleCurrId) {
        this.settleCurrId = settleCurrId;
    }

    public String getSettleCurrName() {
        return settleCurrName;
    }

    public void setSettleCurrName(String settleCurrName) {
        this.settleCurrName = settleCurrName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public Double getMaterialPrice() {
        return MaterialPrice;
    }

    public void setMaterialPrice(Double materialPrice) {
        MaterialPrice = materialPrice;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public Double getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        TaxPrice = taxPrice;
    }

    public Boolean getOnlyFree() {
        return onlyFree;
    }

    public void setOnlyFree(Boolean onlyFree) {
        this.onlyFree = onlyFree;
    }

    public Integer getEntryTaxRate() {
        return entryTaxRate;
    }

    public void setEntryTaxRate(Integer entryTaxRate) {
        this.entryTaxRate = entryTaxRate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getSettleOrgIds() {
        return settleOrgIds;
    }

    public void setSettleOrgIds(Integer settleOrgIds) {
        this.settleOrgIds = settleOrgIds;
    }

    public String getSettleOrgIdsName() {
        return settleOrgIdsName;
    }

    public void setSettleOrgIdsName(String settleOrgIdsName) {
        this.settleOrgIdsName = settleOrgIdsName;
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(Integer planQuantity) {
        this.planQuantity = planQuantity;
    }

    public List<Sales> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList) {
        this.salesList = salesList;
    }

    @Override
    public String toString() {
        return "SalesSummarizing{" +
                "id=" + id +
                ", merchantCode='" + merchantCode + '\'' +
                ", status=" + status +
                ", salesTime=" + salesTime +
                ", totalPrice=" + totalPrice +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", merchantName='" + merchantName + '\'' +
                ", salesSummarizingDetailList=" + salesSummarizingDetailList +
                ", billTypeID='" + billTypeID + '\'' +
                ", billTypeName='" + billTypeName + '\'' +
                ", billNo='" + billNo + '\'' +
                ", date=" + date +
                ", saleOrgId=" + saleOrgId +
                ", saleOrgName='" + saleOrgName + '\'' +
                ", custId=" + custId +
                ", custName='" + custName + '\'' +
                ", settleCurrId='" + settleCurrId + '\'' +
                ", settleCurrName='" + settleCurrName + '\'' +
                ", materialId='" + materialId + '\'' +
                ", MaterialName='" + MaterialName + '\'' +
                ", UnitName='" + UnitName + '\'' +
                ", salesQuantity=" + salesQuantity +
                ", TaxPrice=" + TaxPrice +
                ", onlyFree='" + onlyFree + '\'' +
                ", entryTaxRate=" + entryTaxRate +
                ", deliveryDate=" + deliveryDate +
                ", settleOrgIds=" + settleOrgIds +
                ", settleOrgIdsName='" + settleOrgIdsName + '\'' +
                ", reserveType='" + reserveType + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", planQuantity=" + planQuantity +
                '}';
    }
}