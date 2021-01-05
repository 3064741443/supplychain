package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class StatementSellJbwyImport implements Serializable{

	private String saleGroupCode;
    
    private String saleGroupName;

    private String no;

    private String insureNo;

    private String vechoPrice;

    private String vinNo;

    private String vechoUserName;

    private String deviceSn;

    private String engineNo;

    private String deviceType;

    private String version;

    private String insureDueTime;

    private Double money;

    private String insureReportPractice;

    private String insureStartDate;

    private String insureEndDate;

    private String princeAgent;

    private String cityAgent;

    private String handInMerchant;

    private String shopName;

    private String preMerchant;

    private String area;

    private String certifiNo;

    private String mobile;

    private String vechoBrand;

    private String vechoType;

    private String vechoSet;

    private String vechoColor;

    private String firstMan;

 //   private String insureMaturity;

    private String sellServerMane;

    private Double jbwyServerMoney;

    private String mileage;

    private String insureCompany;

    private String operator;

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

	@ExcelResources(title = "序号",order = 0)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@ExcelResources(title = "保单号",order = 1)
	public String getInsureNo() {
		return insureNo;
	}

	public void setInsureNo(String insureNo) {
		this.insureNo = insureNo;
	}

	@ExcelResources(title = "车辆价格",order = 2)
	public String getVechoPrice() {
		return vechoPrice;
	}

	public void setVechoPrice(String vechoPrice) {
		this.vechoPrice = vechoPrice;
	}

	@ExcelResources(title = "车架号",order = 3)
	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	@ExcelResources(title = "车主姓名",order = 4)
	public String getVechoUserName() {
		return vechoUserName;
	}

	public void setVechoUserName(String vechoUserName) {
		this.vechoUserName = vechoUserName;
	}

	@ExcelResources(title = "设备号",order = 5)
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	@ExcelResources(title = "发动机号",order = 6)
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@ExcelResources(title = "设备类型",order = 7)
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@ExcelResources(title = "版本",order = 8)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ExcelResources(title = "保单期限",order = 9)
	public String getInsureDueTime() {
		return insureDueTime;
	}

	public void setInsureDueTime(String insureDueTime) {
		this.insureDueTime = insureDueTime;
	}

	@ExcelResources(title = "价格",order = 10)
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@ExcelResources(title = "提报成功时间",order = 11)
	public String getInsureReportPractice() {
		return insureReportPractice;
	}

	public void setInsureReportPractice(String insureReportPractice) {
		this.insureReportPractice = insureReportPractice;
	}

	@ExcelResources(title = "生效时间",order = 12)
	public String getInsureStartDate() {
		return insureStartDate;
	}

	public void setInsureStartDate(String insureStartDate) {
		this.insureStartDate = insureStartDate;
	}

	@ExcelResources(title = "结束时间",order = 13)
	public String getInsureEndDate() {
		return insureEndDate;
	}

	public void setInsureEndDate(String insureEndDate) {
		this.insureEndDate = insureEndDate;
	}

	@ExcelResources(title = "省代",order = 14)
	public String getPrinceAgent() {
		return princeAgent;
	}

	public void setPrinceAgent(String princeAgent) {
		this.princeAgent = princeAgent;
	}

	@ExcelResources(title = "市代",order = 15)
	public String getCityAgent() {
		return cityAgent;
	}

	public void setCityAgent(String cityAgent) {
		this.cityAgent = cityAgent;
	}

	@ExcelResources(title = "提报商户",order = 16)
	public String getHandInMerchant() {
		return handInMerchant;
	}

	public void setHandInMerchant(String handInMerchant) {
		this.handInMerchant = handInMerchant;
	}

	@ExcelResources(title = "店面名称",order = 17)
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@ExcelResources(title = "上级服务商",order = 18)
	public String getPreMerchant() {
		return preMerchant;
	}

	public void setPreMerchant(String preMerchant) {
		this.preMerchant = preMerchant;
	}

	@ExcelResources(title = "区域",order = 19)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@ExcelResources(title = "证件号",order = 20)
	public String getCertifiNo() {
		return certifiNo;
	}

	public void setCertifiNo(String certifiNo) {
		this.certifiNo = certifiNo;
	}

	@ExcelResources(title = "手机号",order = 21)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelResources(title = "车品牌",order = 22)
	public String getVechoBrand() {
		return vechoBrand;
	}

	public void setVechoBrand(String vechoBrand) {
		this.vechoBrand = vechoBrand;
	}

	@ExcelResources(title = "车型",order = 23)
	public String getVechoType() {
		return vechoType;
	}

	public void setVechoType(String vechoType) {
		this.vechoType = vechoType;
	}

	@ExcelResources(title = "车系",order = 24)
	public String getVechoSet() {
		return vechoSet;
	}

	public void setVechoSet(String vechoSet) {
		this.vechoSet = vechoSet;
	}

	@ExcelResources(title = "车辆颜色",order = 25)
	public String getVechoColor() {
		return vechoColor;
	}

	public void setVechoColor(String vechoColor) {
		this.vechoColor = vechoColor;
	}

	@ExcelResources(title = "第一受益人",order = 26)
	public String getFirstMan() {
		return firstMan;
	}

	public void setFirstMan(String firstMan) {
		this.firstMan = firstMan;
	}

	/*@ExcelResources(title = "保单期限",order = 27)
	public String getInsureMaturity() {
		return insureMaturity;
	}

	public void setInsureMaturity(String insureMaturity) {
		this.insureMaturity = insureMaturity;
	}*/

	@ExcelResources(title = "销售顾问",order = 27)
	public String getSellServerMane() {
		return sellServerMane;
	}

	public void setSellServerMane(String sellServerMane) {
		this.sellServerMane = sellServerMane;
	}

	@ExcelResources(title = "无忧保障服务价格",order = 28)
	public Double getJbwyServerMoney() {
		return jbwyServerMoney;
	}

	public void setJbwyServerMoney(Double jbwyServerMoney) {
		this.jbwyServerMoney = jbwyServerMoney;
	}

	@ExcelResources(title = "里程",order = 29)
	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	@ExcelResources(title = "承保公司",order = 30)
	public String getInsureCompany() {
		return insureCompany;
	}

	public void setInsureCompany(String insureCompany) {
		this.insureCompany = insureCompany;
	}

	@ExcelResources(title = "操作人",order = 31)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	@ExcelResources(title = "失败原因",order = 32)
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
		return "StatementSellJbwyImport [saleGroupCode=" + saleGroupCode
				+ ", saleGroupName=" + saleGroupName + ", no=" + no
				+ ", insureNo=" + insureNo + ", vechoPrice=" + vechoPrice
				+ ", vinNo=" + vinNo + ", vechoUserName=" + vechoUserName
				+ ", deviceSn=" + deviceSn + ", engineNo=" + engineNo
				+ ", deviceType=" + deviceType + ", version=" + version
				+ ", insureDueTime=" + insureDueTime + ", money=" + money
				+ ", insureReportPractice=" + insureReportPractice
				+ ", insureStartDate=" + insureStartDate + ", insureEndDate="
				+ insureEndDate + ", princeAgent=" + princeAgent
				+ ", cityAgent=" + cityAgent + ", handInMerchant="
				+ handInMerchant + ", shopName=" + shopName + ", preMerchant="
				+ preMerchant + ", area=" + area + ", certifiNo=" + certifiNo
				+ ", mobile=" + mobile + ", vechoBrand=" + vechoBrand
				+ ", vechoType=" + vechoType + ", vechoSet=" + vechoSet
				+ ", vechoColor=" + vechoColor + ", firstMan=" + firstMan
				+ ", insureMaturity=" + ", sellServerMane="
				+ sellServerMane + ", jbwyServerMoney=" + jbwyServerMoney
				+ ", mileage=" + mileage + ", insureCompany=" + insureCompany
				+ ", operator=" + operator + ", workOrder=" + workOrder
				+ ", result=" + result + "]";
	}
    
    
}
