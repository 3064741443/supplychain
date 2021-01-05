package cn.com.glsx.supplychain.mapper.bs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.bs.BsMerchantStock;

@Mapper
public interface BsMerchantStockMapper extends OreMapper<BsMerchantStock>{
  //  int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStock record);

    int insertSelective(BsMerchantStock record);

  //  BsMerchantStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStock record);

    int updateByPrimaryKey(BsMerchantStock record);
    
    Page<BsMerchantStock> pageMerchantStocks(BsMerchantStock record);
    
    List<BsMerchantStock> listMerchantStocks(BsMerchantStock record);
    
    BsMerchantStock getStatMerchantStocks(BsMerchantStock record);
}