package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ProductVO implements Serializable{
	
	//业务类型  JB:驾宝无忧,JR:金融风控,CZ:车机,HS:后视镜,OT:其它
	private	String serviceType;

	//产品编号
	private String productCode;

	//产品名称
    private String productName;
    
    //商户编号
    private String merchantCode;

    //商户名称
    private String merchantName;
    
    //渠道 AL:全部  GH:广汇  OT:其他 TM:同盟会  YK:亿咖通
    private String channel;
    
    //服务期限
    private String serviceTime;
    
    //套餐
    private String packageOne;
    
    //价格
    private Double price;
    
    //硬件物料
    private List<MaterialVO> listMaterialVo;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
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

	public List<MaterialVO> getListMaterialVo() {
		return listMaterialVo;
	}

	public void setListMaterialVo(List<MaterialVO> listMaterialVo) {
		this.listMaterialVo = listMaterialVo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductVo [serviceType=" + serviceType + ", productCode="
				+ productCode + ", productName=" + productName
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", channel=" + channel + ", serviceTime="
				+ serviceTime + ", packageOne=" + packageOne + ", price="
				+ price + ", listMaterialVo=" + listMaterialVo + "]";
	}
    
    
    
    
	
}
