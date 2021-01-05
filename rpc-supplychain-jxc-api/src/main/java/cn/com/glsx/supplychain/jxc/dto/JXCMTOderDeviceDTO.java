package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTOderDeviceDTO implements Serializable{

	public String userName;
	public String dispatchOrderCode;
	public String logisticsNo;
	public String logisticsCpy;
	public Integer sendQulities;
	public String sendTime;
	public List<JXCMTDeviceInfoDTO> listDeviceInfos;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getLogisticsNo() {
		return logisticsNo;
	}
	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	public String getLogisticsCpy() {
		return logisticsCpy;
	}
	public void setLogisticsCpy(String logisticsCpy) {
		this.logisticsCpy = logisticsCpy;
	}
	public List<JXCMTDeviceInfoDTO> getListDeviceInfos() {
		return listDeviceInfos;
	}
	public void setListDeviceInfos(List<JXCMTDeviceInfoDTO> listDeviceInfos) {
		this.listDeviceInfos = listDeviceInfos;
	}
	public Integer getSendQulities() {
		return sendQulities;
	}
	public void setSendQulities(Integer sendQulities) {
		this.sendQulities = sendQulities;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "JXCMTOderDeviceDTO [userName=" + userName
				+ ", dispatchOrderCode=" + dispatchOrderCode + ", logisticsNo="
				+ logisticsNo + ", logisticsCpy=" + logisticsCpy
				+ ", sendQulities=" + sendQulities + ", sendTime=" + sendTime
				+ ", listDeviceInfos=" + listDeviceInfos + "]";
	}
	
	
	
}
