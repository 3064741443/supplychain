package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.DeviceUserManager;

@Mapper
public interface DeviceUserManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceUserManager record);

    int insertSelective(DeviceUserManager record);

    DeviceUserManager selectByPrimaryKey(Integer id);
    
    DeviceUserManager selectByUniqueKey(DeviceUserManager record);

    int updateByPrimaryKeySelective(DeviceUserManager record);

    int updateByPrimaryKeyWithBLOBs(DeviceUserManager record);

    int updateByPrimaryKey(DeviceUserManager record);
}