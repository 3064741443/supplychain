package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTProductSplitDTO implements Serializable{

	@ApiModelProperty(name = "productCode", notes = "产品编码", dataType = "string", required = true, example = "202007241346310752")
	private String productCode;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = true, example = "虎哥大屏导航")
    private String productName;
	@ApiModelProperty(name = "productTypeId", notes = "产品类型配置id", dataType = "int", required = true, example = "413")
	private Integer productTypeId;
	@ApiModelProperty(name = "productTypeName", notes = "产品类型配置名称", dataType = "string", required = true, example = "后视镜")
	private String productTypeName;
	@ApiModelProperty(name = "merchantCode", notes = "产品所属商户编码", dataType = "string", required = true, example = "44193004")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "产品所属商户名称", dataType = "string", required = true, example = "呼和浩特市弘路贸易有限公司")
    private String merchantName;
	@ApiModelProperty(name = "channelId", notes = "产品所属渠道配置id", dataType = "int", required = true, example = "422")
    private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "产品所属渠道配置名称", dataType = "int", required = true, example = "金融风控代销")
    private String channelName;
	@ApiModelProperty(name = "alias", notes = "产品别名", dataType = "string", required = true, example = "虎哥大屏导航")
    private String alias;
	@ApiModelProperty(name = "deviceQuantity", notes = "设备数", dataType = "int", required = true, example = "2")
    private Integer deviceQuantity;
	@ApiModelProperty(name = "serviceTime", notes = "服务期限", dataType = "string", required = true, example = "12")
    private String serviceTime;
	@ApiModelProperty(name = "packageOne", notes = "套餐", dataType = "string", required = true, example = "AI车联网-通用版一年期")
    private String packageOne;
	@ApiModelProperty(name = "total", notes = "数量", dataType = "int", required = true, example = "10")
	private Integer total;
	@ApiModelProperty(name = "unitPrice", notes = "产品单价", dataType = "double", required = true, example = "4999")
	private Double unitPrice;
	@ApiModelProperty(name = "remark", notes = "备注", dataType = "string", required = true, example = "4999")
	private String remark;
	@ApiModelProperty(name = "saleMode", notes = "销售模式 S:经销 D:代销", dataType = "string", required = true, example = "4999")
	private String saleMode;
	@ApiModelProperty(name = "listProductSplitDetailDto", notes = "产品物料详情列表", dataType = "list", required = true, example = "")
	private List<JXCMTProductSplitDetailDTO> listProductSplitDetailDto;
	@ApiModelProperty(name = "listVehicleDto", notes = "车辆信息表", dataType = "list", required = true, example = "")
	private List<JXCMTMerchantOrderVehicleDTO> listVehicleDto;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getDeviceQuantity() {
		return deviceQuantity;
	}
	public void setDeviceQuantity(Integer deviceQuantity) {
		this.deviceQuantity = deviceQuantity;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getPackageOne() {
		return packageOne;
	}
	public void setPackageOne(String packageOne) {
		this.packageOne = packageOne;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}
	public List<JXCMTProductSplitDetailDTO> getListProductSplitDetailDto() {
		return listProductSplitDetailDto;
	}
	public void setListProductSplitDetailDto(
			List<JXCMTProductSplitDetailDTO> listProductSplitDetailDto) {
		this.listProductSplitDetailDto = listProductSplitDetailDto;
	}
	public List<JXCMTMerchantOrderVehicleDTO> getListVehicleDto() {
		return listVehicleDto;
	}
	public void setListVehicleDto(List<JXCMTMerchantOrderVehicleDTO> listVehicleDto) {
		this.listVehicleDto = listVehicleDto;
	}
	@Override
	public String toString() {
		return "JXCMTProductSplitDTO [productCode=" + productCode
				+ ", productName=" + productName + ", productTypeId="
				+ productTypeId + ", productTypeName=" + productTypeName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", channelId=" + channelId + ", channelName="
				+ channelName + ", alias=" + alias + ", deviceQuantity="
				+ deviceQuantity + ", serviceTime=" + serviceTime
				+ ", packageOne=" + packageOne + ", total=" + total
				+ ", unitPrice=" + unitPrice + ", remark=" + remark
				+ ", saleMode=" + saleMode + ", listProductSplitDetailDto="
				+ listProductSplitDetailDto + ", listVehicleDto="
				+ listVehicleDto + "]";
	}
	
	
	
	
}
