package cn.com.glsx.supplychain.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.DeviceInfo;

import com.github.pagehelper.Page;


@Mapper
public interface DeviceInfoMapper extends OreMapper<DeviceInfo> {
	
	DeviceInfo getDeviceInfoById(Integer id);
	
	Page<DeviceInfo> listDeviceInfo(DeviceInfo record);
		
	int count(DeviceInfo record);
	
	int insert(DeviceInfo record);
	
	int update(DeviceInfo record);
	
	int updateByPrimaryKey(DeviceInfo record);
	
	int updateSelectBySn(DeviceInfo record);
	
	List<DeviceInfo> getDeviceInfo(@Param("iccid")String iccid,@Param("imei")String imei);
	
	Page<DeviceInfo> listDeviceInfoByOrderCode(String orderCode);
	
	DeviceInfo getDeviceInfoByImei(@Param("imei")String imei);
	
	DeviceInfo getDeviceInfoBySn(@Param("sn")String sn);
	
	DeviceInfo getDeviceInfoByIccid(@Param("iccid")String iccid);
	
	int addDeviceInfoOnDuplicateKey(DeviceInfo record);
	
	int batchAddDeviceInfoOnDuplicateKey(List<DeviceInfo> deviceInfoList);
	
	int countOutDevices(DeviceInfo record);
	
	int getTotalAttrib(DeviceInfo record);
	
	int getTotalAttribDeviceInfos(DeviceInfo record);
	
	Page<DeviceInfo> pageStatAttrib(DeviceInfo record);
	
	Page<DeviceInfo> pageStatAttribDeviceInfos(DeviceInfo record);
	
	List<DeviceInfo> listExportAttribDeviceInfos(DeviceInfo record);
	
	List<DeviceInfo> listDeviceInfoBySn(@Param("sns") List<String> sns);
	
	List<DeviceInfo> listDeviceInfoByIccids(@Param("iccids") List<String> iccids);

	//查询初始化后的SN
	DeviceInfo getDeviceInfoBySnAndFlagIsY(@Param("sn")String sn,@Param("imei")String imei);
	
}