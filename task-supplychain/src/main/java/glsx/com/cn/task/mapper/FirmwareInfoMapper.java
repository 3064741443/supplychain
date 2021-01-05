package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.FirmwareInfo;

@Mapper
public interface FirmwareInfoMapper extends OreMapper<FirmwareInfo> {
	FirmwareInfo getVersion(FirmwareInfo firmwareInfo);
}
