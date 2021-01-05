package cn.com.glsx.rpc.supplychain.rdn.model.kafka;

import java.io.Serializable;

/**
 * 库销比预警导出条件
 */
@SuppressWarnings("serial")
public class ExportMerchantWarningWaresale implements Serializable{

	/**
	 * 商户渠道id
	 */
	private Integer channelId;
	/**
	 * 服务商名称/编码
	 */
	private String merchantName;
	/**
	 * 设备类型id
	 */
    private Integer deviceType;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "ExportMerchantWarningWaresale{" +
				"channelId=" + channelId +
				", merchantName='" + merchantName + '\'' +
				", deviceType=" + deviceType +
				'}';
	}
}
