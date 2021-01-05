package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;
@Table(name = "bs_product")
public class Product{
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 业务类型ID(1:驾宝无忧,2:金融风控,3:车机,4:后视镜,5:其它)
     */
    private Byte serviceType;
    /**
     * 商户编号
     */
    private String merchantCode;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 别名
     */
    private String alias;
    /**
     * 设备数量
     */
    private Integer deviceQuantity;
    /**
     * 服务期限
     */
    private String serviceTime;
    /**
     * 套餐
     */
    private String packageOne;
    /**
     * 硬件是否包含有源无源
     */
    private String hardwareContainSource;
    /**
     * 有源所占比例
     */
    private Double sourceProportion;
    /**
     * 无源所占比例
     */
    private Double notSourceProportion;
    /**
     * 车机类型(1:广汇车机,2:其它车机)
     */
    private Byte carType;
    /**
     * 产品编号
     */
    private String code;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品规格
     */
    private String specification;
    /**
     * 产品类型
     */
    private String type;
    /**
     * 渠道(0:通用,1:广汇,2:非广汇)
     */
    private Byte channel;
    /**
     * 含税单价
     */
    @Transient
    private Double amount;
    /**
     * 产品状态（1：上架，2：下架）
     */
    private Byte status;
    /**
     * (新)产品拆分ID
     */
    public Long productSplitId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 产品详情
     */
    @Transient
    private List<ProductDetail> productDetailList;



    /**
     * 产品对账拆分详情信息
     */
    @Transient
    private List<ProductSplitDetail> productSplitDetailList;

    /**
     * 产品历史价格类型
     */
    @Transient
    private String productHistoryType;

    /**
     * 产品历史价格List
     */
    @Transient
    private List<ProductHistoryPrice>productHistoryPriceList;

    public String getProductHistoryType() {
        return productHistoryType;
    }

    public void setProductHistoryType(String productHistoryType) {
        this.productHistoryType = productHistoryType;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }

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
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
        this.serviceTime = serviceTime;
    }

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne;
    }

    public String getHardwareContainSource() {
        return hardwareContainSource;
    }

    public void setHardwareContainSource(String hardwareContainSource) {
        this.hardwareContainSource = hardwareContainSource;
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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public List<ProductSplitDetail> getProductSplitDetailList() {
        return productSplitDetailList;
    }

    public void setProductSplitDetailList(List<ProductSplitDetail> productSplitDetailList) {
        this.productSplitDetailList = productSplitDetailList;
    }

    public Long getProductSplitId() {
        return productSplitId;
    }

    public void setProductSplitId(Long productSplitId) {
        this.productSplitId = productSplitId;
    }

    public List<ProductHistoryPrice> getProductHistoryPriceList() {
        return productHistoryPriceList;
    }

    public void setProductHistoryPriceList(List<ProductHistoryPrice> productHistoryPriceList) {
        this.productHistoryPriceList = productHistoryPriceList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", serviceType=" + serviceType +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", alias='" + alias + '\'' +
                ", deviceQuantity=" + deviceQuantity +
                ", serviceTime='" + serviceTime + '\'' +
                ", packageOne='" + packageOne + '\'' +
                ", hardwareContainSource='" + hardwareContainSource + '\'' +
                ", sourceProportion=" + sourceProportion +
                ", notSourceProportion=" + notSourceProportion +
                ", carType=" + carType +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", specification='" + specification + '\'' +
                ", type='" + type + '\'' +
                ", channel=" + channel +
                ", amount=" + amount +
                ", status=" + status +
                ", productSplitId=" + productSplitId +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", productDetailList=" + productDetailList +
                ", productSplitDetailList=" + productSplitDetailList +
                ", productHistoryType='" + productHistoryType + '\'' +
                ", productHistoryPriceList=" + productHistoryPriceList +
                '}';
    }
}