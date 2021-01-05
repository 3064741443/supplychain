package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceUpdateRecord;

@Mapper
public interface DeviceUpdateRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceUpdateRecord record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceUpdateRecord> recordList);

    int insertSelective(DeviceUpdateRecord record);

    DeviceUpdateRecord selectByPrimaryKey(Integer id);
    
    DeviceUpdateRecord selectLastRecord(DeviceUpdateRecord record);

    int updateByPrimaryKeySelective(DeviceUpdateRecord record);

    int updateByPrimaryKey(DeviceUpdateRecord record);
    
    Page<DeviceUpdateRecord> pageDeviceUpdateRecord(DeviceUpdateRecord record);
    
    List<DeviceUpdateRecord> exportDeviceUpdateRecord(DeviceUpdateRecord record);
    
    List<DeviceUpdateRecord> listDeviceUpdateRecord(DeviceUpdateRecord record);
}