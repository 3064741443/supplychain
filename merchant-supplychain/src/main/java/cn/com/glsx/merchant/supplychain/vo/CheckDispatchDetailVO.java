package cn.com.glsx.merchant.supplychain.vo;

public class CheckDispatchDetailVO {

	private String sn;
	
	private String detail;

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
		return "ckDispatchDetailVO [sn=" + sn + ", detail=" + detail + "]";
	}
	
	
}
