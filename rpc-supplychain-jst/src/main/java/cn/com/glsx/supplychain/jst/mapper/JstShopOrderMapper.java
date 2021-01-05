package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.JstShopOrder;

@Mapper
public interface JstShopOrderMapper extends OreMapper<JstShopOrder>{
    
    int insert(JstShopOrder record);

    int insertSelective(JstShopOrder record);

    int updateByPrimaryKeySelective(JstShopOrder record);

    int updateByPrimaryKey(JstShopOrder record);

    Page<JstShopOrder> pageJstShopOrder(JstShopOrder jstShopOrder);

    List<JstShopOrder> pageWxJspShopOrder(JstShopOrder orderCodes);
}