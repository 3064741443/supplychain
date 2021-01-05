package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.ExsysDispatchLog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;


@Mapper
public interface ExsysDispatchLogMapper extends OreMapper<ExsysDispatchLog>{
    
    int insert(ExsysDispatchLog record);

    int insertSelective(ExsysDispatchLog record);

    int updateByPrimaryKeySelective(ExsysDispatchLog record);

    int updateByPrimaryKey(ExsysDispatchLog record);
    
    List<ExsysDispatchLog> listFailedExsysDispatchLog();
}