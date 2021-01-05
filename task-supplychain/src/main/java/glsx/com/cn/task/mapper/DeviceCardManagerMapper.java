package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.DeviceCardManager;

@Mapper
public interface DeviceCardManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCardManager record);

    int insertSelective(DeviceCardManager record);

    DeviceCardManager selectByPrimaryKey(Integer id);

    DeviceCardManager selectByUniqueKey(DeviceCardManager record);
    
    int updateByPrimaryKeySelective(DeviceCardManager record);

    int updateByPrimaryKeyWithBLOBs(DeviceCardManager record);

    int updateByPrimaryKey(DeviceCardManager record);
}