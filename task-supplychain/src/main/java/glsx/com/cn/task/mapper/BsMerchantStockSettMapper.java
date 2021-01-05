package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsMerchantStockSett;

@Mapper
public interface BsMerchantStockSettMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockSett record);

    int insertSelective(BsMerchantStockSett record);

    BsMerchantStockSett selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockSett record);

    int updateByPrimaryKey(BsMerchantStockSett record);
    
    int batchAddAndUpdateMerchantStockSett(List<BsMerchantStockSett> list);
}