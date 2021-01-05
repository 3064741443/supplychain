package cn.com.glsx.rpc.supplychain.rdn.model.kafka;

import java.io.Serializable;

/**
 * 库存明细导出搜索条件
 */
@SuppressWarnings("serial")
public class ExportMerchantStockSn implements Serializable{
    /**
     * 设备大类
     */
	private Integer deviceType;
	/**
	 * TH:大于3个月未激活
	 */
	private String unActiveDayFlag;

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}

	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
	}

	@Override
	public String toString() {
		return "MerchantStockSnVO{" +
				"deviceType=" + deviceType +
				", unActiveDayFlag='" + unActiveDayFlag + '\'' +
				'}';
	}
}
