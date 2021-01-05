package glsx.com.cn.task.mapper;



import glsx.com.cn.task.model.DeviceStatisReport;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface DeviceStatisReportMapper extends OreMapper<DeviceStatisReport> {

    DeviceStatisReport getMaxDeviceStatisReport();

}