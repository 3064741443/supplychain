package glsx.com.cn.task.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.Logistics;

import java.util.List;

@Mapper
public interface LogisticsMapper extends OreMapper<Logistics> {
    Integer updateById(Logistics logistics);

    Integer updateByServiceCodeAndType(Logistics logistics);

    List<Logistics> getLogisticsInfoList(@Param("logistics") List<Logistics> logistics);

    List<Logistics> acceptMerchantOrder();

    Logistics getMaxUpdateLogisticsByserviceCode(Logistics logistics);
}