package glsx.com.cn.task.vo;

import java.util.Date;

public class MerchantOrderStatement {
	
	private String dispatchOrderNumber;
	private String merchantCode;
	private String merchantName;
	private String merchantOrderCode;
	private Date   merchantOrderTime;
	private String productCode;
	private String productName;
	private String materialCode;
	private String materialName;
	private String productType;
	private Date   productPriceTime;
	private Date   updateDate;
	public String getDispatchOrderNumber() {
		return dispatchOrderNumber;
	}
	public void setDispatchOrderNumber(String dispatchOrderNumber) {
		this.dispatchOrderNumber = dispatchOrderNumber;
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
	public String getMerchantOrderCode() {
		return merchantOrderCode;
	}
	public void setMerchantOrderCode(String merchantOrderCode) {
		this.merchantOrderCode = merchantOrderCode;
	}
	public Date getMerchantOrderTime() {
		return merchantOrderTime;
	}
	public void setMerchantOrderTime(Date merchantOrderTime) {
		this.merchantOrderTime = merchantOrderTime;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Date getProductPriceTime() {
		return productPriceTime;
	}
	public void setProductPriceTime(Date productPriceTime) {
		this.productPriceTime = productPriceTime;
	}
	@Override
	public String toString() {
		return "MerchantOrderStatement [dispatchOrderNumber="
				+ dispatchOrderNumber + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", merchantOrderCode="
				+ merchantOrderCode + ", merchantOrderTime="
				+ merchantOrderTime + ", productCode=" + productCode
				+ ", productName=" + productName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", productType=" + productType + ", productPriceTime="
				+ productPriceTime + "]";
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
