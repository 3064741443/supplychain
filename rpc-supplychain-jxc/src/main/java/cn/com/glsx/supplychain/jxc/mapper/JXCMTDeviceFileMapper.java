package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceFile;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTDeviceFileMapper extends OreMapper<JXCMTDeviceFile>{
    List<JXCMTDeviceFileDTO> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO);
}