package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.SalesSummarizingHistory;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface SalesSummarizingHistoryMapper extends OreMapper<SalesSummarizingHistory> {

    int updateById(SalesSummarizingHistory record);
}