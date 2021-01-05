package glsx.com.cn.task.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;
import glsx.com.cn.task.model.ProductHistoryPrice;

import java.util.List;


@Mapper
public interface ProductHistoryPriceMapper extends OreMapper<ProductHistoryPrice> {

    List<ProductHistoryPrice> getProductHistoryPriceByCodeOrType(ProductHistoryPrice productHistoryPrice);

    int updateNowTypeByProductCode(ProductHistoryPrice productHistoryPrice);

    int updateTypeByProductCode(ProductHistoryPrice productHistoryPrice);
}