package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class StatementFinanceTool implements Serializable{

	/**
     * 年月
     */
    private String time;
    /**
     * 工单号
     */
    private String workOrder;
    /**
     * 主设备号
     */
    private String deviceNumberOne;
    /**
     * 从设备号
     */
    private String deviceNumberTwo;
    /**
     * 业务服务期限
     */
    private String serviceTime;
    /**
     * 结算金额
     */
    private Double sum;  
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 是否投保
     */
    private String isSure;
    /**
     * 投保期限
     */
    private String sureTime;
    
    /**
     * 是否分期结算
     */
    private String settleByStages;
    
    /**
     * N:新装结算 C:续费结算 B:补充费结算 D:扣除费结算 I:安装费结算 S:拆装费结算 H:硬件费结算 
     */
    private String workType;
    
    /**
     * 是否结算小安装费 Y:是 N:否 
     */
    private String settleInstall;
    
    /**
     * 合同时间
     */
    private String contractDate;
    
    /**
     * 物料编码
     */
    private String materialCode;
    
    /**
     * 结算客户编码
     */
    private String customerCode;
    
    /**
     * 硬件结算客户编码
     */
    private String hardwareCustomerCode;
    
    /**
     * 非硬件结算客户编码
     */
    private String serviceCustomerCode;
    
    @ExcelResources(title = "月份",order = 0)
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@ExcelResources(title = "工单号",order = 1)
	public String getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}
	@ExcelResources(title = "主设备号",order = 2)
	public String getDeviceNumberOne() {
		return deviceNumberOne;
	}
	public void setDeviceNumberOne(String deviceNumberOne) {
		this.deviceNumberOne = deviceNumberOne;
	}
	@ExcelResources(title = "从设备号",order = 3)
	public String getDeviceNumberTwo() {
		return deviceNumberTwo;
	}
	public void setDeviceNumberTwo(String deviceNumberTwo) {
		this.deviceNumberTwo = deviceNumberTwo;
	}
	@ExcelResources(title = "业务服务期限",order = 4)
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	@ExcelResources(title = "结算金额",order = 5)
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	@ExcelResources(title = "发货仓库编码",order = 6)
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	@ExcelResources(title = "是否投保",order = 7)
	public String getIsSure() {
		return isSure;
	}
	public void setIsSure(String isSure) {
		this.isSure = isSure;
	}
	@ExcelResources(title = "投保期限",order = 8)
	public String getSureTime() {
		return sureTime;
	}
	public void setSureTime(String sureTime) {
		this.sureTime = sureTime;
	}
	@ExcelResources(title = "金融服务费是否分期结算",order = 9)
	public String getSettleByStages() {
		return settleByStages;
	}
	public void setSettleByStages(String settleByStages) {
		this.settleByStages = settleByStages;
	}
	@ExcelResources(title = "是否结安装费",order = 10)
	public String getSettleInstall() {
		return settleInstall;
	}
	public void setSettleInstall(String settleInstall) {
		this.settleInstall = settleInstall;
	}
	@ExcelResources(title = "合同日期",order = 11)
	public String getContractDate() {
		return contractDate;
	}
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}
	@ExcelResources(title = "物料编码",order = 12)
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@ExcelResources(title = "对账类型",order = 13)
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	@ExcelResources(title = "结算客户编码",order = 14)
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	@ExcelResources(title = "硬件结算客户编码",order = 15)
	public String getHardwareCustomerCode() {
		return hardwareCustomerCode;
	}
	public void setHardwareCustomerCode(String hardwareCustomerCode) {
		this.hardwareCustomerCode = hardwareCustomerCode;
	}
	@ExcelResources(title = "非硬件结算客户编码",order = 15)
	public String getServiceCustomerCode() {
		return serviceCustomerCode;
	}
	public void setServiceCustomerCode(String serviceCustomerCode) {
		this.serviceCustomerCode = serviceCustomerCode;
	}
	@Override
	public String toString() {
		return "StatementFinanceTool [time=" + time + ", workOrder="
				+ workOrder + ", deviceNumberOne=" + deviceNumberOne
				+ ", deviceNumberTwo=" + deviceNumberTwo + ", serviceTime="
				+ serviceTime + ", sum=" + sum + ", warehouseCode="
				+ warehouseCode + ", isSure=" + isSure + ", sureTime="
				+ sureTime + ", settleByStages=" + settleByStages
				+ ", workType=" + workType + ", settleInstall=" + settleInstall
				+ ", contractDate=" + contractDate + ", materialCode="
				+ materialCode + ", customerCode=" + customerCode
				+ ", hardwareCustomerCode=" + hardwareCustomerCode
				+ ", serviceCustomerCode=" + serviceCustomerCode + "]";
	}
	
	
	
   
}
