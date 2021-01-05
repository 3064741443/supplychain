package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

import cn.com.glsx.supplychain.model.PageInfo;

@SuppressWarnings("serial")
public class DeviceFileExcelVo extends PageInfo implements Serializable{
    private String sn;
    
    private String iccid;
    
    private String imsi;
    
    private String verifCode;
    
    private String batchNo;
    
    private String name;
    
    private Integer deviceCode;
    
    private String deviceName;
    
    private Integer packageId;

    private String packageName; //商品名称
    
    private String operatorMerchantName;

    private String sendMerchantName;
    
    private String packageUserId;
    
    private String packageUserTime;
    
    private String userFlag;;
    
    private String userTime;

    private String terminalDiscode;
    
    private String modelName;

    private String softVersion;

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getVerifCode() {
		return verifCode;
	}

	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getOperatorMerchantName() {
		return operatorMerchantName;
	}

	public void setOperatorMerchantName(String operatorMerchantName) {
		this.operatorMerchantName = operatorMerchantName;
	}

	public String getSendMerchantName() {
		return sendMerchantName;
	}

	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}

	public String getPackageUserId() {
		return packageUserId;
	}

	public void setPackageUserId(String packageUserId) {
		this.packageUserId = packageUserId;
	}

	public String getPackageUserTime() {
		return packageUserTime;
	}

	public void setPackageUserTime(String packageUserTime) {
		this.packageUserTime = packageUserTime;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getTerminalDiscode() {
		return terminalDiscode;
	}

	public void setTerminalDiscode(String terminalDiscode) {
		this.terminalDiscode = terminalDiscode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	@Override
	public String toString() {
		return "DeviceFileExcelVo{" +
				"sn='" + sn + '\'' +
				", iccid='" + iccid + '\'' +
				", imsi='" + imsi + '\'' +
				", verifCode='" + verifCode + '\'' +
				", batchNo='" + batchNo + '\'' +
				", name='" + name + '\'' +
				", deviceCode=" + deviceCode +
				", deviceName='" + deviceName + '\'' +
				", packageId=" + packageId +
				", packageName='" + packageName + '\'' +
				", operatorMerchantName='" + operatorMerchantName + '\'' +
				", sendMerchantName='" + sendMerchantName + '\'' +
				", packageUserId='" + packageUserId + '\'' +
				", packageUserTime='" + packageUserTime + '\'' +
				", userFlag='" + userFlag + '\'' +
				", userTime='" + userTime + '\'' +
				", terminalDiscode='" + terminalDiscode + '\'' +
				", modelName='" + modelName + '\'' +
				", softVersion='" + softVersion + '\'' +
				'}';
	}
}