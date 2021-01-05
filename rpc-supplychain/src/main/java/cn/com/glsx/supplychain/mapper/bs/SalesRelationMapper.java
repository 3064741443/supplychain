package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.SalesRelation;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface SalesRelationMapper extends OreMapper<SalesRelation> {

    int add(SalesRelation salesRelation);
}
