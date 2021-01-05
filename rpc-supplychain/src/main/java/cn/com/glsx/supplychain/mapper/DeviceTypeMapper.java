package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceType;

@Mapper
public interface DeviceTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceType record);

    int insertSelective(DeviceType record);

    DeviceType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceType record);

    int updateByPrimaryKey(DeviceType record);
    
    List<DeviceType> listDeviceType(DeviceType record);
    
    Page<DeviceType> pageDeviceType(DeviceType record);
}