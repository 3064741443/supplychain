package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;

public class QueryConditionVO implements Serializable{

	private String merchantCode;
	private Integer parentBrandId;
	private Integer subBrandId;
	private Integer audiId;
	private String motorcycle;
	private Integer categoryId;
	private String spaProductCode;
	//M:车型车系查询  A:不带条件全部查询 S:spacode查询
	private String queryType;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	@Override
	public String toString() {
		return "QueryConditionVO [merchantCode=" + merchantCode
				+ ", parentBrandId=" + parentBrandId + ", subBrandId="
				+ subBrandId + ", audiId=" + audiId + ", motorcycle="
				+ motorcycle + ", categoryId=" + categoryId
				+ ", spaProductCode=" + spaProductCode + ", queryType="
				+ queryType + "]";
	}
	
	
}
