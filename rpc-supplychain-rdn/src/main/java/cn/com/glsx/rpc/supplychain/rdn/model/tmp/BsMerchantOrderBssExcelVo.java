package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BsMerchantOrderBssExcelVo implements Serializable{

	//子订单信息
	private String moOrderCode;
	private String merchantOrder;
	private String merchantName;
	private String channelName;
	private String productName;
	private String orderMaterialCode;
	private String orderMaterialName;
	private String materialCode;	
    private String materialName;
    private String mdeviceTypeName;
    private Double price;
    private String checkRemark;
    private String subjectName;
    private String insure;
    //发货信息
    private String dispatchOrderCode;
    private Integer bsTotal;
    private Integer bsCheckQuantity;
    private Integer bsSendQuantity;
    private Integer bsOweQuantity;
    private String MotorcycleDesc;
    private String bsRemark;
    private String warehouseName;
    private String address;
    private String contacts;
    private String mobile;
    private String logisticsDesc;
    private String sendTime;
	private String orderTimeStr;
	private String checkTimeStr;
	private String  statusStr;
	private String checkBy;
	private String fastenConfigDesc;
	private String setOptionConfigDesc;
	@ExcelResources(title = "下单时间", order = 30)
	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	@ExcelResources(title = "审核时间", order = 29)
	public String getCheckTimeStr() {
		return checkTimeStr;
	}

	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}

	@ExcelResources(title = "订单状态",order = 25)
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	@ExcelResources(title = "审核人", order = 28)
	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	@ExcelResources(title = "固定配置", order = 26)
	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}

	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}

	@ExcelResources(title = "选择配置", order = 27)
	public String getSetOptionConfigDesc() {
		return setOptionConfigDesc;
	}

	public void setSetOptionConfigDesc(String setOptionConfigDesc) {
		this.setOptionConfigDesc = setOptionConfigDesc;
	}
    
    @ExcelResources(title = "主订单号",order = 2)
	public String getMoOrderCode() {
		return moOrderCode;
	}
	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}
	@ExcelResources(title = "子订单号",order = 3)
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	@ExcelResources(title = "下单客户",order = 1)
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@ExcelResources(title = "所属渠道",order = 0)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelResources(title = "产品名称",order = 4)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@ExcelResources(title = "订单物料编码",order = 5)
	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}
	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}
	@ExcelResources(title = "订单物料名称",order = 6)
	public String getOrderMaterialName() {
		return orderMaterialName;
	}
	public void setOrderMaterialName(String orderMaterialName) {
		this.orderMaterialName = orderMaterialName;
	}
	@ExcelResources(title = "实发物料编码",order = 7)
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@ExcelResources(title = "实发物料名称",order = 8)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelResources(title = "设备类型",order = 9)
	public String getMdeviceTypeName() {
		return mdeviceTypeName;
	}
	public void setMdeviceTypeName(String mdeviceTypeName) {
		this.mdeviceTypeName = mdeviceTypeName;
	}
	@ExcelResources(title = "单价",order = 10)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@ExcelResources(title = "审核备注",order = 11)
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	@ExcelResources(title = "项目",order = 12)
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	@ExcelResources(title = "是否投保",order = 13)
	public String getInsure() {
		return insure;
	}
	public void setInsure(String insure) {
		this.insure = insure;
	}
	
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	@ExcelResources(title = "订购数量",order = 14)
	public Integer getBsTotal() {
		return bsTotal;
	}
	public void setBsTotal(Integer bsTotal) {
		this.bsTotal = bsTotal;
	}
	@ExcelResources(title = "审核数量",order = 15)
	public Integer getBsCheckQuantity() {
		return bsCheckQuantity;
	}
	public void setBsCheckQuantity(Integer bsCheckQuantity) {
		this.bsCheckQuantity = bsCheckQuantity;
	}
	@ExcelResources(title = "已发数量",order = 16)
	public Integer getBsSendQuantity() {
		return bsSendQuantity;
	}
	public void setBsSendQuantity(Integer bsSendQuantity) {
		this.bsSendQuantity = bsSendQuantity;
	}
	@ExcelResources(title = "欠货数量",order = 17)
	public Integer getBsOweQuantity() {
		return bsOweQuantity;
	}
	public void setBsOweQuantity(Integer bsOweQuantity) {
		this.bsOweQuantity = bsOweQuantity;
	}
	@ExcelResources(title = "车辆信息",order = 18)
	public String getMotorcycleDesc() {
		return MotorcycleDesc;
	}
	public void setMotorcycleDesc(String motorcycleDesc) {
		MotorcycleDesc = motorcycleDesc;
	}
	public String getBsRemark() {
		return bsRemark;
	}
	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
	}
	@ExcelResources(title = "出库仓库",order = 21)
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	@ExcelResources(title = "联系地址",order = 24)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@ExcelResources(title = "联系人",order = 22)
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	@ExcelResources(title = "联系电话",order = 23)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ExcelResources(title = "物流信息",order = 20)
	public String getLogisticsDesc() {
		return logisticsDesc;
	}
	public void setLogisticsDesc(String logisticsDesc) {
		this.logisticsDesc = logisticsDesc;
	}
	@ExcelResources(title = "发货时间",order = 19)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderBssExcelVo [moOrderCode=" + moOrderCode
				+ ", merchantOrder=" + merchantOrder + ", merchantName="
				+ merchantName + ", channelName=" + channelName
				+ ", productName=" + productName + ", orderMaterialCode="
				+ orderMaterialCode + ", orderMaterialName="
				+ orderMaterialName + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", mdeviceTypeName="
				+ mdeviceTypeName + ", price=" + price + ", checkRemark="
				+ checkRemark + ", subjectName=" + subjectName + ", insure="
				+ insure + ", dispatchOrderCode=" + dispatchOrderCode
				+ ", bsTotal=" + bsTotal + ", bsCheckQuantity="
				+ bsCheckQuantity + ", bsSendQuantity=" + bsSendQuantity
				+ ", bsOweQuantity=" + bsOweQuantity + ", MotorcycleDesc="
				+ MotorcycleDesc + ", bsRemark=" + bsRemark
				+ ", warehouseName=" + warehouseName + ", address=" + address
				+ ", contacts=" + contacts + ", mobile=" + mobile
				+ ", logisticsDesc=" + logisticsDesc + ", sendTime=" + sendTime
				+ "]";
	}
	
    
    
    	
}
