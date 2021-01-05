package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsProduct;

@Mapper
public interface BsProductMapper extends OreMapper<BsProduct>{
    
    int insert(BsProduct record);

    int insertSelective(BsProduct record);

    int updateByPrimaryKeySelective(BsProduct record);

    int updateByPrimaryKey(BsProduct record);
}