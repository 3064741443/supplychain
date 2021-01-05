package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsMerchantStockSell;

@Mapper
public interface BsMerchantStockSellMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockSell record);

    int insertSelective(BsMerchantStockSell record);

    BsMerchantStockSell selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockSell record);

    int updateByPrimaryKey(BsMerchantStockSell record);
    
    int batchAddMerchantStockSell(List<BsMerchantStockSell> record);
}