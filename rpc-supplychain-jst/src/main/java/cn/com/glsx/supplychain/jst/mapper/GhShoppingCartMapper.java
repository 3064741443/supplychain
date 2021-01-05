package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jst.model.GhShoppingCart;

@Mapper
public interface GhShoppingCartMapper extends OreMapper<GhShoppingCart>{
   
	public Page<GhShoppingCart> pageGhShoppingCart(GhShoppingCart record);
	
	public Integer insertGhShoppingCartReplace(List<GhShoppingCart> lists);
}