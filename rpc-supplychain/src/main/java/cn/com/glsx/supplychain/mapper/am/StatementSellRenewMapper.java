package cn.com.glsx.supplychain.mapper.am;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementSellRenew;

@Mapper
public interface StatementSellRenewMapper extends OreMapper<StatementSellRenew>{
    
	Page<StatementSellRenew> pageStatementSellRenew(StatementSellRenew record);
}