package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisJstShopOrderVO implements Serializable{

	private Integer pageNum;
	
	private Integer pageSize;
	
	private String createdDateStart;
	
	private String createdDateEnd;
	
	private String shopName;
	
	private String productName;
	
	/**
     * N:扫码出库  Y：不需要 扫码出库
     */
    private String scanType;
    
    /**
     * N:无订单发货  Y:有订单发货
     */
    private String noOrder;

	private String status;
	
	private Integer total;
	
	private Integer pages;
	
	private String materialCode;
	
	private List<JstShopOrderVO> listShopOrder;

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

	public String getCreatedDateStart() {
		return createdDateStart;
	}

	public void setCreatedDateStart(String createdDateStart) {
		this.createdDateStart = createdDateStart;
	}

	public String getCreatedDateEnd() {
		return createdDateEnd;
	}

	public void setCreatedDateEnd(String createdDateEnd) {
		this.createdDateEnd = createdDateEnd;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<JstShopOrderVO> getListShopOrder() {
		return listShopOrder;
	}

	public void setListShopOrder(List<JstShopOrderVO> listShopOrder) {
		this.listShopOrder = listShopOrder;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}
	
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	@Override
	public String toString() {
		return "DisJstShopOrderVO [pageNum=" + pageNum + ", pageSize="
				+ pageSize + ", createdDateStart=" + createdDateStart
				+ ", createdDateEnd=" + createdDateEnd + ", shopName="
				+ shopName + ", productName=" + productName + ", scanType="
				+ scanType + ", status=" + status + ", total=" + total
				+ ", pages=" + pages + ", listShopOrder=" + listShopOrder + "]";
	}

	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	

	
	
	
}
