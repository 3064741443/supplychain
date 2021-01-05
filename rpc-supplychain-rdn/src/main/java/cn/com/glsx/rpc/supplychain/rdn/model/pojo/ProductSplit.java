package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ProductSplit
 * @Author admin
 * @Param
 * @Date 2019/9/12 16:43
 * @Version
 **/
@Table(name = "am_product_split")
public class ProductSplit {
    /**
     * 自增ID
     */
	@Id
    private Long id;
    /**
     * 业务类型(1:驾宝无忧,2:金融风控,3:车机,4:后视镜)
     */
    private Byte serviceType;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 渠道
     */
    private Byte channel;
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
     * 销售方式
     */
    private Byte saleMode;
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

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    //网联智能硬件价格
    @Transient
    private Double softWareSum;
    //服务费
    @Transient
    private Double serviceSum;
    //销售价格
    @Transient
    private Double saleSum;

    //时间类型（0:当前价格,1:未来价格,2:历史价格）
    @Transient
    private Byte type;
    
    //查询条件 物料编号
    @Transient
    private String materialCode;
    
    //查询条件 产品编号集合
    @Transient
    private List<String> listCheckProductCodes;
    
    //当前产品价格 下单时候计算
    @Transient
    private Double price;
    
    //当前产品价格生成时间 下单时候计算
    @Transient
    private Date time;

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

	public Byte getChannel() {
		return channel;
	}

	public void setChannel(Byte channel) {
		this.channel = channel;
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

	public Double getSoftWareSum() {
		return softWareSum;
	}

	public void setSoftWareSum(Double softWareSum) {
		this.softWareSum = softWareSum;
	}

	public Double getServiceSum() {
		return serviceSum;
	}

	public void setServiceSum(Double serviceSum) {
		this.serviceSum = serviceSum;
	}

	public Double getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(Double saleSum) {
		this.saleSum = saleSum;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public List<String> getListCheckProductCodes() {
		return listCheckProductCodes;
	}

	public void setListCheckProductCodes(List<String> listCheckProductCodes) {
		this.listCheckProductCodes = listCheckProductCodes;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ProductSplit [id=" + id + ", serviceType=" + serviceType
				+ ", productCode=" + productCode + ", productName="
				+ productName + ", channel=" + channel + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName + ", alias="
				+ alias + ", deviceQuantity=" + deviceQuantity + ", saleMode="
				+ saleMode + ", serviceTime=" + serviceTime + ", packageOne="
				+ packageOne + ", hardwareContainSource="
				+ hardwareContainSource + ", sourceProportion="
				+ sourceProportion + ", notSourceProportion="
				+ notSourceProportion + ", carType=" + carType + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + ", softWareSum=" + softWareSum
				+ ", serviceSum=" + serviceSum + ", saleSum=" + saleSum
				+ ", type=" + type + ", materialCode=" + materialCode
				+ ", listCheckProductCodes=" + listCheckProductCodes
				+ ", price=" + price + ", time=" + time + "]";
	}
    
    


	
}
