package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.GhMerchantOrderReflect;
import cn.com.glsx.supplychain.jst.model.GhMerchantOrderReflectDispatch;

@Mapper
public interface GhMerchantOrderReflectMapper extends OreMapper<GhMerchantOrderReflect>{
    
	List<GhMerchantOrderReflectDispatch> listReflectDispatch(List<String> listMerchantOrderCodes);
}