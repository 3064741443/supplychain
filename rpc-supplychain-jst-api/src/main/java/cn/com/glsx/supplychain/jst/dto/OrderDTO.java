package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class OrderDTO extends BaseDTO implements Serializable{

	//订单编号
	private String orderCode;
	//采购门店编码（门店订单）
	private String shopCode;
	//采购门店名称（门店订单）
	private String shopName;
	//代理商商户code
    private String agentMerchantCode;
    //代理商商户名称
    private String agentMerchantName;
	//产品编码
	private String productCode;
	//产品名称
	private String productName;
	//套餐
	private String packageOne;
	//服务期限
    private String serviceTime;
    //产品单价
    private Double price;
    //订购总数
    private Integer orderCount;
    //审核数
    private Integer checkCount;
    //已发数量
    private Integer sendCount;
    //未发货数
    private Integer waitCount;
    //物料编码
    private String materialCode;
    //物料名称
    private String materialName;
    //地址信息
    private BsAddressDTO addressDto;
    //物流信息
    private BsLogisticsDTO logisticsDto;
    //订单明细
    private List<OrderDetailDTO> listOrderDetailDto;
    
    
    
	public List<OrderDetailDTO> getListOrderDetailDto() {
		return listOrderDetailDto;
	}
	public void setListOrderDetailDto(List<OrderDetailDTO> listOrderDetailDto) {
		this.listOrderDetailDto = listOrderDetailDto;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}
	
	public BsAddressDTO getAddressDto() {
		return addressDto;
	}
	public void setAddressDto(BsAddressDTO addressDto) {
		this.addressDto = addressDto;
	}
	public BsLogisticsDTO getLogisticsDto() {
		return logisticsDto;
	}
	public void setLogisticsDto(BsLogisticsDTO logisticsDto) {
		this.logisticsDto = logisticsDto;
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
	public String getAgentMerchantCode() {
		return agentMerchantCode;
	}
	public void setAgentMerchantCode(String agentMerchantCode) {
		this.agentMerchantCode = agentMerchantCode;
	}
	public String getAgentMerchantName() {
		return agentMerchantName;
	}
	public void setAgentMerchantName(String agentMerchantName) {
		this.agentMerchantName = agentMerchantName;
	}
	@Override
	public String toString() {
		return "OrderDTO [orderCode=" + orderCode + ", shopCode=" + shopCode
				+ ", shopName=" + shopName + ", agentMerchantCode="
				+ agentMerchantCode + ", agentMerchantName="
				+ agentMerchantName + ", productCode=" + productCode
				+ ", productName=" + productName + ", packageOne=" + packageOne
				+ ", serviceTime=" + serviceTime + ", price=" + price
				+ ", orderCount=" + orderCount + ", checkCount=" + checkCount
				+ ", sendCount=" + sendCount + ", waitCount=" + waitCount
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", addressDto=" + addressDto
				+ ", logisticsDto=" + logisticsDto + ", listOrderDetailDto="
				+ listOrderDetailDto + "]";
	}
	
	
    
    
}
