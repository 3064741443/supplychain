package cn.com.glsx.supplychain.mapper.bs;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.bs.BsMerchantOrderVehicle;

import java.util.List;

@Mapper
public interface BsMerchantOrderVehicleMapper extends OreMapper<BsMerchantOrderVehicle>{
    List<BsMerchantOrderVehicle> listVehicleInformation();
}