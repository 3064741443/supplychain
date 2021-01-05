package cn.com.glsx.supplychain.mapper.am;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.am.StatementSellRecon;

@Mapper
public interface StatementSellReconMapper extends OreMapper<StatementSellRecon>{
    
	Page<StatementSellRecon> pageStatementSellRecon(StatementSellRecon record);
	
	int countStatementSellRecon(@Param("reconTime") String date,@Param("merchantCode") String merchantCode);
}