package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsProductHistoryPrice;

@Mapper
public interface BsProductHistoryPriceMapper  extends OreMapper<BsProductHistoryPrice>{

    int insert(BsProductHistoryPrice record);

    int insertSelective(BsProductHistoryPrice record);

    int updateByPrimaryKeySelective(BsProductHistoryPrice record);

    int updateByPrimaryKey(BsProductHistoryPrice record);
}