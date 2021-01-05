package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceFile;

@Mapper
public interface DeviceFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceFile record);
    
    int insertOnDuplicateKeyUpdate(DeviceFile record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceFile> deviceFileList);

    int insertSelective(DeviceFile record);

    DeviceFile selectByPrimaryKey(Integer id);
    
    DeviceFile selectBySn(String sn);
    
    DeviceFile selectByUniqueKey(DeviceFile record);

    int updateByPrimaryKeySelective(DeviceFile record);

    int updateByPrimaryKey(DeviceFile record);
    
    int countDeviceFilesByDeviceCode(Integer deviceCode);
    
    int countDevicesByDeviceType(Integer typeId);
    
    int countDevicesByPackageStatus(String pkgStatus);
    
    int countDevicesByCondition(DeviceFile record);
    
    int countDevices();
    
    int getMaxDeviceFileId();
    
    Page<DeviceFile> pageDeviceFile(DeviceFile record);
    
    Page<DeviceFile> prePageDeviceFile(DeviceFile record);
    
    Page<DeviceFile> nexPageDeviceFile(DeviceFile record);
    
    List<Integer> nexListDeviceFileIds(DeviceFile record);

    List<Integer> agoListDeviceFileIds(DeviceFile record);
    
    List<DeviceFile> exportDeviceFile(DeviceFile record);

    List<String> findDeviceFileByDeviceSns(@Param("deviceSns") List<String> deviceSns);
    
    List<DeviceFile> listDeviceFilesBySns(@Param("deviceSns") List<String> deviceSns);
    
    
    
}