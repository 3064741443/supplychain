package glsx.com.cn.task.mapper.am;

import glsx.com.cn.task.model.AmSyncLastidRecord;
import glsx.com.cn.task.model.SyncLastidRecord;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface AmSyncLastidRecordMapper extends OreMapper<AmSyncLastidRecord>{

	AmSyncLastidRecord getSyncLastidRecord();
	
	Integer updateSyncLastidRecord(AmSyncLastidRecord AmSyncLastidRecord);
}
