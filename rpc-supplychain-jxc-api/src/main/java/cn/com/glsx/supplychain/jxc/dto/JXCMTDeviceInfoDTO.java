package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTDeviceInfoDTO implements Serializable{

	private String iccid;
    private String imei;
    private String sn;
    private String failedReason;
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getFailedReason() {
		return failedReason;
	}
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	@Override
	public String toString() {
		return "JXCMTDeviceInfoDTO [iccid=" + iccid + ", imei=" + imei
				+ ", sn=" + sn + ", failedReason=" + failedReason + "]";
	}
	
    
}
