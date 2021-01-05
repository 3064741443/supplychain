package cn.com.glsx.supplychain.vo;

import java.util.Date;

public class ProductSplitHistoryPriceVo {

	private Date time;
	
	private String productCode;
	
	private Double salesPrice; // 销售价格
	
	private String netHardMatribCode; //网联智能硬件物料编码
	
	private Double netHardPrice; //网联智能硬件价格
	
	private String hardWareMatribCode; //硬件编码
	
	private Double hardWarePrice; //硬件价格

	private Double hardWareTaxRate;//硬件税率
	
	private String hardConfigMatribCode; //配件物料编号
	
	private Double hardConfigPrice; //配件价格

	private Double hardConfigTaxRate; //配件税率
	
	private String netSoftMatribCode; //网联软件价格
	
	private Double netSoftPrice;//网联软件价格

	private Double netSoftTaxRate; //网联软件税率

	private String netSoftMaterialName;///网联软件物料名称
	
	private String DangerSoftMatribCode; // 风险评估软件编码
	
	private Double DangerSoftPrice; //风险评估软件价格

	private String DangerSoftMaterialName;//风险评估软件物料名称

	private Double dangerSoftTaxRate; //风险评估软件税率
	
	private String ServiceMatribCode; //服务编码
	
	private Double ServicePrice; //服务价格
	
	private String flowControlMatribCode; //风控服务编码
	
	private Double flowControlPrice;// 风控服务价格

	private String flowContRolMaterialName;// 风控服务物料名称

	private Double flowContRolTaxRate; //风控服务税率
	
	private String installSvrMatribCode; //安装服务物料
	
	private Double installSvrPrice; //安装服务编码

	private String installSvrMaterialName;//安装服务物料名称

	private Double installSvrTaxRate; //安装服务税率
	
	private String aiNetCarMatribCode; //AI车联网编码
	
	private Double aiNetCarMatribPrice; //AI车联网价格

	private String aiNetCarMaterialName; //AI车联网物料名称

	private Double aiNetCarTaxRate;//AI车联网物税率
	
	private String smartShopMatribCode; //智慧门店编码
	
	private Double smartShopPrice;//智慧门店价格

	private String smartShopName;//智慧门店物料名称

	private Double smartShopTaxRate;//智慧门店税率
	
	private Date createdDate;
	
	private String status;

	private Byte serviceType;//业务类型(1:驾宝无忧,2:金融风控,3:车机,4:后视镜)

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getNetHardMatribCode() {
		return netHardMatribCode;
	}

	public void setNetHardMatribCode(String netHardMatribCode) {
		this.netHardMatribCode = netHardMatribCode;
	}

	public Double getNetHardPrice() {
		return netHardPrice;
	}

	public void setNetHardPrice(Double netHardPrice) {
		this.netHardPrice = netHardPrice;
	}

	public String getHardWareMatribCode() {
		return hardWareMatribCode;
	}

	public void setHardWareMatribCode(String hardWareMatribCode) {
		this.hardWareMatribCode = hardWareMatribCode;
	}

	public Double getHardWarePrice() {
		return hardWarePrice;
	}

	public void setHardWarePrice(Double hardWarePrice) {
		this.hardWarePrice = hardWarePrice;
	}

	public String getHardConfigMatribCode() {
		return hardConfigMatribCode;
	}

	public void setHardConfigMatribCode(String hardConfigMatribCode) {
		this.hardConfigMatribCode = hardConfigMatribCode;
	}

	public Double getHardConfigPrice() {
		return hardConfigPrice;
	}

	public void setHardConfigPrice(Double hardConfigPrice) {
		this.hardConfigPrice = hardConfigPrice;
	}

	public String getNetSoftMatribCode() {
		return netSoftMatribCode;
	}

	public void setNetSoftMatribCode(String netSoftMatribCode) {
		this.netSoftMatribCode = netSoftMatribCode;
	}

	public Double getNetSoftPrice() {
		return netSoftPrice;
	}

	public void setNetSoftPrice(Double netSoftPrice) {
		this.netSoftPrice = netSoftPrice;
	}

	public String getDangerSoftMatribCode() {
		return DangerSoftMatribCode;
	}

	public void setDangerSoftMatribCode(String dangerSoftMatribCode) {
		DangerSoftMatribCode = dangerSoftMatribCode;
	}

	public Double getDangerSoftPrice() {
		return DangerSoftPrice;
	}

	public void setDangerSoftPrice(Double dangerSoftPrice) {
		DangerSoftPrice = dangerSoftPrice;
	}

	public String getServiceMatribCode() {
		return ServiceMatribCode;
	}

	public void setServiceMatribCode(String serviceMatribCode) {
		ServiceMatribCode = serviceMatribCode;
	}

	public Double getServicePrice() {
		return ServicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		ServicePrice = servicePrice;
	}

	public String getFlowControlMatribCode() {
		return flowControlMatribCode;
	}

	public void setFlowControlMatribCode(String flowControlMatribCode) {
		this.flowControlMatribCode = flowControlMatribCode;
	}

	public Double getFlowControlPrice() {
		return flowControlPrice;
	}

	public void setFlowControlPrice(Double flowControlPrice) {
		this.flowControlPrice = flowControlPrice;
	}

	public String getInstallSvrMatribCode() {
		return installSvrMatribCode;
	}

	public void setInstallSvrMatribCode(String installSvrMatribCode) {
		this.installSvrMatribCode = installSvrMatribCode;
	}

	public Double getInstallSvrPrice() {
		return installSvrPrice;
	}

	public void setInstallSvrPrice(Double installSvrPrice) {
		this.installSvrPrice = installSvrPrice;
	}

	public String getAiNetCarMatribCode() {
		return aiNetCarMatribCode;
	}

	public void setAiNetCarMatribCode(String aiNetCarMatribCode) {
		this.aiNetCarMatribCode = aiNetCarMatribCode;
	}

	public Double getAiNetCarMatribPrice() {
		return aiNetCarMatribPrice;
	}

	public void setAiNetCarMatribPrice(Double aiNetCarMatribPrice) {
		this.aiNetCarMatribPrice = aiNetCarMatribPrice;
	}

	public String getSmartShopMatribCode() {
		return smartShopMatribCode;
	}

	public void setSmartShopMatribCode(String smartShopMatribCode) {
		this.smartShopMatribCode = smartShopMatribCode;
	}

	public Double getSmartShopPrice() {
		return smartShopPrice;
	}

	public void setSmartShopPrice(Double smartShopPrice) {
		this.smartShopPrice = smartShopPrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNetSoftMaterialName() {
		return netSoftMaterialName;
	}

	public void setNetSoftMaterialName(String netSoftMaterialName) {
		this.netSoftMaterialName = netSoftMaterialName;
	}

	public String getDangerSoftMaterialName() {
		return DangerSoftMaterialName;
	}

	public void setDangerSoftMaterialName(String dangerSoftMaterialName) {
		DangerSoftMaterialName = dangerSoftMaterialName;
	}

	public String getFlowContRolMaterialName() {
		return flowContRolMaterialName;
	}

	public void setFlowContRolMaterialName(String flowContRolMaterialName) {
		this.flowContRolMaterialName = flowContRolMaterialName;
	}

	public String getInstallSvrMaterialName() {
		return installSvrMaterialName;
	}

	public void setInstallSvrMaterialName(String installSvrMaterialName) {
		this.installSvrMaterialName = installSvrMaterialName;
	}

	public String getAiNetCarMaterialName() {
		return aiNetCarMaterialName;
	}

	public void setAiNetCarMaterialName(String aiNetCarMaterialName) {
		this.aiNetCarMaterialName = aiNetCarMaterialName;
	}

	public String getSmartShopName() {
		return smartShopName;
	}

	public void setSmartShopName(String smartShopName) {
		this.smartShopName = smartShopName;
	}

	public Byte getServiceType() {
		return serviceType;
	}

	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}

	public Double getHardWareTaxRate() {
		return hardWareTaxRate;
	}

	public void setHardWareTaxRate(Double hardWareTaxRate) {
		this.hardWareTaxRate = hardWareTaxRate;
	}

	public Double getHardConfigTaxRate() {
		return hardConfigTaxRate;
	}

	public void setHardConfigTaxRate(Double hardConfigTaxRate) {
		this.hardConfigTaxRate = hardConfigTaxRate;
	}

	public Double getNetSoftTaxRate() {
		return netSoftTaxRate;
	}

	public void setNetSoftTaxRate(Double netSoftTaxRate) {
		this.netSoftTaxRate = netSoftTaxRate;
	}

	public Double getDangerSoftTaxRate() {
		return dangerSoftTaxRate;
	}

	public void setDangerSoftTaxRate(Double dangerSoftTaxRate) {
		this.dangerSoftTaxRate = dangerSoftTaxRate;
	}

	public Double getFlowContRolTaxRate() {
		return flowContRolTaxRate;
	}

	public void setFlowContRolTaxRate(Double flowContRolTaxRate) {
		this.flowContRolTaxRate = flowContRolTaxRate;
	}

	public Double getInstallSvrTaxRate() {
		return installSvrTaxRate;
	}

	public void setInstallSvrTaxRate(Double installSvrTaxRate) {
		this.installSvrTaxRate = installSvrTaxRate;
	}

	public Double getAiNetCarTaxRate() {
		return aiNetCarTaxRate;
	}

	public void setAiNetCarTaxRate(Double aiNetCarTaxRate) {
		this.aiNetCarTaxRate = aiNetCarTaxRate;
	}

	public Double getSmartShopTaxRate() {
		return smartShopTaxRate;
	}

	public void setSmartShopTaxRate(Double smartShopTaxRate) {
		this.smartShopTaxRate = smartShopTaxRate;
	}

	@Override
	public String toString() {
		return "ProductSplitHistoryPriceVo{" +
				"time=" + time +
				", productCode='" + productCode + '\'' +
				", salesPrice=" + salesPrice +
				", netHardMatribCode='" + netHardMatribCode + '\'' +
				", netHardPrice=" + netHardPrice +
				", hardWareMatribCode='" + hardWareMatribCode + '\'' +
				", hardWarePrice=" + hardWarePrice +
				", hardWareTaxRate=" + hardWareTaxRate +
				", hardConfigMatribCode='" + hardConfigMatribCode + '\'' +
				", hardConfigPrice=" + hardConfigPrice +
				", hardConfigTaxRate=" + hardConfigTaxRate +
				", netSoftMatribCode='" + netSoftMatribCode + '\'' +
				", netSoftPrice=" + netSoftPrice +
				", netSoftTaxRate=" + netSoftTaxRate +
				", netSoftMaterialName='" + netSoftMaterialName + '\'' +
				", DangerSoftMatribCode='" + DangerSoftMatribCode + '\'' +
				", DangerSoftPrice=" + DangerSoftPrice +
				", DangerSoftMaterialName='" + DangerSoftMaterialName + '\'' +
				", dangerSoftTaxRate=" + dangerSoftTaxRate +
				", ServiceMatribCode='" + ServiceMatribCode + '\'' +
				", ServicePrice=" + ServicePrice +
				", flowControlMatribCode='" + flowControlMatribCode + '\'' +
				", flowControlPrice=" + flowControlPrice +
				", flowContRolMaterialName='" + flowContRolMaterialName + '\'' +
				", flowContRolTaxRate=" + flowContRolTaxRate +
				", installSvrMatribCode='" + installSvrMatribCode + '\'' +
				", installSvrPrice=" + installSvrPrice +
				", installSvrMaterialName='" + installSvrMaterialName + '\'' +
				", installSvrTaxRate=" + installSvrTaxRate +
				", aiNetCarMatribCode='" + aiNetCarMatribCode + '\'' +
				", aiNetCarMatribPrice=" + aiNetCarMatribPrice +
				", aiNetCarMaterialName='" + aiNetCarMaterialName + '\'' +
				", aiNetCarTaxRate=" + aiNetCarTaxRate +
				", smartShopMatribCode='" + smartShopMatribCode + '\'' +
				", smartShopPrice=" + smartShopPrice +
				", smartShopName='" + smartShopName + '\'' +
				", smartShopTaxRate=" + smartShopTaxRate +
				", createdDate=" + createdDate +
				", status='" + status + '\'' +
				", serviceType=" + serviceType +
				'}';
	}
}
