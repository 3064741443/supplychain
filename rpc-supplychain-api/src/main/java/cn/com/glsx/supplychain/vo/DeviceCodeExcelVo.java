package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

import cn.com.glsx.supplychain.model.PageInfo;

@SuppressWarnings("serial")
public class DeviceCodeExcelVo extends PageInfo implements Serializable{
    private Integer deviceCode;

    private String deviceName;

    private Integer merchantId;

    private String createdBy;
       
    private String createdTime;
        
    private String merchantName; //品牌定制商名称
        
    private String name;//设备类型名称
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "DeviceCodeExcelVo [deviceCode=" + deviceCode + ", deviceName="
				+ deviceName + ", merchantId=" + merchantId + ", createdBy="
				+ createdBy + ", createdTime=" + createdTime
				+ ", merchantName=" + merchantName + ", name=" + name
				+ ", getName()=" + getName() + ", getDeviceCode()="
				+ getDeviceCode() + ", getDeviceName()=" + getDeviceName()
				+ ", getMerchantId()=" + getMerchantId() + ", getCreatedBy()="
				+ getCreatedBy() + ", getMerchantName()=" + getMerchantName()
				+ ", getCreatedTime()=" + getCreatedTime() + "]";
	}
    
}