package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductSplitVo implements Serializable{
	

	/**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 服务期限
     */
    private String serviceTime;
    /**
     * 套餐
     */
    private String packageOne;
    /**
     * 单位
     */
    private String unitType;
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
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
    
    
    
}
