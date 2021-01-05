package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.OrderInfo;

@Mapper
public interface OrderInfoMapper extends OreMapper<OrderInfo>{

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKeyWithBLOBs(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}