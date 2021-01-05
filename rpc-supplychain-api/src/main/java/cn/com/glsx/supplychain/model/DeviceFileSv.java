package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import cn.com.glsx.supplychain.enums.ExternalFlagEnum;
import cn.com.glsx.supplychain.enums.OutStorageTypeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.SystemPlatEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.enums.VehicleFlagTypeEnum;

@SuppressWarnings("serial")
public class DeviceFileSv implements Serializable {

	//设备sn（imei）
	private String sn;

	//设备ID
    private Integer id;

    //设备编码
    private Integer deviceCode;
    
    //设备编码名称
    private String	deviceName;
    
    //设备类型Id
    private Integer deviceTypeId;
    
    //设备类型名称
    private String deviceTypeName;

    //出库订单号(表)order_info
    private String orderCode;

    //验证码
    private String verifCode;

    //出库批次号
    private String batchNo;

    //出库商品编码或者ID
    private Integer packageId;

    //运营商户编码
    private String operatorMerchantId;

    //发送商户编码
    private String sendMerchantId;

    //入库时间
    private String inStorageTime;

    //出库时间
    private String outStorageTime;

    //出库类型（OutStorageTypeEnum）
    private OutStorageTypeEnum outStorageType;

    //终端显示码
    private String terminalDiscode;

    //商品激活用户ID（表）device_user_manager
    //private Integer packageUserId;
    
    //激活用户标志
    private String packageUserFlag;
    
    //激活用户标志识别类型
    private UserFlagTypeEnum packageUserFlagType;
    
    //商品状态
    private PackageStatuEnum packageStatu;

    //设备当前绑定用户ID（表）device_user_manager
    //private Integer userId;
    
    //当前绑定用户标志
    private String userFlag;
    
    //当前绑定用户标志识别类型
    private UserFlagTypeEnum userFlagType;

    //设备当前卡ID（表）device_card_manager
    //private Integer cardId;
    
    //当前卡iccid
    private String iccid;
    
    //当前卡imsi
    private String imsi;

    //设备当前版本信息ID（表）firmware_info
   // private Integer firmwareId;
    private String softVersion;

    //设备当前车辆ID（表）device_vehicle_manager
    //private Integer vehicleId;
    
    //当前绑定车辆标识
    private String vehicleFlag;
    
    //当前绑定车辆标识识别类型
    private VehicleFlagTypeEnum vehicleFlagType;

    //设备商品激活时间
    private String packageUserTime;
    
    //用户绑定时间
    private String userTime;
    
    //卡绑定时间
    private String cardTime;
    
    //设备所在系统平台(SystemPlatEnum)
    private SystemPlatEnum sysPlat;
    
    //厂商码
    private String manufacturerCode;
    
    //androidpackageid
    private Integer androidPackageId;
        
    //设备所属标识
    private ExternalFlagEnum externalFlag;
    
    //删除标记
    private String deletedFlag;
    
    //品牌定制商
    private Integer merchantId;
    
    //厂商码集合（device_code表中）以逗号隔开 以前老数据移留问题
    private String manuCodesSet;
    
    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    
    
	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getManuCodesSet() {
		return manuCodesSet;
	}

	public void setManuCodesSet(String manuCodesSet) {
		this.manuCodesSet = manuCodesSet;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(String inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public String getOutStorageTime() {
		return outStorageTime;
	}

	public void setOutStorageTime(String outStorageTime) {
		this.outStorageTime = outStorageTime;
	}

	public OutStorageTypeEnum getOutStorageType() {
		return outStorageType;
	}

	public void setOutStorageType(OutStorageTypeEnum outStorageType) {
		this.outStorageType = outStorageType;
	}

	public String getTerminalDiscode() {
		return terminalDiscode;
	}

	public void setTerminalDiscode(String terminalDiscode) {
		this.terminalDiscode = terminalDiscode;
	}

	public String getPackageUserFlag() {
		return packageUserFlag;
	}

	public void setPackageUserFlag(String packageUserFlag) {
		this.packageUserFlag = packageUserFlag;
	}

	public UserFlagTypeEnum getPackageUserFlagType() {
		return packageUserFlagType;
	}

	public void setPackageUserFlagType(UserFlagTypeEnum packageUserFlagType) {
		this.packageUserFlagType = packageUserFlagType;
	}

	public PackageStatuEnum getPackageStatu() {
		return packageStatu;
	}

	public void setPackageStatu(PackageStatuEnum packageStatu) {
		this.packageStatu = packageStatu;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public UserFlagTypeEnum getUserFlagType() {
		return userFlagType;
	}

	public void setUserFlagType(UserFlagTypeEnum userFlagType) {
		this.userFlagType = userFlagType;
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

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getVehicleFlag() {
		return vehicleFlag;
	}

	public void setVehicleFlag(String vehicleFlag) {
		this.vehicleFlag = vehicleFlag;
	}

	public VehicleFlagTypeEnum getVehicleFlagType() {
		return vehicleFlagType;
	}

	public void setVehicleFlagType(VehicleFlagTypeEnum vehicleFlagType) {
		this.vehicleFlagType = vehicleFlagType;
	}

	public String getPackageUserTime() {
		return packageUserTime;
	}

	public void setPackageUserTime(String packageUserTime) {
		this.packageUserTime = packageUserTime;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

	public String getCardTime() {
		return cardTime;
	}

	public void setCardTime(String cardTime) {
		this.cardTime = cardTime;
	}

	public SystemPlatEnum getSysPlat() {
		return sysPlat;
	}

	public void setSysPlat(SystemPlatEnum sysPlat) {
		this.sysPlat = sysPlat;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public ExternalFlagEnum getExternalFlag() {
		return externalFlag;
	}

	public void setExternalFlag(ExternalFlagEnum externalFlag) {
		this.externalFlag = externalFlag;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	
	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public String getOperatorMerchantId() {
		return operatorMerchantId;
	}

	public void setOperatorMerchantId(String operatorMerchantId) {
		this.operatorMerchantId = operatorMerchantId;
	}

	public String getSendMerchantId() {
		return sendMerchantId;
	}

	public void setSendMerchantId(String sendMerchantId) {
		this.sendMerchantId = sendMerchantId;
	}

	public Integer getAndroidPackageId() {
		return androidPackageId;
	}

	public void setAndroidPackageId(Integer androidPackageId) {
		this.androidPackageId = androidPackageId;
	}

	@Override
	public String toString() {
		return "DeviceFileSv [sn=" + sn + ", id=" + id + ", deviceCode="
				+ deviceCode + ", deviceName=" + deviceName + ", deviceTypeId="
				+ deviceTypeId + ", deviceTypeName=" + deviceTypeName
				+ ", orderCode=" + orderCode + ", verifCode=" + verifCode
				+ ", batchNo=" + batchNo + ", packageId=" + packageId
				+ ", operatorMerchantId=" + operatorMerchantId
				+ ", sendMerchantId=" + sendMerchantId + ", inStorageTime="
				+ inStorageTime + ", outStorageTime=" + outStorageTime
				+ ", outStorageType=" + outStorageType + ", terminalDiscode="
				+ terminalDiscode + ", packageUserFlag=" + packageUserFlag
				+ ", packageUserFlagType=" + packageUserFlagType
				+ ", packageStatu=" + packageStatu + ", userFlag=" + userFlag
				+ ", userFlagType=" + userFlagType + ", iccid=" + iccid
				+ ", imsi=" + imsi + ", softVersion=" + softVersion
				+ ", vehicleFlag=" + vehicleFlag + ", vehicleFlagType="
				+ vehicleFlagType + ", packageUserTime=" + packageUserTime
				+ ", userTime=" + userTime + ", cardTime=" + cardTime
				+ ", sysPlat=" + sysPlat + ", manufacturerCode="
				+ manufacturerCode + ", androidPackageId=" + androidPackageId
				+ ", externalFlag=" + externalFlag + ", deletedFlag="
				+ deletedFlag + "]";
	}

	
	
	   
}
