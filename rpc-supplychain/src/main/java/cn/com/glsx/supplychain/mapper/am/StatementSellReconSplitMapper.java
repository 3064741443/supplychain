package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.am.StatementSellReconSplit;

@Mapper
public interface StatementSellReconSplitMapper extends OreMapper<StatementSellReconSplit>{
   
	public List<StatementSellReconSplit> getDistinctReconTotalPrice(List<String> list);
	
	public Integer batchInsertReplace(List<StatementSellReconSplit> list);
}