package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.GhShoppingCartConfigDTO;

@SuppressWarnings("serial")
public class ProductShoppingCartVO implements Serializable{
	
	 private String productConfigCode;
	 private Integer total;
	 private String remark;
	 private List<GhShoppingCartConfigDTO> listCartConfig;
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
	public List<GhShoppingCartConfigDTO> getListCartConfig() {
		return listCartConfig;
	}
	public void setListCartConfig(List<GhShoppingCartConfigDTO> listCartConfig) {
		this.listCartConfig = listCartConfig;
	}
	@Override
	public String toString() {
		return "ProductShoppingCartVO [productConfigCode=" + productConfigCode
				+ ", total=" + total + ", remark=" + remark
				+ ", listCartConfig=" + listCartConfig + "]";
	}
	 
	 
}
