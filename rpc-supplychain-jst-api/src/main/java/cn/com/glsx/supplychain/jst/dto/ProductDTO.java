package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ProductDTO extends BaseDTO implements Serializable {
	
	private Integer id;
	// 业务类型 1:驾宝无忧,2:金融风控,3:车机,4:后视镜,5:其它
	private Byte serviceType;

	// 产品编号
	private String productCode;

	// 产品名称
	private String productName;

	// 商户编号
	private String merchantCode;

	// 商户名称
	private String merchantName;

	// 渠道 0:全部 1:广汇 2:其他 3:同盟会 5:亿咖通
	private Byte channel;
	
	private Integer deviceQuantity;
	
	private Byte saleMode;
	
	private String hardwareContainSource;
	
	private Double sourceProportion;
	
	private Double notSourceProportion;
	
	private Byte carType;

	// 服务期限
	private String serviceTime;

	// 套餐
	private String packageOne;

	// 价格
	private Double price;
	
	//价格时间
	private Date priceTime;

	// 硬件物料
	private List<MaterialDTO> listMaterialDto;

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

	public Byte getChannel() {
		return channel;
	}

	public void setChannel(Byte channel) {
		this.channel = channel;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<MaterialDTO> getListMaterialDto() {
		return listMaterialDto;
	}

	public void setListMaterialDto(List<MaterialDTO> listMaterialDto) {
		this.listMaterialDto = listMaterialDto;
	}

	@Override
	public String toString() {
		return "ProductDTO [serviceType=" + serviceType + ", productCode="
				+ productCode + ", productName=" + productName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", channel=" + channel + ", serviceTime="
				+ serviceTime + ", packageOne=" + packageOne + ", price="
				+ price + ", listMaterialDto=" + listMaterialDto + "]";
	}
	
	

	public Byte getSaleMode() {
		return saleMode;
	}

	public void setSaleMode(Byte saleMode) {
		this.saleMode = saleMode;
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

	public Date getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(Date priceTime) {
		this.priceTime = priceTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceQuantity() {
		return deviceQuantity;
	}

	public void setDeviceQuantity(Integer deviceQuantity) {
		this.deviceQuantity = deviceQuantity;
	}

	

}
