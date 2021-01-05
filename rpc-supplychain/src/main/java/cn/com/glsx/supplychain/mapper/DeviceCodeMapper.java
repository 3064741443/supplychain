package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.DeviceCode;

@Mapper
public interface DeviceCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCode record);

    int insertSelective(DeviceCode record);

    DeviceCode selectByPrimaryKey(Integer id);
    
    DeviceCode selectByUniqueKey(DeviceCode record);
    
    DeviceCode selectByManufacturerCode(String manufacturerCode);

    int updateByPrimaryKeySelective(DeviceCode record);

    int updateByPrimaryKeyWithBLOBs(DeviceCode record);

    int updateByPrimaryKey(DeviceCode record);
    
    Page<DeviceCode> pageDeviceCode(DeviceCode record);
    
    List<DeviceCode> listDeviceCode(DeviceCode record);
    
    List<DeviceCode> exportDeviceCode(DeviceCode record);
    
    List<DeviceCode> listDeviceCodeByIds(@Param("ids") List<Integer> ids);
}