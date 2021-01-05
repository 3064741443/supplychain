package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

/**
 * @ClassName StatementFinance 经销对账单-续费
 * @Author admin
 * @Param
 * @Date 
 * @Version
 **/
@SuppressWarnings("serial")
public class StatementSellRenewImport implements Serializable{

	private String saleGroupCode;
    
    private String saleGroupName;

    private String tradeTime;

    private String pubaccountId;

    private String merchantCode;

    private String specialMerchantCode;

    private String deviceSn;

    private String weixinOrderNo;

    private String merchantOrderNo;

    private String userFlag;

    private String tradeType;

    private String tradeStatu;

    private String payBank;

    private String currencyType;

    private Double shsettleOrderMoney;

    private Double vouchersMoney;

    private String weixinReturnNo;

    private String merchantReturnNo;

    private Double returnMoney;

    private Double erchangeReturnMoney;

    private String returnType;

    private String returnStatu;

    private String merchantName;

    private Double chargesMoney;

    private Double feeRate;

    private Double orderMoney;

    private Double applyReturnMoney;

    private String feeRateRemark;

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

	@ExcelResources(title = "交易时间",order = 0)
	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	@ExcelResources(title = "公众帐号ID",order = 1)
	public String getPubaccountId() {
		return pubaccountId;
	}

	public void setPubaccountId(String pubaccountId) {
		this.pubaccountId = pubaccountId;
	}

	@ExcelResources(title = "商户号",order = 2)
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@ExcelResources(title = "特约商户号",order = 3)
	public String getSpecialMerchantCode() {
		return specialMerchantCode;
	}

	public void setSpecialMerchantCode(String specialMerchantCode) {
		this.specialMerchantCode = specialMerchantCode;
	}

	@ExcelResources(title = "设备号",order = 4)
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	@ExcelResources(title = "微信订单号",order = 5)
	public String getWeixinOrderNo() {
		return weixinOrderNo;
	}

	public void setWeixinOrderNo(String weixinOrderNo) {
		this.weixinOrderNo = weixinOrderNo;
	}

	@ExcelResources(title = "商户订单号",order = 6)
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	@ExcelResources(title = "用户标识",order = 7)
	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	@ExcelResources(title = "交易类型",order = 8)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@ExcelResources(title = "交易状态",order = 9)
	public String getTradeStatu() {
		return tradeStatu;
	}

	public void setTradeStatu(String tradeStatu) {
		this.tradeStatu = tradeStatu;
	}

	@ExcelResources(title = "付款银行",order = 10)
	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	@ExcelResources(title = "货币种类",order = 11)
	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@ExcelResources(title = "应结订单金额",order = 12)
	public Double getShsettleOrderMoney() {
		return shsettleOrderMoney;
	}

	public void setShsettleOrderMoney(Double shsettleOrderMoney) {
		this.shsettleOrderMoney = shsettleOrderMoney;
	}

	@ExcelResources(title = "代金券金额",order = 13)
	public Double getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(Double vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	@ExcelResources(title = "微信退款单号",order = 14)
	public String getWeixinReturnNo() {
		return weixinReturnNo;
	}

	public void setWeixinReturnNo(String weixinReturnNo) {
		this.weixinReturnNo = weixinReturnNo;
	}

	@ExcelResources(title = "商户退款单号",order = 15)
	public String getMerchantReturnNo() {
		return merchantReturnNo;
	}

	public void setMerchantReturnNo(String merchantReturnNo) {
		this.merchantReturnNo = merchantReturnNo;
	}

	@ExcelResources(title = "退款金额",order = 16)
	public Double getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}

	@ExcelResources(title = "充值券退款金额",order = 17)
	public Double getErchangeReturnMoney() {
		return erchangeReturnMoney;
	}

	public void setErchangeReturnMoney(Double erchangeReturnMoney) {
		this.erchangeReturnMoney = erchangeReturnMoney;
	}

	@ExcelResources(title = "退款类型",order = 18)
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@ExcelResources(title = "退款状态",order = 19)
	public String getReturnStatu() {
		return returnStatu;
	}

	public void setReturnStatu(String returnStatu) {
		this.returnStatu = returnStatu;
	}

	@ExcelResources(title = "商品名称",order = 20)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@ExcelResources(title = "手续费",order = 21)
	public Double getChargesMoney() {
		return chargesMoney;
	}

	public void setChargesMoney(Double chargesMoney) {
		this.chargesMoney = chargesMoney;
	}

	@ExcelResources(title = "费率",order = 22)
	public Double getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(Double feeRate) {
		this.feeRate = feeRate;
	}

	@ExcelResources(title = "订单金额",order = 23)
	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	@ExcelResources(title = "申请退款金额",order = 24)
	public Double getApplyReturnMoney() {
		return applyReturnMoney;
	}

	public void setApplyReturnMoney(Double applyReturnMoney) {
		this.applyReturnMoney = applyReturnMoney;
	}

	@ExcelResources(title = "费率备注",order = 25)
	public String getFeeRateRemark() {
		return feeRateRemark;
	}

	public void setFeeRateRemark(String feeRateRemark) {
		this.feeRateRemark = feeRateRemark;
	}

	
	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	@ExcelResources(title = "失败原因",order = 26)
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
		return "StatementSellRenewImport [saleGroupCode=" + saleGroupCode
				+ ", saleGroupName=" + saleGroupName + ", tradeTime="
				+ tradeTime + ", pubaccountId=" + pubaccountId
				+ ", merchantCode=" + merchantCode + ", specialMerchantCode="
				+ specialMerchantCode + ", deviceSn=" + deviceSn
				+ ", weixinOrderNo=" + weixinOrderNo + ", merchantOrderNo="
				+ merchantOrderNo + ", userFlag=" + userFlag + ", tradeType="
				+ tradeType + ", tradeStatu=" + tradeStatu + ", payBank="
				+ payBank + ", currencyType=" + currencyType
				+ ", shsettleOrderMoney=" + shsettleOrderMoney
				+ ", vouchersMoney=" + vouchersMoney + ", weixinReturnNo="
				+ weixinReturnNo + ", merchantReturnNo=" + merchantReturnNo
				+ ", returnMoney=" + returnMoney + ", erchangeReturnMoney="
				+ erchangeReturnMoney + ", returnType=" + returnType
				+ ", returnStatu=" + returnStatu + ", merchantName="
				+ merchantName + ", chargesMoney=" + chargesMoney
				+ ", feeRate=" + feeRate + ", orderMoney=" + orderMoney
				+ ", applyReturnMoney=" + applyReturnMoney + ", feeRateRemark="
				+ feeRateRemark + ", workOrder=" + workOrder + ", result="
				+ result + "]";
	}  
	
	
}
