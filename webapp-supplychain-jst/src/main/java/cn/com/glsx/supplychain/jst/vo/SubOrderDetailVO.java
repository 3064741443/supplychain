package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubOrderDetailVO implements Serializable{

	//门店编码
	private String shopCode;
	// 订单编号
	private String orderCode;
	//地址信息
    private AddressVO addressVo;
    //物流信息
    private LogisticsVO logisticsVo;
    //明细
  	private List<OrderDetailVO> listDetail;

    /**
     * @author: luoqian
     */
	//配送方式：O：线上配送  F:线下配送
	private  String shipType;

	/**
	 *  默认N：表示扫码出库， Y：不扫码出库
	 */
	private  String scanType;

	/**
	 * 物料信息
	 */
	private List<MaterialVO> materialVOList;

	public List<MaterialVO> getMaterialVOList() {
		return materialVOList;
	}

	public void setMaterialVOList(List<MaterialVO> materialVOList) {
		this.materialVOList = materialVOList;
	}

	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public AddressVO getAddressVo() {
		return addressVo;
	}
	public void setAddressVo(AddressVO addressVo) {
		this.addressVo = addressVo;
	}
	public LogisticsVO getLogisticsVo() {
		return logisticsVo;
	}
	public void setLogisticsVo(LogisticsVO logisticsVo) {
		this.logisticsVo = logisticsVo;
	}
	public List<OrderDetailVO> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<OrderDetailVO> listDetail) {
		this.listDetail = listDetail;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	@Override
	public String toString() {
		return "SubOrderDetailVO{" +
				"shopCode='" + shopCode + '\'' +
				", orderCode='" + orderCode + '\'' +
				", addressVo=" + addressVo +
				", logisticsVo=" + logisticsVo +
				", listDetail=" + listDetail +
				", shipType='" + shipType + '\'' +
				", scanType='" + scanType + '\'' +
				", materialVOList=" + materialVOList +
				'}';
	}

	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}


	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}
}
