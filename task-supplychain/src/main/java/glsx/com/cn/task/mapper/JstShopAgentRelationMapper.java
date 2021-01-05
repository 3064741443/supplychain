package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.JstShopAgentRelation;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface JstShopAgentRelationMapper extends OreMapper<JstShopAgentRelation>{
    
    int insert(JstShopAgentRelation record);

    int insertSelective(JstShopAgentRelation record);

    int updateByPrimaryKeySelective(JstShopAgentRelation record);

    int updateByShopCodeAndAgentMerchantCode(JstShopAgentRelation record);

    int updateByPrimaryKey(JstShopAgentRelation record);
}