package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.BsMerchantOrderVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface BsMerchantOrderVehicleMapper extends OreMapper<BsMerchantOrderVehicle>{
    List<BsMerchantOrderVehicle> listVehicleInformation();
}