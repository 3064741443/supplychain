package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.SyncLastidRecord;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface SyncLastidRecordMapper extends OreMapper<SyncLastidRecord>{
	
	SyncLastidRecord getSyncLastidRecord();
	
	Integer updateSyncLastidRecord(SyncLastidRecord syncLastidRecord);
}
