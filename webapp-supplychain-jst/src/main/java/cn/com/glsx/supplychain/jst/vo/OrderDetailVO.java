package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderDetailVO implements Serializable{

	//sn/imei
	public String sn;
	//设备编号
	public String attribCode;

	/**
	 * 是否是系统虚拟的sn Y:是 N:否
	 */
	private String vtSn;

	public String getVtSn() {
		return vtSn;
	}

	public void setVtSn(String vtSn) {
		this.vtSn = vtSn;
	}

	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	@Override
	public String toString() {
		return "OrderDetailVO{" +
				"sn='" + sn + '\'' +
				", attribCode='" + attribCode + '\'' +
				", vtSn='" + vtSn + '\'' +
				'}';
	}


}
