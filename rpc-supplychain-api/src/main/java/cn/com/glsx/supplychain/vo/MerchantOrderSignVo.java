package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

/**
 * @ClassName 生成签收单要的信息
 * @Author 
 * @Param
 * @Date 2019/3/13 14:27
 * @Version
 **/
@SuppressWarnings("serial")
public class MerchantOrderSignVo implements Serializable{

	/**
     * 订单号
     */
    private String orderNumber;
    
    /**
     * 商户CODE
     */
    private String merchantCode;
    
    /**
     * 商户名称
     */ 
    private String merchantName;
    
    /**
     * 生产厂家
     */ 
    private String factoryName;
    
    /**
     * 物料编号
     */ 
    private String materialCode;
    
    /**
     * 物料名称
     */ 
    private String materialName;
    
    /**
     * 发货数量
     */ 
    private String shipmentQuantity;
    
    /**
     * 发货日期
     */ 
    private String shipmentTime;
    
    /**
     * 物流单号
     */ 
    private String logisticsNo;
    
    /**
     * 物流公司
     */ 
    private String logisticsCpy;
    
    /**
     * 物流公司
     */ 
    private String remark;
    
    /**
     * 收获地址
     */ 
    private String address;

    /**
     * 收获人
     */ 
	private String contacts;

	/**
     * 联系人
     */ 
	private String mobile;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
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

	public String getShipmentQuantity() {
		return shipmentQuantity;
	}

	public void setShipmentQuantity(String shipmentQuantity) {
		this.shipmentQuantity = shipmentQuantity;
	}

	public String getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsCpy() {
		return logisticsCpy;
	}

	public void setLogisticsCpy(String logisticsCpy) {
		this.logisticsCpy = logisticsCpy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}
