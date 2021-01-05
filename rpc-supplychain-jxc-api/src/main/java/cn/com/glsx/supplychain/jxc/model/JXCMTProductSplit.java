package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Table(name = "am_product_split")
public class JXCMTProductSplit implements Serializable{
	@Id
    private Integer id;

    private Byte serviceType;

    private String productCode;

    private String productName;

    private String merchantCode;

    private String merchantName;

    private Byte channel;

    private String alias;

    private Integer deviceQuantity;

    private Byte saleMode;

    private String serviceTime;

    private String packageOne;

    private String hardwareContainSource;

    private Double sourceProportion;

    private Double notSourceProportion;

    private Byte carType;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String plusJrfk;
    
    @Transient
    private String materialCode;
    @Transient
    private String materialName;
    @Transient
    private Double productPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(Integer deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }

    public Byte getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(Byte saleMode) {
        this.saleMode = saleMode;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime == null ? null : serviceTime.trim();
    }

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne == null ? null : packageOne.trim();
    }

    public String getHardwareContainSource() {
        return hardwareContainSource;
    }

    public void setHardwareContainSource(String hardwareContainSource) {
        this.hardwareContainSource = hardwareContainSource == null ? null : hardwareContainSource.trim();
    }

    public Double getSourceProportion() {
        return sourceProportion;
    }

    public void setSourceProportion(Double sourceProportion) {
        this.sourceProportion = sourceProportion;
    }

    public Double getNotSourceProportion() {
        return notSourceProportion;
    }

    public void setNotSourceProportion(Double notSourceProportion) {
        this.notSourceProportion = notSourceProportion;
    }

    public Byte getCarType() {
        return carType;
    }

    public void setCarType(Byte carType) {
        this.carType = carType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
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
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

    public String getPlusJrfk() {
        return plusJrfk;
    }

    public void setPlusJrfk(String plusJrfk) {
        this.plusJrfk = plusJrfk == null ? null : plusJrfk.trim();
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

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
    
}