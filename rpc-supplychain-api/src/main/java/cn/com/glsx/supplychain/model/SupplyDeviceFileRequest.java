package cn.com.glsx.supplychain.model;

import java.io.Serializable;

import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.enums.VehicleFlagTypeEnum;

@SuppressWarnings("serial")
public class SupplyDeviceFileRequest extends SupplyRequest implements Serializable{

	//设备sn
	private String sn;

	//设备ID
	//private Integer id;
	
	//车辆标识
	private String vehicleFlag;
	
	//用户标识（用户帐号ID）
	private String userFlag;
	
	//iccid(作为卡标识)
	private String iccid;
	
	//合作公司ID 默认填1
	private Integer companyId;
	
	//设备厂商码
	private String manufactureCode;
	
	//设备软件版本
	private String softVersion;
	
	//imsi
	private String imsi;
	
	//标记类型（VN:车牌标识    UD:用户平台ID  OT:其他标识）
	private VehicleFlagTypeEnum flagType;
	
	//标记类型（PH:用户手机号    UD:用户平台ID  OT:其他标识）
	private UserFlagTypeEnum userFlagType;
	
	//套餐激活状态
	private PackageStatuEnum pkgStatus; 
	
	//外部卡外部设备要传入deviceCode
	private Integer deviceCode;
	
	//外部卡外部设备要传入packageId
	private Integer packageId;
	
	//就设备wince套餐
	private Integer androidPackageId;
	
	
	
	public Integer getAndroidPackageId() {
		return androidPackageId;
	}

	public void setAndroidPackageId(Integer androidPackageId) {
		this.androidPackageId = androidPackageId;
	}

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getVehicleFlag() {
		return vehicleFlag;
	}

	public void setVehicleFlag(String vehicleFlag) {
		this.vehicleFlag = vehicleFlag;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public VehicleFlagTypeEnum getFlagType() {
		return flagType;
	}

	public void setFlagType(VehicleFlagTypeEnum flagType) {
		this.flagType = flagType;
	}

	public PackageStatuEnum getPkgStatus() {
		return pkgStatus;
	}

	public void setPkgStatus(PackageStatuEnum pkgStatus) {
		this.pkgStatus = pkgStatus;
	}
	
	
	public String getManufactureCode() {
		return manufactureCode;
	}

	public void setManufactureCode(String manufactureCode) {
		this.manufactureCode = manufactureCode;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	
	public UserFlagTypeEnum getUserFlagType() {
		return userFlagType;
	}

	public void setUserFlagType(UserFlagTypeEnum userFlagType) {
		this.userFlagType = userFlagType;
	}

	@Override
	public String toString() {
		return "SupplyDeviceFileRequest ["
				+ (sn != null ? "sn=" + sn + ", " : "")
				//+ (id != null ? "id=" + id + ", " : "")
				+ (vehicleFlag != null ? "vehicleFlag=" + vehicleFlag + ", "
						: "")
				+ (userFlag != null ? "userFlag=" + userFlag + ", " : "")
				+ (iccid != null ? "iccid=" + iccid + ", " : "")
				+ (companyId != null ? "companyId=" + companyId + ", " : "")
				+ (manufactureCode != null ? "manufactureCode="
						+ manufactureCode + ", " : "")
				+ (softVersion != null ? "softVersion=" + softVersion + ", "
						: "")
				+ (imsi != null ? "imsi=" + imsi + ", " : "")
				+ (flagType != null ? "flagType=" + flagType + ", " : "")
				+ (userFlagType != null ? "userFlagType=" + userFlagType + ", "
						: "")
				+ (pkgStatus != null ? "pkgStatus=" + pkgStatus + ", " : "")
				+ (deviceCode != null ? "deviceCode=" + deviceCode + ", " : "")
				+ (packageId != null ? "packageId=" + packageId : "") + "]";
	}

	

	

	
}
