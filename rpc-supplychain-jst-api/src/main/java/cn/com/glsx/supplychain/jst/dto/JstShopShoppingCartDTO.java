package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JstShopShoppingCartDTO extends BaseDTO implements Serializable{

	private Integer id;

    private String productCode;

    private String productName;

    private String shopCode;

    private String shopName;

    private String agentMerchantCode;

    private String agentMerchantName;

    private String materialCode;

    private String materialName;

    private String serviceTime;

    private String packageOne;

    private Double price;
    
    private Integer total;
    
    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    //无订单发货 虚拟购物车
    private String Sn;
    
    

	public String getSn() {
		return Sn;
	}

	public void setSn(String sn) {
		Sn = sn;
	}

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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "JstShopShoppingCartDTO [id=" + id + ", productCode="
				+ productCode + ", productName=" + productName + ", shopCode="
				+ shopCode + ", shopName=" + shopName + ", agentMerchantCode="
				+ agentMerchantCode + ", agentMerchantName="
				+ agentMerchantName + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", serviceTime="
				+ serviceTime + ", packageOne=" + packageOne + ", price="
				+ price + ", total=" + total + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}
