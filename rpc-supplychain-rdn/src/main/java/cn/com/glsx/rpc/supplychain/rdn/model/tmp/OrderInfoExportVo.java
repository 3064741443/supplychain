package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderInfoExportVo implements Serializable{
	@ExcelProperty(value = "发货单编号", index = 1)
    private String dispatchOrderCode;

	@ExcelProperty(value = "子订单号", index = 2)
	private String merchantOrder;

	@ExcelProperty(value = "车辆信息",index =3)
	private String motorcycleDesc;

	@ExcelProperty(value = "需求总数",index =4)
	private Integer total;

	@ExcelProperty(value = "已发货数",index =5)
	private Integer sendQuantities;

	@ExcelProperty(value = "订单状态",index =6)
	private String status;

	@ExcelProperty(value = "硬件设备",index =7)
	private String deviceName;

	@ExcelProperty(value = "物料编码",index =8)
	private String materialCode;

	@ExcelProperty(value = "物料名称",index =9)
	private String materialName;

	@ExcelProperty(value = "设备型号", index = 10)
	private String mnumName;


	@ExcelProperty(value = "商品编号", index = 11)
	private String packageOne;

	@ExcelProperty(value = "工厂编号", index = 12)
	private String factMaterialCode;

	@ExcelProperty(value = "发货仓库",index = 13)
	private String warehouseName;

	@ExcelProperty(value = "发往商户名称",index = 14)
	private String sendMerchantName;

	@ExcelProperty(value = "发货地址",index = 15)
	private String address;

	@ExcelProperty(value = "联系人",index = 16)
	private String contacts;

	@ExcelProperty(value = "联系电话",index =17)
	private String mobile;

	@ExcelProperty(value = "创建时间",index =18)
	private String createdTime;

	@ExcelProperty(value = "备注",index =19)
	private String remark;

	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}

	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}

	public String getMerchantOrder() {
		return merchantOrder;
	}

	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}

	public String getMotorcycleDesc() {
		return motorcycleDesc;
	}

	public void setMotorcycleDesc(String motorcycleDesc) {
		this.motorcycleDesc = motorcycleDesc;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSendQuantities() {
		return sendQuantities;
	}

	public void setSendQuantities(Integer sendQuantities) {
		this.sendQuantities = sendQuantities;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public String getMnumName() {
		return mnumName;
	}

	public void setMnumName(String mnumName) {
		this.mnumName = mnumName;
	}

	public String getPackageOne() {
		return packageOne;
	}

	public void setPackageOne(String packageOne) {
		this.packageOne = packageOne;
	}

	public String getFactMaterialCode() {
		return factMaterialCode;
	}

	public void setFactMaterialCode(String factMaterialCode) {
		this.factMaterialCode = factMaterialCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSendMerchantName() {
		return sendMerchantName;
	}

	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "JXCMTOrderInfoExportVo{" +
				"dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", motorcycleDesc='" + motorcycleDesc + '\'' +
				", total=" + total +
				", sendQuantities=" + sendQuantities +
				", status='" + status + '\'' +
				", deviceName='" + deviceName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", mnumName='" + mnumName + '\'' +
				", packageOne='" + packageOne + '\'' +
				", factMaterialCode='" + factMaterialCode + '\'' +
				", warehouseName='" + warehouseName + '\'' +
				", sendMerchantName='" + sendMerchantName + '\'' +
				", address='" + address + '\'' +
				", contacts='" + contacts + '\'' +
				", mobile='" + mobile + '\'' +
				", createdTime='" + createdTime + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
