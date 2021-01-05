package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisNoOrderDetailVO implements Serializable{
	
	public String productCode;
	public String productName;
	public String materialCode;
	public String materialName;
	public Integer count;
	private List<OrderDetailVO> listDetail;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<OrderDetailVO> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<OrderDetailVO> listDetail) {
		this.listDetail = listDetail;
	}
	@Override
	public String toString() {
		return "DisNoOrderDetailVO [productCode=" + productCode
				+ ", productName=" + productName + ", materialCode="
				+ materialCode + ", materialName=" + materialName + ", count="
				+ count + ", listDetail=" + listDetail + "]";
	}
	
	
}
