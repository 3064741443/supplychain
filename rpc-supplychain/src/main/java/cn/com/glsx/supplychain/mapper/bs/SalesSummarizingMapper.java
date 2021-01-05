package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.SalesSummarizing;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingDetail;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SalesSummarizingMapper extends OreMapper<SalesSummarizing> {

    List<SalesSummarizing>exportSalesSummarizingExit(SalesSummarizing salesSummarizing);

    Page<SalesSummarizing> listSalesSummarizing(SalesSummarizing salesSummarizing);

    int updateById(SalesSummarizing record);

    int add(SalesSummarizing salesSummarizing);

    int addSalesSummarizingList(List<SalesSummarizing> salesSummarizingList);

    SalesSummarizing selectMaxTimeSalesSummarizing(SalesSummarizing salesSummarizing);

    List<Date> getDate();

    int batchUpdateByid(@Param("ids") List<Long> ids, @Param("status")Byte status);
}