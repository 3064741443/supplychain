package cn.com.glsx.supplychain.vo;

import cn.com.glsx.supplychain.model.PageInfo;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @ClassName StatementFinanceSplitExcelVo
 * @Author admin
 * @Param
 * @Date 2019/9/19 16:52
 * @Version 1.0
 **/
@SuppressWarnings("serial")
public class StatementFinanceSplitExcelVo extends PageInfo implements Serializable {
	
	@Transient
	private String number;
    /**
     * 单据编码
     */
    @Transient
    private String billTypeCode;
    /**
     * 单据名称
     */
    @Transient
    private String billTypeName;
    /**
     * 单据编号
     */
    @Transient
    private String billNumber;
    /**
     * 日期
     */
    @Transient
    private String time;
    /**
     * 销售组织编码
     */
    @Transient
    private String salesCode;
    /**
     * 销售组织名称
     */
    @Transient
    private String salesName;
    /**
     * 客户编码
     */
    @Transient
    private String customerCode;
    /**
     * 客户名称
     */
    @Transient
    private String customerName;
    /**
     * 销售组编码
     */
    @Transient
    private String saleGroupCode;
    /**
     * 销售组名称
     */
    @Transient
    private String saleGroupName;
    /**
     * 间隔列
     */
    @Transient
    private String cellOne;
    /**
     * 结算币别编码
     */
    @Transient
    private String statementCurrencyCode;
    /**
     * 结算币别名称
     */
    @Transient
    private String statementCurrencyName;
    /**
     * 间隔列
     */
    @Transient
    private String cellTwo;
    /**
     * 物料编码
     */
    @Transient
    private String materialCode;
    /**
     * 物料名称
     */
    @Transient
    private String materialName;
    /**
     * 销售单位编码
     */
    @Transient
    private String salesUnitCode;
    /**
     * 销售单位名称
     */
    @Transient
    private String salesUnitName;
    /**
     * 销售数量
     */
    @Transient
    private Integer salesQuantity;
    /**
     * 含税单价
     */
    @Transient
    private Double price;
    /**
     * 是否赠品
     */
    @Transient
    private String gift;
    /**
     * 税率
     */
    @Transient
    private Double taxRate;
    /**
     * 要货日期
     */
    @Transient
    private String takeGoodsDate;
    /**
     * 结算组织编码
     */
    @Transient
    private String statementOrganizeCode;
    /**
     * 结算组织名称
     */
    @Transient
    private String statementOrganizeName;
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
     * 间隔列
     */
    @Transient
    private String cellThree;
    /**
     * (交货明细)数量
     */
    @Transient
    private Integer quantity;
    
    /**
     * 财务信息(序号)
     */
    @Transient
    private String saleOrderFinance;
    
    /**
     * 订单明细(序号)
     */
    @Transient
    private String saleOrderEntry;
    
    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getTakeGoodsDate() {
        return takeGoodsDate;
    }

    public void setTakeGoodsDate(String takeGoodsDate) {
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

    public String getCellOne() {
        return cellOne;
    }

    public void setCellOne(String cellOne) {
        this.cellOne = cellOne;
    }

    public String getCellTwo() {
        return cellTwo;
    }

    public void setCellTwo(String cellTwo) {
        this.cellTwo = cellTwo;
    }

    public String getCellThree() {
        return cellThree;
    }

    public void setCellThree(String cellThree) {
        this.cellThree = cellThree;
    }
    
    public String getSaleOrderFinance() {
		return saleOrderFinance;
	}

	public void setSaleOrderFinance(String saleOrderFinance) {
		this.saleOrderFinance = saleOrderFinance;
	}

	public String getSaleOrderEntry() {
		return saleOrderEntry;
	}

	public void setSaleOrderEntry(String saleOrderEntry) {
		this.saleOrderEntry = saleOrderEntry;
	}

	@Override
    public String toString() {
        return "StatementFinanceSplitExcelVo{" +
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
                '}';
    }
}
