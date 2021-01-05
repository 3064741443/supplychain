package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantOrderVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTBsMerchantOrderVehicleMapper extends OreMapper<JXCMTBsMerchantOrderVehicle>{
    List<JXCMTBsMerchantOrderVehicle> listVehicleInformation();
}