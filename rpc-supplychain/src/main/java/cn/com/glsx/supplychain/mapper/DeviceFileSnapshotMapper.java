package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeviceFileSnapshotMapper {
    int deleteByPrimaryKey(String sn);

    int insert(DeviceFileSnapshot record);
    
    int insertOnDuplicateKeyUpdate(DeviceFileSnapshot record);

    int insertSelective(DeviceFileSnapshot record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceFileSnapshot> snapshotList);

    DeviceFileSnapshot selectByPrimaryKey(String sn);
    
    DeviceFileSnapshot selectByCardId(Integer cardId);
    
    DeviceFileSnapshot selectByVehicleId(Integer vehicleId);

    int updateByPrimaryKeySelective(DeviceFileSnapshot record);

    int updateByPrimaryKey(DeviceFileSnapshot record);
    
    int countDeviceFileSnapshot(DeviceFileSnapshot record);
    
    List<DeviceFileSnapshot> listDeviceFileSnapshot(DeviceFileSnapshot record);
    
    List<DeviceFileSnapshot> getDeviceFileSnapshotBySns(List<String> sns);
    
    List<DeviceFileSnapshot> getDeviceFileSnapshotByIccids(List<String> iccids);
    
    List<DeviceFileSnapshot> getDeviceFileSnapshotByCardIds(List<Integer> cardIds);
}