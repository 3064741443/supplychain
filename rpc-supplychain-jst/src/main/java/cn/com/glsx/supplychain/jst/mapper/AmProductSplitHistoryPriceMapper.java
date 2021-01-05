package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.AmProductSplitHistoryPrice;

@Mapper
public interface AmProductSplitHistoryPriceMapper extends OreMapper<AmProductSplitHistoryPrice>{
   
    int insert(AmProductSplitHistoryPrice record);

    int insertSelective(AmProductSplitHistoryPrice record);

    int updateByPrimaryKeySelective(AmProductSplitHistoryPrice record);

    int updateByPrimaryKey(AmProductSplitHistoryPrice record);
    
    List<AmProductSplitHistoryPrice> batchSelectCurrentProductPrice(List<String> listProductCode);
    
    AmProductSplitHistoryPrice selectCurrentProductPrice(@Param("productCode")String productCode);
    
    List<AmProductSplitHistoryPrice> listProductSplitPriceByProductCode(List<String> listProductCode);

    List<AmProductSplitHistoryPrice> getCurrentProductPrice(@Param("productCode")String productCode);
}