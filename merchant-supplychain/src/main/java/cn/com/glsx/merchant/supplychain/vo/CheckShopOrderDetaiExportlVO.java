package cn.com.glsx.merchant.supplychain.vo;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckShopOrderDetaiExportlVO extends BaseDTO implements Serializable{
	
	private String sn;
	
	private String detail;

	//失败描述
	private String failDesc;

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

	public String getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}

	@Override
	public String toString() {
		return "CheckShopOrderDetaiExportlDTO{" +
				"sn='" + sn + '\'' +
				", detail='" + detail + '\'' +
				", failDesc='" + failDesc + '\'' +
				'}';
	}


}
