package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.ExsysDispatchRule;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface ExsysDispatchRuleMapper extends OreMapper<ExsysDispatchRule>{
    
    int insert(ExsysDispatchRule record);

    int insertSelective(ExsysDispatchRule record);

    int updateByPrimaryKeySelective(ExsysDispatchRule record);

    int updateByPrimaryKey(ExsysDispatchRule record);
    
}