package glsx.com.cn.task.mapper;

import com.github.pagehelper.Page;

import glsx.com.cn.task.model.DeviceCode;
import glsx.com.cn.task.model.DeviceInfo;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface DeviceCodeMapper{
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