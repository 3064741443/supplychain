package glsx.com.cn.task.mapper;

import com.github.pagehelper.Page;

import glsx.com.cn.task.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.OrderInfoDetail;

import java.util.List;

@Mapper
public interface OrderInfoDetailMapper extends OreMapper<OrderInfoDetail> {


    int insert(OrderInfoDetail record);

    int insertSelective(OrderInfoDetail record);

    List<OrderInfoDetail> getShipmentsQuantityByOrderCodeList(List<String> OrderCodeList);

    OrderInfoDetail getOrderInfoDetailByImei(@Param("imei") String imei);

    OrderInfoDetail getOrderInfoDetailByOrderCode(@Param("orderCode") String orderCode);

    int updateByPrimaryKeySelective(OrderInfoDetail record);

    int updateByPrimaryKey(OrderInfoDetail record);
    
    List<OrderInfoDetail> listOrderInfoDetail(List<OrderInfoDetail> record);
    
    Page<OrderInfoDetail> pageOrderInfoDetail(OrderInfoDetail record);

    List<String> snList(String orderCode);

    OrderInfoDetail getMaxUpdateOrderInfoDetailByOrderCode(@Param("orderCode") String orderCode);
}