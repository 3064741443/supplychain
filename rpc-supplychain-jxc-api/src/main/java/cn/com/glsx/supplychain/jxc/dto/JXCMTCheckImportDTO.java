package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTCheckImportDTO implements Serializable{

	public Integer paratope;
	public List<JXCMTDeviceInfoDTO> listDeviceInfoSuccess;
	public List<JXCMTDeviceInfoDTO> listDeviceInfoFailed;
	public List<JXCMTDeviceInfoDTO> getListDeviceInfoSuccess() {
		return listDeviceInfoSuccess;
	}
	public void setListDeviceInfoSuccess(
			List<JXCMTDeviceInfoDTO> listDeviceInfoSuccess) {
		this.listDeviceInfoSuccess = listDeviceInfoSuccess;
	}
	public List<JXCMTDeviceInfoDTO> getListDeviceInfoFailed() {
		return listDeviceInfoFailed;
	}
	public void setListDeviceInfoFailed(
			List<JXCMTDeviceInfoDTO> listDeviceInfoFailed) {
		this.listDeviceInfoFailed = listDeviceInfoFailed;
	}
	
	public Integer getParatope() {
		return paratope;
	}
	public void setParatope(Integer paratope) {
		this.paratope = paratope;
	}
	@Override
	public String toString() {
		return "JXCMTCheckImportDTO [listDeviceInfoSuccess="
				+ listDeviceInfoSuccess + ", listDeviceInfoFailed="
				+ listDeviceInfoFailed + "]";
	}
	
	
}
