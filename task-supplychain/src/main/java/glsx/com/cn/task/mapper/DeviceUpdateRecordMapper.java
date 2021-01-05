package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.DeviceUpdateRecord;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

@Mapper
public interface DeviceUpdateRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceUpdateRecord record);

    int insertSelective(DeviceUpdateRecord record);

    DeviceUpdateRecord selectByPrimaryKey(Integer id);
    
    DeviceUpdateRecord selectLastRecord(DeviceUpdateRecord record);

    int updateByPrimaryKeySelective(DeviceUpdateRecord record);

    int updateByPrimaryKey(DeviceUpdateRecord record);
    
    Page<DeviceUpdateRecord> pageDeviceUpdateRecord(DeviceUpdateRecord record);
    
    List<DeviceUpdateRecord> exportDeviceUpdateRecord(DeviceUpdateRecord record);
}