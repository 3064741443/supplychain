package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKWarningWaresaleExportVo implements Serializable{
	@ExcelProperty(value = "服务商／直营店",index = 1)
	private String merchantName;

	@ExcelProperty(value = "渠道类型",index = 2)
	private String channelName;

	@ExcelProperty(value = "月份",index = 3)
    private String warningMonth;

	@ExcelProperty(value = "设备类型",index = 4)
	private String deviceTypeName;

	@ExcelProperty(value = "库销比",index = 5)
    private Double waresaleRate;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getWarningMonth() {
		return warningMonth;
	}

	public void setWarningMonth(String warningMonth) {
		this.warningMonth = warningMonth;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public Double getWaresaleRate() {
		return waresaleRate;
	}

	public void setWaresaleRate(Double waresaleRate) {
		this.waresaleRate = waresaleRate;
	}

	@Override
	public String toString() {
		return "STKWarningWaresaleExportVo{" +
				"channelName='" + channelName + '\'' +
				", merchantName='" + merchantName + '\'' +
				", warningMonth='" + warningMonth + '\'' +
				", deviceTypeName='" + deviceTypeName + '\'' +
				", waresaleRate=" + waresaleRate +
				'}';
	}
}
