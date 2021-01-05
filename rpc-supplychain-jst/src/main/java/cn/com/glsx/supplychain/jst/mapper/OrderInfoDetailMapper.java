package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.OrderInfoDetail;

@Mapper
public interface OrderInfoDetailMapper extends OreMapper<OrderInfoDetail>{

    int insert(OrderInfoDetail record);

    int insertSelective(OrderInfoDetail record);

    int updateByPrimaryKeySelective(OrderInfoDetail record);

    int updateByPrimaryKey(OrderInfoDetail record);
    
    List<OrderInfoDetail> ListCountOrderInfoDetailGroupByLogistics(List<String> listOrderCode);
    
    List<OrderInfoDetail> getShopOrderDetailNoOrder(List<String> list);
}