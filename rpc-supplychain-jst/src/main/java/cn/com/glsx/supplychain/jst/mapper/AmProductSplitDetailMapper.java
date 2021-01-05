package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.AmProductSplitDetail;

@Mapper
public interface AmProductSplitDetailMapper extends OreMapper<AmProductSplitDetail>{
   
    int insert(AmProductSplitDetail record);

    int insertSelective(AmProductSplitDetail record);

    int updateByPrimaryKeySelective(AmProductSplitDetail record);

    int updateByPrimaryKey(AmProductSplitDetail record);
    
    List<AmProductSplitDetail> listProductSplitDetailByProductCode(List<String> listProductCode);
}