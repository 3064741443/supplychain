package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.model.DeviceFile;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface DeviceFileMapper extends OreMapper<DeviceFile>{
    List<JXCMTDeviceFileDTO> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO);
}