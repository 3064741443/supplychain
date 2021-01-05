package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.SyncResultLog;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import java.util.List;

@Mapper
public interface SyncResultLogMapper extends OreMapper<SyncResultLog> {

	int insert(SyncResultLog syncResultLog);
	
	Page<SyncResultLog> listSyncResultLog(SyncResultLog syncResultLog);

	int insertList(List<SyncResultLog> syncResultLogList);
}
