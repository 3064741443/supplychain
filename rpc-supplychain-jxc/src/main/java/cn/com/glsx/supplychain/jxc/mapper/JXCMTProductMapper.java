package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTProduct;

@Mapper
public interface JXCMTProductMapper extends OreMapper<JXCMTProduct>{
    
}