package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.ExsysDispatchLog;

@Mapper
public interface ExsysDispatchLogMapper extends OreMapper<ExsysDispatchLog>{
  

    int insert(ExsysDispatchLog record);

    int insertSelective(ExsysDispatchLog record);

    int updateByPrimaryKeySelective(ExsysDispatchLog record);

    int updateByPrimaryKey(ExsysDispatchLog record);
}