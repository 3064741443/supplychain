package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

public class ProductSplitDetailVo implements Serializable{

	/**
     * 产品编号
     */
	private String productCode;
	/**
     * 物料编码
     */
    private String materialCode;
    
    /**
     * 物料名称
     */
    private String materialName;
    
    /**
     * 产品价格
     */
    private Double price;
    
    /**
     * 硬件单价
     */
    private Double hardwarePrice;
    
    /**
     * 服务费单价
     */
    private Double servicePrice;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getHardwarePrice() {
		return hardwarePrice;
	}

	public void setHardwarePrice(Double hardwarePrice) {
		this.hardwarePrice = hardwarePrice;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}
    
    
    
}
