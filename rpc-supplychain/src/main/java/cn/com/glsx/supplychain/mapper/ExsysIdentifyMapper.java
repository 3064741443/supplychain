package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.ExsysIdentify;

@Mapper
public interface ExsysIdentifyMapper extends OreMapper<ExsysIdentify>{
   
    int insert(ExsysIdentify record);

    int insertSelective(ExsysIdentify record);

    int updateByPrimaryKeySelective(ExsysIdentify record);

    int updateByPrimaryKey(ExsysIdentify record);
}