package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class StatementSellGlwyImport implements Serializable{

	private String saleGroupCode;
    
    private String saleGroupName;
    
    private String belongCompany;

    private String area;

    private String shopCode;
    
    private String shopName;

    private String applyNo;

    private String contractPaymentNo;

    private String customerCode;

    private String customerName;

    private String rentAttrible;

    private String financialDes;

    private String vinNo;

    private String engineNo;

    private String contractDate;

    private String insureYear;

    private Double glwyInsureMoney;

    private Double glwySettleMoney;

    private Double rentProfitMoney;

    private Double insureAssureMoney;

    private String contractStatu;

    private String financingMaturity;

    private String shopAttrib;

    private String settleStatu;

    private String billNo;

    private String applyDate;

    private String workOrder;
    
    private String result;
    
    private String settleCustomerCode;
    
    private String settleCustomerName;

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

	@ExcelResources(title = "所属公司",order = 0)
	public String getBelongCompany() {
		return belongCompany;
	}

	public void setBelongCompany(String belongCompany) {
		this.belongCompany = belongCompany;
	}

	@ExcelResources(title = "区域",order = 1)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@ExcelResources(title = "店面代码",order = 2)
	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@ExcelResources(title = "申请单编号",order = 4)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@ExcelResources(title = "合同流水号",order = 5)
	public String getContractPaymentNo() {
		return contractPaymentNo;
	}

	public void setContractPaymentNo(String contractPaymentNo) {
		this.contractPaymentNo = contractPaymentNo;
	}

	@ExcelResources(title = "客户编码",order = 6)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@ExcelResources(title = "客户名称",order = 7)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@ExcelResources(title = "租赁属性",order = 8)
	public String getRentAttrible() {
		return rentAttrible;
	}

	public void setRentAttrible(String rentAttrible) {
		this.rentAttrible = rentAttrible;
	}

	@ExcelResources(title = "金融产品描述",order = 9)
	public String getFinancialDes() {
		return financialDes;
	}

	public void setFinancialDes(String financialDes) {
		this.financialDes = financialDes;
	}

	@ExcelResources(title = "车架号",order = 10)
	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	@ExcelResources(title = "发动机号",order = 11)
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@ExcelResources(title = "合同生效日",order = 12)
	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	@ExcelResources(title = "保险年份",order = 13)
	public String getInsureYear() {
		return insureYear;
	}

	public void setInsureYear(String insureYear) {
		this.insureYear = insureYear;
	}

	@ExcelResources(title = "广联无忧保费总额",order = 14)
	public Double getGlwyInsureMoney() {
		return glwyInsureMoney;
	}

	public void setGlwyInsureMoney(Double glwyInsureMoney) {
		this.glwyInsureMoney = glwyInsureMoney;
	}

	@ExcelResources(title = "广联无忧结算总额",order = 15)
	public Double getGlwySettleMoney() {
		return glwySettleMoney;
	}

	public void setGlwySettleMoney(Double glwySettleMoney) {
		this.glwySettleMoney = glwySettleMoney;
	}

	@ExcelResources(title = "租赁利润",order = 16)
	public Double getRentProfitMoney() {
		return rentProfitMoney;
	}

	public void setRentProfitMoney(Double rentProfitMoney) {
		this.rentProfitMoney = rentProfitMoney;
	}

	@ExcelResources(title = "保额",order = 17)
	public Double getInsureAssureMoney() {
		return insureAssureMoney;
	}

	public void setInsureAssureMoney(Double insureAssureMoney) {
		this.insureAssureMoney = insureAssureMoney;
	}

	@ExcelResources(title = "合同状态",order = 18)
	public String getContractStatu() {
		return contractStatu;
	}

	public void setContractStatu(String contractStatu) {
		this.contractStatu = contractStatu;
	}

	@ExcelResources(title = "融资期限",order = 19)
	public String getFinancingMaturity() {
		return financingMaturity;
	}

	public void setFinancingMaturity(String financingMaturity) {
		this.financingMaturity = financingMaturity;
	}

	@ExcelResources(title = "店面属性",order = 20)
	public String getShopAttrib() {
		return shopAttrib;
	}

	public void setShopAttrib(String shopAttrib) {
		this.shopAttrib = shopAttrib;
	}

	@ExcelResources(title = "结算状态",order = 21)
	public String getSettleStatu() {
		return settleStatu;
	}

	public void setSettleStatu(String settleStatu) {
		this.settleStatu = settleStatu;
	}

	@ExcelResources(title = "发票号",order = 22)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@ExcelResources(title = "申请日期",order = 23)
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	@ExcelResources(title = "失败原因",order = 24)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getSettleCustomerCode() {
		return settleCustomerCode;
	}

	public void setSettleCustomerCode(String settleCustomerCode) {
		this.settleCustomerCode = settleCustomerCode;
	}

	public String getSettleCustomerName() {
		return settleCustomerName;
	}

	public void setSettleCustomerName(String settleCustomerName) {
		this.settleCustomerName = settleCustomerName;
	}

	@Override
	public String toString() {
		return "StatementSellGlwyImport [saleGroupCode=" + saleGroupCode
				+ ", saleGroupName=" + saleGroupName + ", belongCompany="
				+ belongCompany + ", area=" + area + ", shopCode=" + shopCode
				+ ", applyNo=" + applyNo + ", contractPaymentNo="
				+ contractPaymentNo + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", rentAttrible="
				+ rentAttrible + ", financialDes=" + financialDes + ", vinNo="
				+ vinNo + ", engineNo=" + engineNo + ", contractDate="
				+ contractDate + ", insureYear=" + insureYear
				+ ", glwyInsureMoney=" + glwyInsureMoney + ", glwySettleMoney="
				+ glwySettleMoney + ", rentProfitMoney=" + rentProfitMoney
				+ ", insureAssureMoney=" + insureAssureMoney
				+ ", contractStatu=" + contractStatu + ", financingMaturity="
				+ financingMaturity + ", shopAttrib=" + shopAttrib
				+ ", settleStatu=" + settleStatu + ", billNo=" + billNo
				+ ", applyDate=" + applyDate + ", workOrder=" + workOrder
				+ ", result=" + result + "]";
	}

	@ExcelResources(title = "店面名称",order = 3)
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
    
    
}
