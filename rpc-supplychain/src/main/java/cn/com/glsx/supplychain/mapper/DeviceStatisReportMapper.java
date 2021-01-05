package cn.com.glsx.supplychain.mapper;


import cn.com.glsx.supplychain.model.DeviceStatisReport;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface DeviceStatisReportMapper extends OreMapper<DeviceStatisReport> {

    DeviceStatisReport getMaxDeviceStatisReport();

}