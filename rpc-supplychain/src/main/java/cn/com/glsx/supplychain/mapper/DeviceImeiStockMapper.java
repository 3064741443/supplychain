package cn.com.glsx.supplychain.mapper;
import java.util.List;

import cn.com.glsx.supplychain.model.DeviceImeiStock;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceImeiStockMapper {

    Page<DeviceImeiStock> selectdeviceImeiStock(DeviceImeiStock record);

    int insertSelective(DeviceImeiStock record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceImeiStock> stockList);

    DeviceImeiStock selectByPrimaryKey(Integer id);
    
    DeviceImeiStock selectDeviceImeiStockByUniqueKey(DeviceImeiStock record);

    int deleteByPrimaryKey(Integer id);
}