package glsx.com.cn.task.mapper.am;

import glsx.com.cn.task.model.StatementSell;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface StatementSellMapper extends OreMapper<StatementSell>{
    
	public Integer setNullWorkOrder(List<String> listsn);
	
	public Integer batchInsert(List<StatementSell> list);
}