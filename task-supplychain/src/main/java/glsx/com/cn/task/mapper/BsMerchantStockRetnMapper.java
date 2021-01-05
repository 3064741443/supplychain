package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsMerchantStockRetn;

@Mapper
public interface BsMerchantStockRetnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockRetn record);

    int insertSelective(BsMerchantStockRetn record);

    BsMerchantStockRetn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockRetn record);

    int updateByPrimaryKey(BsMerchantStockRetn record);
    
    int batchAddMerchantStockRetn(List<BsMerchantStockRetn> list);
}