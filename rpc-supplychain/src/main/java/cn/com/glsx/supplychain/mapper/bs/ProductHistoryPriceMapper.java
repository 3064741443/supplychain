package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProductHistoryPriceMapper extends OreMapper<ProductHistoryPrice> {

    ProductHistoryPrice getProductHistoryPriceByCodeOrType(ProductHistoryPrice productHistoryPrice);

    List<ProductHistoryPrice> getProductHistoryPriceByCode(@Param(value="productCode") String productCode);

    List<ProductHistoryPrice> getProductHistoryPrice(@Param(value="productCode") String productCode, @Param(value="date") Date date);

    Integer updateById(ProductHistoryPrice productHistoryPrice);
}