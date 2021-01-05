package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTOrderInfoDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "Id", notes = "Id", dataType = "string", required = false, example = "")
    private Integer Id;
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = false, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "merchantOrder", notes = "子订单号", dataType = "string", required = false, example = "JXC202007241346310752")
	private String merchantOrder;
	@ApiModelProperty(name = "orderTime", notes = "子订单下单时间", dataType = "string", required = false, example = "JXC202007241346310752")
	private String orderTime;
	@ApiModelProperty(name = "status", notes = "发货状态 UF:未完成 OV:已完成 CL:已取消", dataType = "string", required = false, example = "")
    private String status;
	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号", dataType = "string", required = false, example = "")
	private String sendMerchantNo;
	@ApiModelProperty(name = "sendMerchantName", notes = "发往商户名称", dataType = "string", required = false, example = "")
	private String sendMerchantName;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "warehouseId", notes = "发货仓库id", dataType = "int", required = false, example = "")
	private Integer warehouseId;
	@ApiModelProperty(name = "warehouseName", notes = "发货仓库", dataType = "string", required = false, example = "")
	private String warehouseName;
	@ApiModelProperty(name = "total", notes = "总数", dataType = "int", required = false, example = "")
    private Integer total;
	@ApiModelProperty(name = "deviceId", notes = "设备小类", dataType = "int", required = false, example = "")
    private Integer deviceId;
	@ApiModelProperty(name = "deviceName", notes = "设备小类名称", dataType = "string", required = false, example = "")
    private String deviceName;
	@ApiModelProperty(name = "packageOne", notes = "商品套餐(激活)", dataType = "string", required = false, example = "")
    private String packageOne;
	@ApiModelProperty(name = "packageTwo", notes = "商品套餐(激活)2", dataType = "string", required = false, example = "")
    private String packageTwo;
	@ApiModelProperty(name = "attribCode", notes = "硬件配置编码", dataType = "string", required = false, example = "")
    private String attribCode;
	@ApiModelProperty(name = "mnumName", notes = "设备型号", dataType = "string", required = false, example = "")
    private String mnumName;
	@ApiModelProperty(name = "orNetId", notes = "是否联网", dataType = "string", required = false, example = "")
    private Integer orNetId;
	@ApiModelProperty(name = "orNet", notes = "是否联网", dataType = "string", required = false, example = "")
    private String orNet;
	@ApiModelProperty(name = "cardSelfId", notes = "自有卡还是外部卡", dataType = "string", required = false, example = "")
    private Integer cardSelfId;
	@ApiModelProperty(name = "cardSelf", notes = "自有卡还是外部卡", dataType = "string", required = false, example = "")
    private String cardSelf;
	@ApiModelProperty(name = "operatorMerchantNo", notes = "运营商户号", dataType = "string", required = false, example = "")
    private String operatorMerchantNo;
	@ApiModelProperty(name = "batch", notes = "批次号", dataType = "string", required = false, example = "")
    private String batch;
	@ApiModelProperty(name = "address", notes = "地址", dataType = "string", required = false, example = "")
    private String address;
	@ApiModelProperty(name = "contacts", notes = "联系人", dataType = "string", required = false, example = "")
    private String contacts;
	@ApiModelProperty(name = "mobile", notes = "联系电话", dataType = "string", required = false, example = "")
    private String mobile;
	@ApiModelProperty(name = "ndScan", notes = "是否扫码出库 Y:是  N:否", dataType = "string", required = false, example = "")
    private String ndScan;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "factMaterialCode", notes = "工厂物料编码", dataType = "string", required = false, example = "")
    private String factMaterialCode;
	@ApiModelProperty(name = "factMaterialName", notes = "工厂物料名称", dataType = "string", required = false, example = "")
    private String factMaterialName;
	@ApiModelProperty(name = "remark", notes = "备注", dataType = "string", required = false, example = "")
    private String remark;
	@ApiModelProperty(name = "sendQuantities", notes = "已发货数", dataType = "int", required = false, example = "")
	private Integer sendQuantities;
	@ApiModelProperty(name = "createdTime", notes = "创建时间", dataType = "string", required = false, example = "")
	private String createdTime;
	@ApiModelProperty(name = "modelDevice", notes = "是否样机", dataType = "string", required = false, example = "")
	private String modelDevice;
	@ApiModelProperty(name = "urlDispatchBills", notes = "发货单下载地址", dataType = "string", required = false, example = "")
	private String urlDispatchBills;
	@ApiModelProperty(name = "orderTimeStart", notes = "下单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeStart;
	@ApiModelProperty(name = "orderTimeEnd", notes = "下单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeEnd;
	@ApiModelProperty(name = "orderDistribTimeStart", notes = "分配订单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderDistribTimeStart;
	@ApiModelProperty(name = "orderDistribTimeEnd", notes = "分配订单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderDistribTimeEnd;
	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类", dataType = "int", required = false, example = "")
    private Integer deviceTypeId;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备大类名称", dataType = "string", required = false, example = "")
    private String deviceTypeName;
	@ApiModelProperty(name = "vehicleId", notes = "关系表id", dataType = "int", required = false, example = "")
    private Integer vehicleId;
	@ApiModelProperty(name = "bsParentBrandName", notes = "父品牌名称", dataType = "string", required = false, example = "")
    private String bsParentBrandName;
	@ApiModelProperty(name = "bsSubBrandName", notes = "子品牌名称", dataType = "string", required = false, example = "")
    private String bsSubBrandName;
	@ApiModelProperty(name = "bsAudiName", notes = "车系名称", dataType = "string", required = false, example = "")
    private String bsAudiName;
	@ApiModelProperty(name = "bsMotorcycle", notes = "车型名称", dataType = "string", required = false, example = "")
    private String bsMotorcycle;
	@ApiModelProperty(name = "bsRemark", notes = "备注 车价/颜色等", dataType = "string", required = false, example = "")
    private String bsRemark;
	@ApiModelProperty(name = "checkRemark", notes = "审核备注", dataType = "string", required = false, example = "")
    private String checkRemark;
	@ApiModelProperty(name = "logisticsDto", notes = "物流信息", dataType = "list", required = false, example = "")
	private List<JXCMTBsLogisticsDTO> logisticsDto;
	@ApiModelProperty(name = "vehicleDto", notes = "车型车系信息", dataType = "object", required = false, example = "")
	private JXCMTBsMerchantOrderVehicleDTO vehicleDto;
	@ApiModelProperty(name = "bsQueryType", notes = "查询方式T:扫码工具", dataType = "object", required = false, example = "")
	private String bsQueryType;
	@ApiModelProperty(name = "fastenConfigDesc", notes = "广汇18家固定配置", dataType = "object", required = false, example = "")
	private String fastenConfigDesc;
	@ApiModelProperty(name = "optionConfigDesc", notes = "广汇18家选项配置", dataType = "object", required = false, example = "")
	private String optionConfigDesc;
	@ApiModelProperty(name = "sendTime", notes = "发货单发货时间", dataType = "string", required = false, example = "")
	private String sendTime;
	@ApiModelProperty(name = "startSendTime", notes = "发货单管理发货开始时间(搜索条件)", dataType = "string", required = false, example = "")
	private String startSendTime;
	@ApiModelProperty(name = "endSendTime", notes = "发货单管理发货结束时间(搜索条件)", dataType = "string", required = false, example = "")
	private String endSendTime;

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStartSendTime() {
		return startSendTime;
	}

	public void setStartSendTime(String startSendTime) {
		this.startSendTime = startSendTime;
	}

	public String getEndSendTime() {
		return endSendTime;
	}

	public void setEndSendTime(String endSendTime) {
		this.endSendTime = endSendTime;
	}

	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getCardSelf() {
		return cardSelf;
	}
	public void setCardSelf(String cardSelf) {
		this.cardSelf = cardSelf;
	}
	public String getOrNet() {
		return orNet;
	}
	public void setOrNet(String orNet) {
		this.orNet = orNet;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public String getSendMerchantName() {
		return sendMerchantName;
	}
	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getPackageOne() {
		return packageOne;
	}
	public void setPackageOne(String packageOne) {
		this.packageOne = packageOne;
	}
	public String getPackageTwo() {
		return packageTwo;
	}
	public void setPackageTwo(String packageTwo) {
		this.packageTwo = packageTwo;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	public String getMnumName() {
		return mnumName;
	}
	public void setMnumName(String mnumName) {
		this.mnumName = mnumName;
	}
	public String getOperatorMerchantNo() {
		return operatorMerchantNo;
	}
	public void setOperatorMerchantNo(String operatorMerchantNo) {
		this.operatorMerchantNo = operatorMerchantNo;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
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
	public String getNdScan() {
		return ndScan;
	}
	public void setNdScan(String ndScan) {
		this.ndScan = ndScan;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSendQuantities() {
		return sendQuantities;
	}
	public void setSendQuantities(Integer sendQuantities) {
		this.sendQuantities = sendQuantities;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public List<JXCMTBsLogisticsDTO> getLogisticsDto() {
		return logisticsDto;
	}
	public void setLogisticsDto(List<JXCMTBsLogisticsDTO> logisticsDto) {
		this.logisticsDto = logisticsDto;
	}
	public JXCMTBsMerchantOrderVehicleDTO getVehicleDto() {
		return vehicleDto;
	}
	public void setVehicleDto(JXCMTBsMerchantOrderVehicleDTO vehicleDto) {
		this.vehicleDto = vehicleDto;
	}
	public String getOrderTimeStart() {
		return orderTimeStart;
	}
	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}
	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}
	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	
	public String getModelDevice() {
		return modelDevice;
	}
	public void setModelDevice(String modelDevice) {
		this.modelDevice = modelDevice;
	}
	public String getUrlDispatchBills() {
		return urlDispatchBills;
	}
	public void setUrlDispatchBills(String urlDispatchBills) {
		this.urlDispatchBills = urlDispatchBills;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getOrNetId() {
		return orNetId;
	}
	public void setOrNetId(Integer orNetId) {
		this.orNetId = orNetId;
	}
	public Integer getCardSelfId() {
		return cardSelfId;
	}
	public void setCardSelfId(Integer cardSelfId) {
		this.cardSelfId = cardSelfId;
	}
	public String getBsParentBrandName() {
		return bsParentBrandName;
	}
	public void setBsParentBrandName(String bsParentBrandName) {
		this.bsParentBrandName = bsParentBrandName;
	}
	public String getBsSubBrandName() {
		return bsSubBrandName;
	}
	public void setBsSubBrandName(String bsSubBrandName) {
		this.bsSubBrandName = bsSubBrandName;
	}
	public String getBsAudiName() {
		return bsAudiName;
	}
	public void setBsAudiName(String bsAudiName) {
		this.bsAudiName = bsAudiName;
	}
	public String getBsMotorcycle() {
		return bsMotorcycle;
	}
	public void setBsMotorcycle(String bsMotorcycle) {
		this.bsMotorcycle = bsMotorcycle;
	}
	public String getBsRemark() {
		return bsRemark;
	}
	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
	}
	
	public String getBsQueryType() {
		return bsQueryType;
	}
	public void setBsQueryType(String bsQueryType) {
		this.bsQueryType = bsQueryType;
	}
	
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}
	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}
	public String getOptionConfigDesc() {
		return optionConfigDesc;
	}
	public void setOptionConfigDesc(String optionConfigDesc) {
		this.optionConfigDesc = optionConfigDesc;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getFactMaterialCode() {
		return factMaterialCode;
	}
	public void setFactMaterialCode(String factMaterialCode) {
		this.factMaterialCode = factMaterialCode;
	}
	public String getFactMaterialName() {
		return factMaterialName;
	}
	public void setFactMaterialName(String factMaterialName) {
		this.factMaterialName = factMaterialName;
	}
	public String getOrderDistribTimeStart() {
		return orderDistribTimeStart;
	}
	public void setOrderDistribTimeStart(String orderDistribTimeStart) {
		this.orderDistribTimeStart = orderDistribTimeStart;
	}
	public String getOrderDistribTimeEnd() {
		return orderDistribTimeEnd;
	}
	public void setOrderDistribTimeEnd(String orderDistribTimeEnd) {
		this.orderDistribTimeEnd = orderDistribTimeEnd;
	}

	@Override
	public String toString() {
		return "JXCMTOrderInfoDTO{" +
				"Id=" + Id +
				", dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", orderTime='" + orderTime + '\'' +
				", status='" + status + '\'' +
				", sendMerchantNo='" + sendMerchantNo + '\'' +
				", sendMerchantName='" + sendMerchantName + '\'' +
				", channelId=" + channelId +
				", channelName='" + channelName + '\'' +
				", warehouseId=" + warehouseId +
				", warehouseName='" + warehouseName + '\'' +
				", total=" + total +
				", deviceId=" + deviceId +
				", deviceName='" + deviceName + '\'' +
				", packageOne='" + packageOne + '\'' +
				", packageTwo='" + packageTwo + '\'' +
				", attribCode='" + attribCode + '\'' +
				", mnumName='" + mnumName + '\'' +
				", orNetId=" + orNetId +
				", orNet='" + orNet + '\'' +
				", cardSelfId=" + cardSelfId +
				", cardSelf='" + cardSelf + '\'' +
				", operatorMerchantNo='" + operatorMerchantNo + '\'' +
				", batch='" + batch + '\'' +
				", address='" + address + '\'' +
				", contacts='" + contacts + '\'' +
				", mobile='" + mobile + '\'' +
				", ndScan='" + ndScan + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", factMaterialCode='" + factMaterialCode + '\'' +
				", factMaterialName='" + factMaterialName + '\'' +
				", remark='" + remark + '\'' +
				", sendQuantities=" + sendQuantities +
				", createdTime='" + createdTime + '\'' +
				", modelDevice='" + modelDevice + '\'' +
				", urlDispatchBills='" + urlDispatchBills + '\'' +
				", orderTimeStart='" + orderTimeStart + '\'' +
				", orderTimeEnd='" + orderTimeEnd + '\'' +
				", orderDistribTimeStart='" + orderDistribTimeStart + '\'' +
				", orderDistribTimeEnd='" + orderDistribTimeEnd + '\'' +
				", deviceTypeId=" + deviceTypeId +
				", deviceTypeName='" + deviceTypeName + '\'' +
				", vehicleId=" + vehicleId +
				", bsParentBrandName='" + bsParentBrandName + '\'' +
				", bsSubBrandName='" + bsSubBrandName + '\'' +
				", bsAudiName='" + bsAudiName + '\'' +
				", bsMotorcycle='" + bsMotorcycle + '\'' +
				", bsRemark='" + bsRemark + '\'' +
				", checkRemark='" + checkRemark + '\'' +
				", logisticsDto=" + logisticsDto +
				", vehicleDto=" + vehicleDto +
				", bsQueryType='" + bsQueryType + '\'' +
				", fastenConfigDesc='" + fastenConfigDesc + '\'' +
				", optionConfigDesc='" + optionConfigDesc + '\'' +
				", sendTime='" + sendTime + '\'' +
				", startSendTime='" + startSendTime + '\'' +
				", endSendTime='" + endSendTime + '\'' +
				'}';
	}
}
