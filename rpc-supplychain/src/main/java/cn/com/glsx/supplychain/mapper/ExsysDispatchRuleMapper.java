package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.ExsysDispatchRule;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface ExsysDispatchRuleMapper extends OreMapper<ExsysDispatchRule> {

    int insert(ExsysDispatchRule record);

    int insertSelective(ExsysDispatchRule record);

    ExsysDispatchRule selectById(Integer id);

    int updateByPrimaryKeySelective(ExsysDispatchRule record);

    int updateByPrimaryKey(ExsysDispatchRule record);
}