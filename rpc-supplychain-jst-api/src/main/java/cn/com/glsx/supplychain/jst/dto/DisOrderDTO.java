package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisOrderDTO extends BaseDTO implements Serializable {

	// 订单状态 WC:待审核 WS：待发货 UF：未完成 FI：已完成
	public String status;
	// 下单月份 格式202002
	public String orderTime;
	
	private Byte serviceType;
	// 产品名称
	private String context;
	// 门店编码
	private String shopCode;
	//商户编码
	private String merchantCode;
	// 订单列表
	private List<OrderDTO> listOrderDto;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Byte getServiceType() {
		return serviceType;
	}

	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	

	public List<OrderDTO> getListOrderDto() {
		return listOrderDto;
	}

	public void setListOrderDto(List<OrderDTO> listOrderDto) {
		this.listOrderDto = listOrderDto;
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

	@Override
	public String toString() {
		return "DisOrderDTO [status=" + status + ", orderTime=" + orderTime
				+ ", serviceType=" + serviceType + ", context=" + context
				+ ", shopCode=" + shopCode + ", listOrderVo=" + listOrderDto
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo + "]";
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

}
