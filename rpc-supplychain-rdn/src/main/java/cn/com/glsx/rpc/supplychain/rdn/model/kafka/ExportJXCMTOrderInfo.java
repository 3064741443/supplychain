package cn.com.glsx.rpc.supplychain.rdn.model.kafka;

import java.io.Serializable;

/**
 * 供应链发货单导出搜索条件
 */
@SuppressWarnings("serial")
public class ExportJXCMTOrderInfo implements Serializable{

	/**
	 * 设备大类id
	 */
	private Integer deviceTypeId;

	/**
	 * 发货单编号
	 */
	private String dispatchOrderCode;

	/**
	 * 发货状态 UF:未完成 OV:已完成 CL:已取消
	 */
	private String status;

	/**
	 * 子订单号
	 */
	private String merchantOrder;

	/**
	 * 发往商户号
	 */
	private String sendMerchantNo;

	/**
	 * 发货仓库id
	 */
	private Integer warehouseId;

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}

	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantOrder() {
		return merchantOrder;
	}

	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}

	public String getSendMerchantNo() {
		return sendMerchantNo;
	}

	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "ExportJXCMTOrderInfo{" +
				"deviceTypeId=" + deviceTypeId +
				", dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", status='" + status + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", sendMerchantNo='" + sendMerchantNo + '\'' +
				", warehouseId=" + warehouseId +
				'}';
	}
}
