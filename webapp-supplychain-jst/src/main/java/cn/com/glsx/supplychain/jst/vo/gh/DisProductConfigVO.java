package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.GhProductConfigDTO;

public class DisProductConfigVO implements Serializable{

	QueryConditionVO queryCondition;
	private List<GhProductConfigDTO> listGhProductConfig;
	// 页面大小
	private Integer pageSize;
	// 页号
	private Integer pageNo;
	public QueryConditionVO getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryConditionVO queryCondition) {
		this.queryCondition = queryCondition;
	}
	public List<GhProductConfigDTO> getListGhProductConfig() {
		return listGhProductConfig;
	}
	public void setListGhProductConfig(List<GhProductConfigDTO> listGhProductConfig) {
		this.listGhProductConfig = listGhProductConfig;
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
	@Override
	public String toString() {
		return "DisProductConfigVO [queryCondition=" + queryCondition
				+ ", listGhProductConfig=" + listGhProductConfig
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo + "]";
	}
	
	
}
