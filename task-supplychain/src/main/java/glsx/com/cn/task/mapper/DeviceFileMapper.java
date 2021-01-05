package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.DeviceFile;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.github.pagehelper.Page;

@Mapper
public interface DeviceFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceFile record);

    int insertSelective(DeviceFile record);

    DeviceFile selectByPrimaryKey(Integer id);
    
    DeviceFile selectByUniqueKey(DeviceFile record);

    int updateByPrimaryKeySelective(DeviceFile record);

    int updateByPrimaryKey(DeviceFile record);
    
    int countDeviceFilesByDeviceCode(Integer deviceCode);
    
    Page<DeviceFile> pageDeviceFile(DeviceFile record);
    
    List<DeviceFile> exportDeviceFile(DeviceFile record);
    
    List<String> findDeviceFileByDeviceSns(@Param("deviceSns") List<String> deviceSns);
    
    List<DeviceFile> selectDeviceFileInfo(DeviceFile deviceFile);
}