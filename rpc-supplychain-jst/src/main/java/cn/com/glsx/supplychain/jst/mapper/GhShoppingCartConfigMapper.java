package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.GhShoppingCartConfig;

@Mapper
public interface GhShoppingCartConfigMapper extends OreMapper<GhShoppingCartConfig>{
   
	public int insertCartConfigList(List<GhShoppingCartConfig> list);
	
	public List<GhShoppingCartConfig> selectCartConfig(GhShoppingCartConfig record);
	
	public int deletCartConfig(GhShoppingCartConfig record);
	
}