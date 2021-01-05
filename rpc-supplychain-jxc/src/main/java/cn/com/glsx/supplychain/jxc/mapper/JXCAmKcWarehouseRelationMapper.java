package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.JXCAmKcWarehouseRelationDTO;
import cn.com.glsx.supplychain.jxc.model.JXCAmKcWarehouseRelation;

@Mapper
public interface JXCAmKcWarehouseRelationMapper extends OreMapper<JXCAmKcWarehouseRelation>{
    
	Page<JXCAmKcWarehouseRelationDTO> pageKcWarehouseRelation(JXCAmKcWarehouseRelation record);
}