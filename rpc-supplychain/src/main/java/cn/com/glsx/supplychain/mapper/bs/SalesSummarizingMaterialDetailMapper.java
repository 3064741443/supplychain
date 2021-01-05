package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.SalesSummarizingMaterialDetail;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SalesSummarizingMaterialDetailMapper extends OreMapper<SalesSummarizingMaterialDetail> {

    int add(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail);

    List<SalesSummarizingMaterialDetail>getSalesSummarizingMaterialDetailList(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail);

    int insertList(List<SalesSummarizingMaterialDetail>salesSummarizingMaterialDetails);
}