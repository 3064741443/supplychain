package cn.com.glsx.supplychain.jxc.vo;

import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfoImport;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheckImportDataVo implements Serializable{

	List<JXCMTDeviceInfoImport> deviceInfoImportSuccessList;
	List<JXCMTDeviceInfoImport> deviceInfoImportFailList;

	public List<JXCMTDeviceInfoImport> getDeviceInfoImportSuccessList() {
		return deviceInfoImportSuccessList;
	}

	public void setDeviceInfoImportSuccessList(List<JXCMTDeviceInfoImport> deviceInfoImportSuccessList) {
		this.deviceInfoImportSuccessList = deviceInfoImportSuccessList;
	}

	public List<JXCMTDeviceInfoImport> getDeviceInfoImportFailList() {
		return deviceInfoImportFailList;
	}

	public void setDeviceInfoImportFailList(List<JXCMTDeviceInfoImport> deviceInfoImportFailList) {
		this.deviceInfoImportFailList = deviceInfoImportFailList;
	}

	@Override
	public String toString() {
		return "CheckImportDataVo{" +
				"deviceInfoImportSuccessList=" + deviceInfoImportSuccessList +
				", deviceInfoImportFailList=" + deviceInfoImportFailList +
				'}';
	}
}
