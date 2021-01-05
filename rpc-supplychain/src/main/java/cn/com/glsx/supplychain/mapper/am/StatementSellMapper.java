package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.am.StatementSell;
import cn.com.glsx.supplychain.model.am.StatementSellParam;

@Mapper
public interface StatementSellMapper extends OreMapper<StatementSell>{
    
	public Integer setNullWorkOrder(List<String> listsn);
	
	public Integer setParamWorkOrder(StatementSellParam sellParam);
}