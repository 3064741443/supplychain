package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.TaskRecordExport;

@Mapper
public interface TaskRecordExportMapper extends OreMapper<TaskRecordExport>{
   
    int insert(TaskRecordExport record);

    int insertSelective(TaskRecordExport record);
    
    int updateByPrimaryKeySelective(TaskRecordExport record);

    int updateByPrimaryKey(TaskRecordExport record);
    
    Page<TaskRecordExport> pageTaskRecordExport(TaskRecordExport record);
}