package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.AmProductSplit;

@Mapper
public interface AmProductSplitMapper extends OreMapper<AmProductSplit>{
    
	int insert(AmProductSplit record);

    int insertSelective(AmProductSplit record);

    int updateByPrimaryKeySelective(AmProductSplit record);

    int updateByPrimaryKey(AmProductSplit record);
    
    List<AmProductSplit> selectDisProduct(@Param("record")AmProductSplit record);
}