package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class StatementSellRzfbImport implements Serializable{

	private String settleCustomerCode;

    private String settleCustomerName;
    
    private String saleGroupCode;
    
    private String saleGroupName;

    private String workOrder;

    private String alipayTransactionNumber;

    private String alipaySerialNumber;

    private String merchantOrderCode;

    private String accountType;

    private Double income;

    private Double expenditure;

    private Double accountBalance;

    private Double serviceFee;

    private String paymentChannel;

    private String signedProducts;

    private String counterAccount;

    private String counterName;

    private String bankOrderNumber;

    private String productName;

    private String recordedData;
    
    private String result;

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

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	@ExcelResources(title = "支付宝交易号",order = 1)
	public String getAlipayTransactionNumber() {
		return alipayTransactionNumber;
	}

	public void setAlipayTransactionNumber(String alipayTransactionNumber) {
		this.alipayTransactionNumber = alipayTransactionNumber;
	}

	@ExcelResources(title = "支付宝流水号",order = 2)
	public String getAlipaySerialNumber() {
		return alipaySerialNumber;
	}

	public void setAlipaySerialNumber(String alipaySerialNumber) {
		this.alipaySerialNumber = alipaySerialNumber;
	}

	@ExcelResources(title = "商户订单号",order = 3)
	public String getMerchantOrderCode() {
		return merchantOrderCode;
	}

	public void setMerchantOrderCode(String merchantOrderCode) {
		this.merchantOrderCode = merchantOrderCode;
	}

	@ExcelResources(title = "账务类型",order = 4)
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@ExcelResources(title = "收入（+元）",order = 5)
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	@ExcelResources(title = "支出(-元)",order = 6)
	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	@ExcelResources(title = "账户余额（元）",order = 7)
	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@ExcelResources(title = "服务费（元）",order = 8)
	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	@ExcelResources(title = "支付渠道",order = 9)
	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	@ExcelResources(title = "签约产品",order = 10)
	public String getSignedProducts() {
		return signedProducts;
	}

	public void setSignedProducts(String signedProducts) {
		this.signedProducts = signedProducts;
	}

	@ExcelResources(title = "对方账户",order = 11)
	public String getCounterAccount() {
		return counterAccount;
	}

	public void setCounterAccount(String counterAccount) {
		this.counterAccount = counterAccount;
	}

	@ExcelResources(title = "对方名称",order = 12)
	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	@ExcelResources(title = "银行订单号",order = 13)
	public String getBankOrderNumber() {
		return bankOrderNumber;
	}

	public void setBankOrderNumber(String bankOrderNumber) {
		this.bankOrderNumber = bankOrderNumber;
	}

	@ExcelResources(title = "商品名称",order = 14)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@ExcelResources(title = "入账时间",order = 0)
	public String getRecordedData() {
		return recordedData;
	}

	public void setRecordedData(String recordedData) {
		this.recordedData = recordedData;
	}

	@ExcelResources(title = "失败原因",order = 15)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
    
    
}
