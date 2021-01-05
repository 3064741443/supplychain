package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.GhShoppingCartConfigDTO;

public class SubProductConfigCartVO implements Serializable{
	
	private String merchantCode;
    private String productConfigCode;
    private String ghMerchantOrderCode;
    private Integer total;
    private String remark;
    private List<GhShoppingCartConfigDTO> listCartConfig;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getProductConfigCode() {
		return productConfigCode;
	}
	public void setProductConfigCode(String productConfigCode) {
		this.productConfigCode = productConfigCode;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}
	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}
	public List<GhShoppingCartConfigDTO> getListCartConfig() {
		return listCartConfig;
	}
	public void setListCartConfig(List<GhShoppingCartConfigDTO> listCartConfig) {
		this.listCartConfig = listCartConfig;
	}
	@Override
	public String toString() {
		return "SubProductConfigCartVO [merchantCode=" + merchantCode
				+ ", productConfigCode=" + productConfigCode
				+ ", ghMerchantOrderCode=" + ghMerchantOrderCode + ", total="
				+ total + ", remark=" + remark + ", listCartConfig="
				+ listCartConfig + "]";
	}
	
    
    
}
