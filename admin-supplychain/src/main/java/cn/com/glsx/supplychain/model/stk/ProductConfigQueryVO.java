package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductConfigQueryVO implements Serializable{

	@ApiModelProperty(name = "merchantName", notes = "商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	@ApiModelProperty(name = "periodStatus", notes = "有效状态 PRE:未到有效期 CUR:有效期内 NEX:已过期", dataType = "string", required = false, example = "")
	private String periodStatus;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
	private String materialName;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPeriodStatus() {
		return periodStatus;
	}
	public void setPeriodStatus(String periodStatus) {
		this.periodStatus = periodStatus;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
		return "ProductConfigQueryVO [merchantName=" + merchantName
				+ ", periodStatus=" + periodStatus + ", materialName="
				+ materialName + ", pageNum=" + pageNum + ", pageSize="
				+ pageSize + "]";
	}
	
}
