package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsMerchantOrderDetail;

@Mapper
public interface BsMerchantOrderDetailMapper  extends OreMapper<BsMerchantOrderDetail>{

    int insert(BsMerchantOrderDetail record);

    int insertSelective(BsMerchantOrderDetail record);

    int updateByPrimaryKeySelective(BsMerchantOrderDetail record);

    int updateByPrimaryKey(BsMerchantOrderDetail record);
}