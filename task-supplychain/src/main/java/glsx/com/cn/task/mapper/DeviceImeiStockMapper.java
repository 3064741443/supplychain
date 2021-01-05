package glsx.com.cn.task.mapper;
import glsx.com.cn.task.model.DeviceImeiStock;

import java.util.List;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceImeiStockMapper {

    Page<DeviceImeiStock> selectdeviceImeiStock(DeviceImeiStock record);

    int insertSelective(DeviceImeiStock record);
    
    int batchInsertOnDuplicateKeyUpdate(List<DeviceImeiStock> stockList);

    Page<DeviceImeiStock> selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    List<DeviceImeiStock> selectDeviceImeiStockList(DeviceImeiStock deviceImeiStock);
}