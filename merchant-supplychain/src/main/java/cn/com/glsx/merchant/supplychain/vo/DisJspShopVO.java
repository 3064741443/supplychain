package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisJspShopVO implements Serializable{

	private Integer pageNum;
	
	private Integer pageSize;
	
	private String shopName;
	
	private String status;
	
	private Integer total;
	
	private Integer pages;
	
	private List<JstShopVO> listJstShop;

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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<JstShopVO> getListJstShop() {
		return listJstShop;
	}

	public void setListJstShop(List<JstShopVO> listJstShop) {
		this.listJstShop = listJstShop;
	}

	@Override
	public String toString() {
		return "DisJspShopVO [pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", shopName=" + shopName + ", status=" + status + ", total="
				+ total + ", pages=" + pages + ", listJstShop=" + listJstShop
				+ "]";
	}
	
	
}
