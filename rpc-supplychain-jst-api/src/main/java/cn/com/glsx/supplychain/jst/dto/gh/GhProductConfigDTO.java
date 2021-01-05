package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

@SuppressWarnings("serial")
public class GhProductConfigDTO extends BaseDTO implements Serializable{
	
	private String merchantCode;
	private String productConfigCode;
    private Integer parentBrandId;  
    private String parentBrandName;
    private Integer subBrandId;  
    private String subBrandName;
    private Integer audiId; 
    private String audiName;
    private Integer modelYearId;
    private String modelYearName;
    private String motorcycle;
    private Integer categoryId; 
    private String categoryName;
    private String spaProductCode;
    private String spaProductName;
    private String glsxProductCode;
    private String glsxProductName;
    //是否显示配置 Y:显示 N:不显示
    private String displayConfig; 
    //车型配置
    private List<GhProductConfigOtherDTO> listConfigOther;
    private Integer modelYearValue;
    
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
	public String getParentBrandName() {
		return parentBrandName;
	}
	public void setParentBrandName(String parentBrandName) {
		this.parentBrandName = parentBrandName;
	}
	public Integer getSubBrandId() {
		return subBrandId;
	}
	public void setSubBrandId(Integer subBrandId) {
		this.subBrandId = subBrandId;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getDisplayConfig() {
		return displayConfig;
	}
	public void setDisplayConfig(String displayConfig) {
		this.displayConfig = displayConfig;
	}
	public List<GhProductConfigOtherDTO> getListConfigOther() {
		return listConfigOther;
	}
	public void setListConfigOther(List<GhProductConfigOtherDTO> listConfigOther) {
		this.listConfigOther = listConfigOther;
	}	
	public String getAudiName() {
		return audiName;
	}
	public void setAudiName(String audiName) {
		this.audiName = audiName;
	}
	public Integer getModelYearId() {
		return modelYearId;
	}
	public void setModelYearId(Integer modelYearId) {
		this.modelYearId = modelYearId;
	}
	public String getModelYearName() {
		return modelYearName;
	}
	public void setModelYearName(String modelYearName) {
		this.modelYearName = modelYearName;
	}
	@Override
	public String toString() {
		return "GhProductConfigDTO [merchantCode=" + merchantCode
				+ ", productConfigCode=" + productConfigCode
				+ ", parentBrandId=" + parentBrandId + ", parentBrandName="
				+ parentBrandName + ", subBrandId=" + subBrandId
				+ ", subBrandName=" + subBrandName + ", audiId=" + audiId
				+ ", audiName=" + audiName + ", motorcycle=" + motorcycle
				+ ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", spaProductCode=" + spaProductCode
				+ ", spaProductName=" + spaProductName + ", glsxProductCode="
				+ glsxProductCode + ", glsxProductName=" + glsxProductName
				+ "]";
	}
	public Integer getModelYearValue() {
		return modelYearValue;
	}
	public void setModelYearValue(Integer modelYearValue) {
		this.modelYearValue = modelYearValue;
	}
	
	
}
