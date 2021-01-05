package glsx.com.cn.task.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "am_statement_sell_split")
public class StatementSellSplit implements Serializable{
	@Id
    private Integer id;

    private String workOrder;
    
    private String workType;

    private String billTypeCode;

    private String billTypeName;

    private String billNumber;

    private Date time;

    private String salesCode;

    private String salesName;

    private String customerCode;

    private String customerName;

    private String saleGroupCode;

    private String saleGroupName;

    private String statementCurrencyCode;

    private String statementCurrencyName;

    private String materialCode;

    private String materialName;

    private String salesUnitCode;

    private String salesUnitName;

    private Integer salesQuantity;

    private Double price;

    private String gift;

    private Double taxRate;

    private Date takeGoodsDate;

    private String statementOrganizeCode;

    private String statementOrganizeName;

    private String reserveType;

    private String warehouseCode;

    private String warehouseName;

    private Integer quantity;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String combileCode;
    
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder == null ? null : workOrder.trim();
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode == null ? null : billTypeCode.trim();
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName == null ? null : billTypeName.trim();
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber == null ? null : billNumber.trim();
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
        this.salesCode = salesCode == null ? null : salesCode.trim();
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName == null ? null : salesName.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode == null ? null : saleGroupCode.trim();
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName == null ? null : saleGroupName.trim();
    }

    public String getStatementCurrencyCode() {
        return statementCurrencyCode;
    }

    public void setStatementCurrencyCode(String statementCurrencyCode) {
        this.statementCurrencyCode = statementCurrencyCode == null ? null : statementCurrencyCode.trim();
    }

    public String getStatementCurrencyName() {
        return statementCurrencyName;
    }

    public void setStatementCurrencyName(String statementCurrencyName) {
        this.statementCurrencyName = statementCurrencyName == null ? null : statementCurrencyName.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getSalesUnitCode() {
        return salesUnitCode;
    }

    public void setSalesUnitCode(String salesUnitCode) {
        this.salesUnitCode = salesUnitCode == null ? null : salesUnitCode.trim();
    }

    public String getSalesUnitName() {
        return salesUnitName;
    }

    public void setSalesUnitName(String salesUnitName) {
        this.salesUnitName = salesUnitName == null ? null : salesUnitName.trim();
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
        this.gift = gift == null ? null : gift.trim();
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
        this.statementOrganizeCode = statementOrganizeCode == null ? null : statementOrganizeCode.trim();
    }

    public String getStatementOrganizeName() {
        return statementOrganizeName;
    }

    public void setStatementOrganizeName(String statementOrganizeName) {
        this.statementOrganizeName = statementOrganizeName == null ? null : statementOrganizeName.trim();
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType == null ? null : reserveType.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
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
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
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
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

    public String getCombileCode() {
        return combileCode;
    }

    public void setCombileCode(String combileCode) {
        this.combileCode = combileCode == null ? null : combileCode.trim();
    }

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
}