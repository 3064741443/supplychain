package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisShopOrderDetailDTO extends BaseDTO implements Serializable {

	// 订单编号
	private String orderCode;
	// 页面大小
	private Integer pageSize;

	// 页号
	private Integer pageNo;

	// 明细
	private List<OrderDetailDTO> listDetailDto;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public List<OrderDetailDTO> getListDetailDto() {
		return listDetailDto;
	}

	public void setListDetailDto(List<OrderDetailDTO> listDetailDto) {
		this.listDetailDto = listDetailDto;
	}

	@Override
	public String toString() {
		return "DisShopOrderDetailDTO [orderCode=" + orderCode + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + ", listDetailDto="
				+ listDetailDto + "]";
	}

}
