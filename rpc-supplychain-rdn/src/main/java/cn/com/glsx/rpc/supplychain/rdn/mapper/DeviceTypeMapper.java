package cn.com.glsx.rpc.supplychain.rdn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.DeviceType;

@Mapper
public interface DeviceTypeMapper extends OreMapper<DeviceType>{
    
}