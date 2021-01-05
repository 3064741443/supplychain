package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisOrderVO implements Serializable {

	// 订单状态 WC:待审核 WS：待发货 UF：未完成 FI：已完成
	public String status;
	// 下单月份 格式202002
	public String orderTime;
	// 业务类型 JB:驾宝无忧,JR:金融风控,CZ:车机,HS:后视镜,OT:其它
	private String serviceType;
	// 产品名称
	private String context;
	//门店编码
	private String shopCode;
	//供应商编码
	private String merchantCode;
	// 订单列表
	private List<OrderVO> listOrderVo;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public List<OrderVO> getListOrderVo() {
		return listOrderVo;
	}

	public void setListOrderVo(List<OrderVO> listOrderVo) {
		this.listOrderVo = listOrderVo;
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

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Override
	public String toString() {
		return "DisOrderVO [status=" + status + ", orderTime=" + orderTime
				+ ", serviceType=" + serviceType + ", context=" + context
				+ ", shopCode=" + shopCode + ", merchantCode=" + merchantCode
				+ ", listOrderVo=" + listOrderVo + ", pageSize=" + pageSize
				+ ", pageNo=" + pageNo + "]";
	}
	
	

}
