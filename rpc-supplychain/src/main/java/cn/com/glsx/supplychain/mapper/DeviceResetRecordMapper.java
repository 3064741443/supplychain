package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceResetRecord;

@Mapper
public interface DeviceResetRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceResetRecord record);

    int insertSelective(DeviceResetRecord record);

    DeviceResetRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceResetRecord record);

    int updateByPrimaryKeyWithBLOBs(DeviceResetRecord record);

    int updateByPrimaryKey(DeviceResetRecord record);
    
    Page<DeviceResetRecord> pageDeviceResetRecord(DeviceResetRecord record);
    
    List<DeviceResetRecord> exportDeviceResetRecord(DeviceResetRecord record);
}