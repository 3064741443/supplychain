package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.am.StatementSellReconDetail;

@Mapper
public interface StatementSellReconDetailMapper extends OreMapper<StatementSellReconDetail>{
   	
	public Integer batchInsertReplace(List<StatementSellReconDetail> list);
}