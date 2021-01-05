package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderDetailDTO implements Serializable{

	@ApiModelProperty(name = "merchantCode", notes = "下单商户号", dataType = "string", required = false, example = "")
	private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "productCode", notes = "产品编号", dataType = "string", required = false, example = "")
	private String productCode;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "")
    private String productName;
	@ApiModelProperty(name = "productTypeId", notes = "产品类型id", dataType = "int", required = false, example = "")
	private Integer productTypeId;
	@ApiModelProperty(name = "productTypeName", notes = "产品类型名称", dataType = "int", required = false, example = "")
	private String productTypeName;
	@ApiModelProperty(name = "packageOne", notes = "产品套餐", dataType = "string", required = false, example = "")
	private String packageOne;
	@ApiModelProperty(name = "serviceTime", notes = "服务期", dataType = "string", required = false, example = "")
	private String serviceTime;
	@ApiModelProperty(name = "productTotal", notes = "产品订购总数", dataType = "string", required = false, example = "")
	private Integer productTotal;
	@ApiModelProperty(name = "remarks", notes = "订单备注", dataType = "string", required = false, example = "")
	private String remarks;
	@ApiModelProperty(name = "subjectId", notes = "项目id", dataType = "Integer", required = false, example = "")
	private Integer subjectId;
	@ApiModelProperty(name = "subjectName", notes = "项目名称", dataType = "string", required = false, example = "")
	private String subjectName;
	@ApiModelProperty(name = "modelDevice", notes = "是否样机", dataType = "string", required = false, example = "")
	private String modelDevice;
	@ApiModelProperty(name = "insure", notes = "是否投保", dataType = "string", required = false, example = "")
	private String insure;
	@ApiModelProperty(name = "checkRemark", notes = "审核备注", dataType = "string", required = false, example = "")
	private String checkRemark;
	@ApiModelProperty(name = "checkQuantity", notes = "审核总数", dataType = "string", required = false, example = "")
	private Integer checkQuantity;
	@ApiModelProperty(name = "orderMaterialInfo", notes = "订购物料信息", dataType = "object", required = false, example = "")
	private JXCMTBsMaterialInfoDTO orderMaterialInfoDto;
	@ApiModelProperty(name = "checkMaterialInfoDto", notes = "审核物料信息", dataType = "object", required = false, example = "")
	private JXCMTBsMaterialInfoDTO checkMaterialInfoDto;
	@ApiModelProperty(name = "listOrderVehicleDto", notes = "子订单车辆发货信息", dataType = "list", required = false, example = "")
	private List<JXCMTBsMerchantOrderVehicleDTO> listOrderVehicleDto;
	@ApiModelProperty(name = "listDispatchOrders", notes = "子订单发货信息详情", dataType = "list", required = false, example = "")
	private List<JXCMTOrderInfoDTO> listDispatchOrders;
	@ApiModelProperty(name = "fastenConfigDesc", notes = "固定配置", dataType = "object", required = false, example = "")
	private  String fastenConfigDesc;
	@ApiModelProperty(name = "optionConfigDesc", notes = "选项配置", dataType = "object", required = false, example = "")
	private  String optionConfigDesc;

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

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getPackageOne() {
		return packageOne;
	}
	public void setPackageOne(String packageOne) {
		this.packageOne = packageOne;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public Integer getProductTotal() {
		return productTotal;
	}
	public void setProductTotal(Integer productTotal) {
		this.productTotal = productTotal;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getModelDevice() {
		return modelDevice;
	}
	public void setModelDevice(String modelDevice) {
		this.modelDevice = modelDevice;
	}
	public String getInsure() {
		return insure;
	}
	public void setInsure(String insure) {
		this.insure = insure;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public JXCMTBsMaterialInfoDTO getOrderMaterialInfoDto() {
		return orderMaterialInfoDto;
	}
	public void setOrderMaterialInfoDto(JXCMTBsMaterialInfoDTO orderMaterialInfoDto) {
		this.orderMaterialInfoDto = orderMaterialInfoDto;
	}
	public JXCMTBsMaterialInfoDTO getCheckMaterialInfoDto() {
		return checkMaterialInfoDto;
	}
	public void setCheckMaterialInfoDto(JXCMTBsMaterialInfoDTO checkMaterialInfoDto) {
		this.checkMaterialInfoDto = checkMaterialInfoDto;
	}
	public List<JXCMTBsMerchantOrderVehicleDTO> getListOrderVehicleDto() {
		return listOrderVehicleDto;
	}
	public void setListOrderVehicleDto(
			List<JXCMTBsMerchantOrderVehicleDTO> listOrderVehicleDto) {
		this.listOrderVehicleDto = listOrderVehicleDto;
	}
	public List<JXCMTOrderInfoDTO> getListDispatchOrders() {
		return listDispatchOrders;
	}
	public void setListDispatchOrders(List<JXCMTOrderInfoDTO> listDispatchOrders) {
		this.listDispatchOrders = listDispatchOrders;
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
	public Integer getCheckQuantity() {
		return checkQuantity;
	}
	public void setCheckQuantity(Integer checkQuantity) {
		this.checkQuantity = checkQuantity;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderDetailDTO{" +
				"merchantCode='" + merchantCode + '\'' +
				", merchantName='" + merchantName + '\'' +
				", channelId=" + channelId +
				", channelName='" + channelName + '\'' +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", productTypeId=" + productTypeId +
				", productTypeName='" + productTypeName + '\'' +
				", packageOne='" + packageOne + '\'' +
				", serviceTime='" + serviceTime + '\'' +
				", productTotal=" + productTotal +
				", remarks='" + remarks + '\'' +
				", subjectId=" + subjectId +
				", subjectName='" + subjectName + '\'' +
				", modelDevice='" + modelDevice + '\'' +
				", insure='" + insure + '\'' +
				", checkRemark='" + checkRemark + '\'' +
				", checkQuantity=" + checkQuantity +
				", orderMaterialInfoDto=" + orderMaterialInfoDto +
				", checkMaterialInfoDto=" + checkMaterialInfoDto +
				", listOrderVehicleDto=" + listOrderVehicleDto +
				", listDispatchOrders=" + listDispatchOrders +
				", fastenConfigDesc='" + fastenConfigDesc + '\'' +
				", optionConfigDesc='" + optionConfigDesc + '\'' +
				'}';
	}


}
