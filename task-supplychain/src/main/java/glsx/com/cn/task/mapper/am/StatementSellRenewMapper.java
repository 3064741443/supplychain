package glsx.com.cn.task.mapper.am;

import java.util.List;

import glsx.com.cn.task.model.StatementSellRenew;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;


@Mapper
public interface StatementSellRenewMapper extends OreMapper<StatementSellRenew>{
    
	Integer setSuccessStatusByIds(List<Integer> ids);
	
	Integer setFailedStatusByIds(List<Integer> ids);
}