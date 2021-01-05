package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
public class CheckShopOrderDetailDTO extends BaseDTO implements Serializable{
	
	private String sn;
	
	private String detail;

	@ExcelResources(title = "设备SN",order = 0)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "CheckShopOrderDetailDTO [sn=" + sn + ", detail=" + detail + "]";
	}
	
	

}
