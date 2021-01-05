package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsMerchantShoppingCart;

@Mapper
public interface BsMerchantShoppingCartMapper extends OreMapper<BsMerchantShoppingCart>{
   
    int insert(BsMerchantShoppingCart record);

    int insertSelective(BsMerchantShoppingCart record);

    int updateByPrimaryKeySelective(BsMerchantShoppingCart record);

    int updateByPrimaryKey(BsMerchantShoppingCart record);
    
    Integer sumMerchantShoppingCart(BsMerchantShoppingCart record);
    
    List<BsMerchantShoppingCart> pageMerchantShoppingCart(BsMerchantShoppingCart record);
}