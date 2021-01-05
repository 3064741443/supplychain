package cn.com.glsx.supplychain.mapper.am;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.am.StatementSn;

@Mapper
public interface StatementSnMapper extends OreMapper<StatementSn>{
    
	public int batchInsertReplace(List<StatementSn> list);
}