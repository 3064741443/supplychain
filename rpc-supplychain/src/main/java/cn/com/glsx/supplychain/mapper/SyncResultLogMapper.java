package cn.com.glsx.supplychain.mapper;

import cn.com.glsx.supplychain.model.SyncResultLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface SyncResultLogMapper extends OreMapper<SyncResultLog> {

	int insert(SyncResultLog syncResultLog);
	
	Page<SyncResultLog> listSyncResultLog(SyncResultLog syncResultLog);

	int insertList(List<SyncResultLog> syncResultLogList);
}
