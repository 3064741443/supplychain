package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExportWarningDeviceSn implements Serializable{

	/**
	 * 发往商户名称/编码
	 */
	private String toMerchantName;
	/**
	 * 设备类型id
	 */
	private Integer deviceType;
	/**
	 * 物料名称/编码
	 */
	private String materialName;
	/**
	 * 预警编码
	 */
	private String warningCode;

	public String getToMerchantName() {
		return toMerchantName;
	}

	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getWarningCode() {
		return warningCode;
	}

	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}

	@Override
	public String toString() {
		return "ExportWarningDeviceSn{" +
				"toMerchantName='" + toMerchantName + '\'' +
				", deviceType=" + deviceType +
				", materialName='" + materialName + '\'' +
				", warningCode='" + warningCode + '\'' +
				'}';
	}
}
