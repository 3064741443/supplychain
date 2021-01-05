package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

@SuppressWarnings("serial")
public class GhShoppingCartDTO extends BaseDTO implements Serializable{

	private Integer id;
    private String cartCode;
    private String merchantCode;
    private String productConfigCode;
    private Integer parentBrandId;
    private String parentBrandName;
    private Integer subBrandId;
    private String subBrandName;
    private Integer audiId;
    private String audiName;
    private String motorcycle;
    private Integer categoryId;
    private String categoryName;
    private String spaProductCode;
    private String spaProductName;
    private String glsxProductCode;
    private String glsxProductName;
    private Integer total;
    private String remark;  
    private List<GhShoppingCartConfigDTO> listCartConfig;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCartCode() {
		return cartCode;
	}
	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getProductConfigCode() {
		return productConfigCode;
	}
	public void setProductConfigCode(String productConfigCode) {
		this.productConfigCode = productConfigCode;
	}
	public Integer getParentBrandId() {
		return parentBrandId;
	}
	public void setParentBrandId(Integer parentBrandId) {
		this.parentBrandId = parentBrandId;
	}
	public Integer getSubBrandId() {
		return subBrandId;
	}
	public void setSubBrandId(Integer subBrandId) {
		this.subBrandId = subBrandId;
	}
	public Integer getAudiId() {
		return audiId;
	}
	public void setAudiId(Integer audiId) {
		this.audiId = audiId;
	}
	public String getMotorcycle() {
		return motorcycle;
	}
	public void setMotorcycle(String motorcycle) {
		this.motorcycle = motorcycle;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getSpaProductCode() {
		return spaProductCode;
	}
	public void setSpaProductCode(String spaProductCode) {
		this.spaProductCode = spaProductCode;
	}
	public String getSpaProductName() {
		return spaProductName;
	}
	public void setSpaProductName(String spaProductName) {
		this.spaProductName = spaProductName;
	}
	public String getGlsxProductCode() {
		return glsxProductCode;
	}
	public void setGlsxProductCode(String glsxProductCode) {
		this.glsxProductCode = glsxProductCode;
	}
	public String getGlsxProductName() {
		return glsxProductName;
	}
	public void setGlsxProductName(String glsxProductName) {
		this.glsxProductName = glsxProductName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<GhShoppingCartConfigDTO> getListCartConfig() {
		return listCartConfig;
	}
	public void setListCartConfig(List<GhShoppingCartConfigDTO> listCartConfig) {
		this.listCartConfig = listCartConfig;
	}
	public String getParentBrandName() {
		return parentBrandName;
	}
	public void setParentBrandName(String parentBrandName) {
		this.parentBrandName = parentBrandName;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}
	public String getAudiName() {
		return audiName;
	}
	public void setAudiName(String audiName) {
		this.audiName = audiName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "GhShoppingCartDTO [id=" + id + ", cartCode=" + cartCode
				+ ", merchantCode=" + merchantCode + ", productConfigCode="
				+ productConfigCode + ", parentBrandId=" + parentBrandId
				+ ", parentBrandName=" + parentBrandName + ", subBrandId="
				+ subBrandId + ", subBrandName=" + subBrandName + ", audiId="
				+ audiId + ", audiName=" + audiName + ", motorcycle="
				+ motorcycle + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", spaProductCode=" + spaProductCode
				+ ", spaProductName=" + spaProductName + ", glsxProductCode="
				+ glsxProductCode + ", glsxProductName=" + glsxProductName
				+ ", total=" + total + ", remark=" + remark
				+ ", listCartConfig=" + listCartConfig + "]";
	} 
	
}
