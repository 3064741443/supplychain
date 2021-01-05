package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.EcMerchantOrder;

import java.util.List;

@Mapper
public interface EcMerchantOrderMapper extends OreMapper<EcMerchantOrder> {

    Integer insertEcMerchantOrder(@Param("ecMerchantOrderList") List<EcMerchantOrder> ecMerchantOrderList);

    Integer add(EcMerchantOrder ecMerchantOrder);

    Integer updateByOrderNumber(EcMerchantOrder ecMerchantOrder);
}