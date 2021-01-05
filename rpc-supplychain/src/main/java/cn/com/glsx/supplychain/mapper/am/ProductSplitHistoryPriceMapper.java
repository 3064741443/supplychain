package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductSplitHistoryPriceMapper extends OreMapper<ProductSplitHistoryPrice> {

    ProductSplitHistoryPrice getProductSplitHistoryPriceById(Long id);

    int add(ProductSplitHistoryPrice record);

    int addProductSplitHistoryPriceList(@Param("productSplitHistoryPriceList") List<ProductSplitHistoryPrice> productSplitHistoryPriceList);

    int updateById(ProductSplitHistoryPrice record);

    int updateByProductCode(ProductSplitHistoryPrice record);

    int updateProductSplitHistoryPriceByTimeProductCode(ProductSplitHistoryPrice record);

    int updateProductSplitHistoryPriceByTime(ProductSplitHistoryPrice record);

    int updateByProductCodeMaterialCodeProductType(ProductSplitHistoryPrice record);

    int updateTaxRateByProductType(ProductSplitHistoryPrice record);

/*    int batchInsertOnDuplicateKeyUpdateProductSplitHistor(List<ProductSplitHistoryPrice> productSplitHistoryPriceList);*/

    List<ProductSplitHistoryPrice> getProductSplitHistoryPriceByTypeNotTwo(ProductSplitHistoryPrice productSplitHistoryPrice);

    List<ProductSplitHistoryPrice> getProductSplitHistoryPrice(ProductSplitHistoryPrice productSplitHistoryPrice);

    int updateDeletedFlagByProductCodeTimeMaterialCodeProductType(ProductSplitHistoryPrice productSplitHistoryPrice);
}