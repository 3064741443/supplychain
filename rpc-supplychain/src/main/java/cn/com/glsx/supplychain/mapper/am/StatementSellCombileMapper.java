package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementSellCombile;

@Mapper
public interface StatementSellCombileMapper extends OreMapper<StatementSellCombile> {
    
	public Page<StatementSellCombile> pageStatementSellCombile(StatementSellCombile record);
	 
	public List<StatementSellCombile> listStatementSellCombile(StatementSellCombile record);
}