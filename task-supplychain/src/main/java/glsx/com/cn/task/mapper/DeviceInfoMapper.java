package glsx.com.cn.task.mapper;

import java.util.List;

import glsx.com.cn.task.model.DeviceInfo;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;


@Mapper
public interface DeviceInfoMapper extends OreMapper<DeviceInfo>{
	
	List<DeviceInfo> getDeviceInfoUsedInSyncCardFlow(DeviceInfo deviceInfo);
	
	List<DeviceInfo> getDeviceInfoUserInSyncPhicalDevice(DeviceInfo deviceInfo);
	
	DeviceInfo getDeviceInfo(DeviceInfo deviceInfo);
}
