package cn.com.glsx.supplychain.mapper;


import cn.com.glsx.supplychain.model.SyncLastidRecord;
import cn.com.glsx.supplychain.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface SyncLastIdRecordMapper extends OreMapper<SyncLastidRecord> {

    int updateSyncLastidRecord(SyncLastidRecord record);
}