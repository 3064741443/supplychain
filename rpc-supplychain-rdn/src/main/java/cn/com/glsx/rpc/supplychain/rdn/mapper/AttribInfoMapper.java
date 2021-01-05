package cn.com.glsx.rpc.supplychain.rdn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.AttribInfo;

@Mapper
public interface AttribInfoMapper extends OreMapper<AttribInfo>{
    
}