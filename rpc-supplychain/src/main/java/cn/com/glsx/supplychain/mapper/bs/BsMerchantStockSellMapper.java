package cn.com.glsx.supplychain.mapper.bs;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.bs.BsMerchantStockSell;

@Mapper
public interface BsMerchantStockSellMapper extends OreMapper<BsMerchantStockSell>{
  //  int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockSell record);

    int insertSelective(BsMerchantStockSell record);

 //   BsMerchantStockSell selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockSell record);

    int updateByPrimaryKey(BsMerchantStockSell record);
    
    Page<BsMerchantStockSell> pageMerchantStockSell(BsMerchantStockSell record);
}