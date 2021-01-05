package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class STKProductConfigDTO implements Serializable{
	@ApiModelProperty(name = "operateCode", notes = "操作编码", dataType = "string", required = false, example = "")
	private String operateCode;
	@ApiModelProperty(name = "configCode", notes = "配置编码", dataType = "string", required = false, example = "")
    private String configCode;
	@ApiModelProperty(name = "merchantCode", notes = "商户编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "商户名称", dataType = "string", required = false, example = "")
    private String merchantName;
	@ApiModelProperty(name = "warehouseCode", notes = "k3仓库编码", dataType = "string", required = false, example = "")
    private String warehouseCode;
	@ApiModelProperty(name = "warehouseName", notes = "k3仓库名称", dataType = "string", required = false, example = "")
    private String warehouseName;
	@ApiModelProperty(name = "periodStart", notes = "有效期起始2020-11-05", dataType = "string", required = false, example = "")
    private String periodStart;
	@ApiModelProperty(name = "periodEnd", notes = "有效期结束2020-11-05", dataType = "string", required = false, example = "")
    private String periodEnd;
	@ApiModelProperty(name = "periodStatus", notes = "有效状态 PRE:未到有效期 CUR:有效期内 NEX:已过期", dataType = "string", required = false, example = "")
	private String periodStatus;
	@ApiModelProperty(name = "periodStatusName", notes = "有效状态名称", dataType = "string", required = false, example = "")
	private String periodStatusName;
	@ApiModelProperty(name = "listConfigDetail", notes = "配置物料明细", dataType = "object", required = false, example = "")
	private List<STKProductConfigDetailDTO> listConfigDetail;
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
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
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public String getPeriodStatus() {
		return periodStatus;
	}
	public void setPeriodStatus(String periodStatus) {
		this.periodStatus = periodStatus;
	}
	public List<STKProductConfigDetailDTO> getListConfigDetail() {
		return listConfigDetail;
	}
	public void setListConfigDetail(List<STKProductConfigDetailDTO> listConfigDetail) {
		this.listConfigDetail = listConfigDetail;
	}
	public String getPeriodStatusName() {
		return periodStatusName;
	}
	public void setPeriodStatusName(String periodStatusName) {
		this.periodStatusName = periodStatusName;
	}
	@Override
	public String toString() {
		return "STKProductConfigDTO [operateCode=" + operateCode
				+ ", configCode=" + configCode + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", warehouseCode=" + warehouseCode + ", warehouseName="
				+ warehouseName + ", periodStart=" + periodStart
				+ ", periodEnd=" + periodEnd + ", periodStatus=" + periodStatus
				+ ", periodStatusName=" + periodStatusName
				+ ", listConfigDetail=" + listConfigDetail + "]";
	}
	
	
}
