package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.GhProductConfigOther;

@Mapper
public interface GhProductConfigOtherMapper extends OreMapper<GhProductConfigOther>{
   
	public List<GhProductConfigOther> selectProductConfigOthers(GhProductConfigOther record);
}