package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsLogistics;

import java.util.List;

@Mapper
public interface BsLogisticsMapper extends OreMapper<BsLogistics>{
    
    int insert(BsLogistics record);

    int insertSelective(BsLogistics record);

    int updateByPrimaryKeySelective(BsLogistics record);

    int updateByPrimaryKey(BsLogistics record);

    List<BsLogisticsDTO> listLogisticsByGhMerchantOrderCode(@Param(value = "ghMerchantOrderCode") String ghMerchantOrderCode);
}