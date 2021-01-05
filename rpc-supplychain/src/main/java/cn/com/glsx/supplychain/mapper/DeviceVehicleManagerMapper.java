package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.DeviceVehicleManager;

@Mapper
public interface DeviceVehicleManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceVehicleManager record);

    int insertSelective(DeviceVehicleManager record);

    DeviceVehicleManager selectByPrimaryKey(Integer id);
    
    DeviceVehicleManager selectByUniqueKey(DeviceVehicleManager record);

    int updateByPrimaryKeySelective(DeviceVehicleManager record);

    int updateByPrimaryKeyWithBLOBs(DeviceVehicleManager record);

    int updateByPrimaryKey(DeviceVehicleManager record);
}