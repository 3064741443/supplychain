package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

@Table(name = "am_product_split_detail")
public class ProductSplitDetail{
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 业务类型(1:驾宝无忧,2:金融风控,3:车机,4:后视镜)
     */
    private Byte serviceType;
    /**
     * 物料编号
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料类型（1:主物料）
     */
    private Byte type;
    /**
     * 产品类型(0:硬件,1:网联软件,2:风险评估,3:风控服务,4:安装服务,5:智慧门店服务,6:AI车联网服务)
     */
    private String productType;
    //设备类型
    @Transient
    private String deviceType;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    @Transient
    private String materialCodeString;

    /**
     * 渠道
     */
    @Transient
    private Byte channel;

    /**
     * 商户编号
     */
    @Transient
    private String merchantCode;
    /**
     * 产品名称
     */
    @Transient
    private String productName;
    /**
     * 套餐
     */
    @Transient
    private String OnePackage;
    /**
     * 服务期限
     */
    @Transient
    private String serviceTime;
    /**
     * 来源
     */
    @Transient
    private String source;

    public String getOnePackage() {
        return OnePackage;
    }

    public void setOnePackage(String onePackage) {
        OnePackage = onePackage;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getMaterialCodeString() {
        return materialCodeString;
    }

    public void setMaterialCodeString(String materialCodeString) {
        this.materialCodeString = materialCodeString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ProductSplitDetail{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", serviceType=" + serviceType +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", type=" + type +
                ", productType='" + productType + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}