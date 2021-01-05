package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.GhMerchantOrderConfig;
import cn.com.glsx.supplychain.jst.model.GhShoppingCartConfig;

@Mapper
public interface GhMerchantOrderConfigMapper extends OreMapper<GhMerchantOrderConfig>{
   
	public int insertMerchantOrderConfigList(List<GhMerchantOrderConfig> list);
	
	public List<GhMerchantOrderConfig> selectMerchantOrderConfig(GhMerchantOrderConfig record);
}