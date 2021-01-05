package cn.com.glsx.supplychain.jst.model;

import java.util.Date;

public class BsProduct {
    private Long id;

    private Byte serviceType;

    private String merchantCode;

    private String merchantName;

    private String alias;

    private Integer deviceQuantity;

    private String serviceTime;

    private String packageOne;

    private String hardwareContainSource;

    private Double sourceProportion;

    private Double notSourceProportion;

    private Byte carType;

    private String code;

    private String name;

    private String specification;

    private String type;

    private Byte channel;

    private Byte status;

    private Long productSplitId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getProductSplitId() {
        return productSplitId;
    }

    public void setProductSplitId(Long productSplitId) {
        this.productSplitId = productSplitId;
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
}