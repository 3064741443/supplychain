package cn.com.glsx.supplychain.vo;

/**
 * @Title: 业务系统上报设备状态VO
 * @Description: 
 * @author 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
public class ExsysDeviceStatuVo {

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
	
	
	
}
