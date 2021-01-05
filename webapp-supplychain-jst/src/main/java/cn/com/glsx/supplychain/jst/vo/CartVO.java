package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CartVO implements Serializable {
	
	//数据id
	private Integer id;

	//产品编码
	private String productCode;

	//产品名称
	private String productName;

	//商户编码（门店登陆时 这里是供应商  代理商登陆 这里返回NULL）
	private String MerchantCode;
	//商户名称（门店登陆时 这里是供应商  代理商登陆 这里返回NULL）
	private String MerchantName;

	//物料编码
	private String materialCode;

	//物料名称
	private String materialName;

	//服务期限
	private String serviceTime;

	//套餐
	private String packageOne;

	//产品价格
	private Double price;

	//N:产品在购物车  Y:移除出购物车
	private String deletedFlag;
	
	//订购数量
	private Integer orderCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMerchantCode() {
		return MerchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		MerchantCode = merchantCode;
	}

	public String getMerchantName() {
		return MerchantName;
	}

	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	@Override
	public String toString() {
		return "CartVo [id=" + id + ", productCode=" + productCode
				+ ", productName=" + productName + ", MerchantCode="
				+ MerchantCode + ", MerchantName=" + MerchantName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", serviceTime=" + serviceTime
				+ ", packageOne=" + packageOne + ", price=" + price
				+ ", deletedFlag=" + deletedFlag + ", orderCount=" + orderCount
				+ "]";
	}
	
}
