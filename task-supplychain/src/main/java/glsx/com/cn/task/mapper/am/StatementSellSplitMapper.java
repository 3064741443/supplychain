package glsx.com.cn.task.mapper.am;

import glsx.com.cn.task.model.StatementSellSplit;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

@Mapper
public interface StatementSellSplitMapper extends OreMapper<StatementSellSplit>{
  
	public Page<StatementSellSplit> pageStatementSellSplit(StatementSellSplit record);
	
	public List<StatementSellSplit> exportStatementSellSplit(StatementSellSplit record);
}