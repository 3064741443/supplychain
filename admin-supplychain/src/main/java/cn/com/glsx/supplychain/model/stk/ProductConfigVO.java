package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ProductConfigVO implements Serializable{

	@ApiModelProperty(name = "operateCode", notes = "新增的时候不填 修改的时候必填", dataType = "string", required = false, example = "")
    private String operateCode;
	@ApiModelProperty(name = "periodStart", notes = "有效期起始2020-11-05", dataType = "string", required = true, example = "")
    private String periodStart;
	@ApiModelProperty(name = "periodEnd", notes = "有效期结束2020-11-05", dataType = "string", required = true, example = "")
    private String periodEnd;
	@ApiModelProperty(name = "listMerchant", notes = "区域服务商列表", dataType = "object", required = true, example = "")
	private List<ProductConfigMerchantVO> listMerchant;
	@ApiModelProperty(name = "listSalesMaterial", notes = "销售物料列表", dataType = "object", required = true, example = "")
	private List<ProductConfigSalesMaterialVO> listSalesMaterial;
	@ApiModelProperty(name = "listSalesMaterial", notes = "发货物料列表", dataType = "object", required = true, example = "")
	private List<ProductConfigSendMaterialVO> listSendMaterial;
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
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
	public List<ProductConfigMerchantVO> getListMerchant() {
		return listMerchant;
	}
	public void setListMerchant(List<ProductConfigMerchantVO> listMerchant) {
		this.listMerchant = listMerchant;
	}
	public List<ProductConfigSalesMaterialVO> getListSalesMaterial() {
		return listSalesMaterial;
	}
	public void setListSalesMaterial(
			List<ProductConfigSalesMaterialVO> listSalesMaterial) {
		this.listSalesMaterial = listSalesMaterial;
	}
	public List<ProductConfigSendMaterialVO> getListSendMaterial() {
		return listSendMaterial;
	}
	public void setListSendMaterial(
			List<ProductConfigSendMaterialVO> listSendMaterial) {
		this.listSendMaterial = listSendMaterial;
	}
	@Override
	public String toString() {
		return "ProductConfigVO [operateCode=" + operateCode + ", periodStart="
				+ periodStart + ", periodEnd=" + periodEnd + ", listMerchant="
				+ listMerchant + ", listSalesMaterial=" + listSalesMaterial
				+ ", listSendMaterial=" + listSendMaterial + "]";
	}
	
	
	
	
}
