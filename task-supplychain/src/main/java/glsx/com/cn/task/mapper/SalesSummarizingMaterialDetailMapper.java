package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.SalesSummarizingMaterialDetail;

import java.util.List;

@Mapper
public interface SalesSummarizingMaterialDetailMapper extends OreMapper<SalesSummarizingMaterialDetail> {

    int add(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail);

    List<SalesSummarizingMaterialDetail>getSalesSummarizingMaterialDetailList(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail);

    int insertList(List<SalesSummarizingMaterialDetail> salesSummarizingMaterialDetails);
}