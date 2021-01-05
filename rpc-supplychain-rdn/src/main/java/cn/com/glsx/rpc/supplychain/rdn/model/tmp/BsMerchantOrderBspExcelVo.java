package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BsMerchantOrderBspExcelVo implements Serializable{

	//子订单信息
	private String merchantOrderCode;
	private String merchantName;
	private String channelName;
	private String materialCode;	
    private String materialName;
    private String mdeviceTypeName;
    private String checkRemark;
    private String checkTime;	
	private String checkBy;
	private String subjectName;
    //发货信息
    private String dispatchOrderCode;
    private String MotorcycleDesc;
    private String dispatchOrderStatus;
    private Integer bsCheckQuantity;
    private Integer bsSendQuantity;
    private Integer bsOweQuantity;
    private String address;
    private String contacts;
    private String mobile;
    private String warehouseName;
    //物流信息
    private String orderNumber;
    private String company;
    private Integer shipmentsQuantity;
    private String sendTime;
	private String fastenConfigDesc;
	private String optionConfigDesc;
	@ExcelResources(title = "子订单号",order = 22)
	public String getMerchantOrderCode() {
		return merchantOrderCode;
	}

	public void setMerchantOrderCode(String merchantOrderCode) {
		this.merchantOrderCode = merchantOrderCode;
	}
	@ExcelResources(title = "固定配置",order = 23)
	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}

	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}
	@ExcelResources(title = "选择配置",order = 24)
	public String getOptionConfigDesc() {
		return optionConfigDesc;
	}

	public void setOptionConfigDesc(String optionConfigDesc) {
		this.optionConfigDesc = optionConfigDesc;
	}

	@ExcelResources(title = "下单客户",order = 0)
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@ExcelResources(title = "所属渠道",order = 1)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelResources(title = "物料编号",order = 2)
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@ExcelResources(title = "物料名称",order = 3)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelResources(title = "设备类型",order = 4)
	public String getMdeviceTypeName() {
		return mdeviceTypeName;
	}
	public void setMdeviceTypeName(String mdeviceTypeName) {
		this.mdeviceTypeName = mdeviceTypeName;
	}
	@ExcelResources(title = "审核备注",order = 13)
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	@ExcelResources(title = "审核时间",order = 11)
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	@ExcelResources(title = "审核人",order = 12)
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	@ExcelResources(title = "项目",order = 17)
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	@ExcelResources(title = "发货单号",order = 5)
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	@ExcelResources(title = "车辆属性",order = 6)
	public String getMotorcycleDesc() {
		return MotorcycleDesc;
	}
	public void setMotorcycleDesc(String motorcycleDesc) {
		MotorcycleDesc = motorcycleDesc;
	}
	@ExcelResources(title = "订单状态",order = 7)
	public String getDispatchOrderStatus() {
		return dispatchOrderStatus;
	}
	public void setDispatchOrderStatus(String dispatchOrderStatus) {
		this.dispatchOrderStatus = dispatchOrderStatus;
	}
	@ExcelResources(title = "审核数量",order = 8)
	public Integer getBsCheckQuantity() {
		return bsCheckQuantity;
	}
	public void setBsCheckQuantity(Integer bsCheckQuantity) {
		this.bsCheckQuantity = bsCheckQuantity;
	}
	@ExcelResources(title = "已发数量",order = 9)
	public Integer getBsSendQuantity() {
		return bsSendQuantity;
	}
	public void setBsSendQuantity(Integer bsSendQuantity) {
		this.bsSendQuantity = bsSendQuantity;
	}
	@ExcelResources(title = "欠发数量",order = 10)
	public Integer getBsOweQuantity() {
		return bsOweQuantity;
	}
	public void setBsOweQuantity(Integer bsOweQuantity) {
		this.bsOweQuantity = bsOweQuantity;
	}
	@ExcelResources(title = "地址",order = 16)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@ExcelResources(title = "联系人",order = 14)
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	@ExcelResources(title = "联系电话",order = 15)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ExcelResources(title = "出库仓库",order = 18)
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	@ExcelResources(title = "物流单号",order = 20)
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	@ExcelResources(title = "物流公司",order = 19)
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@ExcelResources(title = "物流数量",order = 21)
	public Integer getShipmentsQuantity() {
		return shipmentsQuantity;
	}
	public void setShipmentsQuantity(Integer shipmentsQuantity) {
		this.shipmentsQuantity = shipmentsQuantity;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderBspExcelVo [merchantName=" + merchantName
				+ ", channelName=" + channelName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", mdeviceTypeName=" + mdeviceTypeName + ", checkRemark="
				+ checkRemark + ", checkTime=" + checkTime + ", checkBy="
				+ checkBy + ", subjectName=" + subjectName
				+ ", dispatchOrderCode=" + dispatchOrderCode
				+ ", MotorcycleDesc=" + MotorcycleDesc
				+ ", dispatchOrderStatus=" + dispatchOrderStatus
				+ ", bsCheckQuantity=" + bsCheckQuantity + ", bsSendQuantity="
				+ bsSendQuantity + ", bsOweQuantity=" + bsOweQuantity
				+ ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", warehouseName=" + warehouseName
				+ ", orderNumber=" + orderNumber + ", company=" + company
				+ ", shipmentsQuantity=" + shipmentsQuantity + ", sendTime="
				+ sendTime + "]";
	}
    
	
    
    
}
