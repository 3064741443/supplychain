package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCDealerUserInfoSearchVO implements Serializable{

	@ApiModelProperty(name = "searchKey", notes = "搜索关键字", dataType = "string", required = true, example = "")
    private String searchKey;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "JXCDealerUserInfoSearchVO [searchKey=" + searchKey
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
	
}
