package cn.com.glsx.supplychain.model.stk;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockSnExportVo implements Serializable{

	@ExcelProperty(value = "设备sn",index = 1)
	private String sn;
	@ExcelProperty(value = "发往商户编码",index = 2)
	private String toMerchantCode;
	@ExcelProperty(value = "发往商户名称",index = 3)
	private String toMerchantName;
	@ExcelProperty(value = "当前所在商户编码",index = 4)
	private String curMerchantCode;
	@ExcelProperty(value = "当前所在商户名称",index = 5)
	private String curMerchantName;
	@ExcelProperty(value = "设备类型名称",index = 6)
	private String deviceTypeName;
	@ExcelProperty(value = "物料编码",index = 7)
	private String materialCode;
	@ExcelProperty(value = "物料名称",index = 8)
	private String materialName;
	@ExcelProperty(value = "激活时间",index = 9)
	private String activeTime;
	@ExcelProperty(value = "发货时间",index = 10)
	private String sendTime;
	@ExcelProperty(value = "最后调拨时间",index = 11)
	private String transferTime;


	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getCurMerchantCode() {
		return curMerchantCode;
	}

	public void setCurMerchantCode(String curMerchantCode) {
		this.curMerchantCode = curMerchantCode;
	}

	public String getCurMerchantName() {
		return curMerchantName;
	}

	public void setCurMerchantName(String curMerchantName) {
		this.curMerchantName = curMerchantName;
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

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}


}
