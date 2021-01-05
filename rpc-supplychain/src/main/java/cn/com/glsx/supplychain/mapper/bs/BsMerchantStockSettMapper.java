package cn.com.glsx.supplychain.mapper.bs;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.bs.BsMerchantStockSett;

@Mapper
public interface BsMerchantStockSettMapper extends OreMapper<BsMerchantStockSett>{
 //   int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockSett record);

    int insertSelective(BsMerchantStockSett record);

 //   BsMerchantStockSett selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockSett record);

    int updateByPrimaryKey(BsMerchantStockSett record);
    
    Page<BsMerchantStockSett> pageMerchantStockSett(BsMerchantStockSett record);
}