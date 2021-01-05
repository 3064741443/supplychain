package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import cn.com.glsx.supplychain.model.bs.Sales;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface SalesManagerMapper {

    Page<Sales> listSalesManager(Sales sales);

    List<Sales> select(Sales sales);

    int deleteByPrimaryKey(Long id);

    int insertList(List<Sales> salesList);

    int insertSelective(Sales record);

    Sales selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sales sales);

    int updateByPrimaryKey(Sales record);

    int updateSalesInfoByid(@Param("ids") List<Long> ids,@Param("status")Byte status);

    List<Sales> getSalesList(String orderNumber);

    Integer updateById(Sales sales);

}