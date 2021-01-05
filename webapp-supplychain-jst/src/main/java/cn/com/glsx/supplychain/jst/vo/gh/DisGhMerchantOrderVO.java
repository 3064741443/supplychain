package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.GhMerchantOrderDTO;

@SuppressWarnings("serial")
public class DisGhMerchantOrderVO implements Serializable{

	//U:未完成 F:已完成
	private String status;
	private int categoryId;
	private String merchantCode;
	private List<GhMerchantOrderDTO> listGhMerchantOrder;
	// 页面大小
	private Integer pageSize;
	// 页号
	private Integer pageNo;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<GhMerchantOrderDTO> getListGhMerchantOrder() {
		return listGhMerchantOrder;
	}
	public void setListGhMerchantOrder(List<GhMerchantOrderDTO> listGhMerchantOrder) {
		this.listGhMerchantOrder = listGhMerchantOrder;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "DisGhMerchantOrderVO [status=" + status + ", categoryId="
				+ categoryId + ", merchantCode=" + merchantCode
				+ ", listGhMerchantOrder=" + listGhMerchantOrder
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo + "]";
	}
	
}
