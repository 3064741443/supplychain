package cn.com.glsx.supplychain.mapper;


import cn.com.glsx.supplychain.model.DeviceFileUnstock;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface DeviceFileUnstockMapper extends OreMapper<DeviceFileUnstock> {

    int insertSelective(DeviceFileUnstock record);

    List<DeviceFileUnstock> selectDeviceFileUnstock(DeviceFileUnstock deviceFileUnstock);

    int updateByPrimaryKeySelective(DeviceFileUnstock record);

    int updateByImsi(DeviceFileUnstock deviceFileUnstock);

    Page<DeviceFileUnstock> listDeviceFileUnstock(DeviceFileUnstock deviceFileUnstock);

    List<DeviceFileUnstock> selectList(DeviceFileUnstock deviceFileUnstock);
}