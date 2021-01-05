package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface LogisticsMapper extends OreMapper<Logistics> {
    Integer updateById(Logistics logistics);

    Integer updateByServiceCodeAndType(Logistics logistics);

    List<Logistics> getLogisticsInfoList(@Param("logistics") List<Logistics>logistics);
    
    Logistics selectByLogisticsNo(Logistics logistics);

    int insertSelective(Logistics logistics);

    List<Logistics>getLogisticsInfoListByServiceCode(Logistics logistics);

    Logistics selectByLogisticsId(Logistics logistics);

    Logistics getMaxUpdateLogisticsByserviceCode(Logistics logistics);
    
    List<Logistics> getLogisticsForSignOrder(List<String> list);
}