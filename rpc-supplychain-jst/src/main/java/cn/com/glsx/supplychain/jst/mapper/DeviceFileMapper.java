package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.DeviceFile;
import cn.com.glsx.supplychain.jst.model.DeviceSnNotInMerchantStock;

@Mapper
public interface DeviceFileMapper extends OreMapper<DeviceFile>{
	
	List<DeviceSnNotInMerchantStock> listSnNotInMerchantStock(List<String> listSn);
    
}