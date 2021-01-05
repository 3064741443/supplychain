package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.SyncLastidRecordMerChantStock;

@Mapper
public interface SyncLastidRecordMerChantStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SyncLastidRecordMerChantStock record);

    int insertSelective(SyncLastidRecordMerChantStock record);

    SyncLastidRecordMerChantStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SyncLastidRecordMerChantStock record);

    int updateByPrimaryKey(SyncLastidRecordMerChantStock record);
    
}