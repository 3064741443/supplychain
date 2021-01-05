package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.JstShopAgentRelation;

@Mapper
public interface JstShopAgentRelationMapper extends OreMapper<JstShopAgentRelation>{
    
    int insert(JstShopAgentRelation record);

    int insertSelective(JstShopAgentRelation record);

    int updateByPrimaryKeySelective(JstShopAgentRelation record);

    int updateByShopCodeAndAgentMerchantCode(JstShopAgentRelation record);

    int updateByPrimaryKey(JstShopAgentRelation record);
}