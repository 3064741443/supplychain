package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceFileVirtual;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeviceFileVirtualMapper {

	
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceFileVirtual record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceFileVirtual> records);

    int insertSelective(DeviceFileVirtual record);

    DeviceFileVirtual selectByPrimaryKey(Integer id);

    Page<DeviceFileVirtual> selectFileVirtual(DeviceFileVirtual record);

    DeviceFileVirtual selectByImsi(String imsi);
    
    DeviceFileVirtual getDeviceFileVirtualByIccid(String iccid);

    int updateByPrimaryKeySelective(DeviceFileVirtual record);

    int updateByPrimaryKey(DeviceFileVirtual record);
    
    int countDeviceFilesByDeviceCode(Integer deviceCode);
    
    int countDevicesByDeviceType(Integer typeId);
    
    int countDevices();
    
    List<DeviceFileVirtual> getDeviceFileVirtualByIccids(List<String> list);
    
    List<DeviceFileVirtual> getDeviceFileVirtualByImsis(List<String> list);
    
}