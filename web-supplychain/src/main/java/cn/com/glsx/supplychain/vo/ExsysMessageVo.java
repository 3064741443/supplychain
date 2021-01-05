package cn.com.glsx.supplychain.vo;

import java.util.List;

public class ExsysMessageVo {

	private Integer total;
	private List<ExsysDeviceStatuVo> infos;
	private List<ExsysOrderInfoVo> orderinfos;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<ExsysDeviceStatuVo> getInfos() {
		return infos;
	}
	public void setInfos(List<ExsysDeviceStatuVo> infos) {
		this.infos = infos;
	}
	public List<ExsysOrderInfoVo> getOrderinfos() {
		return orderinfos;
	}
	public void setOrderinfos(List<ExsysOrderInfoVo> orderinfos) {
		this.orderinfos = orderinfos;
	}
	
	
	
}
