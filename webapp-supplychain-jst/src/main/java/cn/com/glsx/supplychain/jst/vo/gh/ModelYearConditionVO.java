package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;

public class ModelYearConditionVO implements Serializable{
	
	private String glsxProductCode;
	private QueryConditionVO queryCondition;
	public String getGlsxProductCode() {
		return glsxProductCode;
	}
	public void setGlsxProductCode(String glsxProductCode) {
		this.glsxProductCode = glsxProductCode;
	}
	public QueryConditionVO getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryConditionVO queryCondition) {
		this.queryCondition = queryCondition;
	}
	@Override
	public String toString() {
		return "ModelYearConditionVO [glsxProductCode=" + glsxProductCode
				+ ", queryCondition=" + queryCondition + "]";
	}
	
	

}
