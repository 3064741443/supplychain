package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKProductConfigQueryDTO implements Serializable{
	@ApiModelProperty(name = "merchantCode", notes = "商户编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	@ApiModelProperty(name = "periodStatus", notes = "有效状态 PRE:未到有效期 CUR:有效期内 NEX:已过期", dataType = "string", required = false, example = "")
	private String periodStatus;
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
	private String materialName;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
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
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
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
		return "STKProductConfigQueryDTO [merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", periodStatus="
				+ periodStatus + ", materialCode=" + materialCode
				+ ", materialName=" + materialName + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + "]";
	}
	
	
}
