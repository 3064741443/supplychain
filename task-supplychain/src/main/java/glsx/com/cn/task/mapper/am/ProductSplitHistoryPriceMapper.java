package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductSplitHistoryPriceMapper extends OreMapper<ProductSplitHistoryPrice> {

    int add(ProductSplitHistoryPrice record);

    int updateById(ProductSplitHistoryPrice record);

    List<ProductSplitHistoryPrice> getNewDateProductSplitHistoryPrice(ProductSplitHistoryPrice record);

    List<ProductSplitHistoryPrice> selecrtProductSplitHistoryPriceByServiceTypeOneThreeFour(ProductSplitHistoryPrice productSplitHistoryPrice);

    List<ProductSplitHistoryPrice> selecrtProductSplitHistoryPriceByServiceTypeOneThreeFourProductTypeZero(ProductSplitHistoryPrice productSplitHistoryPrice);

    List<ProductSplitHistoryPrice> selecrtProductSplitHistoryPriceByServiceTypeTwo(ProductSplitHistoryPrice productSplitHistoryPrice);

    List<ProductSplitHistoryPrice> selecrtProductSplitHistoryPriceByServiceTypeTwoProductTypeZero(ProductSplitHistoryPrice productSplitHistoryPrice);
    
    
    
    List<ProductSplitHistoryPrice> listProductSplitHistoryPrice(ProductSplitHistoryPrice record);
    
    int updateTypeProductSplitPrice(@Param("type")Byte ptype,@Param("list")List<Long> list);
}