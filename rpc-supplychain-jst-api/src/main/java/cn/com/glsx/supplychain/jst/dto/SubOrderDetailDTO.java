package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubOrderDetailDTO extends BaseDTO implements Serializable {

	// 门店编码
	private String shopCode;
	// 代理商商户号
	private String agentMerchantCode;
	// 订单编号
	private String orderCode;
	// 地址信息
	private BsAddressDTO addressDto;
	// 物流信息
	private BsLogisticsDTO logisticsDto;
	// 明细
	private List<OrderDetailDTO> listDetailDto;

	//配送方式：O：线上配送  F:线下配送
	private  String shipType;

	/**
	 *  默认Y：表示扫码出库， N：不扫码出库
	 */
	private  String scanType;

	/**
	 * 物料信息
	 */
	private List<MaterialDTO> materialDTOList;

	public List<MaterialDTO> getMaterialDTOList() {
		return materialDTOList;
	}

	public void setMaterialDTOList(List<MaterialDTO> materialDTOList) {
		this.materialDTOList = materialDTOList;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BsAddressDTO getAddressDto() {
		return addressDto;
	}

	public void setAddressDto(BsAddressDTO addressDto) {
		this.addressDto = addressDto;
	}

	public BsLogisticsDTO getLogisticsDto() {
		return logisticsDto;
	}

	public void setLogisticsDto(BsLogisticsDTO logisticsDto) {
		this.logisticsDto = logisticsDto;
	}

	public List<OrderDetailDTO> getListDetailDto() {
		return listDetailDto;
	}

	public void setListDetailDto(List<OrderDetailDTO> listDetailDto) {
		this.listDetailDto = listDetailDto;
	}

	@Override
	public String toString() {
		return "SubOrderDetailDTO{" +
				"shopCode='" + shopCode + '\'' +
				", agentMerchantCode='" + agentMerchantCode + '\'' +
				", orderCode='" + orderCode + '\'' +
				", addressDto=" + addressDto +
				", logisticsDto=" + logisticsDto +
				", listDetailDto=" + listDetailDto +
				", shipType='" + shipType + '\'' +
				", scanType='" + scanType + '\'' +
				", materialDTOList=" + materialDTOList +
				'}';
	}

	public String getAgentMerchantCode() {
		return agentMerchantCode;
	}

	public void setAgentMerchantCode(String agentMerchantCode) {
		this.agentMerchantCode = agentMerchantCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

}
