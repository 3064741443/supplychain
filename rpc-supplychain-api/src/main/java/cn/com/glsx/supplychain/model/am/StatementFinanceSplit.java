package cn.com.glsx.supplychain.model.am;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StatementFinanceSplit 对账单-金融风控(拆分)
 * @Author admin
 * @Param
 * @Date 2019/9/12 14:32
 * @Version
 **/
@Table(name = "am_statement_finance_split")
public class StatementFinanceSplit implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 金融风控ID
     */
    private Long financeId;
    /**
     * 单据编码
     */
    private String billTypeCode;
    /**
     * 单据名称
     */
    private String billTypeName;
    /**
     * 单据编号
     */
    private String billNumber;
    /**
     * 日期
     */
    private Date time;
    /**
     * 销售组织编码
     */
    private String salesCode;
    /**
     * 销售组织名称
     */
    private String salesName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 销售组编码
     */
    private String saleGroupCode;
    /**
     * 销售组名称
     */
    private String saleGroupName;
    /**
     * 结算币别编码
     */
    private String statementCurrencyCode;
    /**
     * 结算币别名称
     */
    private String statementCurrencyName;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 销售单位编码
     */
    private String salesUnitCode;
    /**
     * 销售单位名称
     */
    private String salesUnitName;
    /**
     * 销售数量
     */
    private Integer salesQuantity;
    /**
     * 含税单价
     */
    private Double price;
    /**
     * 是否赠品
     */
    private String gift;
    /**
     * 税率
     */
    private Double taxRate;
    /**
     * 要货日期
     */
    private Date takeGoodsDate;
    /**
     * 结算组织编码
     */
    private String statementOrganizeCode;
    /**
     * 结算组织名称
     */
    private String statementOrganizeName;
    /**
     * 预留类型
     */
    private String reserveType;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 发货仓库名称
     */
    private String warehouseName;
    /**
     * (交货明细)数量
     */
    private Integer quantity;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String combileCode;
    
    private String financeCustomerCode;
    
    /**
     * N:新装结算 C:续费结算 B:补充费结算 D:扣除费结算 I:安装费结算 S:拆装费结算 H:硬件费结算 
     */
    private String workType;
    
    private String workOrder;

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
    
    @Transient
    private String merchantCode;
    
    @Transient
    private String merchantName;

    public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

    public Long getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Long financeId) {
        this.financeId = financeId;
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode;
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName;
    }

    public String getStatementCurrencyCode() {
        return statementCurrencyCode;
    }

    public void setStatementCurrencyCode(String statementCurrencyCode) {
        this.statementCurrencyCode = statementCurrencyCode;
    }

    public String getStatementCurrencyName() {
        return statementCurrencyName;
    }

    public void setStatementCurrencyName(String statementCurrencyName) {
        this.statementCurrencyName = statementCurrencyName;
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

    public String getSalesUnitCode() {
        return salesUnitCode;
    }

    public void setSalesUnitCode(String salesUnitCode) {
        this.salesUnitCode = salesUnitCode;
    }

    public String getSalesUnitName() {
        return salesUnitName;
    }

    public void setSalesUnitName(String salesUnitName) {
        this.salesUnitName = salesUnitName;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Date getTakeGoodsDate() {
        return takeGoodsDate;
    }

    public void setTakeGoodsDate(Date takeGoodsDate) {
        this.takeGoodsDate = takeGoodsDate;
    }

    public String getStatementOrganizeCode() {
        return statementOrganizeCode;
    }

    public void setStatementOrganizeCode(String statementOrganizeCode) {
        this.statementOrganizeCode = statementOrganizeCode;
    }

    public String getStatementOrganizeName() {
        return statementOrganizeName;
    }

    public void setStatementOrganizeName(String statementOrganizeName) {
        this.statementOrganizeName = statementOrganizeName;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "StatementFinanceSplit{" +
                "id=" + id +
                ", financeId='" + financeId + '\'' +
                ", billTypeCode='" + billTypeCode + '\'' +
                ", billTypeName='" + billTypeName + '\'' +
                ", billNumber='" + billNumber + '\'' +
                ", time=" + time +
                ", salesCode='" + salesCode + '\'' +
                ", salesName='" + salesName + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", saleGroupCode='" + saleGroupCode + '\'' +
                ", saleGroupName='" + saleGroupName + '\'' +
                ", statementCurrencyCode='" + statementCurrencyCode + '\'' +
                ", statementCurrencyName='" + statementCurrencyName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", salesUnitCode='" + salesUnitCode + '\'' +
                ", salesUnitName='" + salesUnitName + '\'' +
                ", salesQuantity=" + salesQuantity +
                ", price=" + price +
                ", gift='" + gift + '\'' +
                ", taxRate=" + taxRate +
                ", takeGoodsDate=" + takeGoodsDate +
                ", statementOrganizeCode='" + statementOrganizeCode + '\'' +
                ", statementOrganizeName='" + statementOrganizeName + '\'' +
                ", reserveType='" + reserveType + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", quantity=" + quantity +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

	public String getCombileCode() {
		return combileCode;
	}

	public void setCombileCode(String combileCode) {
		this.combileCode = combileCode;
	}

	public String getFinanceCustomerCode() {
		return financeCustomerCode;
	}

	public void setFinanceCustomerCode(String financeCustomerCode) {
		this.financeCustomerCode = financeCustomerCode;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
}
