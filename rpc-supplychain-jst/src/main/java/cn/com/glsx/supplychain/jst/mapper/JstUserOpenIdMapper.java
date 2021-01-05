package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.JstUserOpenId;

@Mapper
public interface JstUserOpenIdMapper extends OreMapper<JstUserOpenId>{
  
    int insert(JstUserOpenId record);

    int insertSelective(JstUserOpenId record);

    int updateByPrimaryKeySelective(JstUserOpenId record);

    int updateByPrimaryKey(JstUserOpenId record);
}