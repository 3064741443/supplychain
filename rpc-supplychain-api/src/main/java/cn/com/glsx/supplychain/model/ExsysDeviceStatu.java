package cn.com.glsx.supplychain.model;

import java.io.Serializable;

public class ExsysDeviceStatu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sn;
	private String imsi;
	private String flagtype;
	private String userflag;
	private String vehicleflagtype;
	private String vehicleflag;
	private Long activetime;
	private Long updatetime;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getFlagtype() {
		return flagtype;
	}
	public void setFlagtype(String flagtype) {
		this.flagtype = flagtype;
	}
	public String getUserflag() {
		return userflag;
	}
	public void setUserflag(String userflag) {
		this.userflag = userflag;
	}
	public String getVehicleflagtype() {
		return vehicleflagtype;
	}
	public void setVehicleflagtype(String vehicleflagtype) {
		this.vehicleflagtype = vehicleflagtype;
	}
	public String getVehicleflag() {
		return vehicleflag;
	}
	public void setVehicleflag(String vehicleflag) {
		this.vehicleflag = vehicleflag;
	}
	public Long getActivetime() {
		return activetime;
	}
	public void setActivetime(Long activetime) {
		this.activetime = activetime;
	}
	public Long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "ExsysDeviceStatu [sn=" + sn + ", imsi=" + imsi + ", flagtype="
				+ flagtype + ", userflag=" + userflag + ", vehicleflagtype="
				+ vehicleflagtype + ", vehicleflag=" + vehicleflag
				+ ", activetime=" + activetime + ", updatetime=" + updatetime
				+ "]";
	}
	
	
}
