package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementFinanceCombile;

@Mapper
public interface StatementFinanceCombileMapper extends OreMapper<StatementFinanceCombile>{
    
	int deleteStatementFinanceCombileByDate(StatementFinanceCombile record);
	
	Page<StatementFinanceCombile> listStatementFinanceCombile(StatementFinanceCombile record);
	
	List<StatementFinanceCombile>exportStatementFinanceCombileExit(StatementFinanceCombile record);
}