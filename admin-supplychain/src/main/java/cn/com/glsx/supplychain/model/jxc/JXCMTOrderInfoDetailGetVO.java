package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTOrderInfoDetailGetVO implements Serializable{

	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = true, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
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
		return "JXCMTOrderInfoDetailGetVO [dispatchOrderCode="
				+ dispatchOrderCode + ", pageNum=" + pageNum + ", pageSize="
				+ pageSize + "]";
	}
	
	
	
}
