package cn.com.glsx.supplychain.model.stk;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class StockSnToMerchantStatVO implements Serializable{
	@ApiModelProperty(name = "unActiveDayFlag", notes = "TH:大于3个月未激活 SI:大于6个月未激活 NI:大于9个月未激活", dataType = "string", required = true, example = "")
    private String unActiveDayFlag;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}
	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
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
		return "StockSnToMerchantStatVO [unActiveDayFlag=" + unActiveDayFlag
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
}
