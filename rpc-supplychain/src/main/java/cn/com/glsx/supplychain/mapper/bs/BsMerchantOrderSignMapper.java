package cn.com.glsx.supplychain.mapper.bs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.bs.BsMerchantOrderSign;

@Mapper
public interface BsMerchantOrderSignMapper extends OreMapper<BsMerchantOrderSign>{
    //int deleteByPrimaryKey(Long id);

    int insert(BsMerchantOrderSign record);

    int insertSelective(BsMerchantOrderSign record);

   // BsMerchantOrderSign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BsMerchantOrderSign record);

    int updateByPrimaryKey(BsMerchantOrderSign record);
    
    int updateMerchantOrderSignBySignCode(BsMerchantOrderSign record);
    
    List<BsMerchantOrderSign> listMerchantOrderSignByMerchantOrder(List<String> listMerchantOrders);
      
}