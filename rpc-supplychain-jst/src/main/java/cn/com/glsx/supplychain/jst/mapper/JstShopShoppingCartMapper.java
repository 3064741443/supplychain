package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.JstShopShoppingCart;

@Mapper
public interface JstShopShoppingCartMapper extends OreMapper<JstShopShoppingCart>{
   
    int insert(JstShopShoppingCart record);

    int insertSelective(JstShopShoppingCart record);

    int updateByPrimaryKeySelective(JstShopShoppingCart record);

    int updateByPrimaryKey(JstShopShoppingCart record);
    
    Integer sumShopShoppingCart(JstShopShoppingCart record);
    
    List<JstShopShoppingCart> pageShopShoppingCart(JstShopShoppingCart record);
    
}