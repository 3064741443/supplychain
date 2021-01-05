package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.EcMerchantOrder;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface EcMerchantOrderMapper extends OreMapper<EcMerchantOrder> {

    List<EcMerchantOrder>exportEcMerchantOrderExit(EcMerchantOrder ecMerchantOrder);

}