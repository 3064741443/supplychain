package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.SalesSummarizingDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface SalesSummarizingDetailMapper extends OreMapper<SalesSummarizingDetail> {


    List<SalesSummarizingDetail> getSalesSummarizingDetailBySalesSummarizingId(@Param("salesSummarizingId")Long salesSummarizingId);

    int updateById(SalesSummarizingDetail record);
}