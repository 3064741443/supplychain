package cn.com.glsx.rpc.supplychain.rdn.model.kafka;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * 库存导出搜索条件
 */
public class ExportSTKMerchantStock implements Serializable{
	/**
	 * 商户渠道id
	 */
	private Integer channelId;
	/**
	 * 服务商登陆名
	 */
	private String merchantSearchKey;
	/**
	 * 设备类型id
	 */
	private Integer deviceType;
	/**
	 * 月份 格式2020-08
	 */
	private String stockMonth;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getMerchantSearchKey() {
		return merchantSearchKey;
	}

	public void setMerchantSearchKey(String merchantSearchKey) {
		this.merchantSearchKey = merchantSearchKey;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getStockMonth() {
		return stockMonth;
	}

	public void setStockMonth(String stockMonth) {
		this.stockMonth = stockMonth;
	}

	@Override
	public String toString() {
		return "ExportSTKMerchantStock{" +
				"channelId=" + channelId +
				", merchantSearchKey='" + merchantSearchKey + '\'' +
				", deviceType=" + deviceType +
				", stockMonth='" + stockMonth + '\'' +
				'}';
	}
}
