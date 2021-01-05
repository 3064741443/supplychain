package cn.com.glsx.supplychain.mapper.bs;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.bs.BsMerchantStockRetn;

@Mapper
public interface BsMerchantStockRetnMapper extends OreMapper<BsMerchantStockRetn>{
  //  int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockRetn record);

    int insertSelective(BsMerchantStockRetn record);

  //  BsMerchantStockRetn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockRetn record);

    int updateByPrimaryKey(BsMerchantStockRetn record);
    
    Page<BsMerchantStockRetn> pageMerchantStockRetn(BsMerchantStockRetn record);
}