package cn.com.glsx.supplychain.mapper.am;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementSellJbwy;

@Mapper
public interface StatementSellJbwyMapper extends OreMapper<StatementSellJbwy>{
   
	Page<StatementSellJbwy> pageStatementSellJbwy(StatementSellJbwy record);
}