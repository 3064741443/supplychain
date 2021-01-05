package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsProductType;

@Mapper
public interface BsProductTypeMapper extends OreMapper<BsProductType>{
   
    int insert(BsProductType record);

    int insertSelective(BsProductType record);

    int updateByPrimaryKeySelective(BsProductType record);

    int updateByPrimaryKey(BsProductType record);
}