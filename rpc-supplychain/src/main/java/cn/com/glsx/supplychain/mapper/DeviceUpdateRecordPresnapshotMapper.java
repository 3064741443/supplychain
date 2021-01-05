package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.DeviceUpdateRecordPresnapshot;

@Mapper
public interface DeviceUpdateRecordPresnapshotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceUpdateRecordPresnapshot record);

    int insertSelective(DeviceUpdateRecordPresnapshot record);

    DeviceUpdateRecordPresnapshot selectByPrimaryKey(Integer id);
    
    DeviceUpdateRecordPresnapshot selectByUniqueKey(DeviceUpdateRecordPresnapshot record);

    int updateByPrimaryKeySelective(DeviceUpdateRecordPresnapshot record);

    int updateByPrimaryKey(DeviceUpdateRecordPresnapshot record);
    
    
}