package cn.com.glsx.supplychain.model.am;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StatementCollection 对账单-广汇采集
 * @Author admin
 * @Param
 * @Date 2019/9/12 11:28
 * @Version
 **/
@SuppressWarnings("serial")
public class StatementCollectionImport implements Serializable {

    /**
     * 年月
     */
    private String time;
    /**
     * 大区
     */
    private String area;
    
    /**
     * 门店
     */
    private String shopName;
    
    /**
     * 服务商/经销商
     */
    private String merchant;
    
    /**
     * 总部结算公司
     */
    private String settleCompany;
    
    /**
     * 商品类别3
     */
    private String goodsType;
    
    /**
     * 商品编码
     */
    private String goodsCode;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 销售数量
     */
    private String salesQuantity;
    
    /**
     * 采购金额
     */
    private String price;
    /**
     * 返利点
     */
    private String rebate;
    /**
     * 业务类型
     */
    private Byte serviceType;
    
    /**
     * 销售组编码
     */
    private String saleCode;
    
    /**
     * 销售组名称
     */
    private String saleName;
    
    
    /**
     * 失败原因
     */
    private String failDesc;
    
    @ExcelResources(title = "年月",order = 0)
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@ExcelResources(title = "大区",order = 3)
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@ExcelResources(title = "门店",order = 4)
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@ExcelResources(title = "服务商编码",order = 2)
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	@ExcelResources(title = "总部结算公司编码",order = 1)
	public String getSettleCompany() {
		return settleCompany;
	}
	public void setSettleCompany(String settleCompany) {
		this.settleCompany = settleCompany;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	@ExcelResources(title = "商品编码",order = 5)
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	@ExcelResources(title = "商品名称",order = 6)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@ExcelResources(title = "数量",order = 7)
	public String getSalesQuantity() {
		return salesQuantity;
	}
	public void setSalesQuantity(String salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	@ExcelResources(title = "总部总金额",order = 8)
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@ExcelResources(title = "返利点",order = 9)
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	public Byte getServiceType() {
		return serviceType;
	}
	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}
	@ExcelResources(title = "失败原因",order = 10)
	public String getFailDesc() {
		return failDesc;
	}
	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}
	@Override
	public String toString() {
		return "StatementCollectionImport [time=" + time + ", area=" + area
				+ ", shopName=" + shopName + ", merchant=" + merchant
				+ ", settleCompany=" + settleCompany + ", goodsType="
				+ goodsType + ", goodsCode=" + goodsCode + ", goodsName="
				+ goodsName + ", salesQuantity=" + salesQuantity + ", price="
				+ price + ", rebate=" + rebate + ", serviceType=" + serviceType
				+ ", failDesc=" + failDesc + "]";
	}
	public String getSaleCode() {
		return saleCode;
	}
	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	
	
	
	
     
}
