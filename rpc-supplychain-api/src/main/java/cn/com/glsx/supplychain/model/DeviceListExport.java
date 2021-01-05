package cn.com.glsx.supplychain.model;

import java.io.Serializable;

import javax.persistence.Table;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

/**
 * 硬件管理异常设备导出
 */
@SuppressWarnings("serial")
@Table(name = "DeviceListExport")
public class DeviceListExport implements Serializable{

	private String iccid;
	
	private String imsi;
	
	private String imei;
	
	private String vcode;
	
	private String batchNo;
	
	private String packageNo;
	
	private String mentchantNo;
	
	private String reason;
	
	private String modulePhone;

	@ExcelResources(title = "模块手机号",order=0)
	public String getModulePhone() {
		return modulePhone;
	}

	public void setModulePhone(String modulePhone) {
		this.modulePhone = modulePhone;
	}

	@ExcelResources(title = "ICCID",order=1)
	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	@ExcelResources(title = "IMSI",order=2)
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@ExcelResources(title = "IMEI/SN",order=3)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@ExcelResources(title = "验证码",order=4)
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	@ExcelResources(title = "批次号",order=5)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@ExcelResources(title = "商品编号",order=6)
	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	@ExcelResources(title = "发往商户号",order=7)
	public String getMentchantNo() {
		return mentchantNo;
	}

	public void setMentchantNo(String mentchantNo) {
		this.mentchantNo = mentchantNo;
	}

	@ExcelResources(title = "失败原因",order=8)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
