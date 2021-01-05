package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

public class STKWarningDevicesnExportVo implements Serializable{
	@ExcelProperty(value = "SN／IMEI",index = 1)
	private String sn;

	@ExcelProperty(value = "设备类型",index = 2)
	private String deviceTypeName;

	@ExcelProperty(value = "物料编码",index = 3)
	private String materialCode;

	@ExcelProperty(value = "物料名称",index = 4)
	private String materialName;

	@ExcelProperty(value = "发往商户",index = 5)
    private String toMerchantName;

	@ExcelProperty(value = "广联发货时间",index = 6)
	private String sendTime;

	@ExcelProperty(value = "总呆滞时长（天）",index = 7)
	private Integer unActiveDays;

	@ExcelProperty(value = "当前滞留仓位",index = 8)
	private String curMerchantName;

	@ExcelProperty(value = "滞留仓位入库时间",index = 9)
	private String transferTime;

	@ExcelProperty(value = "仓位滞留时长(天)",index = 10)
	private Integer stayCurMerchantDays;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getToMerchantName() {
		return toMerchantName;
	}

	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getUnActiveDays() {
		return unActiveDays;
	}

	public void setUnActiveDays(Integer unActiveDays) {
		this.unActiveDays = unActiveDays;
	}

	public String getCurMerchantName() {
		return curMerchantName;
	}

	public void setCurMerchantName(String curMerchantName) {
		this.curMerchantName = curMerchantName;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public Integer getStayCurMerchantDays() {
		return stayCurMerchantDays;
	}

	public void setStayCurMerchantDays(Integer stayCurMerchantDays) {
		this.stayCurMerchantDays = stayCurMerchantDays;
	}

	@Override
	public String toString() {
		return "STKWarningDevicesnExportVo{" +
				"sn='" + sn + '\'' +
				", deviceTypeName='" + deviceTypeName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", toMerchantName='" + toMerchantName + '\'' +
				", sendTime='" + sendTime + '\'' +
				", unActiveDays=" + unActiveDays +
				", curMerchantName='" + curMerchantName + '\'' +
				", transferTime='" + transferTime + '\'' +
				", stayCurMerchantDays=" + stayCurMerchantDays +
				'}';
	}
}
