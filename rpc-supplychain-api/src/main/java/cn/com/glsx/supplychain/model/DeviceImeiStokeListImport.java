package cn.com.glsx.supplychain.model;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 硬件管理设备导入
 */
@SuppressWarnings("serial")
@Table(name = "DeviceImeiStokeListImport")
public class DeviceImeiStokeListImport implements Serializable{

	private String imei;
	private String externalFlag;
	private Integer devType;
	private String merchantNo;

	@ExcelResources(title = "IMEI",order=0)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@ExcelResources(title = "设备标识",order=1)
	public String getExternalFlag() {
		return externalFlag;
	}

	public void setExternalFlag(String externalFlag) {
		this.externalFlag = externalFlag;
	}
	@ExcelResources(title = "设备大类型",order=2)
	public Integer getDevType() {
		return devType;
	}

	public void setDevType(Integer devType) {
		this.devType = devType;
	}
	@ExcelResources(title = "商户号",order=3)
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
}
