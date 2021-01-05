package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsProductDetail;

@Mapper
public interface BsProductDetailMapper extends OreMapper<BsProductDetail>{

    int insert(BsProductDetail record);

    int insertSelective(BsProductDetail record);  

    int updateByPrimaryKeySelective(BsProductDetail record);

    int updateByPrimaryKey(BsProductDetail record);
}