package glsx.com.cn.task.mapper.am;

import glsx.com.cn.task.model.StatementSellCombile;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

@Mapper
public interface StatementSellCombileMapper extends OreMapper<StatementSellCombile> {
    
	public Page<StatementSellCombile> pageStatementSellCombile(StatementSellCombile record);
	 
	public List<StatementSellCombile> listStatementSellCombile(StatementSellCombile record);
}