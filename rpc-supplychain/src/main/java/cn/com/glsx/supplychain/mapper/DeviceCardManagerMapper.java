package cn.com.glsx.supplychain.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import cn.com.glsx.supplychain.model.DeviceCardManager;

@Mapper
public interface DeviceCardManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCardManager record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceCardManager> cardList);

    int insertSelective(DeviceCardManager record);

    DeviceCardManager selectByPrimaryKey(Integer id);

    DeviceCardManager selectByUniqueKey(DeviceCardManager record);
    
    DeviceCardManager selectByIccid(DeviceCardManager record);
    
    int updateByPrimaryKeySelective(DeviceCardManager record);

    int updateByPrimaryKeyWithBLOBs(DeviceCardManager record);

    int updateByPrimaryKey(DeviceCardManager record);

    int updateIccidByImsi(DeviceCardManager deviceCardManager);
    
    List<DeviceCardManager> getSampleColumByCollectImsi(Map<String,Object> mapParam);
    
    List<DeviceCardManager> getDeviceCardManagerByImsis(List<String> imsis);
}