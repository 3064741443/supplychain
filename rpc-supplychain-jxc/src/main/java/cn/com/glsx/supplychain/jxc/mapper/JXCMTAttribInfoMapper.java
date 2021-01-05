package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTAttribInfo;

@Mapper
public interface JXCMTAttribInfoMapper extends OreMapper<JXCMTAttribInfo>{
    Integer getAttribInfoMaxValue(@Param("type")Integer type);
}