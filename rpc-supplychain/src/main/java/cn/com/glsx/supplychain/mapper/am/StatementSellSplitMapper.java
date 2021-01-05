package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementSellSplit;

@Mapper
public interface StatementSellSplitMapper extends OreMapper<StatementSellSplit>{
  
	public Page<StatementSellSplit> pageStatementSellSplit(StatementSellSplit record);
	
	public List<StatementSellSplit> exportStatementSellSplit(StatementSellSplit record);
}