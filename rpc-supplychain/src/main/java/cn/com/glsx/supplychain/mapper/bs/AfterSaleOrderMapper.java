package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.AfterSaleOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AfterSaleOrderMapper  extends OreMapper<AfterSaleOrder>{

    Page<AfterSaleOrder> listAfterSaleOrder(AfterSaleOrder afterSaleOrder);

    Integer updateByOrderNumber(AfterSaleOrder afterSaleOrder);

    List<AfterSaleOrder> getAfterSaleOrderListByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //查询正在进行的订单
    List<AfterSaleOrder> selectAfterSaleOrderOngoingBySn(@Param("sn") String sn);

    //查询正在进行的售后订单
    List<AfterSaleOrder> selectMaintainProductOngoingBySn(@Param("sn") String sn);

    //获取实际签收数量
    AfterSaleOrder getAfterSaleOrderSignQuantity(AfterSaleOrder afterSaleOrder);

    Integer updateById(AfterSaleOrder afterSaleOrder);
}