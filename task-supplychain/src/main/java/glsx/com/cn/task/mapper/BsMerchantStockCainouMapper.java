package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsMerchantStockCainou;

@Mapper
public interface BsMerchantStockCainouMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStockCainou record);

    int insertSelective(BsMerchantStockCainou record);

    BsMerchantStockCainou selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStockCainou record);

    int updateByPrimaryKey(BsMerchantStockCainou record);
}