package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.DeviceFileSnapshot;

@Mapper
public interface DeviceFileSnapshotMapper {
    int deleteByPrimaryKey(String sn);

    int insert(DeviceFileSnapshot record);

    int insertSelective(DeviceFileSnapshot record);

    DeviceFileSnapshot selectByPrimaryKey(String sn);
    
    DeviceFileSnapshot selectByCardId(Integer cardId);

    int updateByPrimaryKeySelective(DeviceFileSnapshot record);

    int updateByPrimaryKey(DeviceFileSnapshot record);
    
    DeviceFileSnapshot countByPackageStatus(DeviceFileSnapshot record);
}