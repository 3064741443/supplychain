package cn.com.glsx.supplychain.mapper.bs;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.bs.BsMerchantStockCainou;

@Mapper
public interface BsMerchantStockCainouMapper extends OreMapper<BsMerchantStockCainou>{
   // int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockCainou record);

    int insertSelective(BsMerchantStockCainou record);

   // BsMerchantStockCainou selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockCainou record);

    int updateByPrimaryKey(BsMerchantStockCainou record);
    
    Page<BsMerchantStockCainou> pageMerchantStockCainou(BsMerchantStockCainou record);
}