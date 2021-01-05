package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceInfoGpsPreimport;

@Mapper
public interface DeviceInfoGpsPreimportMapper extends OreMapper<DeviceInfoGpsPreimport>{

//    int deleteByPrimaryKey(Integer id);

    int insert(DeviceInfoGpsPreimport record);

    int insertSelective(DeviceInfoGpsPreimport record);

  //  DeviceInfoGpsPreimport selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(DeviceInfoGpsPreimport record);

    int updateByPrimaryKey(DeviceInfoGpsPreimport record);
    
    int batchAddDeviceInfoGpsPerimport(List<DeviceInfoGpsPreimport> gpsDeviceList);
    
    Page<DeviceInfoGpsPreimport> pageDeviceInfoGpsPreImport(DeviceInfoGpsPreimport record);
    
    int updateDeviceInfoGpsPreImportStatu(DeviceInfoGpsPreimport record);
}