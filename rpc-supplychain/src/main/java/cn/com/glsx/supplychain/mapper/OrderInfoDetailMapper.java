package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.OrderInfoDetail;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderInfoDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfoDetail record);

    int insertSelective(OrderInfoDetail record);

    OrderInfoDetail selectByPrimaryKey(Integer id);

    OrderInfoDetail getOrderInfoDetailByImei(@Param("imei") String imei);

    OrderInfoDetail getOrderInfoDetailByOrderCode(@Param("orderCode") String orderCode);

    List<OrderInfoDetail>getOrderInfoDetailListByOrderCode(@Param("orderCode") String orderCode);
    
    List<OrderInfoDetail> getLogisticIdsByOrderCode(@Param("orderCode") String orderCode);
    
    OrderInfoDetail getOrderInfoDetailBySn(@Param("sn") String sn);

    OrderInfoDetail getMaxUpdateOrderInfoDetailByOrderCode(@Param("orderCode") String orderCode);

    int updateByPrimaryKeySelective(OrderInfoDetail record);

    int updateByPrimaryKey(OrderInfoDetail record);
    
    List<OrderInfoDetail> listOrderInfoDetail(List<OrderInfoDetail> record);
    
    Page<OrderInfoDetail> pageOrderInfoDetail(OrderInfoDetail record);
    
    Page<OrderInfoDetail> pageOrderDetailForDelivery(OrderInfoDetail record);
    
    int getDeviceCountByOrderCode(@Param("orderCode") String orderCode);
    
    int batchAddOrderInfoDetail(@Param("orderDetailList") List<OrderInfoDetail> orderDetailList);
    
    int getCountOrderInfoDetailByLogistics(OrderInfoDetail record);
}