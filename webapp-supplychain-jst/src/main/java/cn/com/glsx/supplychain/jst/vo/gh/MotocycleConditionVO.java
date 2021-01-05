package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;

public class MotocycleConditionVO implements Serializable{

	private QueryConditionVO queryCondition;
	private Integer modelYearId;
	private Integer audiId;
	private String glsxProductCode;
	private Integer pageSize;
	private Integer pageNo;
	
	public QueryConditionVO getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryConditionVO queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	
	public Integer getAudiId() {
		return audiId;
	}
	public void setAudiId(Integer audiId) {
		this.audiId = audiId;
	}
	public String getGlsxProductCode() {
		return glsxProductCode;
	}
	public void setGlsxProductCode(String glsxProductCode) {
		this.glsxProductCode = glsxProductCode;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getModelYearId() {
		return modelYearId;
	}
	public void setModelYearId(Integer modelYearId) {
		this.modelYearId = modelYearId;
	}
	@Override
	public String toString() {
		return "MotocycleConditionVO [queryCondition=" + queryCondition
				+ ", modelYearId=" + modelYearId + ", audiId=" + audiId
				+ ", glsxProductCode=" + glsxProductCode + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + "]";
	}
	
	
}
