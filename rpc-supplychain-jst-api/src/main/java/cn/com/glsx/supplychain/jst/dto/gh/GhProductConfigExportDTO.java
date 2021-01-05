package cn.com.glsx.supplychain.jst.dto.gh;

import com.alibaba.excel.annotation.ExcelProperty;

@SuppressWarnings("serial")
public class GhProductConfigExportDTO{
	/**
	 * 父品牌名称
	 */
	@ExcelProperty(value = "父品牌名称",index = 1)
    private String parentBrandName;

	/**
	 * 子品牌名称
	 */
	@ExcelProperty(value = "子品牌名称",index = 2)
    private String subBrandName;

    /**
     * 车系
     */
	@ExcelProperty(value = "车系",index = 3)
    private String audiName;

	/**
	 * 车型
	 */
	@ExcelProperty(value = "车型",index = 4)
    private String motorcycle;

	/**
	 * 产品分类名称
	 */
	@ExcelProperty(value = "产品分类名称",index = 5)
    private String categoryName;

    /**
     * SAP产品编码
     */
	@ExcelProperty(value = "SAP产品编码",index = 6)
    private String spaProductCode;

    /**
     * SAP产品名称
     */
	@ExcelProperty(value = "SAP产品名称",index = 7)
    private String spaProductName;

    /**
     * 映射产品编码
     */
	@ExcelProperty(value = "映射产品编码",index = 8)
    private String glsxProductCode;

    /**
     * 映射产品名称
     */
	@ExcelProperty(value = "映射产品名称",index = 9)
    private String glsxProductName;

	//固定配置
	@ExcelProperty(value = "固定配置", index = 10)
	private String fasternConfig;

	//选项配置
	@ExcelProperty(value = "选项配置", index = 11)
	private String optionConfig;

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

	public String getMotorcycle() {
		return motorcycle;
	}

	public void setMotorcycle(String motorcycle) {
		this.motorcycle = motorcycle;
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

	public String getFasternConfig() {
		return fasternConfig;
	}

	public void setFasternConfig(String fasternConfig) {
		this.fasternConfig = fasternConfig;
	}

	public String getOptionConfig() {
		return optionConfig;
	}

	public void setOptionConfig(String optionConfig) {
		this.optionConfig = optionConfig;
	}
	
	
	
	
}
