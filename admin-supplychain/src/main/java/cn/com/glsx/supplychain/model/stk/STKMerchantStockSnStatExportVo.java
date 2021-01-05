package cn.com.glsx.supplychain.model.stk;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockSnStatExportVo implements Serializable{
	@ExcelProperty(value = "设备类型名称",index = 1)
	private String deviceTypeName;
	@ExcelProperty(value = "发往商户编码",index = 2)
	private String toMerchantCode;
	@ExcelProperty(value = "发往商户名称",index = 3)
    private String toMerchantName;
	@ExcelProperty(value = "数量",index = 4)
    private Integer tradeTotal;
	@ExcelProperty(value = "未激活天数",index = 5)
    private Integer unActiveDays;
	@ExcelProperty(value = "激活与否",index = 6)
    private String activeOrNot;

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getToMerchantCode() {
		return toMerchantCode;
	}

	public void setToMerchantCode(String toMerchantCode) {
		this.toMerchantCode = toMerchantCode;
	}

	public String getToMerchantName() {
		return toMerchantName;
	}

	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
	}

	public Integer getTradeTotal() {
		return tradeTotal;
	}

	public void setTradeTotal(Integer tradeTotal) {
		this.tradeTotal = tradeTotal;
	}

	public Integer getUnActiveDays() {
		return unActiveDays;
	}

	public void setUnActiveDays(Integer unActiveDays) {
		this.unActiveDays = unActiveDays;
	}

	public String getActiveOrNot() {
		return activeOrNot;
	}

	public void setActiveOrNot(String activeOrNot) {
		this.activeOrNot = activeOrNot;
	}

	@Override
	public String toString() {
		return "STKMerchantStockSnStatExportVo{" +
				"deviceTypeName='" + deviceTypeName + '\'' +
				", toMerchantCode='" + toMerchantCode + '\'' +
				", toMerchantName='" + toMerchantName + '\'' +
				", tradeTotal=" + tradeTotal +
				", unActiveDays=" + unActiveDays +
				", activeOrNot='" + activeOrNot + '\'' +
				'}';
	}
}
