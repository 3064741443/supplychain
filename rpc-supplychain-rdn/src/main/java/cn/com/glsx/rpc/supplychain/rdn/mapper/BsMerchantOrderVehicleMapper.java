package cn.com.glsx.rpc.supplychain.rdn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.BsMerchantOrderVehicle;

import java.util.List;

@Mapper
public interface BsMerchantOrderVehicleMapper extends OreMapper<BsMerchantOrderVehicle>{
    List<BsMerchantOrderVehicle> listVehicleInformation();
}