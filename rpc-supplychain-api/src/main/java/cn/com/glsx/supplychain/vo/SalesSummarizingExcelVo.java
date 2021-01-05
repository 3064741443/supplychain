package cn.com.glsx.supplychain.vo;

import cn.com.glsx.supplychain.model.PageInfo;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SalesSummarizingExcelVo
 * @Author admin
 * @Param
 * @Date 2019/3/13 14:27
 * @Version
 **/

@SuppressWarnings("serial")
public class SalesSummarizingExcelVo extends PageInfo implements Serializable {
    /**
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
    private String date;

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
     * 销售单位名称
     */
    @Transient
    private String UnitName;

    /**
     * 销售数量
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
    private  Boolean onlyFree;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
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

    @Override
    public String toString() {
        return "SalesSummarizingExcelVo{" +
                "billTypeID='" + billTypeID + '\'' +
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
