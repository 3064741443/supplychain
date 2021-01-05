package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisOrderDetailVO implements Serializable {

	// 订单编号
	private String orderCode;
	// 页面大小
	private Integer pageSize;

	// 页号
	private Integer pageNo;

	// 明细
	private List<OrderDetailVO> listDetail;

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

	public List<OrderDetailVO> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<OrderDetailVO> listDetail) {
		this.listDetail = listDetail;
	}
	
	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Override
	public String toString() {
		return "DisOrderDetailVO [orderCode=" + orderCode + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + ", listDetail="
				+ listDetail + "]";
	}

}
