package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.OrderInfo;

/**
 * @Title: DemoMapper.java
 * @Description:
 * @author leiyj
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */

@Mapper
public interface OrderInfoMapper extends OreMapper<OrderInfo> {
	
	OrderInfo getOrderInfoById(Integer id);
	
	Page<OrderInfo> listOrderInfo(OrderInfo record);
	
	Page<OrderInfo> pageOrderInfoFroDelivery(OrderInfo record);
	
	int count(OrderInfo record);

    int insertOrderInfo(OrderInfo record);

    int update(OrderInfo record);
    
    List<OrderInfo> getOrderList(OrderInfo orderInfo);
    
    OrderInfo getOrderInfoByOrderCode(String orderCode);

    Integer getShipmentsQuantityByOrderCode(String orderCode);
    
    List<OrderInfo> getCountOrderDetails(@Param("orderCodes") List<String> orderCodes);
    
    List<OrderInfo> getOrderInfoForSignOrder(List<String> list);
   
}
