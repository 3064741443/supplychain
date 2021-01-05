package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCAmKcWarehouseRelationQueryVO implements Serializable{
	@ApiModelProperty(name = "warehouseName", notes = "K3仓库名称", dataType = "string", required = false, example = "")
    private String warehouseName;
	@ApiModelProperty(name = "merchantName", notes = "服务商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
		return "JXCAmKcWarehouseRelationQueryVO [warehouseName="
				+ warehouseName + ", merchantName=" + merchantName
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
	
}
